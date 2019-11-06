package cl.devetel.pagostelsur.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	//private Pattern allowedMethods = Pattern.compile("^(GET|POST)$");

	// Secure the endpoins with HTTP Basic authentication
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Build the request matcher for CSFR
		RequestMatcher csrfRequestMatcher = new RequestMatcher() {

			// Enabled CSFR protection on the following urls:
			private AntPathRequestMatcher[] requestMatchers = { 
					new AntPathRequestMatcher("/api/pagos/**"),
					new AntPathRequestMatcher("/api/pagos/listar_comprobantes/"),
					new AntPathRequestMatcher("/api/pagos/sendComprobante/"), 
					new AntPathRequestMatcher("/api/pagos/actualizar_mailcliente/")
					 

			};

			@Override
			public boolean matches(HttpServletRequest request) {
				// Skip allowed methods
				/*if (allowedMethods.matcher(request.getMethod()).matches()) {
					return false;
				}*/

				// If the request match one url the CSFR protection will be enabled
				for (AntPathRequestMatcher rm : requestMatchers) {
					if (rm.matches(request)) {
						return true;
					}
				}
				return false;
			}

		}; // new RequestMatcher

		//http.sessionManagement().maximumSessions(20);
		http
			// Enable csrf only on some request matches								
			.csrf()
				.requireCsrfProtectionMatcher(csrfRequestMatcher)
			.and()
			.authorizeRequests().antMatchers("/resources/static/js/**", "/resources/static/css/**", "/resources/static/img/**","/resurces/static/**","/pagos/**","/css/**","/js/**","/img/**").permitAll();
				
				
		
	}
	
	
	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
	    StrictHttpFirewall firewall = new StrictHttpFirewall();
	    firewall.setAllowUrlEncodedSlash(true);
	    firewall.setAllowSemicolon(true);
	    return firewall;
	}

}