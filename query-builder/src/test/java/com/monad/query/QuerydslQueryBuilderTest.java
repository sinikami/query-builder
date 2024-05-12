package com.monad.query;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.monad.query.dto.DefaultSearchDto;
import com.monad.query.entity.QTestEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
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
        booleanBuilder.and(testEntity.userName.eq(keyword));

        //Stubbing
        QuerydslQueryBuilder<EntityPathBase> queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act
        queryBuilder.merge(testEntity.userName.eq(keyword));

        //Assertion
        assertEquals(queryBuilder.build(), booleanBuilder.getValue());
    }

    @Test
    void either() {
        //Act
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(testEntity.userName.eq(keyword));

        //Stubbing
        QuerydslQueryBuilder<EntityPathBase> queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act
        queryBuilder.either(testEntity.userName.eq(keyword));

        //Assertion
        assertEquals(queryBuilder.build(), booleanBuilder.getValue());
    }

    @Test
    void testEither() {
        //Act
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(testEntity.userName.eq(keyword));

        //Stubbing
        QuerydslQueryBuilder<QTestEntity>queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act
        queryBuilder.either(e->e.userName.eq(keyword));

        //Assertion
        assertEquals(queryBuilder.build(), booleanBuilder.getValue());
    }

    @Test
    void with() {
        //Act
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(testEntity.userName.eq(keyword));

        //Stubbing
        QuerydslQueryBuilder<QTestEntity>queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act
        queryBuilder.with(e->e.userName.eq(keyword));

        //Assertion
        assertEquals(queryBuilder.build(), booleanBuilder.getValue());
    }

    @Test
    void anyOf() {
        //Act
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.andAnyOf(testEntity.userName.isNull(),testEntity.userName.eq(keyword));

        //Stubbing
        QuerydslQueryBuilder<QTestEntity>queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act
        queryBuilder.anyOf(e -> e.userName.isNull(), e -> e.userName.eq(keyword));

        //Assertion
        assertEquals(queryBuilder.build(), booleanBuilder.getValue());
    }

    @Test
    void orAllOf() {
    }

    @Test
    void allOf() {
        //Act
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(testEntity.userName.isNotNull());
        booleanBuilder.and(testEntity.userName.eq(keyword));

        //Stubbing
        QuerydslQueryBuilder<QTestEntity>queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act
        queryBuilder.allOf(e -> e.userName.isNotNull(), e -> e.userName.eq(keyword));

        //Assertion
        assertEquals(queryBuilder.build(), booleanBuilder.getValue());
    }
    @Test
    void allOfBooleanExpressionSucceed() {
        //Act
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(testEntity.userName.isNotNull());
        booleanBuilder.and(testEntity.userName.eq(keyword));

        //Stubbing
        QuerydslQueryBuilder<QTestEntity>queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act
        queryBuilder.allOf(testEntity.userName.isNotNull(),testEntity.userName.eq(keyword));

        //Assertion
        assertEquals(queryBuilder.build(), booleanBuilder.getValue());
    }

    @Test
    void allOfFailed() {
        //Act
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(testEntity.userName.isNull());
        booleanBuilder.and(testEntity.userName.eq(keyword));

        //Stubbing
        QuerydslQueryBuilder<QTestEntity>queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act
        queryBuilder.allOf(e -> e.userName.isNotNull(), e -> e.userName.eq(keyword));

        //Assertion
        assertNotEquals(queryBuilder.build(), booleanBuilder.getValue());
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
        //Stubbing
        QuerydslQueryBuilder<EntityPathBase> queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act && Assertion
        assertThrows(NullPointerException.class, ()->queryBuilder.when());
    }

    @Test
    void searchDto() {


        //Stubbing
        QuerydslQueryBuilder<EntityPathBase> queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act
        queryBuilder.searchDto(searchDto);

        //Assertion
        assertDoesNotThrow(()->queryBuilder.when());
    }

    @Test
    void groupBy() {
        //Stubbing
        QuerydslQueryBuilder<EntityPathBase> queryBuilder = QuerydslQueryBuilder.of(testEntity);

        //Act
        Expression[] userNames = queryBuilder.groupBy("userName");

        //Assertion
        assertTrue(ArrayUtils.contains(userNames, testEntity.userName));
    }
}