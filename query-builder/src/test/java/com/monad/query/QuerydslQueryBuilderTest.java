package com.monad.query;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.monad.query.dto.DefaultSearchDto;
import com.monad.query.entity.QTestEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.EntityPathBase;

class QuerydslQueryBuilderTest {
    DefaultSearchDto searchDto;
    String searchField = "userName";
    String keyword = "sinikami";
    QTestEntity testEntity = QTestEntity.testEntity;

    @BeforeEach
    public void init(){

        searchDto = DefaultSearchDto.builder()
                                    .searchField(searchField)
                                    .keyword(keyword)
                                    .build();
    }
    @Test
    void merge() {

        //Act
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(testEntity.userName.eq(searchField));

        //Stubbing
        QuerydslQueryBuilder<EntityPathBase> queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act
        queryBuilder.merge(testEntity.userName.eq(searchField));

        //Assertion
        assertEquals(queryBuilder.build(), booleanBuilder.getValue());
    }

    @Test
    void either() {
        //Act
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(testEntity.userName.eq(searchField));

        //Stubbing
        QuerydslQueryBuilder<EntityPathBase> queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act
        queryBuilder.either(testEntity.userName.eq(searchField));

        //Assertion
        assertEquals(queryBuilder.build(), booleanBuilder.getValue());
    }

    @Test
    void testEither() {
    }

    @Test
    void with() {
    }

    @Test
    void anyOf() {
    }

    @Test
    void orAllOf() {
    }

    @Test
    void allOf() {
    }

    @Test
    void testAllOf() {
    }

    @Test
    void testAnyOf() {
    }

    @Test
    void orAnyOf() {
    }

    @Test
    void testOrAllOf() {
    }

    @Test
    void when_setSearchDto_QuerydslPredicate() {
        // Arrange
        final String searchField = "userName";
        final String keyword = "sinikami";
        final DefaultSearchDto searchDto = DefaultSearchDto.builder()
                                                           .searchField(searchField)
                                                           .keyword(keyword)
                                                           .build();

        QTestEntity testEntity = QTestEntity.testEntity;

        //Stubbing
        QuerydslQueryBuilder<EntityPathBase> queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act
        queryBuilder.searchDto(searchDto);

        //Act && Assertion
        assertDoesNotThrow(() -> queryBuilder.when());
        assertEquals(queryBuilder.when() instanceof QuerydslPredicate, true);
        assertTrue(queryBuilder.when().filter(ObjectUtils::isNotEmpty).exists());

    }

    @Test
    void when_withNoSearchDto_throwsException() {
        // Arrange
        final String searchField = "userName";
        final String keyword = "sinikami";
        final DefaultSearchDto searchDto = DefaultSearchDto.builder()
                                                           .searchField(searchField)
                                                           .keyword(keyword)
                                                           .build();

        QTestEntity testEntity = QTestEntity.testEntity;

        //Stubbing
        QuerydslQueryBuilder<EntityPathBase> queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act && Assertion
        assertThrows(NullPointerException.class, ()->queryBuilder.when());
    }

    @Test
    void searchDto() {
        // Arrange
        final String searchField = "userName";
        final String keyword = "sinikami";
        final DefaultSearchDto searchDto = DefaultSearchDto.builder()
                                                           .searchField(searchField)
                                                           .keyword(keyword)
                                                           .build();

        QTestEntity testEntity = QTestEntity.testEntity;

        //Stubbing
        QuerydslQueryBuilder<EntityPathBase> queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act
        queryBuilder.searchDto(searchDto);

        //Assertion
        assertDoesNotThrow(()->queryBuilder.when());
    }
}