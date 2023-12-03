# SNS_project
도메인 주도 개발, 이벤트 주도 개발을 공부하면서 같은 요구사항에 대해서 다양한 아키텍처를 적용해본 프로젝트 <br/>
단순한 레이어드 아키텍쳐부터 이벤트 드리븐 아키텍쳐까지 코드 구조를 바꿔보았습니다. <br/>

[첫번째 프로젝트](https://github.com/ReadnThink/pocoapoco-refectoring) 이후 기본기를 튼튼히 하여 코드의 질을 높였습니다. <br/>
배울것이 너무나도 많고 기본이 튼튼해야 빠르고 견고하게 성장할 수 있다는것을 깨닫게 해준 프로젝트입니다. <br/>

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


# 배운점

## 메세지 기반 Spring Integration 적용

트래픽이 높을 경우 애플리케이션에 과부하가 된다는 것을 알게되었습니다. <br/>
Spring Integration을 사용하여 메세지 기반 비동기 처리방식을 알게되었고 비동기 처리를 적용하여 API의 처리량을 늘려보았습니다.

🔗[[Spring Integration 개발 과정](https://github.com/ReadnThink/SNS_project/issues/32)](https://github.com/ReadnThink/SNS_project/issues/32)

![image](https://github.com/ReadnThink/architecture_study_project/assets/103480627/48675724-ad16-4440-8262-70d25be0f6c5)


## ExecutorChannel

Spring Integration의 ExecutorChannel을 사용해 Create, Update, Delete 작업시 비동기 처리를 하여 API의 처리량과 속도를 늘렸습니다.

## ThraedPool을 재정의

ThreadPool을 재정의하고 ExecutorChannel에서 사용할 수 있게 주입해주었습니다. <br/>
ThreadPool의 동작을 알게되었고  QueueCapacity 설정에 대한 장단점을 알게되었습니다.

## TransactionManager

TransactionSynchronizationManager, ThreadLocal을 사용하여 트랜잭션 성공시 Event 발행하여 트랜잭션을 동기화 하였습니다.

## CQRS적용

**Flow (SpringIntegration + Kafka)**

![image](https://github.com/ReadnThink/architecture_study_project/assets/103480627/2c53b6e1-2c77-4d3b-8ef4-974359449897)

## Read 모듈, CUD 모듈 분리

데이터 조작과 조회만하는 쿼리를 분리하여 성능과 확장성을 최대화하였습니다.<br/>

기존 아키텍처에서는 DB를 조회, 변경시 동일한 데이터 모델을 사용합니다. <br/>
이 구조는 기본적인 CRUD 작업에 적합합니다. <br/>
더 복잡한 애플리케이션에서는 레이어드 아키텍처를 사용하기 어려울 수 있습니다. <br/>
조회 작업이 많은 서비스의 특성상 CQRS를적용하여 해결할 수 있습니다.<br/>
사용빈도가 높은 조회 작업을 별도의 모델로 분리하여 요청을 분산할 수 있습니다.<br/>

![image](https://github.com/ReadnThink/architecture_study_project/assets/103480627/878124fc-d1f2-4426-997f-71f35bd6d05d)

## Spring (Kafka, Integration) 를 이용한 비동기식 모듈 통신

Spring Integration의 메세징을 이용해 커맨드를 발행하고, 트랜잭션 성공시 Producer에 Event를 발행하여 Kafka Consumer를 통해 받아 후처리를 하게 적용했습니다. <br/>
이 구조의 장점은 커맨드 발행 이후에는 비동기 처리로 정의한 스레드풀을 활용하여 cpu활용도가 향상되고 API처리량과 응답 시간이 짧아진다는 것입니다.
