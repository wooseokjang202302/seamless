# seamless
심리상담과 관련하여 인정되는 민간자격은 7500여 개로 범람하고 있고 소비자는 어떠한 센터가 좋은 혹은 공인될만한 자격을 소지하고 있는지 알기가 어려운 실정입니다.

소비자가 유사자격을 가진 자가 아닌 적절한 자격을 가진 사람에게 심리서비스를 제공받을 수 있도록 하고 싶었습니다. 그리하여 사용자 주변에 공신력이 있는 5여 개의 자격을 보유한 심리서비스 제공 업체에 대한 정보를 제공하고자 본 프로젝트를 기획하게 되었습니다.

## 주요기능
### ERD
https://www.erdcloud.com/d/BKc9E9sLQ6jbEYLT8

### 회원기능
- JWT 토큰 기반 로그인

### 지도
- 네이버맵 API를 이용해 지도에 심리센터들을 마커로 표시했습니다.
- 필터 기능을 이용해 지역별로 센터를 확인할 수 있습니다.

### 북마크
- 로그인한 사용자는 북마크를 이용해 사용자가 관심있는 심리센터를 저장할 수 있습니다.

## 개발환경
### 백엔드: <img src="https://img.shields.io/badge/Java-007396?style=flat&logo=OpenJDK&logoColor=white"/><img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=SpringBoot&logoColor=white"/><img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white"/>
### 프론트: <img src="https://img.shields.io/badge/Vue.js-4FC08D?style=flat&logo=Vue.js&logoColor=white"/>
### 크롤링: <img src="https://img.shields.io/badge/Python-3776AB?style=flat&logo=Python&logoColor=white"/>
---
#### java: jdk-17
#### spring boot: 3.1.4
#### jjwt: 0.11.5

## 설치 및 사용방법
### application.properties 작성
- src > main경로에 resources디렉토리를 만들고 application.propertie파일을 만들어줍니다.
- 해당파일에는 db접속에 필요한 url, 유저명, 비밀번호, DB드라이버 등이 있습니다.
```java
spring.datasource.url=jdbc:mysql://localhost:<포트번호>/seamless?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=<유저명>
spring.datasource.password=<비밀번호>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

jwt.secret=<JWT 시크릿키>

token.access-token-duration=<엑세스 토큰 유효기간>
token.refresh-token-duration=<리프레시 토큰 만료기간>
```
#### 예시
```java
spring.datasource.url=jdbc:mysql://localhost:3306/seamless?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=password123!@#
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

jwt.secret=6QnEaSD6551DegrqqnRsdf4615iiIK+Q4xASDFCXV68994NuvSDF$@%651DFS666zcHFY//BsFDs!@

# 엑세스토큰 15 * 60 * 1000 (15분)
token.access-token-duration=900000

# 리프레시 토큰 7 * 24 * 60 * 60 * 1000 (7일)
token.refresh-token-duration=604800000
```
#### JWT 시크릿키 생성이 어려울 때
```java
public class SecretKeyGenerator {

    public static void main(String[] args) {
        String secretKey = generateSecureSecretKey();
        System.out.println(secretKey);
    }

    public static String generateSecureSecretKey() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
```
위 코드와 같이 키를 생성하는 클래스를 이용해 안전한 시크릿키를 만들 수 있습니다.
오로지 시크릿키를 생성하기 위한 클래스이기에 실행하고 키를 얻은다음 해당 클래스는 삭제해도 무관합니다.

### SeamlessApplication 실행
`SpringBootApplication`어노테이션을 가지고 있는 클래스를 실행합니다.
