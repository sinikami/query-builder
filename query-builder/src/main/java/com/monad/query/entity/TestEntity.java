package com.monad.query.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "test")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity {
    @Id
    private Long id;
    private String userName;
}
