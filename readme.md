# Backend Starter

이 프로젝트는 Spring Boot 기반의 백엔드 스타터 팩입니다.
초기 세팅 시간을 단축하고, 바로 비즈니스 로직 개발에 집중할 수 있도록 구성되었습니다.

## 🛠 기술 스택 및 버전

- **Java**: 17
- **Spring Boot**: 3.4.2 (build.gradle에는 4.0.2로 되어 있으나 Spring Boot 최신 안정 버전은 3.x 대입니다. 확인 필요)
- **Build Tool**: Gradle
- **Database**: MySQL

## 📦 추가된 의존성 (Dependencies)

| 의존성 | 설명 |
| --- | --- |
| `spring-boot-starter-data-jpa` | JPA 및 Hibernate 사용 |
| `spring-boot-starter-validation` | 데이터 유효성 검사 (@Valid 등) |
| `spring-boot-starter-webmvc` | Spring MVC 웹 애플리케이션 구축 |
| `lombok` | Boilerplate 코드 제거 (Getter, Setter, Builder 등) |
| `spring-boot-devtools` | 개발 편의성 제공 (자동 재시작 등) |
| `mysql-connector-j` | MySQL 데이터베이스 드라이버 |

## 🚀 주요 기능 및 설정

### 1. AOP (Aspect Oriented Programming)
- **위치**: `com.yonsai.starter.aop.LoggingAspect`
- **기능**: Controller 및 Service 계층의 메서드 실행 전/후 로그를 자동으로 남깁니다.
- **특징**:
  - 실행 시간 측정 (ms 단위)
  - 예외 발생 시 에러 로그 기록
  - `com.yonsai.starter..controller..*` 및 `com.yonsai.starter..service..*` 패키지 하위 메서드에 적용

### 2. Exception Handling (예외 처리)
- **위치**: `com.yonsai.starter.exception`
- **구성**:
  - `GlobalExceptionHandler`: 전역 예외 처리기 (@RestControllerAdvice)
  - `BusinessException`: 비즈니스 로직용 커스텀 예외
  - `ErrorCode`: 에러 코드 및 메시지 관리 Enum
  - `ErrorResponse`: 클라이언트에 반환될 에러 응답 포맷 (JSON)
- **사용법**:
  ```java
  // 예시: 비즈니스 로직에서 예외 발생 시키기
  throw new BusinessException(ErrorCode.BAD_REQUEST);
  ```

### 3. Database Configuration (DB 설정)
- **파일**: `src/main/resources/application.properties`
- **필수 설정**: 프로젝트 실행 전 아래 항목을 본인의 DB 환경에 맞게 수정해야 합니다.

```properties
# 애플리케이션 이름
spring.application.name=backend-starter

# DB 연결 설정 (필수 수정)
# '연결디비이름', '유저이름', '비밀번호'를 실제 값으로 변경하세요.
spring.datasource.url=jdbc:mysql://localhost:3306/연결디비이름?serverTimezone=Asia/Seoul&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=유저이름
spring.datasource.password=비밀번호
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate 설정
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## ▶️ 실행 방법

1. MySQL 데이터베이스를 생성합니다.
2. `application.properties` 파일에서 DB 접속 정보를 수정합니다.
3. 프로젝트 루트에서 다음 명령어로 실행합니다.
   ```bash
   ./gradlew bootRun
   ```
