# SNS_project

# Spring Integration
[트랜잭션 정상 Commit 이후 Event 등록하기](https://sol-b.tistory.com/95) <br/>
[DirectChannel에서 ExecutorChannel로 변경한 이유](https://sol-b.tistory.com/96) <br/>
LocalThread란? <br/>
Spring Integration이란? 사용한 이유 <br/>

![image](https://github.com/ReadnThink/SNS_project/assets/103480627/f39fbb6d-c6d0-45c3-b55e-9cb7a07c32b9)




# SpringBootTest VS WebMvcTest

---

Test환경에서 Aop를 적용한 Validation 처리가 안되어서 SpringBootTest와 WebMvcTest의 차이를
명확하게 알게되었습니다. <br/>
처음에는 SpringBoot 3 버전에 설정이 달라진줄 알고 많이 헤매었습니다.

**SpringBootTest** : 실제 환경과 유사한 Test환경을 제공합니다. 

* MockMvc를 사용하려면 @AutoConfigureMockMvc 어노테이션을 선언해야함
* @SpringBootTest에서는 프로젝트의 컨트롤러, 리포지토리, 서비스가 @Autowired로 다 주입된다.


**WebMvc** : MockMvc 객체 주입이 디폴트 <br/>
컨트롤러는 주입이 정상적으로 되지만, @Component로 등록된 리포지토리와 서비스는 주입이 되지 않는다.<br/>
따라서, @WebMvcTest에서 리포지토리와 서비스를 사용하기 위해서는 @MockBean을 사용하여 리포지토리와 서비스를 Mock 객체에 빈으로 등록해줘야 한다.

### 결론

**WebMvc**는 Web Layer 관련 빈들만 등록하기 때문에 가볍고 빠르게 작동됩니다.
하지만, 실제 환경과 유사하지 않는다는 단점이 있습니다.

**SpringBootTest**는 모든 빈을 등록해서 실제 환경과 유사한 테스트가 가능합니다.
하지만, 빈 등록이 다 되는만큼 무거워 느립니다.

Aop까지 실제 환경과 유사한 Test를 하려면 SpringBootTest를 사용해야 합니다.


# H2 db 접속방법
.yml 설정
```java
spring:
  h2:
    console:
      enabled: true
      path: /h2-console


  datasource:
    url: jdbc:h2:mem:sns
    username: sa
    password:
    driver-class-name: org.h2.Driver
```

url : 주소:포트/ {설정path (/h2-console)}/login.jsp?jsessionid=8806234939a192c038cee2be13d829fb


# commit message convention

--- 

git commit 템플릿 사용하여 commit message convention을 적용하였습니다.

- 블로그 링크달기

# 데이터베이스에서 주의할점

---

## 데이터베이스 기본 표기법

데이터 베이스의 기본 표기법은 스네이크 케이스이다...
data.sql에 데이터를 입력할때 오류가 나서 많이 고생했다.

## 데이터 베이스의 USER명은 예약어입니다.

```java
url: jdbc:h2:mem:sns;NON_KEYWORDS=USER
```
를 통해 H2에서 USER를 사용할 수 있습니다.

# @PatchMapping vs @PutMapping

---

보안적 이슈 확인해보기

# Base64

String <-> byte


# 비밀번호 암호화

1. 해시
2. 해시방식
   * SHA1
   * SHA256
   * MD5
   * 왜 이런거로 비번 암호화 하면 안되는지
3. BCrypt Scrypt, Argon2
   4. 해시방식은 salt 값?


# Csrf
