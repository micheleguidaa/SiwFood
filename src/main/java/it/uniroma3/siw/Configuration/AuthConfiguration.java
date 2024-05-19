package it.uniroma3.siw.Configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static it.uniroma3.siw.model.Credenziali.ADMIN_ROLE;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {
	@Autowired
	private DataSource dataSource;
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	auth.jdbcAuthentication()
	.dataSource(dataSource)
	.authoritiesByUsernameQuery("SELECT username, ruolo from credenziali WHERE username=?")
	.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credenziali WHERE username=?");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
	return new BCryptPasswordEncoder();
	}
	
	@Bean
	protected SecurityFilterChain configure(final HttpSecurity httpSecurity)
	throws Exception{
	httpSecurity
	.csrf().and().cors().disable()
	.authorizeHttpRequests()

	// .requestMatchers("/**").permitAll()
	// chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini

	.requestMatchers(HttpMethod.GET,"/","/login","/index","/ricette","/cuochi","/registerCuoco","/css/**", "/images/**", "favicon.ico").permitAll()

	// chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register

	.requestMatchers(HttpMethod.POST,"/","/registerCuoco","/login").permitAll()
	.requestMatchers(HttpMethod.GET,"/admin/**").hasAnyAuthority(ADMIN_ROLE)
	.requestMatchers(HttpMethod.POST,"/admin/**").hasAnyAuthority(ADMIN_ROLE)

	// tutti gli utenti autenticati possono accere alle pagine rimanenti

	.anyRequest().authenticated()

	// LOGIN: qui definiamo il login

	.and().formLogin()
	.loginPage("/login")
	.permitAll()
	.defaultSuccessUrl("/success",true)
	.failureUrl("/login?error=true")

	// LOGOUT: qui definiamo il logout

	.and()
	.logout()

	// il logout è attivato con una richiesta GET a "/logout"

	.logoutUrl("/logout")

	// in caso di successo, si viene reindirizzati alla home

	.logoutSuccessUrl("/")
	.invalidateHttpSession(true)
	.deleteCookies("JSESSIONID")
	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	.clearAuthentication(true).permitAll();
	return httpSecurity.build();
	}
}