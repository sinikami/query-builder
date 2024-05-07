package com.monad.query.interfaces;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import com.monad.query.QuerydslPredicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;

public interface IPredicate<E extends EntityPathBase, T> {


    void ifPresent(Function<E,BooleanExpression> predicate);

    void ifPresent(BiFunction<? super T, E, BooleanExpression> predicate);


    QuerydslPredicate<E, T> filter(Predicate<? super T> predicate);

    QuerydslPredicate<E, T> anyOf(Predicate<? super T>...predicate);

    QuerydslPredicate<E, T> allOf(Predicate<? super T>... predicates);

    <R> QuerydslPredicate<E, R> map(Function<? super T, ? extends R> predicate);
}
