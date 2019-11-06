package cl.devetel.pagostelsur.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpSessionConfig {
	
	private final Logger log = LoggerFactory.getLogger(HttpSessionConfig.class);
	
	
	
	
    @Bean // bean for http session listener
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
        	@Autowired
        	private ApplicationProperties applicationProperties;
        	
            @Override
            public void sessionCreated(HttpSessionEvent se) {// This method will be called when session created
                Integer maxInactiveInterval = Integer.parseInt(applicationProperties.getMaxInactiveInterval());
                se.getSession().setMaxInactiveInterval(maxInactiveInterval);
        		String fechaCreacion = new SimpleDateFormat("YYYYMMdd HH:mm:ss").format(new Date());
                log.info("Sesion creada id : {} , fecha creacion : {}, maxInactiveInterval : {} ", se.getSession().getId(), fechaCreacion, se.getSession().getMaxInactiveInterval());
                
            }
            @Override
            public void sessionDestroyed(HttpSessionEvent se) {// This method will be automatically called when session destroyed
            	String fechaTermino = new SimpleDateFormat("YYYYMMdd HH:mm:ss").format(new Date());
                log.info("Sesion finalizada id: {}, fecha termino : {}", se.getSession().getId(), fechaTermino);
                //se.getSession().invalidate();
            }
        };
    }
	
    /*@Bean                   // bean for http session attribute listener
    public HttpSessionAttributeListener httpSessionAttributeListener() {
        return new HttpSessionAttributeListener() {
            @Override
            public void attributeAdded(HttpSessionBindingEvent se) {            // This method will be automatically called when session attribute added
            	
                log.info("Attribute Added following information");
                log.info("Attribute Name...: {}", se.getName());
                log.info("Attribute Value..: {}", se.getName());
            }
            @Override
            public void attributeRemoved(HttpSessionBindingEvent se) {      // This method will be automatically called when session attribute removed
                log.info("attributeRemoved");
            }
            @Override
            public void attributeReplaced(HttpSessionBindingEvent se) {     // This method will be automatically called when session attribute replace
                log.info("Attribute Replaced following information");
                log.info("Attribute Name.........: {}", se.getName());
                log.info("Attribute Old Value....: {}", se.getValue());
            }
        };
    }*/
}
