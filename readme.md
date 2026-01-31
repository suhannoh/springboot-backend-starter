# Backend Starter

이 프로젝트는 Spring Boot 기반의 백엔드 스타터 템플릿입니다.  
반복되는 초기 세팅(AOP, 전역 예외 처리, 응답 포맷 통일 등)을 미리 구성하여  
바로 비즈니스 로직 개발에 집중할 수 있도록 설계되었습니다.

---

## 기술 스택

- Java 17
- Spring Boot 3.3.5
- Gradle
- MySQL

---

## 주요 의존성

| 의존성 | 설명 |
|---|---|
| spring-boot-starter-data-jpa | JPA 및 Hibernate 사용 |
| spring-boot-starter-validation | DTO 유효성 검사 (@Valid 등) |
| spring-boot-starter-web | Spring MVC + REST + JSON + Tomcat |
| lombok | Getter/Setter/Builder 등 반복 코드 제거 |
| spring-boot-devtools | 개발 편의 기능 (자동 재시작 등) |
| mysql-connector-j | MySQL 드라이버 |

---

## 프로젝트에 포함된 공통 기능

### 1. AOP 로깅

위치  
com.yonsai.starter.aop.LoggingAspect

기능

- Controller / Service 계층 실행 로그 자동 기록
- 실행 시간(ms) 측정
- 예외 발생 시 에러 로그 출력

적용 범위

com.yonsai.starter..controller..  
com.yonsai.starter..service..

---

### 2. 전역 예외 처리

위치  
com.yonsai.starter.exception

구성

- GlobalExceptionHandler
- BusinessException
- ErrorCode enum
- ErrorResponse DTO

사용 예시

throw new BusinessException(ErrorCode.BAD_REQUEST);

모든 예외는 전역 예외 처리기를 통해 일관된 JSON 형식으로 반환됩니다.

---

### 3. ApiResponse (응답 포맷 통일)

위치  
com.yonsai.starter.common.response.ApiResponse

모든 성공/실패 응답은 ApiResponse 구조로 통일됩니다.

성공 응답 예시

{
"success": true,
"data": {...},
"message": "OK",
"error": null
}

실패 응답 예시

{
"success": false,
"data": null,
"message": null,
"error": {
"code": "BAD_REQUEST",
"msg": "입력값이 올바르지 않습니다",
"timeStamp": "2026-01-31T22:10:00"
}
}

컨트롤러 사용 예시

return ResponseEntity.ok(ApiResponse.success(data));

---

### 4. DTO 검증 (@Valid)

DTO 단에서 검증 규칙을 작성하고  
컨트롤러에서 @Valid로 자동 검증됩니다.

예시

public record UserRequest(
@NotBlank(message = "이름은 필수입니다")
String name
) {}

검증 실패 시 전역 예외 처리기가 자동으로 잡아  
첫 번째 필드 에러 메시지를 반환합니다.

---

## 데이터베이스 설정

파일 위치  
src/main/resources/application.properties

실행 전 반드시 DB 정보를 수정해야 합니다.

spring.application.name=backend-starter

spring.datasource.url=jdbc:mysql://localhost:3306/DB이름?serverTimezone=Asia/Seoul&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true  
spring.datasource.username=유저이름  
spring.datasource.password=비밀번호  
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true  
spring.jpa.properties.hibernate.format_sql=true

---

## 실행 방법

1. MySQL 데이터베이스 생성
2. application.properties 수정
3. 프로젝트 실행

./gradlew bootRun

또는 IDE 실행 버튼 사용

