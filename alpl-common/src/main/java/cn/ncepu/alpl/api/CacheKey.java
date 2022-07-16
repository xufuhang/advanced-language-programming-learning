package cn.ncepu.alpl.api;

import lombok.Data;

/**
 * @author xufuhang
 * @date 2022/5/14-11:23
 */
@Data
public class CacheKey<T> {

    private T key;

    private Integer pageNum;

    private Integer pageSize;

    private Integer sortType;

}
