package com.monad.example.repository;

import java.util.List;

import com.monad.example.entity.UserEntity;
import com.querydsl.core.types.Predicate;

public interface IUserRepository {
    List<UserEntity> findByTestId(Long id);
    List<UserEntity> findAllByPredicate(Predicate predicate);
}
