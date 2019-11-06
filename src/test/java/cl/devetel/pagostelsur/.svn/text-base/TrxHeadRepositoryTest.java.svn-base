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
import cl.devetel.pagostelsur.core.domain.DvtTlTrxHead;
import cl.devetel.pagostelsur.core.repositories.TrxHeadRepository;
import cl.devetel.pagostelsur.core.to.ComprobantesAnteriores;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED)
@ContextConfiguration(classes = { CoreDbConfig.class, ProvDbConfig.class })
@ActiveProfiles("default")
public class TrxHeadRepositoryTest {

	private final Logger log = LoggerFactory.getLogger(TrxHeadRepositoryTest.class);

	@Autowired
	private TrxHeadRepository repository;
	
	@TestConfiguration
	static class TrxHeadTestContextConfiguration {
		
		@Bean
		public ApplicationProperties applicationProperties() {
			return new ApplicationProperties();
		}
		
	}
	

	@Test
	public void obtenerPagotest() {
		log.info("Calculando lista de transacciones");
		DvtTlTrxHead trxHead = repository.findByIdtrx("527863347000001154");
		if(trxHead != null) {
			log.info("codaump : " + trxHead.getCodauMp());
			log.info("dt mp	: " + trxHead.getDtMp());
			log.info("fecha transaccion : " + trxHead.getFectr());
			log.info("fecha pago : " + trxHead.getFepag());
			log.info("cliente : " + trxHead.getIdcli());
			log.info("mail : " + trxHead.getMailCli());
			log.info("canal : " + trxHead.getIdcnl());
			log.info("empresa : " + trxHead.getIdemp());
			log.info("medio de pago : " + trxHead.getIdMp());
			log.info("recaudador : " + trxHead.getIdrec());
			log.info("sucursal : " + trxHead.getIdsuc());
			log.info("terminal : " + trxHead.getIdter());
			log.info("transaccion : " + trxHead.getIdtrx());
			log.info("transaccion medio pago: " + trxHead.getIdtrxMp());
			//almacenando nuevo valor del registro encontrado.
			trxHead.setMailCli(null);
			repository.save(trxHead);
		}else {
			log.info("No existe transaccion...");
		}
		
		
		
	}
	
	@Test
	public void obtenerComprobantesAnterioresTest() {
		log.info("Obteniendo comprobantes anteriores anteriores");
		List<ComprobantesAnteriores> caList = repository.findComprobantesAnteriores("100150395","000024");
		for (ComprobantesAnteriores ca : caList) {
			log.info("fecha pago : " + ca.getFechaPago());
			log.info("monto : " + ca.getMonto());
			log.info("transaccion : " + ca.getIdtrx());
			log.info("documentos pagados : " + ca.getDocumentosPagados());
			log.info("----------------------------------");
		}
	}

}
