package com.monad.query.interfaces;

import com.monad.query.QuerydslQueryBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;

public abstract class AbstractBuilder<E extends EntityPathBase> {

    public abstract QuerydslQueryBuilder<E> getInstance();
    public abstract Predicate build() ;
}