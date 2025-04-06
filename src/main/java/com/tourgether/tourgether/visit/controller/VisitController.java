package com.tourgether.tourgether.visit.controller;

import com.tourgether.tourgether.auth.CustomUserDetails;
import com.tourgether.tourgether.common.dto.ApiResponse;
import com.tourgether.tourgether.visit.dto.request.VisitCreateRequest;
import com.tourgether.tourgether.visit.dto.response.VisitResponse;
import com.tourgether.tourgether.visit.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/visits")
public class VisitController {

  private final VisitService visitService;

  @PostMapping
  public ResponseEntity<ApiResponse<VisitResponse>> createVisit(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody @Valid VisitCreateRequest request) {

    VisitResponse response = visitService.createVisit(userDetails.memberId(), request);

    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
