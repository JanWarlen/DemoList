package com.janwarlen.controller;

import com.janwarlen.DemoModel;
import com.janwarlen.util.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: DemoController
 * @author: wangjian
 * @Date: 2020/8/27 16:34
 * @Description: 
 */
@RestController
public class DemoController {

    @RequestMapping("/demo")
    public Response demo(@RequestBody DemoModel demoModel) {
        System.out.println("111");
        return new Response(demoModel);
    }
    @RequestMapping("/hello")
    public Response hello() {
        System.out.println("111");
        return new Response("hello");
    }
}
