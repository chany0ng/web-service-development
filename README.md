# 🚴‍♂️DB 프로젝트 (따릉이 관리 시스템)
<p align="center">
  <img src="https://github.com/minig0lem/web-service-development/assets/92259521/92cec902-04f5-4f06-a0e9-b1600d71a5fe">
</p>
<p align="center">
  🔺 로그인 페이지
</p>
<br>
<p align="center">
  <img src="https://github.com/minig0lem/web-service-development/assets/92259521/0bea1954-8fa5-43f9-b084-6c6c5cc4b856">
</p>
<p align="center">
  🔺 메인 페이지
</p>
<br>
<p align="center">
  <img src="https://github.com/minig0lem/web-service-development/assets/92259521/ad308487-8a60-4238-abe0-13b883240e71">
</p>
<p align="center">
  🔺 대여소 조회 페이지
</p>

## 🧑‍🤝‍🧑팀원
### Frontend
- 박찬용 [(@chany0ng)](https://github.com/chany0ng)
### Backend
- 이동익 [(@Dongick)](https://github.com/Dongick)
- 정희성 [(@minig0lem)](https://github.com/minig0lem)

## ℹ 프로젝트 소개
- 서울시 공공 자전거인 '따릉이'를 관리하는 시스템을 개발한 프로젝트입니다.
- 대여소 정보 공공 데이터와 네이버 지도 API를 활용해 실제 대여소 정보를 기반으로 대여, 반납을 할 수 있습니다.
- 기존 따릉이 홈페이지에 존재하지 않는 대여소 즐겨찾기, 사용자 별 따릉이 이용 랭킹 조회, 자유게시판 등의 기능을 추가했습니다.
- 관리자는 대여소 추가/삭제, 고장/신고 관리, 자전거 관리, 사용자 관리 등을 할 수 있습니다.

## 📄 시작 가이드
### Frontend
```
$ cd frontend/project
$ npm install
$ npm start
```
### Backend
1. build.gradle에서 dependencies 설치
2. applcation.yml 파일에서 사용자 정의 값으로 변경
3. backend bootRun 실행

## 🖥 개발 환경 및 기술 스택
### Environment
![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-007ACC?style=for-the-badge&logo=Visual%20Studio%20Code&logoColor=white)
![Intellij IDEA](https://img.shields.io/badge/Intellij%20IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white)
### Development
#### Frontend
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=Javascript&logoColor=white)
![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=white)
#### Backend
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

## ✏ 주요 기능
### 사용자
#### 내 정보 관리
- 개인정보 수정
- 대여소 즐겨찾기 관리
- 금액 충전, 추가 요금 결제
- 대여/반납 이력 조회, 대여 랭킹 조회
#### 대여소 조회
- 네이버 지도 API를 통해 대여소 위치 조회
- 따릉이 대여/반납
#### 이용권 구매
- 이용권 구매
- 이용권 선물
#### 공지/문의
- 공지사항 조회
- 따릉이 고장 신고
#### 게시판
- 자유게시판 글 조회/작성
  
### 관리자
#### 회원 관리
- 회원 정보 조회
#### 따릉이 관리
- 따릉이 정보 조회/추가/삭제
- 대여소 개설/폐쇄
#### 공지/신고 관리
- 공지사항 작성
- 고장 신고 수리

