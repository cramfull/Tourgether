# Tourgether

**Your Personal Docent in Seoul**  
서울 관광을 위한 다국어 지원 스마트 도슨트 플랫폼

🚀 **서비스 바로가기**: [https://tourgether.site](https://tourgether.site)

---

## 🌏 프로젝트 개요

`Tourgether`는 서울을 방문한 외국인 관광객을 위한 **지도 기반 스마트 관광 플랫폼**입니다.  
서울시 공공데이터 및 한국관광공사의 오디오 가이드 API를 기반으로, **다국어 관광 정보 제공**, **대중교통 길안내**, **TTS 기반 오디오 도슨트**, **퀴즈 기능**을 통합 제공하는 **몰입형 관광 서비스**입니다.

> 2025 서울 열린데이터광장 공공데이터 활용 창업 경진대회 출품작

---

## ✨ 주요 기능

| 기능 | 설명 |
|------|------|
| 🗺 관광지 탐색 | 서울시 명소 데이터를 기반으로 지도에 핀으로 표시. 지역/언어별 검색 지원 |
| 🚏 대중교통 길안내 | 사용자의 위치에서 명소까지 ODsay API로 최적 경로 안내 (도보/버스/지하철) |
| 🎧 오디오 도슨트 | 공공 오디오 API 및 TTS를 활용한 다국어 오디오 가이드 제공 |
| ✅ 관광지 퀴즈 | 도착 후 간단한 OX 퀴즈로 정보 학습 및 방문 기록 저장 |
| 📍 위치 기반 UX | 네이버 지도 기반으로 현재 위치 반경 검색 및 관광지 시각화 |
| 🌐 다국어 지원 | 한국어, 영어, 일본어, 중국어 지원 (자동 전환) |
| 🧾 마이페이지 | 찜 목록, 방문 기록, 퀴즈 결과 조회 |
| 🔐 OAuth2 로그인 | Google, Kakao, Naver 연동 지원 |

---

## 🧑‍💻 기술 스택

### Frontend
- **React**, **TypeScript**
- **Naver Maps API**
- **i18n**
- **Web Speech API** (TTS)
- **PWA** (앱 설치 없이 브라우저에서 앱처럼 사용)

### Backend
- **Spring Boot**, **Java**, **JPA**
- **PostgreSQL + PostGIS** (공간 질의)
- **ODsay API** (길찾기 및 노선 시각화)
- **JWT + Redis** (토큰 인증 및 재발급)
- **OAuth2** (Google, Kakao, Naver)

### DevOps
- **AWS EC2**, **Docker Compose**

---

## 📌 시스템 아키텍쳐

![System Architecture](https://raw.githubusercontent.com/Tourgether/.github/main/profile/images/system-architecture.png)

---

## 🔗 활용 공공데이터

- 서울 관광명소(서울관광재단)
- 한국관광공사_관광지_오디오 가이드정보_GW(한국관광공사)
- 한국관광공사_관광사진_정보_GW(한국관광공사)

---


## 🖼 화면 구성

### 🏠 로그인 & 홈

![Login and Home](https://raw.githubusercontent.com/Tourgether/.github/main/profile/images/login-home.png)

---

### 🗺 지도 및 마이페이지

![Map and MyPage](https://raw.githubusercontent.com/Tourgether/.github/main/profile/images/map-mypage.png)

---

### 🌐 언어 설정

![Language Settings](https://raw.githubusercontent.com/Tourgether/.github/main/profile/images/language.png)

---

### 📍 관광지 상세 및 길안내

![Detail](https://raw.githubusercontent.com/Tourgether/.github/main/profile/images/detail.png)

---

### 🎧 도슨트 & 퀴즈

![Quiz](https://raw.githubusercontent.com/Tourgether/.github/main/profile/images/quiz.png)

---

