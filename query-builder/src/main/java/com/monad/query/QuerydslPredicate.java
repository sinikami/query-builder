package com.monad.query;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.monad.query.interfaces.IPredicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
public class QuerydslPredicate<E extends EntityPathBase, T> implements IPredicate<E, T> {
    private Optional<T> target;
    private QuerydslQueryBuilder<E> builder;
    private QuerydslPredicate QuerydslPredicate;

    private QuerydslPredicate() {}
    private QuerydslPredicate(T target, QuerydslQueryBuilder<E> builder) {
        this.target = Optional.ofNullable(target);
        this.builder = builder;
    }

    public static <E extends EntityPathBase, T> QuerydslPredicate<E, T> pipe(T target, QuerydslQueryBuilder<E> builder) {
        return new QuerydslPredicate<>(target, builder);
    }

    private static <E extends EntityPathBase, T> QuerydslPredicate<E, T> of(T target, QuerydslQueryBuilder<E> builder) {
        return new QuerydslPredicate<>(target, builder);
    }

    @Override
    public void ifPresent(Function<E, BooleanExpression> expression) {
        Objects.requireNonNull(expression);
        Objects.requireNonNull(getBuilder());
        if (getTarget().isPresent()) {
            BooleanExpression predicate = expression.apply(getBuilder().getFrom());
            builder.merge(predicate);
        }
    }

    @Override
    public void ifPresent(BiFunction<? super T, E, BooleanExpression> expression) {
        Objects.requireNonNull(expression);
        Objects.requireNonNull(getBuilder());
        getTarget().ifPresent(v -> {
                    BooleanExpression predicate = expression.apply(v, getBuilder().getFrom());
                    builder.merge(predicate);
                });
    }

    public Boolean exists(){
        return  getTarget().isPresent();
    }

    public Optional<T> peek(){
        return getTarget();
    }

    @Override
    public QuerydslPredicate<E, T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        Optional<T> t = getTarget().filter(predicate);
        return of(t.orElse(null), getBuilder());
    }

    @Override
    public QuerydslPredicate<E, T> anyOf(Predicate<? super T>... predicates) {
        return filter(t->Stream.of(predicates).filter(pred -> pred.test(t)).findAny().isPresent());
    }

    @Override
    public QuerydslPredicate<E, T> allOf(Predicate<? super T>... predicates) {
        return filter(t -> {
            Boolean rst = true;
            for (Predicate<? super T> predicate : predicates) {
                rst = rst && predicate.test(t);
            }
            return rst;
        });
    }

    @Override
    public <R> QuerydslPredicate<E, R> map(Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper);
        Optional<? extends R> r = getTarget().map(mapper);
        return of(r.orElse(null), getBuilder());
    }
}
