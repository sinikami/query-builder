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
        var allOf = pipe.allOf(v -> ObjectUtils.isNotEmpty(v.getSearchField()),v->searchField.equals(v.getSearchField()));

        // Assertion
        Assertions.assertTrue(allOf.exists());
    }

    @Test
    void anyOf() {
        // Arrange
        final String searchField = "userName";
        final String keyword = "sinikami";
        final DefaultSearchDto searchDto = DefaultSearchDto.builder()
                                                           .searchField(searchField)
                                                           .keyword(keyword)
                                                           .build();
        QuerydslQueryBuilder<QTestEntity> queryBuilder = new QuerydslQueryBuilder(QTestEntity.testEntity);
        QuerydslPredicate<QTestEntity, DefaultSearchDto> pipe = QuerydslPredicate.pipe(searchDto, queryBuilder);

        // Act
        var anyOf = pipe.anyOf(v ->v.getSearchField().equals(keyword),v->searchField.equals(v.getSearchField()));

        // Assertion
        Assertions.assertTrue(anyOf.exists());
    }


    @Test
    void map() {
        // Arrange
        final String searchField = "userName";
        final String keyword = "sinikami";
        final DefaultSearchDto searchDto = DefaultSearchDto.builder()
                                                           .searchField(searchField)
                                                           .keyword(keyword)
                                                           .build();
        QuerydslQueryBuilder<QTestEntity> queryBuilder = new QuerydslQueryBuilder(QTestEntity.testEntity);
        QuerydslPredicate<QTestEntity, DefaultSearchDto> pipe = QuerydslPredicate.pipe(searchDto, queryBuilder);

        // Act
        var anyOf = pipe.anyOf(v ->v.getSearchField().equals(keyword),v->searchField.equals(v.getSearchField()))
                        .map(v->v.getKeyword());

        // Assertion
        Assertions.assertTrue(anyOf.exists());
        anyOf.peek().ifPresent(v-> {
            Assertions.assertEquals(v, keyword);
        });
    }
}