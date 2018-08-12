package com.xangqun.springcloud.consumerfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
public class SpringDemoFeignController {

    @Autowired
    SpringDemoFeignService springDemoFeignService;

    @Autowired
    private Task task;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String port() throws Exception{
        Future<String> futureResult = task.run();
        String result = futureResult.get(5, TimeUnit.SECONDS);
        return springDemoFeignService.hello();
    }


}
