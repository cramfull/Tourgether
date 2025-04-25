package com.tourgether.tourgether.visit.service.impl;

import com.tourgether.tourgether.attraction.entity.Attraction;
import com.tourgether.tourgether.attraction.repository.AttractionRepository;
import com.tourgether.tourgether.member.entity.Member;
import com.tourgether.tourgether.member.repository.MemberRepository;
import com.tourgether.tourgether.visit.dto.request.VisitCreateRequest;
import com.tourgether.tourgether.visit.dto.response.VisitResponse;
import com.tourgether.tourgether.visit.entity.Visit;
import com.tourgether.tourgether.visit.repository.VisitRepository;
import com.tourgether.tourgether.visit.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitServiceImpl implements VisitService {

  private final VisitRepository visitRepository;
  private final MemberRepository memberRepository;
  private final AttractionRepository attractionRepository;

  @Transactional
  @Override
  public VisitResponse createVisit(Long memberId, VisitCreateRequest request) {
    Member member = memberRepository.getMemberOrThrow(memberId);

    Attraction attraction = attractionRepository.findById(request.attractionId())
        .orElseThrow(
            () -> new IllegalArgumentException("존재하지 않는 관광지 ID : " + request.attractionId()));

    Visit visit = Visit.of(member, attraction);
    visitRepository.save(visit);

    return VisitResponse.from(visit);
  }
}
