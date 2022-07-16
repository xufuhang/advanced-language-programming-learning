package cn.ncepu.alpl.api;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/*
@author xufuhang
@date 2022/3/29-17:21
*/
@Data
public class CommonPage<T> {

    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 总页数
     */
    private Integer pages;
    /**
     * 总条数
     */
    private Long total;
    /**
     * 分页数据
     */
    private List<T> list;

    /**
     * 将SpringData分页后的list转为分页信息
     */
    public static <T> CommonPage<T> genCommonPage(List<T> list) {
        PageInfo<T> pageInfo = new PageInfo<>(list);
        CommonPage<T> page = new CommonPage<>();
        page.setPageNum(pageInfo.getPageNum());
        page.setPageSize(pageInfo.getPageSize());
        page.setPages(pageInfo.getPages());
        page.setTotal(pageInfo.getTotal());
        page.setList(pageInfo.getList());
        return page;
    }

}
