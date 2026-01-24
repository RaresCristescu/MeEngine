package com.app.server.utils;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import com.app.data.pagination.FilterRequest;
import com.app.data.pagination.SpecificationBuilder;

import jakarta.persistence.metamodel.SingularAttribute;

public final class PaginationSpecificationUtils {

	private PaginationSpecificationUtils() {

	}

	public static <T> Specification<T> buildSpecification(List<FilterRequest> filters,
			Map<String, SingularAttribute<T, ?>> fieldMap) {

		SpecificationBuilder<T> builder = new SpecificationBuilder<>();

		for (FilterRequest filter : filters) {

			SingularAttribute<T, ?> attribute = fieldMap.get(filter.getField());

			if (attribute == null) {
				throw new IllegalArgumentException("Filtering not allowed for field: " + filter.getField());
			}

			builder.with(attribute, filter.getOperator(), filter.getValue());
		}

		return builder.build();
	}
}
