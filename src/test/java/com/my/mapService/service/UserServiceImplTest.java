package com.my.mapService.service;

import com.my.mapService.dto.User;
import com.my.mapService.repository.ListUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    ListUserRepository userRepository = new ListUserRepository();
    UserServiceImpl userService = new UserServiceImpl(userRepository);

    @AfterEach
    void afterEach() {
        userRepository.clearStore();
    }

    @Test
    void signIn() {
        // Given
        User kim = new User();
        kim.setUserId("aaa");
        kim.setNickName("bbb");
        kim.setPassword("ccc");


        // When
        Long result = userService.signUp(kim);

        // Then
        assertThat(result).isEqualTo(0L);
    }

    @Test
    void findById() {
        // Given
        User kim = new User();
        kim.setUserId("aaa");
        kim.setNickName("bbb");
        kim.setPassword("ccc");
        userService.signUp(kim);

        // When
        User find = userService.findById("aaa").orElse(null);

        // Then
        assertThat(find.getNickName()).isEqualTo("bbb");
    }

    @Test
    void findByIdFail() {
        // Given
        User kim = new User();
        kim.setUserId("aaa");
        kim.setNickName("bbb");
        kim.setPassword("ccc");
        userService.signUp(kim);

        // When
        Optional<User> find = userService.findById("bbb");
        System.out.println(find);

        // Then
        assertThat(find).isEqualTo(null);
    }

    @Test
    void findAll() {
        // Given
        User kim = new User();
        kim.setUserId("aaa");
        kim.setNickName("bbb");
        kim.setPassword("ccc");
        userService.signUp(kim);

        User lee = new User();
        lee.setUserId("ddd");
        lee.setNickName("eee");
        lee.setPassword("fff");
        userService.signUp(lee);

        // When
        List<User> all = userService.findAll();
        // Then
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    void deleteById() {        // Given
        User kim = new User();
        kim.setUserId("aaa");
        kim.setNickName("bbb");
        kim.setPassword("ccc");
        userService.signUp(kim);

        // When
        userService.deleteById("aaa");

        // Then
        Optional<User> result = userService.findById("aaa");
        assertThat(result).isEqualTo(null);
    }

    @Test
    void update() {
        // Given
        User kim = new User();
        kim.setUserId("aaa");
        kim.setNickName("bbb");
        kim.setPassword("ccc");
        userService.signUp(kim);

        // When
        User updateData = new User();
        updateData.setUserId("aaa");
        updateData.setNickName("updateNickName");
        updateData.setPassword("updatePassword");
        userService.update(updateData);

        // Then
        User result = userService.findById("aaa").orElse(null);
        assertThat(result.getNickName())
                .isEqualTo("updateNickName");
    }
}