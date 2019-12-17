package com.cloud.springcloudweb.model;

import com.cloud.springcloudweb.repository.UserRepository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@ActiveProfiles("dev")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTest {

    @Autowired
    public UserRepository userRepository;

    @Test
    @DisplayName("사용자_생성_테스트")//not working!! why??
    public void userCreateTest() {
        User user = User.builder()
                .name("glenn_new")
                .build();

        User save = userRepository.save(user);
        User find = userRepository.findById(save.getId()).orElse(null);

        Assert.assertNotNull(find.getName());
        Assert.assertEquals(find.getName(), "glenn_new");

    }
}
