# SNS_project

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


# commit message convention

--- 

git commit 템플릿 사용하여 commit message convention을 적용하였습니다.

- 블로그 링크달기