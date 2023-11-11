package com.rookie.customize.async;

import cn.hutool.extra.spring.SpringUtil;
import com.rookie.domain.system.log.db.ISysOperationLogService;
import com.rookie.domain.system.log.db.SysOperationLogEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * 异步工厂（产生任务用）
 *
 * @author yayee
 */
@Slf4j
public class AsyncTaskFactory {

    private AsyncTaskFactory() {
    }

    /**
     * 操作日志记录
     *
     * @param operationLog 操作日志信息
     * @return 任务task
     */
    public static Runnable recordOperationLog(final SysOperationLogEntity operationLog) {
        return () -> {
            SpringUtil.getBean(ISysOperationLogService.class).save(operationLog);
        };
    }
}

