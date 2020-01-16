package com.shiro.config;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * MpGenerator.java
 * Description: mybatis plus 自动生成启动类
 * 注意事项：这里会自动生成一个web包来装controller 同时也说自动生成一个@Controller的注解
 * 把它改成@RestController
 *
 * @author Peng Shiquan
 * Copyright  2018-2019  创捷运维智能科技有限公司
 * All rights reserved.
 * @version: 1.0
 * Reversion:
 * 1.0 - 新建
 */
public class MpGenerator {
    public static void main(String[] args) {

        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //工程路径
        gc.setOutputDir("c://");
        //是否进行文件覆盖
        gc.setFileOverride(true);
        //是否支持AR
        gc.setActiveRecord(true);

        //主键策略
        gc.setIdType(IdType.AUTO);


        gc.setEnableCache(false);// XML 二级缓存

        gc.setBaseResultMap(true);// XML ResultMap

        gc.setBaseColumnList(false);// XML columList
        //作者
        gc.setAuthor("IceZhang");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！

        //设置生成的service接口的名字的首字母是否为I 例如IUserService
        // gc.setServiceName("%sService");

        gc.setMapperName("%sDao");

        // gc.setXmlName("%sDao");

        gc.setServiceName("%sService");

        gc.setServiceImplName("%sServiceImpl");

        // gc.setControllerName("%sAction");

        mpg.setGlobalConfig(gc);

        // 数据源配置

        DataSourceConfig dsc = new DataSourceConfig();

        dsc.setDbType(DbType.MYSQL);

        dsc.setDriverName("com.mysql.jdbc.Driver");

        dsc.setUsername("root");

        dsc.setPassword("qhlk@2017");

        dsc.setUrl("jdbc:mysql://49.235.230.132:3306/login?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=GMT");

        mpg.setDataSource(dsc);

        // 策略配置

        StrategyConfig strategy = new StrategyConfig();

        //strategy.setDbColumnUnderline(true);//指定表名字段名是否使用下划线

        //strategy.setCapitalMode(true);//全局大写命名

        //strategy.setTablePrefix("beautiful_");// 此处可以修改为您的表前缀

        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名映射实体生成策略

        strategy.setInclude(new String[]{"user"}); // 需要生成的表

        // strategy.setExclude(new String[]{"test"}); // 排除生成的表

        // 自定义实体父类

        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");

        // 自定义实体，公共字段

        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });

        // 自定义 mapper 父类

        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");

        // 自定义 service 父类

        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");

        // 自定义 service 实现类父类

        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");

        // 自定义 controller 父类

        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");

        // 【实体】是否生成字段常量（默认 false）

        // public static final String ID = "test_id";

        // strategy.setEntityColumnConstant(true);

        // 【实体】是否为构建者模型（默认 false）

        // public User setName(String name) {this.name = name; return this;}

        // strategy.setEntityBuliderModel(true);

        mpg.setStrategy(strategy);

        // 包配置

        PackageConfig pc = new PackageConfig();

        pc.setParent("com");

        pc.setModuleName("shiro");

        pc.setXml("mapper");

        pc.setMapper("dao");

        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值

        InjectionConfig cfg = new InjectionConfig() {

            @Override

            public void initMap() {

                Map<String, Object> map = new HashMap<String, Object>();

                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");

                this.setMap(map);

            }

        };

        mpg.setCfg(cfg);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/template 下面内容修改，

        // 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称

        // TemplateConfig tc = new TemplateConfig();

        // tc.setController("...");

        // tc.setEntity("...");

        // tc.setMapper("...");

        // tc.setXml("...");

        // tc.setService("...");

        // tc.setServiceImpl("...");

        // mpg.setTemplate(tc);

        // 执行生成

        mpg.execute();

        // 打印注入设置

        System.err.println(mpg.getCfg().getMap().get("abc"));

    }

}


