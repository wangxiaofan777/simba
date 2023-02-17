package com.wxf.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WangMaoSong
 * @date 2023/1/12 14:54
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob()
                // 任务ID
                .withIdentity("downloadJobDetail")
                // 传参
                .usingJobData("key", "value")
                // 即使没有任务关联也不能删除该任务
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("key", "value")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ? *"))
                .build();
    }
}
