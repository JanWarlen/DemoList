package com.janwarlen.controller;

import com.janwarlen.dubbo.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName: DemoController
 * @author: wangjian
 * @Date: 2019/8/21 15:11
 * @Description:
 */
@Controller
public class DemoController {

    @Autowired
    private DubboReference dubboReference;

    @RequestMapping(value = "demo", method = RequestMethod.POST)
    public void demo() {
        dubboReference.query();
    }
}
