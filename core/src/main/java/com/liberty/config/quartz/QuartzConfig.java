package com.liberty.config.quartz;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author Dmytro_Kovalskyi.
 * @since 27.01.2017.
 */
@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class QuartzConfig {


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setApplicationContextSchedulerContextKey("applicationContext");
        scheduler.setConfigLocation(new ClassPathResource("quartz.properties"));
        // scheduler.setAutoStartup(false);  // to not automatically start after startup
        //scheduler.setWaitForJobsToCompleteOnShutdown(true);
        return scheduler;
    }
 

}
