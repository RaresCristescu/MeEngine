package com.app.data.pagination;

import org.springframework.data.domain.Sort;

import lombok.Data;

@Data
public class SortRequest {
	private String field;
	private Sort.Direction direction = Sort.Direction.ASC;
}
