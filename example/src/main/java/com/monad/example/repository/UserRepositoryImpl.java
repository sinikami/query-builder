package com.monad.example.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.monad.example.entity.QUserEntity;
import com.monad.example.entity.UserEntity;
import com.monad.query.QuerydslQueryBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements IUserRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserEntity> findByTestId(Long id) {
        QuerydslQueryBuilder<QUserEntity> query = QuerydslQueryBuilder.of(QUserEntity.userEntity);
        query.with(e -> e.id.eq(id));

        return jpaQueryFactory.selectFrom(QUserEntity.userEntity)
                              .where(query.build())
                              .fetch();
    }

    @Override
    public List<UserEntity> findAllByPredicate(Predicate predicate) {
        return jpaQueryFactory.selectFrom(QUserEntity.userEntity).where(predicate).fetch();
    }
}
