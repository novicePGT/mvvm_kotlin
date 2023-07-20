# KoreanAppCancel changes
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
- fix: update User Profile 오류 fix
- docs: setting page UI 수정 완
- refactor: auto login view refactor
- docs: login UI 수정
- feat: 권한 추가 기능 구현
- docs: 권한 추가
- feat: Delete And Update User Profile

### 2023-06-01 (목)
- [feat]: add character action
- [feat]: add character action
- [refactor]: delete update pass
- [docs]: add action file
- [docs]: add lottie event
- [chore]: add lottie repository
- [docs]: 번역본 추가
- [fix]: Exception 처리
- [docs]: 화면 밖으로 나가는 현상 변경
- [docs]: 화면 밖으로 나가는 현상 변경
- [delete]: Delete seekbar action
- [docs]: UI 비율 맞게 수정
- [docs]: 겉면 리니어로 변경
- [docs]: seekbar -> progressbar

### 2023-06-02 (금)
- docs: 비밀번호 검증 조건 삽입
- chore: Change main color
- refator
- feat: 정규식으로 비밀번호 검증
- feat: 비밀번호 처리 로직 변경
- feat: register 예외처리 완
- feat: button 중복 제거
- feat: add character action

### 2023-06-04 (일)
- [feat]: Change spinner default setting
- [feat]: logout 시 정보 날림
- [feat]: item이 rock 이면 클릭 비활성화
- [refactor]: delete .
- [refactor]: delete fragment num
- [docs]: refactor Ui
- [fix]: delete .
- add arrow icon
- [docs]: delete textView
- [docs]: editText 수정
- [docs]: logout 문구 수정
- [docs]: 번역 파일 수정
- [refactor]: login activity 수정사항 커밋

### 2023-06-05 (월)
- [fix]: count가 잘못되는 오류 수정
- [feat]: 단어장 기능 완성
- [docs]: 단어 해석본 추가
- [feat]: 버튼으로 넘어가는 부분 삭 & 뷰 최적화
- [docs]: 버튼으로 넘어가는 부분 삭제
- [docs]: 다이얼로그 애니메이션 뷰 추가
- [feat]: RandomButtonListener view merged
- [delete]: RandomButtonListener view merged
- [refactor]: 로티 애니메이션 이전

### 2023-06-07 (수)
- [chore]: 데이터베이스 기업 계정으로 
- [feat]: 한 번도 시도하지 못한 레벨이라면 클릭 불가능하게 변경
- [refactor]: upload user profile refactoring
- [fix]: 번역본 없는 부분 수정
- [refactor]: Spinner 최종 수정
- [fix]: 공백 수정
- [fix]: userprofile 부분 수정
- [fix]: 첫 번째 아이템 클릭 수정

### 2023-06-08 (목)
- [docs]: id 값 추가
- [feat]: add control bottom view
- [feat]: create fragment interface
- [feat]: 문제를 다 풀면 Progress fragment 로 이동

### 2023-06-09 (금)
- [feat]: 하단에 틀린 문제도 설명해주는 기능 추가
- [docs]: 프레임레이아웃의 공간을 잘못차지하는 부분 수정
- [docs]: 프레임레이아웃의 공간을 잘못차지하는 부분 수정
- [refactor]: quiz count 로 인해 value 값이 뒤틀린 부분 수정
- [refactor]: quiz count 로 인해 생겼던 데이터 오류 해결
- [refactor]: quiz count 각 shared 데이터 꼬여있던 부분 해결

### 2023-06-11 (일)
- [feat]: 각 국가에 맞는 언어로 변경 ex) japanese ->  日本)
- [feat]: 일본어 번역본 추가
- [feat]: 일본어 번역을 위한 코드 추가
- [docs]: 번역 빠진 부분 추가
- [feat]: register 번역 수정
- [feat]: id 값 추가
- [feat]: 이메일 검증 번역
- [feat]: 토스트메세지 번역본으로 수정
- [docs]: frame layout 이 바텀 뷰까지 영역 침범하여 차지하는 현상 수정 

### 2023-06-13 (화)
- [feat]: 태국어 추가
- [feat]: 독일어 추가
- [feat]: login 화면에 언어선택 부분 추가
- [docs]: login 화면에 언어 선택 뷰 추가

### 2023-06-14 (수)
- [refactor]: User Uid 기반 Shared Preferences 로 user profile 데이터 저장
- [refactor]: User Uid 기반 Shared Preferences 로 setting 데이터 저장
- [refactor]: User Uid 기반 Shared Preferences 로 progress 데이터 저장
- [refactor]: User Uid 기반 Shared Preferences 로 quiz count 데이터 저장
- [refactor]: User Uid 기반 Shared Preferences 로 로그인 데이터 저장
- [refactor]: User Uid 기반 Shared Preferences 로 로그인 데이터 저장
- [refactor]: User Uid 기반 Shared Preferences 로 언어 번역 데이터 저장
- [feat]: User Uid 기반 Shared Preferences 로 수정 데이터 반영

### 2023-06-16 (금)
- [feat]: bar chart 를 비동기식으로 해서 1분에 한 번씩 바뀐 데이터를 불러오도록함

### 2023-06-17 (토)
- [refactor]: onStart -> onResume, onStop -> onPause 로 변경하여 단계를 낮춤
- [feat]: User profile이 삭제되는 문제 해결
- [feat]: 백그라운드에서 차트를 지속적으로 업데이트하도록 함
- [refactor]: bar chart 데이터를 받아오는 부분 수정
- [refactor]: 로그아웃 시 모든 데이터를 날리는 부분을 id / pass 만 날리는 방식으로 수정

### 2023-06-19 (월)
- [feat]: Item 형식을 규정하기 위한 클래스 추가
- [docs]: add All word view
- [docs]: add All word view item
- [docs]: 북 아이콘 추가
- [refactor]: 더욱 더 찾기 쉬운 패키징 방식으로 수정
- [feat]: Word fragment 로 전환하기 위한 메서드 추가
- [docs]: Word fragment 를 위한 아이콘 추가
- [refactor]: 패키지 이동, basic -> fragment

### 2023-06-20 (화)
- [feat]: Add class for support word manager
- [feat]: Add class for specifying word manager
- [docs]: add All word translation
- [docs]: add recycler item view
- [refactor]: Delete unused method
- [feat]: Word List View Holder
- [feat]: Word List View Adapter
- [feat]: add All Word fragment
- [docs]: add All Word View
- [fix]: 리스트에 없는 아이템 삭제

### 2023-06-22 (목)
- [docs]: 수정을 위한 뷰 분기점 생성 : 버튼 추가 초기
- [feat]:  Item에 맞는 뷰를 출력할 수 있도록 메서드 추가
- [docs]: 레벨별로 수정
- [feat]: 레벨에 맞는 단어로 HashMap 만드는 메서드 추가
- [feat]: Item의 name 만 set 할 수 있는 생성자 생성
- [feat]: practiceItems 의 size 가 0 인 경우에만 리스트를 만들도록 변경

### 2023-06-23 (금)
- [refactor]: 클릭이벤트 구조 변경
- [docs]: 버튼 클릭 이벤트 생성
- [docs]: 수정을 위한 뷰 분기점 생성 : 버튼 수정

### 2023-06-26 (월)
- [refactor]: 버튼에 정답이 2개인 경우가 있었는데 그것에 대한 문제점 해결
- [refactor]: 회원가입 시 토스트 메세지 번역본 출력
- [refactor]: 번역 추가
- [refactor]: 이메일 인증 부분 삭제
- [refactor]: 비밀번호가 일치하지 않을 시, 버튼을 클릭하지 못했던 문제를 스크롤 뷰로 해결
- [refactor]: 강제 뒤로가기 시 하단 뷰 안보이는 문제 해결
- [refactor]: 처음 클릭될 버튼 백그라운드 색상 변경

### 2023-06-27 (화)
- [feat]: 중복 단어 오류 검토본 최종 -> 코드 리뷰
- [docs]: 로딩 애니메이션 수행 -> 이미지가 늦게 로드될 시 지루함을 덜어주기 위함
- [docs]: 로딩 애니메이션 프레임 가운데에 추가
- [feat]: 로딩 애니메이션 추가

### 2023-06-28 (수)
- [chore]: Update Fragment 생성
- [chore]: ManagementActivity 권한 추가
- [docs]: ManagementActivity 디자인 추가
- [feat]: MainActivity -> ManagementActivity 로 넘어가는 부분 추가
- [docs]: 관리자 setting icon 메인 프레임에 추가
- [docs]: 관리자 셋팅 페이지 아이콘 추가
- [docs]: 하나로만 되어있는 레벨 단계를 3 구분 지음
- [refactor]: 빈 문자열 조건문 추가
- [feat]: 3개의 array 를 리스트로 병합하는 메서드 추가

### 2023-06-29 (목)
- [feat]: 관리자 아이디 일 경우에만 관리자 세팅 페이지 보임
- [icon]: 아이콘 추가
- [docs]: 문제 추가하는 부분 프레그먼트 아이템 추가
- [docs]: 어떤 관리자인지 선택하는 스피너 추가
- [docs]: Update Activity UI 추가
- [chore]: Update Activity 추가

### 2023-06-30 (금)
- [docs]: 스피너에 등록될 아이템 추가
- [feat]: fragment 레벨 설정 구현
- [docs]: level 선택 디자인 수정
- [feat]: recyclerItem 리포지토리에 올리기 위해 스피너 구현
- [feat]: recyclerItem 리포지토리에 올리는 기능 추가

### 2023-07-04 (화)
- [feat]: word & translation & add image 에 필요한 메서드 구현
- [feat]: Previous & Next 버튼 구현, addword 눌러서 리스트에 추가 구현 & creat 버튼 클릭하면 데이터베이스에 업데이트 구현
- [feat]: PracticeItem 생성자 추가
- [feat]: 뒤로 돌아갈 수 있게 하기 위해 메서드 삭제
- [docs]: firebase에 Pratice items 추가하는 메서드 추가
- [docs]: 사용할 UI 에 Id 값 추가
- [docs]: 사용할 UI 에 Id 값 추가

### 2023-07-05 (수)

### 2023-07-06 (목)
- [feat]: 번역을 데이터베이스에 올리는 기능 추가 
- [feat]: 번역을 올리기 위한 클래스 추가
- [feat]: Word Item Translation 업로드 기능 추가
- [feat]: Manager Selector
- [feat]: 문제 업로드 추가
- [feat]: 이미지 업로드 구현

### 2023-07-07 (금)
- [feat]: 번역본을 받아오는 메서드 추가
- [docs]: 번역을 보낼 페이지 디자인 생성
- [chore]: 액티비티 주입
- [feat]: 번역 액티비티 추가
- [feat]: Bean 기본 생성자 주입

### 2023-07-11 (화)
- [feat]: 번역 업데이트 기능 추가
- [feat]: translationActivity 로 인텐트 추가
- [feat]: 파이어베이스에 객체 별 번역 추가하는 기능 추가
- [docs]: 버튼 & 에딧텍스트 에 id 값 설정

### 2023-07-12 (수)
- [feat]: All word Item 데이터 구조 변경 초안 적용본
- [feat]: All word fragment 데이터 전송 방식 변경 초안
- [feat]: 번역 후 데이터 반환해주는 클래스 추가로 인한 해설 불러오는 방식 변경
- [feat]: 번역 후 데이터 반환해주는 클래스 추가

### 2023-07-13 (목)
- [feat]: TranslationActivity 기능 수정 완료
- [feat]: UploadActivity 기능 수정 완료

### 2023-07-14 (금)
- [docs]: 문제 해설을 이제 firebase에서 받아오는 이유로 문제 해설 삭제
- [chore]: build.gradle dependency
- [feat]: delete exam in firebase
- [feat]: 관리자 페이지 구현 완료
- [feat]: 오류 발생 유발 코드 삭제
- [feat]: exam 삭제 코드 추가
- [docs]: delete exam 을 위한 다이얼로그 뷰 추가

### 2023-07-18 (화)
- [feat]: 아이템 로드에 관련한 인터페이스 추가 & 데이터 전달 객체 추가
- [feat]: 번역 관리를 위한 클래스 추가
- [feat]: 유저가 선택한 언어 순서 변경
- [feat]: 뷰홀더에서 모델의 뷰 제어
- [feat]: 뷰홀더로 넘길 때 콘텍스트 추가

### 2023-07-19 (수)
- [feat]: 인터페이스 구현체 추가
- [feat]: Worditem 을 사용할 이유가 없어서 삭제
- [feat]: 필요없는 매개변수 삭제, TreeMap으로 변경
- [feat]: HashMap -> TreeMap 으로 변경
- [feat]: 전체 단어 뷰 데이터 구조 변경

### 2023-07-20 (목)
- [feat]: word item 삭제를 위한 메서드 사용 초안
- [docs]: word item 삭제하기 위한 메서드 구현
- [docs]: 번역 빠진 부분 추가
- [feat]: onViewCreated 로 뷰가 생성되었을 때 이미지의 정보를 유지할 수 있게 변경
- [feat]: back stack 제거로 프레그먼트 데이터 유지할 수 있도록 변경
