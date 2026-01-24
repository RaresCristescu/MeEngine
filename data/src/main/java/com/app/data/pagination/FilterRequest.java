package com.app.data.pagination;

import com.app.data.enums.FilterOperator;

import lombok.Data;

@Data
public class FilterRequest {
	private String field;
	private FilterOperator operator;
	private Object value;
}
