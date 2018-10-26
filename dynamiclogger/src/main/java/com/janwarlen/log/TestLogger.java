package com.janwarlen.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class TestLogger {
    private static Logger LOGGER = LoggerFactory.getLogger(TestLogger.class);

    @RequestMapping("/loggerHelloWorld")
    @ResponseBody
    public String loggerHelloWorld() {
        LOGGER.info("loggerHelloWorld" + new Date());
        return "hi";
    }

    @RequestMapping("/loggerInit")
    @ResponseBody
    public String loggerInit() {
        LogUtil.init();
        return "finished";
    }
}
