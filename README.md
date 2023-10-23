# SNS_project
도메인 주도 개발, 이벤트 주도 개발을 공부하면서 같은 요구사항에 대해서 다양한 아키텍처를 적용해본 프로젝트 <br/>
단순한 레이어드 아키텍쳐부터 이벤트 드리븐 아키텍쳐까지 코드 구조를 바꿔보았습니다. <br/>

[첫번째 프로젝트](https://github.com/ReadnThink/pocoapoco-refectoring) 이후 기본기를 튼튼히 하여 코드의 질을 높였습니다. <br/>
배울것이 너무나도 많고 기본이 튼튼해야 빠르고 견고하게 성장할 수 있다는것을 깨닫게 해준 프로젝트입니다. <br/>

# Spring Integration
[Spring Integration을 적용하며 성장한 기록](https://github.com/ReadnThink/SNS_project/issues/32)

## Integration Flow
![image](https://github.com/ReadnThink/SNS_project/assets/103480627/19fb03ad-6a9f-45ba-be93-6cff5fce4fc5)


# CQRS - Kafka
Kafka를 적용하며 성장한 기록

## Kafka Flow
![image](https://github.com/ReadnThink/SNS_project/assets/103480627/35e063ec-c10f-446c-9945-482dc9278af8)

# 🏄‍♂ 요구사항
아래의 요구사항을 레이어드 아키텍쳐부터 이벤트 드리븐 아키텍쳐까지 코드 구조를 변경하면서 적용해보기.

### USER

- [x] **로그인**
    * ID/PW 이용한 기본 로그인 기능
    * JWT를 쿠키에 담아 인증, 인가처리 진행
- [x] **회원가입**

### POST

- [x] **CRUD 기능**
    - 게시글 단건조회
    - 게시글 전체조회
    - 회원에 한해서 작성 가능
    - 작성자만 수정, 삭제 가능
- [x] **게시글 댓글**
   - 댓글 CRUD 기능
   - 회원 한해서 작성 가능
   - 작성자 한해서 수정, 삭제 가능
#### 제약사항
- 글의 제목은 30자 이내여야 함
- 제목, 작성자, 작성시간, 수정시간 항목은 필수값
- 금칙어를 설정하고 글과 댓글의 제목과 내용에 금칙어가 포함되지 않도록 하기
   - "바보"
- 제약 사항 위반시 예외 던지기
