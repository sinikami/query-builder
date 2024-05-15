package com.monad.example.repository;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.monad.example.entity.QUserEntity;
import com.monad.example.entity.UserEntity;
import com.monad.example.shared.DataJpaTestConfig;
import com.monad.query.QuerydslQueryBuilder;

@DataJpaTest
@Import(DataJpaTestConfig.class)
class UserRepositoryImplTest {

    @SpyBean
    private UserRepository userRepository;

    private final Long id = 10L;
    private final String userName = "sinikami";

    @BeforeEach
    public void init() {
        // Dummy data
        UserEntity entity = UserEntity.builder()
                                      .id(id)
                                      .userName(userName)
                                      .build();
        userRepository.save(entity);
    }

    @AfterEach
    public void destroy() {
        userRepository.deleteAll();
    }

    @Test
    void findById_Result_Exists() {

        //Act
        List<UserEntity> rst = userRepository.findByTestId(id);

        //Assertion
        Assertions.assertFalse(rst.isEmpty());
        rst.forEach(v -> {
            Assertions.assertEquals(v.getId(), id);
        });
    }

    @Test
    void findById_Result_Empty() {

        //Act
        List<UserEntity> rst = userRepository.findByTestId(1L);

        //Assertion
        Assertions.assertTrue(rst.isEmpty());
    }

    @Test
    void findAllByPredicate_AnyOf_Exists() {
        //Arrange
        QuerydslQueryBuilder<QUserEntity> query = QuerydslQueryBuilder.of(QUserEntity.userEntity);

        query.anyOf(e->e.id.eq(1L), e->e.id.eq(id));

        //Act
        List<UserEntity> rst = userRepository.findAllByPredicate(query.build());

        //Assertion
        Assertions.assertFalse(rst.isEmpty());
        rst.forEach(v -> {
            Assertions.assertEquals(v.getId(), id);
        });
    }
}