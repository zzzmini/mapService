package com.my.mapService.repository;

import com.my.mapService.dto.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ListUserRepositoryTest {
    ListUserRepository userRepository = new ListUserRepository();

    @AfterEach
    void clearStore() {
        userRepository.clearStore();
    }

    @Test
    void save() {
        // Given
        User kim = new User();
        kim.setUserId("aaa");
        kim.setNickName("bbb");
        kim.setPassword("ccc");
        userRepository.save(kim);

        // When
        List<User> list = userRepository.findAll();

        // Then
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("아이디로 찾기 성공")
    void findById() {
        // Given
        User kim = new User();
        kim.setUserId("aaa");
        kim.setNickName("bbb");
        kim.setPassword("ccc");
        userRepository.save(kim);

        // When
        Optional<User> findUser = userRepository.findById("aaa");

        // Then
        assertThat(findUser.get().getNickName()).isEqualTo("bbb");
    }

    @Test
    @DisplayName("아이디로 찾기 실패")
    void findByIdFail() {
        // Given
        User kim = new User();
        kim.setUserId("aaa");
        kim.setNickName("bbb");
        kim.setPassword("ccc");
        userRepository.save(kim);

        // When
        User findUser = userRepository
                .findById("bbb").orElse(null);

        // Then
        assertThat(findUser).isEqualTo(null);
    }

    @Test
    void findAll() {
        // Given
        User kim = new User();
        kim.setUserId("aaa");
        kim.setNickName("bbb");
        kim.setPassword("ccc");
        userRepository.save(kim);


        User lee = new User();
        lee.setUserId("ddd");
        lee.setNickName("eee");
        lee.setPassword("fff");
        userRepository.save(lee);
        // When
        List<User> list = userRepository.findAll();
        // Then
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void deleteById() {
        // Given
        User kim = new User();
        kim.setUserId("aaa");
        kim.setNickName("bbb");
        kim.setPassword("ccc");
        userRepository.save(kim);
        // When
        userRepository.deleteById("aaa");

        // Then
        User result = userRepository.findById("aaa")
                .orElse(null);
        assertThat(result).isEqualTo(null);
    }

    @Test
    void updateById() {
        // Given
        User kim = new User();
        kim.setUserId("aaa");
        kim.setNickName("bbb");
        kim.setPassword("ccc");
        userRepository.save(kim);
        // When
        User updateKim = new User();
        updateKim.setUserId("aaa");
        updateKim.setNickName("zzz");
        updateKim.setPassword("xxx");

        userRepository.updateById("aaa", updateKim);
        // Then
        User find = userRepository.findById("aaa").orElse(null);
        assertThat(find).isEqualTo(updateKim);
    }
}