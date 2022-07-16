package cn.ncepu.alpl.api;

import lombok.Data;

/**
 * @author xufuhang
 * @date 2022/5/14-11:23
 * 缓存键通用类，用于存储和读取缓存，该类实例的toString方法作为key，可保证同样的查询条件对应同一个缓存
 */
@Data
public class CacheKey<T> {

    private T key;

    private Integer pageNum;

    private Integer pageSize;

    private Integer sortType;

}
