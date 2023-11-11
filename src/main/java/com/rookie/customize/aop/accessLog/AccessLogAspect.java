package com.rookie.customize.aop.accessLog;

import com.rookie.customize.async.AsyncTaskFactory;
import com.rookie.domain.system.log.LogApplicationService;
import com.rookie.domain.system.log.db.SysOperationLogEntity;
import com.rookie.thread.ThreadPoolManager;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 操作日志记录处理
 *
 * @author yayee
 */
@Aspect
@Component
@Slf4j
public class AccessLogAspect {

    @Resource
    private LogApplicationService logApplicationService;

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, AccessLog controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, AccessLog controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, AccessLog accessLog, final Exception e, Object jsonResult) {
        try {
            SysOperationLogEntity entity = logApplicationService.createOperationLog(joinPoint, accessLog, e,
                jsonResult);

            // 保存数据库
            ThreadPoolManager.execute(AsyncTaskFactory.recordOperationLog(entity));
        } catch (Exception exp) {
            log.error("写入操作日式失败", exp);
        }
    }
}
