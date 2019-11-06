package cl.devetel.pagostelsur;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {TestContext.class, SpringMVC.class})
@WebAppConfiguration 
@ActiveProfiles("default")
public class MessageBoundleTest {
	
	@Autowired
	private MessageSource messageSource;
	
	@Bean(name = "messageSource")
	@Profile("default")
	public MessageSource messageSource() {
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    messageSource.setBasenames("/messages/");
	    messageSource.setCacheSeconds(5);
	    return messageSource;
	}
	
	@Test
	public void getMessageTest() {
		//messageSource.getMessage("pagostelsur.ingreso.errorpdr", null, Locale.getDefault());
	}

	/*@Bean(name = "messageSource")
	@Profile("test")
	public MessageSource testMessageSource() {
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    return messageSource;
	}*/

}
