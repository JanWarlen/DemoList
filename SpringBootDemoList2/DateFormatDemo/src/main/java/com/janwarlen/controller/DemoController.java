package com.janwarlen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @ClassName: DemoController
 * @author: wangjian
 * @Date: 2020/1/14 17:06
 * @Description:
 */
@Controller
public class DemoController {

    @RequestMapping(value = "now", method = RequestMethod.GET)
    @ResponseBody
    public Date now() {
        return new Date();
    }
}
