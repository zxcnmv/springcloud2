/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.consumerribbon;

import com.xangqun.springcloud.mail.JsonResult;
import com.xangqun.springcloud.mail.MailService;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author laixiangqun
 * @since 2018-8-13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailControllerTest extends BaseMockTest {

    //引入 ContiPerf 进行性能测试
    @Rule
    public ContiPerfRule contiPerfRule = new ContiPerfRule();

    @Autowired
    private MailService mailService;//注入发送邮件的各种实现方法
    @Test
    public void testIndex() throws Exception{
        JsonResult msg =new JsonResult();
        //get("/email").param("msg", msg)
        MvcResult result = this.mockMvc.perform(get("/email")).andDo(print()).andExpect(status().isOk())
                .andReturn();
        //断言 是否和预期相等
        Assert.assertEquals(msg, result.getResponse().getContentAsString());
    }

    @Test
    //10个线程 执行10次
    @PerfTest(invocations = 100,threads = 10)
    public void test() throws Exception{
        mailService.sendSimpleMail("xxx@126.com","SpringBoot Email","这是一封普通的SpringBoot测试邮件");
    }

}
