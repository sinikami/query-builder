package com.monad.query;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.monad.query.dto.DefaultSearchDto;
import com.monad.query.entity.QTestEntity;

class QuerydslPredicateTest {

    @Test
    void filter() {
        // Arrange
        DefaultSearchDto searchDto = DefaultSearchDto.builder().build();

        QuerydslQueryBuilder<QTestEntity> queryBuilder = new QuerydslQueryBuilder(QTestEntity.testEntity);
        QuerydslPredicate<QTestEntity, DefaultSearchDto> pipe = QuerydslPredicate.pipe(searchDto, queryBuilder);

        // Act
       var filter = pipe.filter(v -> ObjectUtils.isNotEmpty(v.getSearchField()));

        // Assertion
        Assertions.assertFalse(filter.exists());

    }

    @Test
    void allOf() {
        // Arrange
        final String searchField = "userName";
        final DefaultSearchDto searchDto = DefaultSearchDto.builder()
                                                           .searchField(searchField)
                                                           .build();
        QuerydslQueryBuilder<QTestEntity> queryBuilder = new QuerydslQueryBuilder(QTestEntity.testEntity);
        QuerydslPredicate<QTestEntity, DefaultSearchDto> pipe = QuerydslPredicate.pipe(searchDto, queryBuilder);

        // Act
        var anyOf = pipe.allOf(v -> ObjectUtils.isNotEmpty(v.getSearchField()),v->searchField.equals(v.getSearchField()));

        // Assertion
        Assertions.assertTrue(anyOf.exists());
    }
}