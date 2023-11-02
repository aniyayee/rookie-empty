package com.rookie.common.core.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import lombok.Data;

/**
 * 分页模型类
 *
 * @author yayee
 */
@Data
public class PageDTO<T> {

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 列表数据
     */
    private List<T> records;

    /**
     * 每页显示条数，默认 10
     */
    private Long size;

    /**
     * 当前页
     */
    private Long current;

    /**
     * 当前分页总页数
     */
    private Long pages;

    public PageDTO(Long total, List<T> records, Long size, Long current, Long pages) {
        this.total = total;
        this.records = records;
        this.size = size;
        this.current = current;
        this.pages = pages;
    }

    public PageDTO(Page<T> page) {
        this.total = page.getTotal();
        this.records = page.getRecords();
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.pages = page.getPages();
    }

    public PageDTO(List<T> list, Page page) {
        this.total = page.getTotal();
        this.records = list;
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.pages = page.getPages();
    }
}
