package com.cloud.springcloudweb.domain.model;

import com.cloud.springcloudweb.repository.UserRepository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

//@Disabled("테스트 불필요")//junit4에서의 @Ignore의 역할, 테스트를 진행하지않는 이유를 적을수있다.
@DataJpaTest//JPA 테스트할수있게 도와주는 어노테이션이며, 기본은 인메모리 데이터베이스 사용
@ActiveProfiles("dev")
@DisplayName("User model에 대한 테스트")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//해당 어노테이션으로 실제 Datasource를 사용할수있게 설정
public class UserTest {

    @Autowired
    public UserRepository userRepository;

    //static으로 선언해야합니다.
    @BeforeAll
    public static void beforeTest() {
        System.out.println("-------------------beforeTest---------------------------");
    }

    //static으로 선언해야합니다.
    @AfterAll
    public static void afterTest() {
        System.out.println("-------------------afterTest---------------------------");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("-------------------beforeEach---------------------------");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("-------------------afterEach---------------------------");
    }

    @Test//org.junit.jupiter.api.Test를 사용해야함!!
    @RepeatedTest(5)
    @DisplayName("사용자_생성_테스트")//jUnit5에서는 테스트 수행결과 메소드의 이름을 직접 작성할수있다!
    public void userCreateTest() {
        User user = User.builder()
                .name("glenn")
                .build();

        User save = userRepository.save(user);
        User find = userRepository.findById(save.getId()).orElse(null);

        Assertions.assertAll(
                 () -> Assertions.assertNotNull(find)
                ,() -> Assertions.assertEquals(find.getName(), "glenn")
        );
    }
}
