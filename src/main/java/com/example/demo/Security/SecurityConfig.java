package com.example.demo.Security;

import com.example.demo.Service.RoleService;
import com.example.demo.Service.UserService;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repository.RoleRepository;
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

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

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
        authProvider.setUserDetailsService(userDetailsService);// предоставляет юзеров.Чтоб понимал
        authProvider.setPasswordEncoder(passwordEncoder());// ПОДКЛЮЧАЕМ  его
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // конфигурация для прохождения аутентификации
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()// перехватчик URL
                //.antMatchers("/index").permitAll()
                .antMatchers( "/user/**", "/webjars/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/**").hasRole("ADMIN")
                .and()
                .formLogin() // Spring сам подставит свою логин форму
                .successHandler(successUserHandler) // подключаем наш SuccessHandler для перенеправления по ролям
                .and()
                .logout().logoutSuccessUrl("/")// и без него перенаправляет на регистрацию после выхода
                .and()
                .csrf()
                .disable();

 /*               .antMatchers("/users").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .usernameParameter("email")
                .defaultSuccessUrl("/users")
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
    }
*/
        // Необходимо для шифрования паролей
        //
    }
/*
        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }*/


/*    @PostConstruct
    private void postConstruct() {
        User admin = new User("andr", "andr1000", "andrey@mail.ru", "100",
                roleService.getAllRoles());
        userService.save(admin);
    }*/
}

