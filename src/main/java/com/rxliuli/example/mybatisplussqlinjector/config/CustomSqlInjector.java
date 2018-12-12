package com.rxliuli.example.mybatisplussqlinjector.config;

import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.mapper.AutoSqlInjector;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

/**
 * 自定义 sql 注入器
 * 注: 此处不能声明为 Bean, 因为回和 MybatisPlus 自己的 SqlInjector 冲突
 *
 * @author rxliuli by 2018/5/26 14:52
 */
public class CustomSqlInjector extends AutoSqlInjector {
    /**
     * 根据 id 确定数据是否存在
     */
    private static final String SQL_EXISTS_BY_ID = "select exists(select 0 from %s where id = #{id});";
    /**
     * 查询全部数据
     */
    private static final String SQL_LIST_FOR_ALL = "SELECT %s FROM %s";
    /**
     * 删除全部数据
     */
    private static final String SQL_DELETE_FOR_ALL = "delete from %s";
    /**
     * 获取指定数量的随机数据
     */
    private static final String SQL_FIND_BY_RANDOM = "select g.* from %s g join (select id from %s where rand() < (select ((#{size} / count(0)) * 10) from %s) order by rand() limit #{size}) as z on z.id = g.id;";

    @Override
    public void inject(Configuration configuration, MapperBuilderAssistant builderAssistant, Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        deleteForAll(mapperClass, modelClass, table);
        listForAll(mapperClass, modelClass, table);
        listByRandom(mapperClass, modelClass, table);
        existsById(mapperClass, modelClass, table);
    }

    public void existsById(Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        final String sql = String.format(SQL_EXISTS_BY_ID, table.getTableName());
        final SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        this.addSelectMappedStatement(mapperClass, "existsById", sqlSource, Boolean.class, table);
    }

    public void deleteForAll(Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        //执行 sql
        String sql = String.format(SQL_DELETE_FOR_ALL, table.getTableName());
        //mapper 接口方法名一致
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        this.addDeleteMappedStatement(mapperClass, "deleteForAll", sqlSource);
    }

    public void listForAll(Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        //执行 sql
        String sql = String.format(SQL_LIST_FOR_ALL, this.sqlSelectColumns(table, false), table.getTableName());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        this.addSelectMappedStatement(mapperClass, "listForAll", sqlSource, modelClass, table);
    }

    public void listByRandom(Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        final String sql = String.format(SQL_FIND_BY_RANDOM,
                table.getTableName(),
                table.getTableName(),
                table.getTableName(),
                table.getTableName()
        );
        final SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        this.addSelectMappedStatement(mapperClass, "listByRandom", sqlSource, modelClass, table);
    }
}
