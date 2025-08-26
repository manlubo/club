# Spring Security

#### 인증 : 로그인 과정 
#### 인가 : 사용자의 권한

### [SecurityConfig 설정]
- Spring Security 기본 설정 클래스

### [PasswordEncoder]
- `BCryptPasswordEncoder` 사용
- 비밀번호를 암호화해서 저장/검증

### [InMemoryUserDetailsManager]
- 메모리 기반 사용자 정보 저장
- 기본 계정: `user1 / 1111`
- 권한: `ROLE_USER`

### [SecurityFilterChain]
- `sample/all`: 모든 사용자 접근 허용
- `sample/admin`: `ADMIN` 권한 사용자만 접근 가능
- 그 외 요청: 인증 필요
- CSRF 비활성화
- 기본 로그인/로그아웃 폼 제공

### [구글 OAuth2 로그인 통합]
- DB 정보로 로그인처리, Google 로그인 처리
- `ClubUserDetailsService`, `ClubOAuthUserDetailsService`클래스로 나누어 관리
- ClubAuthMemberDTO를 UserDetails + OAuth2User로 통합
- SuccessHandler 캐스팅 에러 해결

### [Spring Security 최신 설정]
- `@EnableGlobalMethodSecurity` ❌ → `@EnableMethodSecurity` ✅
- `SecurityFilterChain` 방식으로 등록해야 함
- `addFilterBefore(new ApiCheckFilter("/api/**"), UsernamePasswordAuthenticationFilter.class)` 로 커스텀 필터 추가

### [ApiCheckFilter]
- `AntPathMatcher` 패턴 활용
- `/note/*` → 한 단계
- `/note/**` → 모든 하위 경로
- `/note/**/*` → 마지막 단계까지 있어야 매칭

### [MapStruct]
- DTO ↔ Entity 변환 자동화
- `@Mapper(componentModel = "spring")` 로 빈 등록
- `@Mapping(target="writer", source="writerMno")` 같은 매핑 지정 가능
- 반복 코드 줄이고 컴파일 타임 체크 가능

### [Swagger UI]
- springdoc 기준 기본 URL → `http://localhost:8080/swagger-ui/index.html`
- API 문서 JSON → `/v3/api-docs`

### [JWT 인증 처리]
- 토큰값 기반 로그인
- 토큰값 세션등록