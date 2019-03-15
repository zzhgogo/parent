package com.zhuhao.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: zhuhao
 * @Date: 2018/10/24 下午5:08
 * @Description:
 */
@Component("testJob")
public class TestJob {

    private final static Logger logger = LoggerFactory.getLogger(TestJob.class);

    public void job1() {
        logger.info("job1 start.. {}", new Date());
    }

}
