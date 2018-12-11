package com.rxliuli.example.mybatisplussqlinjector.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 注入公共字段自动填充,任选注入方式即可
 * 注: 此处不能声明为 Bean, 因为会和 MybatisPlus 自己的 MetaObjectHandler 冲突
 *
 * @author rxliuli
 */
public class CustomMetaObjectHandler extends MetaObjectHandler {

    protected final static Logger logger = LoggerFactory.getLogger(CustomMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
//        logger.info("新增的时候干点不可描述的事情");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        logger.info("更新的时候干点不可描述的事情");
    }
}
