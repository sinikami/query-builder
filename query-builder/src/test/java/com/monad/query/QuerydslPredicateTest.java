package com.monad.query;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.monad.query.dto.DefaultSearchDto;
import com.monad.query.entity.QTestEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@TestMethodOrder(OrderAnnotation.class)
class QuerydslPredicateTest {

    @Test
    @Order(1)
    void filter() {
        // Arrange
        DefaultSearchDto searchDto = DefaultSearchDto.builder().build();

        QuerydslQueryBuilder<QTestEntity> queryBuilder = QuerydslQueryBuilder.of(QTestEntity.testEntity);
        QuerydslPredicate<QTestEntity, DefaultSearchDto> pipe = QuerydslPredicate.pipe(searchDto, queryBuilder);

        // Act
       var filter = pipe.filter(v -> ObjectUtils.isNotEmpty(v.getSearchField()));

        // Assertion
        Assertions.assertFalse(filter.exists());

    }

    @Test
    @Order(2)
    void allOf() {
        // Arrange
        final String searchField = "userName";
        final DefaultSearchDto searchDto = DefaultSearchDto.builder()
                                                           .searchField(searchField)
                                                           .build();
        QuerydslQueryBuilder<QTestEntity> queryBuilder = QuerydslQueryBuilder.of(QTestEntity.testEntity);
        QuerydslPredicate<QTestEntity, DefaultSearchDto> pipe = QuerydslPredicate.pipe(searchDto, queryBuilder);

        // Act
        var allOf = pipe.allOf(v -> ObjectUtils.isNotEmpty(v.getSearchField()),v->searchField.equals(v.getSearchField()));

        // Assertion
        Assertions.assertTrue(allOf.exists());
    }

    @Test
    @Order(3)
    void anyOf() {
        // Arrange
        final String searchField = "userName";
        final String keyword = "sinikami";
        final DefaultSearchDto searchDto = DefaultSearchDto.builder()
                                                           .searchField(searchField)
                                                           .keyword(keyword)
                                                           .build();
        QuerydslQueryBuilder<QTestEntity> queryBuilder = QuerydslQueryBuilder.of(QTestEntity.testEntity);
        QuerydslPredicate<QTestEntity, DefaultSearchDto> pipe = QuerydslPredicate.pipe(searchDto, queryBuilder);

        // Act
        var anyOf = pipe.anyOf(v ->v.getSearchField().equals(keyword),v->searchField.equals(v.getSearchField()));

        // Assertion
        Assertions.assertTrue(anyOf.exists());
    }


    @Test
    @Order(4)
    void map() {
        // Arrange
        final String searchField = "userName";
        final String keyword = "sinikami";
        final DefaultSearchDto searchDto = DefaultSearchDto.builder()
                                                           .searchField(searchField)
                                                           .keyword(keyword)
                                                           .build();
        QuerydslQueryBuilder<QTestEntity> queryBuilder = QuerydslQueryBuilder.of(QTestEntity.testEntity);
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

    @Test
    @Order(5)
    public void ifPresent() {

        // Arrange
        final String searchField = "userName";
        final String keyword = "sinikami";
        final DefaultSearchDto searchDto = DefaultSearchDto.builder()
                                                           .searchField(searchField)
                                                           .keyword(keyword)
                                                           .build();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(QTestEntity.testEntity.userName.eq(searchField));
        //Stubbing
        QuerydslQueryBuilder<QTestEntity> queryBuilder =  QuerydslQueryBuilder.of(QTestEntity.testEntity);
        QuerydslPredicate<QTestEntity, DefaultSearchDto> pipe = QuerydslPredicate.pipe(searchDto, queryBuilder);

        //Act
        var anyOf = pipe.anyOf(v ->v.getSearchField().equals(keyword),v->searchField.equals(v.getSearchField()));
        anyOf.ifPresent(v->v.userName.eq("userName"));
        Predicate predicate = queryBuilder.build();

       // Assertion
        Assertions.assertEquals(predicate, booleanBuilder.getValue());
    }

    @Test
    @Order(6)
    public void ifPresent_BiFunction_test() {

        // Arrange
        final String searchField = "userName";
        final String keyword = "sinikami";
        final DefaultSearchDto searchDto = DefaultSearchDto.builder()
                                                           .searchField(searchField)
                                                           .keyword(keyword)
                                                           .build();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(QTestEntity.testEntity.userName.eq(searchField));
        //Stubbing
        QuerydslQueryBuilder<QTestEntity> queryBuilder = QuerydslQueryBuilder.of(QTestEntity.testEntity);
        QuerydslPredicate<QTestEntity, DefaultSearchDto> pipe = QuerydslPredicate.pipe(searchDto, queryBuilder);

        //Act
        var anyOf = pipe.anyOf(v ->v.getSearchField().equals(keyword),v->searchField.equals(v.getSearchField()));
        anyOf.ifPresent((t,e)->e.userName.eq(t.getSearchField()));
        Predicate predicate = queryBuilder.build();
        //Assertion
        Assertions.assertEquals(predicate, booleanBuilder.getValue());
    }
}
