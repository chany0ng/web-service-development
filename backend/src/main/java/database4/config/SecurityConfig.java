package database4.config;

import database4.config.jwt.TokenProvider;
import database4.config.oauth.OAuth2SuccessHandler;
import database4.config.oauth.OAuth2UserCustomService;
import database4.repository.RefreshTokenRepository;
import database4.repository.UserRepository;
import database4.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http//.httpBasic(HttpBasicConfigurer::disable)
                .cors(Customizer.withDefaults())
                .csrf(CsrfConfigurer::disable)
                .headers(headers -> headers.frameOptions(Customizer.withDefaults()))
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/signup", "/api/login", "/api/token", "/api/test").permitAll();
                    //auth.requestMatchers("/api/test").hasRole("USER");
                    auth.requestMatchers("/error/**").permitAll();
                    auth.anyRequest().authenticated();
                });

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.oauth2Login(oauth2Login -> {
            oauth2Login.userInfoEndpoint(userInfoEndpoint ->
                    userInfoEndpoint.userService(oAuth2UserCustomService));
            oauth2Login.successHandler(oAuth2SuccessHandler());
        });

        http.exceptionHandling(exception ->  {
            exception.accessDeniedHandler(jwtAccessDeniedHandler);
            exception.authenticationEntryPoint(jwtAuthenticationEntryPoint);
        });

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }
    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider, refreshTokenRepository, userRepository);
    }

}