package cl.devetel.pagostelsur;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("cl.devetel.*")
//@EnableConfigurationProperties(ApplicationProperties.class)
@PropertySource("file:E:/properties/pagos-telsur/application.properties")
//@PropertySource("file:${jboss.server.config.dir}/dvt436_pago_telsur/application.properties")
public class PagosTelsurApplication extends SpringBootServletInitializer	{
	
	private final Logger log = LoggerFactory.getLogger(PagosTelsurApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(PagosTelsurApplication.class, args);
	}
	
   /* @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
          servletContext.getSessionCookieConfig().setPath("/");
          servletContext.getSessionCookieConfig().setHttpOnly(true);
          servletContext.getSessionCookieConfig().setSecure(true);
          servletContext.getSessionCookieConfig().setMaxAge(3600);
          
          Set<SessionTrackingMode> sessionTracking = new HashSet<>();
          sessionTracking.add(SessionTrackingMode.COOKIE);
          sessionTracking.add(SessionTrackingMode.URL);
          servletContext.setSessionTrackingModes(sessionTracking);
          
          
          
          super.onStartup(servletContext);
    }*/

	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		/*ConfigurableEnvironment environment = new StandardEnvironment();
		environment.setActiveProfiles(environment.getActiveProfiles());
		application.environment(environment);*/
		return application.sources(PagosTelsurApplication.class);
	}
	
}
