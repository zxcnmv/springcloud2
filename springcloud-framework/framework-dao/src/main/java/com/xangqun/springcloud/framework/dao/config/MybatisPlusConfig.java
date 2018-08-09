package com.xangqun.springcloud.framework.dao.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

import javax.sql.DataSource;


@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {
	  /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
	@ConditionalOnMissingBean
    public DataSource dataSource(){
        return DruidDataSourceBuilder.create().build();
    }
//    @Bean
////	@ConditionalOnMissingBean
//    public DataSourceHealthIndicator dataSourceHealthIndicator(){
//        return new DataSourceHealthIndicator(dataSource(),null);
//    }
}
