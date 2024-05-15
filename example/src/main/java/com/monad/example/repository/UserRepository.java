package com.monad.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monad.example.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, IUserRepository {
}
