package com.liberty.schedule;

import com.liberty.repositories.JobHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Dmytro_Kovalskyi.
 * @since 27.01.2017.
 */
@Slf4j
//@Component
public class JobHistoryListener implements SchedulerPlugin, JobListener {

    private String name;
    private Scheduler scheduler;
   // @Autowired
    private JobHistoryRepository repository;

    public void initialize(String pname, Scheduler scheduler, ClassLoadHelper classLoadHelper) throws SchedulerException {
//        this.name = pname;
//        this.scheduler = scheduler;
//        scheduler.getListenerManager().addJobListener(this, EverythingMatcher.allJobs());
    }

    public String getName() {
        return name;
    }

    public void start() {
        // retrieve Spring application context to setup
        try {
            log.debug("Available context keys: {}", Arrays.asList(scheduler.getContext().getKeys()));
            log.info("Retrieving mongo client from context: {}", Arrays.asList(scheduler.getContext().getKeys()));
        } catch (SchedulerException e) {
            log.error("Unable to retrieve application context from quartz scheduler", e);
        }
    }

    public void shutdown() {
        // nothing to do...
    }

    public void jobToBeExecuted(JobExecutionContext context) {
        // nothing to do...
    }

    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("jobWasExecuted :: {}", context);
        if (StringUtils.isEmpty(jobException)) {
            repository.add(new HashMap<String, Object>() {{
                put("ts", new Date());
                put("name", context.getJobDetail().getKey().getName());
                put("group", context.getJobDetail().getKey().getGroup());
                put("started", context.getFireTime());
                put("runtime", context.getJobRunTime());
                put("refireCount", context.getRefireCount());
                put("result", String.valueOf(context.getResult()));
            }});
            // TODO: have explict field: hasException: true / false ?
        } else {
            repository.add(new HashMap<String, Object>() {{
                put("ts", new Date());
                put("name", context.getJobDetail().getKey().getName());
                put("group", context.getJobDetail().getKey().getGroup());
                put("started", context.getFireTime());
                put("runtime", context.getJobRunTime());
                put("refireCount", context.getRefireCount());
                put("errMsg", jobException.getMessage());
                put("jobException", jobException.getMessage());
            }});
        }
    }

    public void jobExecutionVetoed(JobExecutionContext context) {
        log.info("jobExecutionVetoed :: {}", context);
        repository.add(new HashMap<String, Object>() {{
            put("ts", new Date());
            put("name", context.getJobDetail().getKey().getName());
            put("group", context.getJobDetail().getKey().getGroup());
            put("started", context.getFireTime());
            put("runtime", context.getJobRunTime());
            put("refireCount", context.getRefireCount());
            put("veto", true);
        }});
    }

}
