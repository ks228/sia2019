package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	javax.sql.DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	throws Exception {
		auth.ldapAuthentication()
			.userSearchBase("ou=people")
			.userSearchFilter("(uid={0})")
			.groupSearchBase("ou=groups")
			.groupSearchFilter("member={0}")
			.passwordCompare()
				.passwordEncoder(new BCryptPasswordEncoder())
				.passwordAttribute("passcode")
			.and()
			.contextSource()
			//.url("ldap://tacocloud.com:33389/dc=tacocloud,dc=com");
				.root("dc=tacocloud,dc=com") //configures embedded ldap server
				.ldif("classpath:users.ldif"); // you need ldif file as well for this in your classpath
	}
}
