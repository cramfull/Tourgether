package com.tourgether.tourgether.member.repository;

import com.tourgether.tourgether.member.entity.Member;
import com.tourgether.tourgether.member.enums.Provider;
import com.tourgether.tourgether.member.enums.Status;
import com.tourgether.tourgether.member.exception.MemberNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);
    default Member getMemberOrThrow(Long id){
        return findById(id)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));
    }

    Optional<Member> findByIdAndStatus(Long id, Status status);
    default Member getActiveMemberByIdOrThrow(Long id) {
        return findByIdAndStatus(id, Status.ACTIVE)
            .orElseThrow(() -> new MemberNotFoundException("탈퇴한 회원입니다."));
    }

    Optional<Member> findByProviderAndProviderId(Provider provider, String providerId);
}
