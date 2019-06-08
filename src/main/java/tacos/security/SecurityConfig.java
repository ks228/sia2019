package tacos.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	throws Exception {
		auth.inMemoryAuthentication()
			.withUser("buzz")
				.password("{noop}infinity")
				.authorities("ROLE_USER")
			.and()
			.withUser("woody")
				.password("{noop}bullseye")
				.authorities("ROLE_USER");
	}
}
