package com.example.my_market.util.specification;

import com.example.my_market.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> productGreaterOrEqualsThen(Integer minPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> productLessOrEquals(Integer maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> productGreaterOrEqualsThen(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), title);
        //String.format("%%%%s%%", title)
    }
}
