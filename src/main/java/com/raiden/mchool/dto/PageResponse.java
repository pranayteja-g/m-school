package com.raiden.mchool.dto;

import java.util.List;
import org.springframework.data.domain.Page;
import lombok.Data;

@Data
public class PageResponse<T> {

	private List<T> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    
    public PageResponse(List<T> content, Page<T> page) {
        this.content = content;
        this.pageNo = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
    }
}
