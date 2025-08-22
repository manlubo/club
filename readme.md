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
