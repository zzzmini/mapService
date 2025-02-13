package com.my.mapService.service;

import com.my.mapService.dto.Member;
import com.my.mapService.repository.MapMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceTest {
    MapMemberRepository repository = new MapMemberRepository();
    MemberService memberService = new MemberService(repository);

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    void 회원가입() {
        // Given
        Member member = new Member();
        member.setName("장원영");
        member.setAddress("서울");

        // When
        Long saveId = memberService.join(member);

        // Then
        Member findMember = memberService
                .findOne(saveId).orElse(null);
        assertThat(findMember.getName())
                .isEqualTo(member.getName());
    }

    @Test
    public void 중복_회원_검사() {
        // Given([장원영, 서울],[안유진, 제주] 입력
        Member jang = new Member();
        jang.setName("장원영");
        jang.setAddress("서울");
        memberService.join(jang);

        Member jin = new Member();
        jin.setName("안유진");
        jin.setAddress("제주");
        memberService.join(jin);

        // When - [장원영, 서울] -- 입력 시도
        Member jang2 = new Member();
        jang2.setName("장원영");
        jang2.setAddress("서울");

        Long result = memberService.join(jang2);

        // Then : 입력 후 결과가 -1 인지 확인
        assertThat(result).isEqualTo(-1L);
    }

    @Test
    public void findOne() {
        // Given
        Member member = new Member();
        member.setName("장원영");
        member.setAddress("서울");
        memberService.join(member);

        // When(1번 검색)
        Optional<Member> result = memberService.findOne(1L);

        // Then(이름이 장원영 인지 확인)
        assertThat(result.get().getName()).isEqualTo("장원영");
        assertThat(result.get().getAddress()).isEqualTo("서울");
    }

    @Test
    @DisplayName("전체 리스트 검색 테스트")
    public void findAll() {
        // Given
        // Given([장원영, 서울],[안유진, 제주] 입력
        Member jang = new Member();
        jang.setName("장원영");
        jang.setAddress("서울");
        memberService.join(jang);

        Member jin = new Member();
        jin.setName("안유진");
        jin.setAddress("제주");
        memberService.join(jin);

        // When
        List<Member> memberList = memberService.findAll();

        // Then
        assertThat(memberList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("삭제 테스트")
    void deleteById() {
        // Given
        Member jang = new Member();
        jang.setName("장원영");
        jang.setAddress("서울");
        memberService.join(jang);

        // When
        memberService.deleteById(1L);

        // Then
        int size = memberService.findAll().size();
        assertThat(size).isEqualTo(0);
    }

    @Test
    @DisplayName("수정하기 테스트")
    void update() {
        // Given
        Member jang = new Member();
        jang.setName("장원영");
        jang.setAddress("서울");
        memberService.join(jang);

        // When
        Member updateJang = new Member();
        updateJang.setId(1L);
        updateJang.setName("안유진");
        updateJang.setAddress("대전");

        // Then
        memberService.update(updateJang);
        Member result = memberService
                .findOne(1L).orElse(null);

        assertThat(result.getName()).isEqualTo("안유진");
        assertThat(result.getAddress()).isEqualTo("대전");
    }
}