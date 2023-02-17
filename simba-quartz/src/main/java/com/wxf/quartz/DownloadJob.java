package com.wxf.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDate;

/**
 * 下载任务
 *
 * @author WangMaoSong
 * @date 2023/1/12 14:51
 */
public class DownloadJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println(LocalDate.now() + " ============> 正在下载数据");

    }
}
