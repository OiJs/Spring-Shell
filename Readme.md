# Spring Core Final

Spring Boot 기반의 지자체별 수도 요금 조회 CLI 애플리케이션입니다.  
Spring Shell을 통해 터미널에서 로그인/로그아웃, 도시 목록 조회, 업종별 요금 계산 등의 기능을 제공합니다.

---

## 기술 스택

- **Java 21**
- **Spring Boot 4.0.5**
- **Spring Shell 3.2.4** — CLI 인터페이스
- **Spring AOP** — 로그인/요금 조회 활동 로깅
- **Jackson** — JSON 파싱
- **OpenCSV 5.9** — CSV 파싱
- **Lombok**
- **Logback** — 파일/콘솔 분리 로깅
- **JUnit 5 + Mockito** — 단위 테스트

---

## 프로젝트 구조

```
src/main/java/com/nhnacademy/core/
├── SpringCoreFinalApplication.java
├── account/
│   ├── dto/Account.java
│   ├── repository/AccountRepository.java (interface)
│   │   └── impl/AccountRepositoryImpl.java
│   └── service/AuthenticationService.java (interface)
│       └── impl/AuthenticationServiceImpl.java
├── aop/
│   ├── AccountAop.java      # 로그인/로그아웃 로깅
│   └── PriceAop.java        # 요금 조회 로깅
├── config/
│   └── JacksonConfig.java
├── dataparser/
│   ├── DataParser.java (interface)
│   └── impl/
│       ├── CsvDataParser.java
│       └── JsonDataParser.java
├── exception/
│   ├── AccountNotFoundException.java
│   ├── CityNotFoundException.java
│   ├── InvalidPasswordException.java
│   ├── NotLoginException.java
│   └── TariffNotFoundException.java
├── formatter/
│   ├── OutPutFormatter.java (interface)
│   └── impl/
│       ├── EnglishOutputFormatter.java  # Profile: eng, default
│       └── KoreanOutputFormatter.java   # Profile: kor
├── price/
│   ├── dto/Price.java
│   ├── repository/PriceRepository.java (interface)
│   │   └── impl/PriceRepositoryImpl.java
│   └── service/PriceService.java (interface)
│       └── impl/PriceServiceImpl.java
├── properties/
│   └── FileProperties.java
└── shell/
    └── MyCommands.java
```

---

## 빌드 및 실행

### 기본 실행 (CSV, 영문 출력)

```bash
./mvnw clean package
java -jar target/spring-core-final-0.0.1-SNAPSHOT.jar
```

### JSON 파일 기반으로 실행

```bash
java -Dspring.profiles.active=json -jar target/spring-core-final-0.0.1-SNAPSHOT.jar
```

### 한글 출력으로 실행

```bash
java -Dspring.profiles.active=kor -jar target/spring-core-final-0.0.1-SNAPSHOT.jar
```

---

## 프로파일 설정

| 프로파일 | 설명 |
|---|---|
| `default` / `eng` | 영문 출력, CSV 파일 사용 |
| `kor` | 한글 출력, CSV 파일 사용 |
| `json` | JSON 파일 사용 |
| `csv` | CSV 파일 명시적 사용 |

`application.properties` (default: csv)와 `application-json.properties` 중 프로파일에 따라 선택됩니다.

---

## 사용 가능한 Shell 명령어

| 명령어 | 설명 | 예시 |
|---|---|---|
| `login <id> <password>` | 로그인 | `login 1 1` |
| `logout` | 로그아웃 | `logout` |
| `current-user` | 현재 로그인 사용자 조회 | `current-user` |
| `city` | 도시 목록 조회 | `city` |
| `sector <city>` | 업종 목록 조회 | `sector 고령군` |
| `price <city> <sector>` | 구간 요금 조회 | `price 고령군 대중탕용` |
| `bill-total <city> <sector> <usage>` | 요금 계산 | `bill-total 고령군 대중탕용 100` |

> 로그인 후에만 조회 명령어 사용이 가능합니다.

---

## 데이터 파일

| 파일 | 설명 |
|---|---|
| `src/main/resources/data/account.csv` | 계정 정보 (CSV) |
| `src/main/resources/data/account.json` | 계정 정보 (JSON) |
| `src/main/resources/data/Tariff.csv` | 지자체별 수도 요금 (CSV) |
| `src/main/resources/data/Tariff.json` | 지자체별 수도 요금 (JSON) |

---

## 로깅

Logback 기반으로 로그를 분리 저장합니다.

| 로그 파일 | 내용 |
|---|---|
| `logs/account.log` | 로그인 / 로그아웃 이벤트 (AOP) |
| `logs/price.log` | 요금 조회 이벤트 (AOP) |
| 콘솔 (STDOUT) | 전체 INFO 레벨 로그 |

---

## 테스트 실행

```bash
./mvnw test
```

주요 테스트 클래스:

- `AuthenticationServiceImplTest` — 로그인 성공/실패, 현재 계정 조회
- `PriceServiceImplTest` — 도시/업종/요금 조회, 미로그인 예외, 데이터 없음 예외

---

## 계정 정보 (기본 제공)

| 아이디 | 비밀번호 | 이름 |
|---|---|---|
| 1 | 1 | 선도형 |
| 2 | 2 | sando |
| 3 | 3 | bada |
| 4 | 4 | potato |
| 5 | 5 | manty |
| ... | ... | ... |