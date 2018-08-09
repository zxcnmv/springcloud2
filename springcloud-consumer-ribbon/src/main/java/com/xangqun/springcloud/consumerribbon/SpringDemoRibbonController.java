package com.xangqun.springcloud.consumerribbon;
import java.util.Date;

import com.xangqun.springcloud.mapper.UserMapper;
import com.xangqun.springcloud.mapper.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringDemoRibbonController {

    @Autowired
    SpringDemoRibbonService springDemoRibbonService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("hellox")
    public String hello( int index) {
        User user=new User();
        user.setId(Long.valueOf(index));
        user.setUserName("xanuqn"+index);
        user.setPassword("xanuqn"+index);
        user.setRealName("xanuqn"+index);
        user.setEmployeeId("");
        user.setEmail("");
        user.setCreatedBy("admin");
        user.setCreateTime(new Date());
        user.setLastUpdatedBy("");
        user.setLastUpdateTime(new Date());
        user.setIsDeleted((byte)0);
        userMapper.insert(user);
//        String UserName = userMapper.selectByPrimaryKey(7L).getUserName();
        return "水电费";
    }

    @RequestMapping("hello")
    public String port() {
        return springDemoRibbonService.port();
    }
}
