package cl.dev.mmatush.moviesapp.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class RestResponsePage<T> {

    private List<T> content;
    private int page;
    private int size;
    private int elements;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public RestResponsePage(Page<T> page) {
        this.content = page.getContent();
        this.page = page.getPageable().getPageNumber();
        this.size = page.getPageable().getPageSize();
        this.elements = content.size();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
    }

}
