package cl.devetel.pagostelsur;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import cl.devetel.pagostelsur.config.ApplicationProperties;
import cl.devetel.pagostelsur.config.CoreDbConfig;
import cl.devetel.pagostelsur.config.ProvDbConfig;
import cl.devetel.pagostelsur.core.domain.DvtTlTrxBody;
import cl.devetel.pagostelsur.core.repositories.TrxBodyRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
//parametro que utiliza las configuraciones indicadas en la anotacion contextconfiguration
@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED)
@ActiveProfiles("default")
@ContextConfiguration(classes={CoreDbConfig.class, ProvDbConfig.class})
public class TrxBodyRepositoryTest {
	
	private final Logger log = LoggerFactory.getLogger(TrxBodyRepositoryTest.class);
	
	@Autowired
	private TrxBodyRepository repository;
	
	@TestConfiguration
	static class PagosServiceImplTestContextConfiguration {

		@Bean
		public ApplicationProperties properties() {
			return new ApplicationProperties();
		}
	}
	
	
	@Test
	public void obtenerPagotest() {
		log.info("Calculando lista de transacciones");
		List<DvtTlTrxBody> trxBodyList = repository.findAllByIdIdtrx("527171968000000985");
		for(DvtTlTrxBody trxBody : trxBodyList){
			log.info("transaccion       : " + trxBody.getId().getIdtrx());
			log.info("documento         : " + trxBody.getId().getDoc());
			log.info("fecha emision     : " + trxBody.getFeemi());
			log.info("fecha vencimiento : " + trxBody.getFeven());
			log.info("monto 	       : " + trxBody.getMnt());
		}
	}

}
