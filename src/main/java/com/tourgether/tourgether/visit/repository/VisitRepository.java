package com.tourgether.tourgether.visit.repository;

import com.tourgether.tourgether.visit.entity.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {

  Page<Visit> findByMemberId(Long memberId, Pageable pageable);
}
