package com.monad.query;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ObjectUtils;

import com.monad.query.dto.DefaultSearchDto;
import com.monad.query.interfaces.AbstractBuilder;
import com.monad.query.interfaces.IOperator;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class QuerydslQueryBuilder<E extends EntityPathBase> extends AbstractBuilder implements IOperator<E> {
    @Getter(AccessLevel.PUBLIC)
    private E from;
    private BooleanBuilder booleanBuilder;
    private DefaultSearchDto defaultSearchDto;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    private QuerydslQueryBuilder() {}

    public QuerydslQueryBuilder(E from) {
        this.from = from;
        booleanBuilder = new BooleanBuilder();
    }

    public static <E extends EntityPathBase> QuerydslQueryBuilder<E> of(final E entityPath) {
        return new QuerydslQueryBuilder<>(entityPath);
    }

    @Override
    public QuerydslQueryBuilder<E> getInstance() {
        return of(getFrom());
    }

    @Override
    public QuerydslQueryBuilder<E> merge(Predicate predicate) {
        Objects.requireNonNull(getBooleanBuilder());
        getBooleanBuilder().and(predicate);
        return this;
    }

    @Override
    public QuerydslQueryBuilder<E> either(Predicate predicate) {
        Objects.requireNonNull(getBooleanBuilder());
        getBooleanBuilder().or(predicate);
        return this;
    }

    @Override
    public QuerydslQueryBuilder<E> either(Function<E, Predicate> expression) {
        return null;
    }

    @Override
    public QuerydslQueryBuilder<E> with(Function<E, BooleanExpression> expression) {
        return null;
    }

    @Override
    public QuerydslQueryBuilder<E> anyOf(Function<E, Predicate>... expressions) {
        return null;
    }

    @Override
    public QuerydslQueryBuilder<E> orAllOf(Function<E, Predicate>... expressions) {
        return null;
    }

    @Override
    public QuerydslQueryBuilder<E> allOf(Function<E, Predicate>... expressions) {
        return null;
    }

    @Override
    public QuerydslQueryBuilder<E> allOf(BooleanExpression... expressions) {
        return null;
    }

    @Override
    public QuerydslQueryBuilder<E> anyOf(BooleanExpression... expressions) {
        return null;
    }

    @Override
    public QuerydslQueryBuilder<E> orAnyOf(BooleanExpression... expressions) {
        return null;
    }

    @Override
    public QuerydslQueryBuilder<E> orAllOf(BooleanExpression... expressions) {
        return null;
    }

    @Override
    public <S> QuerydslPredicate<E,S> when(S target) {
        return QuerydslPredicate.pipe(target, this);
    }

    @Override
    public <S extends DefaultSearchDto> QuerydslPredicate<E, S> when() {
        Objects.requireNonNull(getDefaultSearchDto());
        return when((S) getDefaultSearchDto());
    }

    @Override
    public <S extends DefaultSearchDto> QuerydslQueryBuilder<E> searchDto(S searchDto) {
        setDefaultSearchDto(searchDto);
        return this;
    }

    private Predicate[] toPredicateArray(Function<E, Predicate>[] expressions) {
        return null;
    }

    @Override
    public Predicate build() {
        final Predicate predicate = getBooleanBuilder().getValue();
        booleanBuilder = new BooleanBuilder();
        return predicate;
    }

    public Expression[] groupBy(String... args) {
        return null;
    }
}
