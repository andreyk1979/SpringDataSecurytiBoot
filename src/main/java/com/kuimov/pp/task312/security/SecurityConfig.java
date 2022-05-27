package com.kuimov.pp.task312.security;

import com.kuimov.pp.task312.service.RoleService;
import com.kuimov.pp.task312.service.UserDetailsServiceImpl;
import com.kuimov.pp.task312.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService; // используется только для Создание админа в БД
    private final RoleService roleService; // используется только для Создание админа в БД

    private final SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfig(UserService userService, RoleService roleService, SuccessUserHandler successUserHandler, UserDetailsServiceImpl userDetailsService) {
        this.userService = userService;
        this.roleService = roleService;
        this.successUserHandler = successUserHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // конфигурация для прохождения аутентификации
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()// перехватывает URL
                .antMatchers("/user/**", "/webjars/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/**").hasRole("ADMIN")
                .and()
                .formLogin() // Spring подставляет стандартную логин форму
                .successHandler(successUserHandler) // подключаем SuccessHandler для перенеправления по ролям
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .csrf()
                .disable();

    }
    //Создание админа в БД
/*    @PostConstruct
    private void postConstruct() {
        User admin = new User("Kuimov", "Kuimov", "kuimov@mail.ru", "100",
                roleService.getAllRoles());
        userService.save(admin);
    }*/
}