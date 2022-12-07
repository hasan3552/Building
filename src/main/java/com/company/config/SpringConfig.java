package com.company.config;

import com.company.exp.ItemNotFoundException;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;
    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**"
    };


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Authorization
        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/auth", "/auth/*").permitAll()
                .antMatchers("/home/public", "/home/public/**").permitAll()
                .antMatchers("/swagger-ui", "/swagger-ui/**").permitAll()
                .antMatchers("/attach/public", "/attach/open", "/attach/open/*", "/attach/download").permitAll()
                .antMatchers("/attach/adm", "/attach/adm/*").hasAnyRole("ADMIN")
                .antMatchers("/home/adm", "/home/adm/**").hasAnyRole("ADMIN")
                .antMatchers("/profile/adm", "/profile/adm/*", "/profile/adm/**").hasAnyRole("ADMIN")
                .antMatchers("/home/comp", "/home/comp/*", "/home/comp/**").hasAnyRole("ADMIN", "COMPANY")
                .anyRequest().authenticated()
                .and()
             //   .formLogin()
             // .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//                .httpBasic();
        http.csrf().disable();
        http.cors().disable();
//          .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .and()
//                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
////                .httpBasic();
//        http.csrf().disable();
//        http.cors().disable();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String md5 = MD5Util.getMd5(rawPassword.toString());
                if(!md5.equals(encodedPassword)){
                    throw new ItemNotFoundException("Password is wrong");
                }

                return md5.equals(encodedPassword);
            }
        };

    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
