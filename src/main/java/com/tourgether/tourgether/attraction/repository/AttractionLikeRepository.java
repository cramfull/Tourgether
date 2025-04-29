package com.tourgether.tourgether.attraction.repository;

import com.tourgether.tourgether.attraction.entity.AttractionLike;
import com.tourgether.tourgether.attraction.entity.AttractionLikeId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionLikeRepository extends JpaRepository<AttractionLike, Long> {

  boolean existsById(AttractionLikeId id);

  void deleteById(AttractionLikeId id);

  List<AttractionLike> findAllByMemberId(Long memberId);
}
