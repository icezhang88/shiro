package com.shiro.config;

import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;

import com.shiro.Injector.MoSqlInjector;
import com.shiro.Injector.MoSqlMetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MybatisPlusConfig.java
 * Description: mybatisplus配置文件
 *
 * @author Peng Shiquan
 * Copyright  2018-2019  创捷运维智能科技有限公司
 * All rights reserved.
 * @version: 1.0
 * Reversion:
 * 1.0 - 新建
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.shiro.dao")
public class MybatisPlusConfig {
    /**
     * Method: performanceInterceptor
     * Description: mybatis-plus SQL执行效率插件【生产环境可以关闭】
     *
     * @param
     * @return com.baomidou.mybatisplus.plugins.PerformanceInterceptor
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * Method: globalConfiguration
     * Description: 配置定義全局操作
     *
     * @param
     * @return com.baomidou.mybatisplus.entity.GlobalConfiguration
     */
    @Bean
    public GlobalConfiguration globalConfiguration() {
        GlobalConfiguration globalConfiguration = new GlobalConfiguration();
        globalConfiguration.setMetaObjectHandler(handler());
        globalConfiguration.setSqlInjector(new MoSqlInjector());
        return globalConfiguration;
    }

    @Bean
    public MoSqlMetaObjectHandler handler() {
        return new MoSqlMetaObjectHandler();
    }

    /**
     * Method: paginationInterceptor
     * Description: 分页插件，自动识别数据库类型
     * 多租户，请参考官网【插件扩展】
     *
     * @param
     * @return com.baomidou.mybatisplus.plugins.PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        return paginationInterceptor;
    }
}


