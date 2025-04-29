package com.tourgether.tourgether.attraction.service.impl;

import com.tourgether.tourgether.attraction.dto.AttractionLikeResponse;
import com.tourgether.tourgether.attraction.entity.Attraction;
import com.tourgether.tourgether.attraction.entity.AttractionLike;
import com.tourgether.tourgether.attraction.entity.AttractionLikeId;
import com.tourgether.tourgether.attraction.entity.AttractionTranslation;
import com.tourgether.tourgether.attraction.exception.AttractionNotFoundException;
import com.tourgether.tourgether.attraction.exception.AttractionTranslationNotFoundException;
import com.tourgether.tourgether.attraction.repository.AttractionLikeRepository;
import com.tourgether.tourgether.attraction.repository.AttractionRepository;
import com.tourgether.tourgether.attraction.repository.AttractionTranslationRepository;
import com.tourgether.tourgether.attraction.service.AttractionLikeService;
import com.tourgether.tourgether.member.entity.Member;
import com.tourgether.tourgether.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionLikeServiceImpl implements AttractionLikeService {

  private final AttractionLikeRepository likeRepository;
  private final MemberRepository memberRepository;
  private final AttractionRepository attractionRepository;
  private final AttractionTranslationRepository translationRepository;

  @Transactional
  @Override
  public boolean toggleLike(Long attractionId, Long memberId) {
    AttractionLikeId attractionLikeId = new AttractionLikeId(attractionId, memberId);

    if (likeRepository.existsById(attractionLikeId)) {
      likeRepository.deleteById(attractionLikeId);
      return false;
    }

    Attraction attraction = attractionRepository.findById(attractionId).orElseThrow(
        () -> new AttractionNotFoundException("존재하지 않는 관광지 ID : " + attractionId)
    );

    Member member = memberRepository.getMemberOrThrow(memberId);

    likeRepository.save(new AttractionLike(attractionLikeId, member, attraction));

    return true;
  }

  @Transactional(readOnly = true)
  @Override
  public boolean isLiked(Long attractionId, Long memberId) {
    return likeRepository.existsById(new AttractionLikeId(memberId, attractionId));
  }

  @Transactional(readOnly = true)
  @Override
  public List<AttractionLikeResponse> getMyLikedAttractions(Long memberId) {
    Member member = memberRepository.getMemberOrThrow(memberId);

    Long languageId = member.getLanguageId().getId();

    return likeRepository.findAllByMemberId(memberId).stream()
        .map(like -> {
          Attraction attraction = like.getAttraction();
          AttractionTranslation translation = translationRepository.findByAttractionIdAndLanguageId(
                  attraction.getId(), languageId
              )
              .orElseThrow(
                  () -> new AttractionTranslationNotFoundException("해당 번역 ID의 여행지 정보를 찾을 수 없습니다."));
          return AttractionLikeResponse.from(attraction, translation);
        })
        .toList();
  }
}
