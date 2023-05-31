# KoreanApp
2023.04 ~ KStyles의 외국인들이 쉽게 한국어를 배울 수 있도록 학습 환경을 제공하고 관리해주는 어플리케이션을 제작하는 프로젝트를 진행한다.

### 2023-04-21 (금)
- login activity 제작완료

### 2023-04-22 (토)
- register 로직 제작 완료

### 2023-04-25 (화)
- main UI 초안 구성 완료

### 2023-04-26 (수)
- 메인화면 구현
- 프레그먼트 구현

### 2023-04-28 (금)
- auto login 구현
- practice ui 구현

### 2023-04-29 (토)
- setting page ui 구성

### 2023-04-30 (일)
- progress page ui 구성

### 2023-05-02 (화)
- practice page 구현
- random 함수를 이용하여 4개 중 하나는 정답으로 배치 시키고 나머지는 string.xml에서 다양한 값을 가져와 나머지 부분에 배치시키는 로직 추가

### 2023-05-03 (수)
- practice page 구현 완료
- seekbar 포지션 구현 완료

### 2023-05-04 (목)
- progress page 구현 중
- word 와 완료한 단원에 대한 구현 완료

### 2023-05-06 (토)
- progress page의 그래프 구현 완료

### 2023-05-07 (일)
- progress 페이지 코드 리팩토링

### 2023-05-09 (화)
- progress 리사이클러뷰 예시로 구현 완료

### 2023-05-10 (수)
- progress 리사이클러 뷰 구현 완료
- settings page 의 비밀번호 변경 로직 구현 완료

### 2023-05-11 (목)
- 로그인 기능에 오류 발생으로 인한 수정 및 검토

---
파리에서 열리는 Korean Expo 참여

### 2023-05-22 (월)
- 베트남어 언어 번역 일부 진행
- 오토로그인 기능 수정중...

### 2023-05-23 (화)
- 프랑스어 번역본 추가
- 토글 버튼으로 사운드 제어 기능 추가
- 토글 버튼으로 알림 서비스 제어 기능 추가
- spinner 기본 설정 변경
- 사용시간 저장이 다른 날짜에 저장되는 이슈 발생, 수정완
- auto login 기능에 firebase SDK를 사용할 수 없어 로직 변경
- 메인페이지 UI 일부 수정

### 2023-05-24 (수)
- Practice page binary index 발생, 수정완
- 메인 UI 동적으로 수정
- docs : 오타수정
- docs : 토글버튼 디자인 추가
- auto login 기능 완성
- setting : audio control 로직 추가
- setting : notifications control 로직 추가 

### 2023-05-25 (목)
- Progress : 날짜 순서가 잘못되어 수정
- docs : level 2 문제 추가
- Progress : data.compareTo() 부분에서 무한 재귀 발생, 수정완
- Practice : 오류 수정했던 부분 rollback
- fix : level 2 테스트 중 문제를 끝내도 뷰에서 체크로 바뀌지 않는 현상 발생, 수정완
- fix : level 2 테스트 중 quiz completed 값 증가하지 않는 이슈 발생, 수정완
- fix : ProgressRecycler Revise 상태에서 Name & Image 로드 오류 발생, 수정완

### 2023-05-26 (금)
- 비밀번호 변경을 위한 UI 추가
- Login page : 비밀번호 변경 로직 완성
- Ex2 프레임 제거
- 앱 로고 적용
- 상단 상태 바 색상 검정색으로 변경
- Practice page : CardView 사용해서 모서리 조금 둥글게 사용함
- level 3 & 4 추가
- 버튼 디자인 fix
- 버튼 정답 유무 색상 변경 구문 추가

### 2023-05-29 (월)

- docs: 토글버튼 디자인 추가
- feat: firebase storage 에 유저 프로필 저장 성공   
- docs: Ui 재수정   
- feat: 저장소에 올리기 위한 메서드 추가   
- chore: database Storage 를 사용하기 위한 의존성 추가   
- docs: profile 설정 추가   
- feat: 이미지 설정 기능 추가   
- chore: 갤러리 접근 권한 추가   
- docs: imageView 백그라운드 뷰 추가   
- docs: level 5 & 6 추가   

### 2023-05-30 (화)
- docs: edit profile docs
- docs: UI fix
- feat: 회원가입 완료 후 처리 로직 추가
- docs: UI 폰트 & 이미지 뷰 사이즈 수정
- feat: User Profile 설정 기능 완료
- fix: 배경색 회색으로 fix
- feat: User Profile 업로드하고 shared에 저장
- feat: Get User Profile 메서드 분리
- docs: 디자인 수정

### 2023-05-31 (수)
fix: update User Profile 오류 fix
docs: setting page UI 수정 완
refactor: auto login view refactor
docs: login UI 수정
feat: 권한 추가 기능 구현
docs: 권한 추가
feat: Delete And Update User Profile
