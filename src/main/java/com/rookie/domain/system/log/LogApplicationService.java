package com.rookie.domain.system.log;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rookie.common.core.page.PageDTO;
import com.rookie.customize.aop.accessLog.AccessLog;
import com.rookie.customize.service.login.domain.SystemLoginUser;
import com.rookie.domain.system.log.db.ISysOperationLogService;
import com.rookie.domain.system.log.db.SysOperationLogEntity;
import com.rookie.domain.system.log.dto.OperationLogDTO;
import com.rookie.domain.system.log.query.OperationLogQuery;
import com.rookie.enums.BasicEnumUtil;
import com.rookie.enums.common.OperationStatusEnum;
import com.rookie.enums.common.RequestMethodEnum;
import com.rookie.security.SystemLoginUserHolder;
import com.rookie.utils.ServletHolderUtil;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

/**
 * @author yayee
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LogApplicationService {

    public static final int MAX_DATA_LENGTH = 512;

    private final ISysOperationLogService operationLogService;

    public PageDTO<OperationLogDTO> getOperationLogList(OperationLogQuery query) {
        Page<SysOperationLogEntity> page = operationLogService.page(query.toPage(), query.toQueryWrapper());
        List<OperationLogDTO> records = page.getRecords().stream().map(OperationLogDTO::new)
            .collect(Collectors.toList());
        return new PageDTO<>(records, page);
    }

    public void deleteOperationLog(Long operationId) {
        operationLogService.removeById(operationId);
    }

    public SysOperationLogEntity createOperationLog(JoinPoint joinPoint, AccessLog accessLog, Exception e,
        Object jsonResult) {
        SysOperationLogEntity entity = new SysOperationLogEntity();
        this.fillOperatorInfo(entity);
        this.fillRequestInfo(entity, joinPoint, accessLog, jsonResult);
        this.fillStatus(entity, e);
        this.fillAccessLogInfo(entity, accessLog);
        return entity;
    }

    public void fillOperatorInfo(SysOperationLogEntity entity) {
        SystemLoginUser loginUser = SystemLoginUserHolder.getSystemLoginUser();
        if (loginUser != null) {
            entity.setUserId(loginUser.getUserId());
            entity.setUsername(loginUser.getUsername());
        }

        entity.setOperationTime(DateUtil.date());
    }

    public void fillRequestInfo(SysOperationLogEntity entity, JoinPoint joinPoint, AccessLog accessLog,
        Object jsonResult) {
        HttpServletRequest request = ServletHolderUtil.getRequest();
        entity.setRequestUrl(request.getRequestURI());
        // 设置方法名称
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String methodFormat = StrUtil.format("{}.{}()", className, methodName);
        entity.setCalledMethod(methodFormat);
        // 设置请求方式
        RequestMethodEnum requestMethodEnum = EnumUtil.fromString(RequestMethodEnum.class, request.getMethod());
        entity.setRequestMethod(
            requestMethodEnum != null ? requestMethodEnum.getValue() : RequestMethodEnum.UNKNOWN.getValue());

        // 是否需要保存request，参数和值
        if (accessLog.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中
            recordRequestData(entity, joinPoint, request);
        }
        // 是否需要保存response，参数和值
        if (accessLog.isSaveResponseData() && jsonResult != null) {
            entity.setOperationResult(StrUtil.sub(JSONUtil.toJsonStr(jsonResult), 0, MAX_DATA_LENGTH));
        }
    }

    public void fillStatus(SysOperationLogEntity entity, Exception e) {
        if (e != null) {
            entity.setStatus(OperationStatusEnum.FAIL.getValue());
            entity.setErrorStack(StrUtil.sub(e.getMessage(), 0, MAX_DATA_LENGTH));
        } else {
            entity.setStatus(OperationStatusEnum.SUCCESS.getValue());
        }
    }

    public void fillAccessLogInfo(SysOperationLogEntity entity, AccessLog log) {
        // 设置action动作
        entity.setBusinessType(log.businessType().ordinal());
        // 设置标题
        entity.setRequestModule(log.title());
        // 设置操作人类别
        entity.setOperatorType(log.operatorType().ordinal());
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param joinPoint 方法切面
     * @param request 服务请求
     */
    private void recordRequestData(SysOperationLogEntity entity, JoinPoint joinPoint,
        HttpServletRequest request) {
        RequestMethodEnum requestMethodEnum = BasicEnumUtil.fromValue(RequestMethodEnum.class,
            entity.getRequestMethod());

        if (requestMethodEnum == RequestMethodEnum.GET || requestMethodEnum == RequestMethodEnum.POST) {
            String params = argsArrayToString(joinPoint.getArgs());
            entity.setOperationParam(StrUtil.sub(params, 0, MAX_DATA_LENGTH));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            entity.setOperationParam(StrUtil.sub(paramsMap.toString(), 0, MAX_DATA_LENGTH));
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null) {
            for (Object o : paramsArray) {
                if (o != null && !isCanNotBeParseToJson(o)) {
                    try {
                        Object jsonObj = JSONUtil.parseObj(o);
                        params.append(jsonObj).append(",");
                    } catch (Exception e) {
                        log.info("参数拼接错误", e);
                    }
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isCanNotBeParseToJson(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
            || o instanceof BindingResult;
    }
}
