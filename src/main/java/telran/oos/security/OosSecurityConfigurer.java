package telran.oos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class OosSecurityConfigurer extends WebSecurityConfigurerAdapter {
    AuthJwtFilter authJwtFilter;

    @Autowired
    public void setAuthJwtFilter(AuthJwtFilter authJwtFilter) {
        this.authJwtFilter = authJwtFilter;
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        String wsEndpoint = String.format("/%s/**", WS_ENDPOINT);
//
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authJwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests()
                .antMatchers("/**").permitAll();
//                .antMatchers(HttpMethod.GET).hasAnyRole(ADMIN, USER)
//                .anyRequest().hasRole(ADMIN);
    }
}
