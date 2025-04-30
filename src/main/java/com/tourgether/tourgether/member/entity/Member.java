package com.tourgether.tourgether.member.entity;

import com.tourgether.tourgether.attraction.entity.AttractionLike;
import com.tourgether.tourgether.language.entity.Language;
import com.tourgether.tourgether.member.dto.response.MemberInfoResponse;
import com.tourgether.tourgether.member.enums.Provider;
import com.tourgether.tourgether.member.enums.Status;
import com.tourgether.tourgether.visit.entity.Visit;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "social_members")
//TODO (provider, providerId) Unique key
public class Member {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "provider", nullable = false)
  @Enumerated(EnumType.STRING)
  private Provider provider;

  @Column(name = "provider_id", nullable = false)
  private String providerId;

  @Column(name = "nickname", nullable = false, unique = true)
  private String nickname;

  @Column(name = "profile_image", nullable = false)
  private String profileImage;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToOne
  @JoinColumn(name = "language_id", nullable = false)
  private Language languageId;

  @OneToMany(mappedBy = "member")
  private List<Visit> visitList = new ArrayList<>();

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AttractionLike> attractionLikes = new ArrayList<>();

  public void withdraw() {
    this.status = Status.WITHDRAW;
    this.updatedAt = LocalDateTime.now();
  }

  public void updateLanguage(Language language) {
    this.languageId = language;
    this.updatedAt = LocalDateTime.now();
  }

  public void updateNickname(String nickname) {
    this.nickname = nickname;
    this.updatedAt = LocalDateTime.now();
  }

  @Builder
  private Member(Provider provider, String providerId, String nickname, String profileImage,
      Language languageId) {
    this.provider = provider;
    this.providerId = providerId;
    this.nickname = nickname;
    this.profileImage = profileImage;
    this.createdAt = LocalDateTime.now();
    this.status = Status.ACTIVE;
    this.languageId = languageId;
  }

}
