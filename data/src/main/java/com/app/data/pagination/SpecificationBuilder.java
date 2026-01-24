package com.app.data.pagination;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.app.data.enums.FilterOperator;

import jakarta.persistence.metamodel.SingularAttribute;

public class SpecificationBuilder<T> {
	 private final List<FilterCriteria<T>> criteriaList = new ArrayList<>();

	    public SpecificationBuilder<T> with(
	            SingularAttribute<T, ?> attribute,
	            FilterOperator operator,
	            Object value
	    ) {
	        if (value != null) {
	            criteriaList.add(new FilterCriteria<>(attribute, operator, value));
	        }
	        return this;
	    }

	    public Specification<T> build() {
	        if (criteriaList.isEmpty()) {
	            return Specification.where(null);
	        }

	        Specification<T> spec = new GenericSpecification<>(criteriaList.get(0));

	        for (int i = 1; i < criteriaList.size(); i++) {
	            spec = spec.and(new GenericSpecification<>(criteriaList.get(i)));
	        }

	        return spec;
	    }
}
