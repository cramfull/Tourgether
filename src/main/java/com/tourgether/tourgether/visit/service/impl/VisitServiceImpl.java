package com.tourgether.tourgether.visit.service.impl;

import com.tourgether.tourgether.attraction.entity.Attraction;
import com.tourgether.tourgether.attraction.entity.AttractionTranslation;
import com.tourgether.tourgether.attraction.exception.AttractionNotFoundException;
import com.tourgether.tourgether.attraction.exception.AttractionTranslationNotFoundException;
import com.tourgether.tourgether.attraction.repository.AttractionRepository;
import com.tourgether.tourgether.attraction.repository.AttractionTranslationRepository;
import com.tourgether.tourgether.member.entity.Member;
import com.tourgether.tourgether.member.repository.MemberRepository;
import com.tourgether.tourgether.visit.dto.request.VisitCreateRequest;
import com.tourgether.tourgether.visit.dto.response.VisitCreateResponse;
import com.tourgether.tourgether.visit.dto.response.VisitResponse;
import com.tourgether.tourgether.visit.entity.Visit;
import com.tourgether.tourgether.visit.repository.VisitRepository;
import com.tourgether.tourgether.visit.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitServiceImpl implements VisitService {

  private final VisitRepository visitRepository;
  private final MemberRepository memberRepository;
  private final AttractionRepository attractionRepository;
  private final AttractionTranslationRepository attractionTranslationRepository;

  @Transactional
  @Override
  public VisitCreateResponse createVisit(Long memberId, VisitCreateRequest request) {
    Member member = memberRepository.getMemberOrThrow(memberId);

    Attraction attraction = attractionRepository.findById(request.attractionId())
        .orElseThrow(
            () -> new AttractionNotFoundException("존재하지 않는 관광지 ID : " + request.attractionId()));

    Visit visit = Visit.of(member, attraction);
    visitRepository.save(visit);

    return VisitCreateResponse.from(visit);
  }

  @Override
  public Page<VisitResponse> getVisitHistory(Long memberId, Pageable pageable) {
    return visitRepository.findByMemberId(memberId, pageable)
        .map(visit -> {
          AttractionTranslation translation = attractionTranslationRepository
              .findByAttractionIdAndLanguageId(
                  visit.getAttraction().getId(),
                  visit.getMember().getLanguageId().getId()
              )
              .orElseThrow(() -> new AttractionTranslationNotFoundException("번역 정보가 없습니다."));
          return VisitResponse.from(visit, translation);
        });
  }
}
