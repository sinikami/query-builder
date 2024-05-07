package com.monad.query.interfaces;

import java.util.function.Function;

import com.monad.query.QuerydslPredicate;
import com.monad.query.QuerydslQueryBuilder;
import com.monad.query.dto.DefaultSearchDto;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;

public interface IOperator<E extends EntityPathBase> {

    QuerydslQueryBuilder<E> merge(Predicate predicate);

    QuerydslQueryBuilder<E> either(Predicate predicate);

    QuerydslQueryBuilder<E> either(Function<E, Predicate> expression);

    QuerydslQueryBuilder<E> with(Function<E, BooleanExpression> expression);

    QuerydslQueryBuilder<E> anyOf(Function<E, Predicate>... expressions);

    QuerydslQueryBuilder<E> orAllOf(Function<E, Predicate>... expressions);

    QuerydslQueryBuilder<E> allOf(Function<E, Predicate>... expressions);

    QuerydslQueryBuilder<E> allOf(BooleanExpression... expressions);

    QuerydslQueryBuilder<E> anyOf(BooleanExpression... expressions);

    QuerydslQueryBuilder<E> orAnyOf(BooleanExpression... expressions);

    QuerydslQueryBuilder<E> orAllOf(BooleanExpression... expressions);

    <S> QuerydslPredicate<E, S> when(S target);

    <S extends DefaultSearchDto> QuerydslPredicate<E, S> when();

    <S extends DefaultSearchDto> QuerydslQueryBuilder<E> searchDto(S searchDto);

}
