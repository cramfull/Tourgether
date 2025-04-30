package com.tourgether.tourgether.visit.service;

import com.tourgether.tourgether.visit.dto.request.VisitCreateRequest;
import com.tourgether.tourgether.visit.dto.response.VisitCreateResponse;
import com.tourgether.tourgether.visit.dto.response.VisitResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VisitService {

  VisitCreateResponse createVisit(Long memberId, VisitCreateRequest request);

  Page<VisitResponse> getVisitHistory(Long memberId, Pageable pageable);
}
