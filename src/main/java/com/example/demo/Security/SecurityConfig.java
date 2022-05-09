package com.example.demo.Security;

import com.example.demo.Service.RoleService;
import com.example.demo.Service.UserService;
import com.example.demo.models.User;  //Создание админа в БД через @PostConstruct
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct; //Создание админа в БД через @PostConstruct

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    private SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);// предоставляет юзеров
        authProvider.setPasswordEncoder(passwordEncoder());// ПОДКЛЮЧАЕМ  его
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // конфигурация для прохождения аутентификации
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
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
        User admin = new User("Kuimow", "Kuimow", "kuimow@mail.ru", "$2y$10$WJYXsLawkitdHYMTlx4ieuvBDV0Zdov/biPubsjFv64YdbRkBUc7u",
                roleService.getAllRoles());
        userService.save(admin);
    }*/
}
