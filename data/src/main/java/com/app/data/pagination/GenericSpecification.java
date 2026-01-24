package com.app.data.pagination;

import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class GenericSpecification<T> implements Specification<T> {
	private static final long serialVersionUID = -8501504730949973393L;
	private final FilterCriteria<T> criteria;

    public GenericSpecification(FilterCriteria<T> criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        Path<?> path = root.get(criteria.getAttribute());
        Object value = criteria.getValue();

        switch (criteria.getOperator()) {
            case EQ:
                return cb.equal(path, value);
            case LIKE:
                return cb.like(cb.lower(path.as(String.class)), "%" + value.toString().toLowerCase() + "%");
            case GT:
                return cb.greaterThan(path.as(Comparable.class), (Comparable) value);
            case GTE:
                return cb.greaterThanOrEqualTo(path.as(Comparable.class), (Comparable) value);
            case LT:
                return cb.lessThan(path.as(Comparable.class), (Comparable) value);
            case LTE:
                return cb.lessThanOrEqualTo(path.as(Comparable.class), (Comparable) value);
            case IN:
                return path.in((Collection<?>) value);
            default:
                return null;
        }
    }

}
