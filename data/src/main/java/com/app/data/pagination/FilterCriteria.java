package com.app.data.pagination;

import com.app.data.enums.FilterOperator;

import jakarta.persistence.metamodel.SingularAttribute;
import lombok.Data;

@Data
public class FilterCriteria<T> {
	private final SingularAttribute<T, ?> attribute;
	private final FilterOperator operator;
	private final Object value;

	public FilterCriteria(SingularAttribute<T, ?> attribute, FilterOperator operator, Object value) {
		this.attribute = attribute;
		this.operator = operator;
		this.value = value;
	}
}
