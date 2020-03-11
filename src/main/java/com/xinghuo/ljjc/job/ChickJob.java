package com.xinghuo.ljjc.job;

import com.xinghuo.ljjc.service.ExcelHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName ChickJob  定时检测链接定时任务
 * @Author sussen
 * @Version 1.0
 * @Data 2020/3/2
 */
@Component
public class ChickJob {

    @Autowired
    private ExcelHandlerService handlerService;

    //@Scheduled(fixedDelay = 30 * 60000)
    @Scheduled(cron = "0 0 12 ? * MON")
    public void readExcel() {
        handlerService.readExcel();
    }

}
