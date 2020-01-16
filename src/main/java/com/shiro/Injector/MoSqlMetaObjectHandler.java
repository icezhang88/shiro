package com.shiro.Injector;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

public class MoSqlMetaObjectHandler extends MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        Object testType = getFieldValByName("name", metaObject);
        System.out.println("name=" + testType);
        if (testType == null) {
            setFieldValByName("name", "ice", metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
