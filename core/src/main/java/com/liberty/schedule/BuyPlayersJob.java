package com.liberty.schedule;

import com.liberty.services.BuyPlayerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class BuyPlayersJob extends QuartzJobBean {

    @Autowired
    private BuyPlayerService buyPlayerService;
    
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        buyPlayerService.runBuyProcedure();
        log.info("CheckPlayerJob, executed");
    }
}
