package cl.devetel.pagostelsur;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/*@SpringBootTest
@ActiveProfiles("default")
@RunWith(SpringRunner.class)*/
public class ProfilingTest {
	private final Logger log = LoggerFactory.getLogger(ProfilingTest.class);
	
	@Autowired
	private Environment environment;
	
	//@Test
	public void testCurrentProfile(){
		log.info("JNDI connection : " + this.environment.getProperty("app.dbCore.jndi-name"));
		log.info("Active profile : " + this.environment.getActiveProfiles()[0]);
	}
	


}
