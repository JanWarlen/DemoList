package com.janwarlen.schedule;

import com.janwarlen.redis.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;

@Component
public class LockSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(LockSchedule.class);

    @Autowired
    private RedisLock redisLock;

    @Scheduled(cron = "0/5 * * * * *")
    public void testRedisLock() throws InterruptedException {
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator("0/30 * * * * *");
        Date next = cronSequenceGenerator.next(new Date());
        String nextDate = String.valueOf(next.getTime() - 100);
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        if (redisLock.lock("testRedisLock", nextDate)) {
            System.out.println("--------------------------" + runtimeMXBean.getName() + "----------------------------------");
//            Thread.sleep(30 * 1000);
//            redisLock.unlock("testRedisLock", nextDate);
        } else {
            System.out.println("-------------------------locked:" + runtimeMXBean.getName() + "----------------------------");
        }

    }
}
