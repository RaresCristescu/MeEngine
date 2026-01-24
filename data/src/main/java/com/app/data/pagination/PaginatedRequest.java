package com.app.data.pagination;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Data;

@Data
public class PaginatedRequest implements Serializable{
	private static final long serialVersionUID = -5341075649328257991L;
	
	private int page = 0;
    private int size = 10;
    private List<SortRequest> sort = List.of();
    private List<FilterRequest> filters = List.of();

    public Pageable toPageable() {
        Sort s = sort.isEmpty()
                ? Sort.unsorted()
                : Sort.by(
                    sort.stream()
                        .map(sr -> new Sort.Order(
                                sr.getDirection(),
                                sr.getField()
                        ))
                        .toList()
                );

        return PageRequest.of(page, size, s);
    }

}
