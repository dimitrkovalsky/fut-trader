package com.liberty.config.quartz;

import com.liberty.schedule.BuyPlayersJob;
import com.liberty.schedule.UpdatePlayerPriceJob;
import org.joda.time.DateTime;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author Dmytro_Kovalskyi.
 * @since 27.01.2017.
 */
@Configuration
public class JobConfig {
    public static final String USER_JOBS = "user_jobs";
    public static final String CHECK_PLAYER_TRIGGER = "check_player_trg";
    public static final String UPDATE_PLAYER_PRICE_TRIGGER = "update_player_price_trg";
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @PostConstruct
    private void initialize() throws Exception {
        schedulerFactoryBean.getScheduler().addJob(checkPlayerJob(), true, true);
        if (!schedulerFactoryBean.getScheduler().checkExists(new TriggerKey(CHECK_PLAYER_TRIGGER, USER_JOBS))) {
            schedulerFactoryBean.getScheduler().scheduleJob(checkPlayerTrigger());
        }

        schedulerFactoryBean.getScheduler().addJob(jobDetailMyJobTwo(), true, true);
        if (!schedulerFactoryBean.getScheduler().checkExists(new TriggerKey(UPDATE_PLAYER_PRICE_TRIGGER, USER_JOBS))) {
            schedulerFactoryBean.getScheduler().scheduleJob(cronTriggerMyJobTwo());
        }
    }

    private static JobDetail checkPlayerJob() {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setKey(new JobKey("check_player", USER_JOBS));
        jobDetail.setJobClass(BuyPlayersJob.class);
        jobDetail.setDurability(true);
        return jobDetail;
    }

    private static Trigger checkPlayerTrigger() {
        return newTrigger()
                .forJob(checkPlayerJob())
                .withIdentity(CHECK_PLAYER_TRIGGER, USER_JOBS)
                .withPriority(50)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(120))
                .startAt(DateTime.now().plusSeconds(3).toDate())
                .build();
    }


    private static JobDetail jobDetailMyJobTwo() {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setKey(new JobKey("update_player_price", USER_JOBS));
        jobDetail.setJobClass(UpdatePlayerPriceJob.class);
        jobDetail.setDurability(true);
        JobDataMap map = new JobDataMap();
        map.put("name", "HaHa");
        map.put(UpdatePlayerPriceJob.COUNT, 1);
        jobDetail.setJobDataMap(map);
        return jobDetail;
    }

    private static Trigger cronTriggerMyJobTwo() {
        return newTrigger()
                .forJob(jobDetailMyJobTwo())
                .withIdentity(UPDATE_PLAYER_PRICE_TRIGGER, USER_JOBS)
                .withPriority(100)
                // Job is scheduled for every 1 minute
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(20)
                        .withRepeatCount(5))
                .startAt(DateTime.now().plusSeconds(3).toDate())
                .build();
    }

}
