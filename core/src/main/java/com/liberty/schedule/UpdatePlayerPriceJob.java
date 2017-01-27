package com.liberty.schedule;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Dmytro_Kovalskyi.
 * @since 27.01.2017.
 */
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class UpdatePlayerPriceJob extends QuartzJobBean {

    public static final String COUNT = "count";
    public static final String LAST_EXECUTION = "LAST_EXECUTION";

    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
        JobDataMap dataMap = ctx.getJobDetail().getJobDataMap();
        int cnt = dataMap.getInt(COUNT);
        JobKey jobKey = ctx.getJobDetail().getKey();
        log.info("{}: times executed {}", jobKey, cnt);
        cnt++;
        dataMap.put(COUNT, cnt);
        dataMap.put(LAST_EXECUTION, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        ctx.setResult("ok");
    }

}