package com.tourgether.tourgether.visit.service;

import com.tourgether.tourgether.visit.dto.request.VisitCreateRequest;
import com.tourgether.tourgether.visit.dto.response.VisitResponse;

public interface VisitService {

  VisitResponse createVisit(Long memberId, VisitCreateRequest request);
}
