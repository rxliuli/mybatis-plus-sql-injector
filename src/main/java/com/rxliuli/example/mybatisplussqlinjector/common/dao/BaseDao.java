package com.rxliuli.example.mybatisplussqlinjector.common.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author rxliuli
 */
public interface BaseDao<T extends Serializable> extends BaseMapper<T> {
    /**
     * 查询当前表的全部数据(仅用于测试)
     *
     * @return 当前表的全部数据
     */
    List<T> listForAll();

    /**
     * 删除表的全部数据
     *
     * @return 受影响行数
     */
    Integer deleteForAll();

    /**
     * 随机查询出一条数据
     *
     * @return 随机的一条数据
     */
    default T findByRandom() {
        final List<T> list = listByRandom(1);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 随机查询出指定数量的数据
     *
     * @param size 指定数据条数
     * @return 随机的数据列表
     */
    List<T> listByRandom(@Param("size") Integer size);

    /**
     * 根据 id 查询数据是否存在
     *
     * @param id 数据 id
     * @return 数据是否存在
     */
    Boolean existsById(@Param("id") Long id);
}
