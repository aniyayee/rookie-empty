package com.rookie.domain.system.log.dto;

import com.rookie.domain.system.log.db.SysOperationLogEntity;
import com.rookie.enums.BasicEnumUtil;
import com.rookie.enums.common.BusinessTypeEnum;
import com.rookie.enums.common.OperationStatusEnum;
import com.rookie.enums.common.OperatorTypeEnum;
import com.rookie.enums.common.RequestMethodEnum;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 * @author yayee
 */
@Data
public class OperationLogDTO {

    public OperationLogDTO(SysOperationLogEntity entity) {
        if (entity != null) {
            this.operationId = entity.getOperationId();
            this.businessType = entity.getBusinessType();
            this.requestModule = entity.getRequestModule();
            this.requestUrl = entity.getRequestUrl();
            this.calledMethod = entity.getCalledMethod();
            this.operatorType = entity.getOperatorType();
            this.userId = entity.getUserId();
            this.username = entity.getUsername();
            this.operationParam = entity.getOperationParam();
            this.operationResult = entity.getOperationResult();
            this.status = entity.getStatus();
            this.errorStack = entity.getErrorStack();
            this.operationTime = entity.getOperationTime();
            this.businessTypeStr = BasicEnumUtil.getDescriptionByValue(BusinessTypeEnum.class,
                entity.getBusinessType());
            this.requestMethod = BasicEnumUtil.getDescriptionByValue(RequestMethodEnum.class,
                entity.getRequestMethod());
            this.operatorTypeStr = BasicEnumUtil.getDescriptionByValue(OperatorTypeEnum.class,
                entity.getOperatorType());
            this.statusStr = BasicEnumUtil.getDescriptionByValue(OperationStatusEnum.class, entity.getStatus());
        }

    }

    @ApiModelProperty("日志ID")
    private Long operationId;

    @ApiModelProperty("业务类型（0其它 1新增 2修改 3删除）")
    private Integer businessType;

    @ApiModelProperty("请求模块")
    private String requestModule;

    @ApiModelProperty("请求URL")
    private String requestUrl;

    @ApiModelProperty("调用方法")
    private String calledMethod;

    @ApiModelProperty("操作类别（0其它 1后台用户 2手机端用户）")
    private Integer operatorType;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("操作人员")
    private String username;

    @ApiModelProperty("请求参数")
    private String operationParam;

    @ApiModelProperty("返回参数")
    private String operationResult;

    @ApiModelProperty("操作状态（1正常 0异常）")
    private Integer status;

    @ApiModelProperty("错误消息")
    private String errorStack;

    @ApiModelProperty("操作时间")
    private Date operationTime;

    private String businessTypeStr;
    private String requestMethod;
    private String operatorTypeStr;
    private String statusStr;
}
