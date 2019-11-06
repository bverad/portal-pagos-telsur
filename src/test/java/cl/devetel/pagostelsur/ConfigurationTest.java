package cl.devetel.pagostelsur;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.google.gson.Gson;

import cl.devetel.pagostelsur.config.ApplicationProperties;
import cl.devetel.pagostelsur.responses.Cuenta;


/*@SpringBootTest
@ActiveProfiles("default")
@RunWith(SpringRunner.class)*/
public class ConfigurationTest {
	
	private static Logger log = LoggerFactory.getLogger(ConfigurationTest.class);

	@TestConfiguration
	static class PagosServiceImplTestContextConfiguration {

		@Bean
		public ApplicationProperties properties() {
			return new ApplicationProperties();
		}
	}
	
	@Autowired
    private ApplicationProperties properties;
    
    
    @Autowired
    private Environment environment;

    //@Test
    public void urlTest() {
    	log.info("URL Telsur\t\t: {}", properties.getUrl().get("telsur"));
    	log.info("URL GTD 		: {}", properties.getUrl().get("gtd"));
    	log.info("Header Telsur : {}", properties.getHeader().get("telsur"));
    	log.info("Header GTD 	: {}", properties.getHeader().get("gtd"));
    	log.info("Footer Telsur : {}", properties.getFooter().get("telsur"));
    	log.info("Footer GTD 	: {}", properties.getFooter().get("gtd"));
    }
    
    //@Test
    public void getEncodingTest()
    {
       final byte [] bytes = {'-'};
       final InputStream inputStream = new ByteArrayInputStream(bytes);
       final InputStreamReader reader = new InputStreamReader(inputStream);
       final String encoding = reader.getEncoding();
       log.info("Encoding value : " , encoding);
    }
    
    //@Test
    public void developmentProfileTest(){
        String email = properties.getEmail();
        String jndiName = properties.getDbprov().get("jndi-name");
        String ddlAuto = properties.getDbprov().get("ddl-auto");
        String dialect = properties.getDbprov().get("dialect");
        String driver = properties.getDbprov().get("driver-class-name");
        log.info("Profile 1	: " + environment.getActiveProfiles()[0]);
        log.info("Configuracion base de datos PROV");
        log.info("Ddl-auto....: " + ddlAuto);
        log.info("JNDI name...: " + jndiName);
        log.info("Dialect.....: " + dialect);
        log.info("Driver......: " + driver);
        log.info("**********************************" );
        
        jndiName = properties.getDbcore().get("jndi-name");
        ddlAuto = properties.getDbcore().get("ddl-auto");
        dialect = properties.getDbcore().get("dialect");
        driver = properties.getDbcore().get("driver-class-name");
        log.info("Configuracion base de datos CORE");
        log.info("Ddl-auto....: " + ddlAuto);
        log.info("JNDI name...: " + jndiName);
        log.info("Dialect.....: " + dialect);
        log.info("Driver......: " + driver);
        log.info("**********************************" );
        
        assertThat(email).contains("default@email.com");
    }
    
    @Test
    public void simpleDateFormatTest() {
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String fecha = sdf.format(new Date()); 
        System.out.println("Fecha Formateada : " + fecha );*/
        // Instantiate a Date object
        Date date = new Date();
        // Get current date and time
        // 1st way: current time and date using toString()
        System.out.println("La fecha de hoy es: " + date.toString());
 
        // 2nd way: current time and date using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println("La fecha de hoy es: " + dateFormat.format(date));
 
        // Convert string to date
        SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyyMMddHHmmss");
        String strdate2 = "20190613223542";
        try {
            Date newdate = dateformat2.parse(strdate2);
            System.out.println(dateFormat.format(newdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void encodingCuentasTest() {
    	//String cuentas = "%5B%7B%22nmro_documen%22:%2233924937%22,%22fech_documen%22:%2201-05-2019%22,%22fech_vencmto%22:%2205-06-2019%22,%22valor%22:%2225274%22,%22tipo_doc%22:%2201%22,%22saldo%22:%2225274%22,%22desc_tipo_documen%22:%22Boleta%20Comp.%22,%22document_id%22:%22560870440000016574%22,%22io2%22:%22JORGE%20ALESSANDRI%20474%22%7D%5D,%5B%7B%22nmro_documen%22:%2233924937%22,%22fech_documen%22:%2201-05-2019%22,%22fech_vencmto%22:%2205-06-2019%22,%22valor%22:%2225274%22,%22tipo_doc%22:%2201%22,%22saldo%22:%2225274%22,%22desc_tipo_documen%22:%22Boleta%20Comp.%22,%22document_id%22:%22560870440000016574%22,%22io2%22:%22JORGE%20ALESSANDRI%20474%22%7D%5D";
    	String cuentas = "%5B%7B%22nmro_documen%22:%2211280592%22,%22fech_documen%22:%2201-10-2017%22,%22fech_vencmto%22:%2225-10-2017%22,%22valor%22:%2298104%22,%22tipo_doc%22:%2202%22,%22saldo%22:%2298104%22,%22desc_tipo_documen%22:%22Factura%20Comp.%22,%22document_id%22:%22561559893000001278%22,%22io2%22:%22CARLOS%20ACHARAN%20ARCE%20S/N%20%22%7D,%7B%22nmro_documen%22:%2211331651%22,%22fech_documen%22:%2201-12-2017%22,%22fech_vencmto%22:%2226-12-2017%22,%22valor%22:%2298010%22,%22tipo_doc%22:%2202%22,%22saldo%22:%2298010%22,%22desc_tipo_documen%22:%22Factura%20Comp.%22,%22document_id%22:%22561559893000001278%22,%22io2%22:%22CARLOS%20ACHARAN%20ARCE%20S/N%20%22%7D,%7B%22nmro_documen%22:%2211356954%22,%22fech_documen%22:%2201-01-2018%22,%22fech_vencmto%22:%2225-01-2018%22,%22valor%22:%2298390%22,%22tipo_doc%22:%2202%22,%22saldo%22:%2298390%22,%22desc_tipo_documen%22:%22Factura%20Comp.%22,%22document_id%22:%22561559893000001278%22,%22io2%22:%22CARLOS%20ACHARAN%20ARCE%20S/N%20%22%7D%5D";
    	String decoded;
		try {
			decoded = URLDecoder.decode(cuentas, StandardCharsets.UTF_8.toString());
			System.out.println(decoded);
			List<Cuenta> cuentasList = Arrays.asList(new Gson().fromJson(decoded, Cuenta[].class));
			for(Cuenta cuenta : cuentasList) {
				log.info(cuenta.toString());
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
    }
    
    @Test
    public void parseIntTest() {
    	BigInteger value = new BigInteger("005370097500");
    	System.out.println(value);
    }
    
}
