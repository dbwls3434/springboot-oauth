package org.yujin.myproc.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.yujin.myproc.config.auth.PrincipalDetailService;
import org.yujin.myproc.config.oauth.PrincipalOAuth2UserService;
import org.yujin.myproc.service.UserService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    private final PrincipalOAuth2UserService principalOAuth2UserService;

    @Value("${jwt.token.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(withDefaults())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests((requests) -> requests
                       /* .requestMatchers(
                                "/"
                                ,"/api/user/join"
                                ,"/api/user/login"
                                ,"/auth/**"
                                ,"/user/login"
                                ,"/resources/**"
                        ).permitAll()*/
                        //.anyRequest().authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .permitAll()
                ).oauth2Login(oauth2 -> oauth2
                        .loginPage("/user/login")
                        .userInfoEndpoint(userInfoEndpointConfig -> {
                            userInfoEndpointConfig.userService(principalOAuth2UserService);
                        })
                );

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}
