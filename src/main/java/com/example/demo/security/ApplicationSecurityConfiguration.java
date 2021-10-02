package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.example.demo.security.ApplicationUserRole.*;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	/* Implementing basic authentication
	 * 
	- csrf disables Spring Security and allows cross-site request forgery, because when the client logs in into the server,
	the server sends a generated token back to it and whenever the client perform a HTTP request (POST/PUT or DELETE)
	it'll have to send the generated token back to the server within the same request with the operation performed.
	then the server will check whether the token is the same it has sent previously or not in order to allow the operation.
	
	- Once the user logs in on the login page, it's redirected to the courses page. RememberMe is used to extend the user session.
	
	- The method GET is used to log out of the system, then the authentications are cleared, the session is invalidated,
	the cookies are deleted and the user is redirected to the login page.
	*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
			.antMatchers("/api/**").hasRole(CUSTOMER.name())
			/* .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
			.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
			.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
			.antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name())
			 */
			.anyRequest()
			.authenticated()
			.and()
			//.httpBasic() // Basic Authentication. There's no way to log out in this functionality.
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.defaultSuccessUrl("/courses", true)
			.and()
			.rememberMe()
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(20))
				.key("springssecurity")
			.and()
			.logout()
				.logoutUrl("/logout")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID", "remember-me")
				.logoutSuccessUrl("/login");
	}
	
	// Creating 3 different types of users to test the system
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails edinUser = User.builder()
				.username("Edin")
				.password(passwordEncoder.encode("password"))
				.authorities(CUSTOMER.getGrantedAuthorities())
				.build();

		UserDetails simoneUser = User.builder()
				.username("Simone")
				.password(passwordEncoder.encode("passwordadm"))
				.authorities(ADMIN.getGrantedAuthorities())
				.build();

		UserDetails giovanniUser = User.builder()
				.username("Giovanni")
				.password(passwordEncoder.encode("passwordadm"))
				.authorities(ADMINTRAINEE.getGrantedAuthorities())
				.build();

		return new InMemoryUserDetailsManager(
				edinUser,
				simoneUser,
				giovanniUser
				);
	}
}
