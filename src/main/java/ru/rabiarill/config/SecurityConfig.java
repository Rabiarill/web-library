package ru.rabiarill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.rabiarill.services.PeopleDetailsService;

@EnableWebSecurity
public class  SecurityConfig extends WebSecurityConfigurerAdapter {

   private final PeopleDetailsService peopleDetailsService;

   @Autowired
   public SecurityConfig(PeopleDetailsService peopleDetailsService) {
      this.peopleDetailsService = peopleDetailsService;
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http
                 .authorizeRequests()
                 .antMatchers("/", "/auth/login", "/auth/registration", "/error").permitAll()
                 .antMatchers("/people/info", "/people/*/edit",
                         "/people/*/edit/password", "/people/*/delete").authenticated()
                 .antMatchers("/**").hasRole("EMPLOYEE")
              .and()
                 .formLogin()
                 .loginPage("/auth/login")
                 .loginProcessingUrl("/sign_in")
                 .defaultSuccessUrl("/people/info")
                 .failureUrl("/auth/login?error")
              .and()
                 .logout()
                 .logoutUrl("/logout")
                 .logoutSuccessUrl("/auth/login");
   }

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(peopleDetailsService)
              .passwordEncoder(getPasswordEncoder());
   }

   @Bean
   public PasswordEncoder getPasswordEncoder() {
      return new BCryptPasswordEncoder();
   }
}
