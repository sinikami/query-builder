package com.monad.query;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.monad.query.dto.DefaultSearchDto;
import com.monad.query.entity.QTestEntity;
import com.querydsl.core.types.dsl.EntityPathBase;

class QuerydslQueryBuilderTest {

    @Test
    void merge() {
    }

    @Test
    void either() {
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
        assertDoesNotThrow(()->queryBuilder.when());
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