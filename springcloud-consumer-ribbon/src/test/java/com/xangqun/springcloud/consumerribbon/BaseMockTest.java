/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.consumerribbon;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author laixiangqun
 * @since 2018-8-13
 */
public abstract class BaseMockTest {
    @Autowired
    private WebApplicationContext wc;

    protected MockMvc mockMvc;

    @Before
    public void beforeSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wc).build();
    }

}
