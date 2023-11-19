package com.rookie.controller.system;

import com.rookie.common.constants.RoleKeyConstants;
import com.rookie.common.core.base.BaseController;
import com.rookie.common.core.dto.ResponseDTO;
import com.rookie.common.core.page.PageDTO;
import com.rookie.customize.aop.accessLog.AccessLog;
import com.rookie.customize.aop.permission.CheckRole;
import com.rookie.domain.system.log.LogApplicationService;
import com.rookie.domain.system.log.dto.OperationLogDTO;
import com.rookie.domain.system.log.query.OperationLogQuery;
import com.rookie.enums.common.BusinessTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 操作日志记录 前端控制器
 * </p>
 *
 * @author yayee
 */
@Api(value = "OperationLog Interfaces", tags = "OperationLog Interfaces")
@RestController
@RequestMapping("/system/logs")
@Validated
@RequiredArgsConstructor
public class SysOperationLogController extends BaseController {

    private final LogApplicationService logApplicationService;

    @ApiOperation("Delete operationLog")
    @CheckRole(value = {RoleKeyConstants.ADMIN})
    @AccessLog(title = "操作日志管理", businessType = BusinessTypeEnum.DELETE)
    @PostMapping("/delete/{operationId}")
    public ResponseDTO<Void> removeOperationLogs(@PathVariable Long operationId) {
        logApplicationService.deleteOperationLog(operationId);
        return ResponseDTO.ok();
    }

    @ApiOperation("Query operationLog List")
    @GetMapping("/list")
    public ResponseDTO<PageDTO<OperationLogDTO>> operationLogs(OperationLogQuery query) {
        PageDTO<OperationLogDTO> pageDTO = logApplicationService.getOperationLogList(query);
        return ResponseDTO.ok(pageDTO);
    }
}
