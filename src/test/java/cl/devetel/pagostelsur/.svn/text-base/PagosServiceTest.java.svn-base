package cl.devetel.pagostelsur;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import cl.devetel.canalgmp.jdbc.dao.TransaccionDao;
import cl.devetel.canalgmp.jdbc.dao.mysql.Impl.TransaccionDaoImpl;
import cl.devetel.gmp.core.facade.GmpManager;
import cl.devetel.gmp.core.model2.Transaccion;
import cl.devetel.pagostelsur.config.ApplicationProperties;
import cl.devetel.pagostelsur.config.CoreDbConfig;
import cl.devetel.pagostelsur.config.ProvDbConfig;
import cl.devetel.pagostelsur.enums.ConstantsEnum;
import cl.devetel.pagostelsur.enums.EmpresaEnum;
import cl.devetel.pagostelsur.prov.domain.DvtTpParamWeb;
import cl.devetel.pagostelsur.prov.repositories.ParametrosWebRepository;
import cl.devetel.pagostelsur.responses.BodyDocument;
import cl.devetel.pagostelsur.responses.BotonMedioPago;
import cl.devetel.pagostelsur.responses.Cuenta;
import cl.devetel.pagostelsur.responses.HeadDocument;
import cl.devetel.pagostelsur.responses.SucursalDocument;
import cl.devetel.pagostelsur.services.PagosService;
import cl.devetel.pagostelsur.services.PagosServiceImpl;
import cl.devetel.ws.consultadeuda.ConsultaDeudaResponse;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("default")
//parametro que utiliza las configuraciones indicadas en la anotacion contextconfiguration
@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED)
@ContextConfiguration(classes={CoreDbConfig.class, ProvDbConfig.class})

public class PagosServiceTest {
	private final Logger log = LoggerFactory.getLogger(PagosServiceTest.class);

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private ParametrosWebRepository repository;

	@TestConfiguration
	static class PagosServiceImplTestContextConfiguration {

		@Bean
		public PagosService pagosService() {
			return new PagosServiceImpl();
		}
		
		@Bean
		public TransaccionDao transaccion() {
			return new TransaccionDaoImpl();
		}
		
		@Bean
		public ApplicationProperties applicationProperties() {
			return new ApplicationProperties();
		}
		
	}

	
	@Autowired
    private TransaccionDao transaccionDao;
	@Autowired
	private PagosService pagosService;

	/**
	 * Test para consulta a PDR
	 * @author bvera
	 * @throws MalformedURLException
	 * @throws ParseException 
	 */
	@Test
	public void consultaDeudaTest() throws MalformedURLException, ParseException {
		log.info("-------------------------------------------------------");
		DvtTpParamWeb param = repository.findByIdParamNom("PDR_CONSULTA_DEUDA_WSURL");
		log.info(param.getParamVal());

		Transaccion transaccion = new Transaccion();
		transaccion.setIdTrx("494039461007105354");
		transaccion.setIdCliente("813805006");
		//ubicar en el mismo orden tanto los ids como los enums para la descripcion de empresa
		LinkedHashMap<String, ConsultaDeudaResponse> deudaResponseMap = pagosService.obtenerPagos(transaccion, 
																ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELSUR.getId(),
																ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELCOY.getId());
		HeadDocument headDocument = pagosService.obtenerDocumento(deudaResponseMap, transaccion,
																EmpresaEnum.TELSUR.getDescripcion(),
																EmpresaEnum.TELCOY.getDescripcion());
		
		log.info("Head:");
		log.info("transaccion prueba : " + headDocument.getTransaccionId());
		log.info("registros TELSUR   : " + headDocument.getCantidadRegistrosTelsur());
		log.info("registros TELCOY   : " + headDocument.getCantidadRegistrosTelcoy());
		//recorre respuesta
		for(SucursalDocument sucursal : headDocument.getSucursalList()) {
			log.info("Sucursal");
			log.info("--> empresa..:" +  sucursal.getEmpresa());
			log.info("--> tipo.....:" +  sucursal.getTipo());
			log.info("--> total....:" +  sucursal.getTotal());

			log.info("Documentos :");
			for(BodyDocument body : sucursal.getDocumentList()) {
				log.info("----> " +  body.getNumeroDocumento());
				log.info("----> " +  body.getMonto());
				log.info("----> " +  body.getTipoDocumento());
				log.info("----> " +  body.getFechaDocumento());
				log.info("----> " +  body.getFechaVencimiento());
				log.info("------------------------------------");
			}
		}

	}
	
	/**
	 * Test para consulta a PDR
	 * @author bvera
	 * @throws MalformedURLException
	 * @throws ParseException 
	 */
	@Test
	public void validacionSeleccionDeudaTest() throws MalformedURLException, ParseException, Exception {
		log.info("-------------------------------------------------------");
		DvtTpParamWeb param = repository.findByIdParamNom("PDR_CONSULTA_DEUDA_WSURL");
		log.info(param.getParamVal());

		Transaccion transaccion = new Transaccion();
		transaccion.setIdTrx("494039461007105354");
		transaccion.setIdCliente("813805006");
		//ubicar en el mismo orden tanto los ids como los enums para la descripcion de empresa
		LinkedHashMap<String, ConsultaDeudaResponse> deudaResponseMap = pagosService.obtenerPagos(transaccion, 
																ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELSUR.getId(),
																ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELCOY.getId());
		HeadDocument headDocument = pagosService.obtenerDocumento(deudaResponseMap, transaccion,
																EmpresaEnum.TELSUR.getDescripcion(),
																EmpresaEnum.TELCOY.getDescripcion());

		
		List<Cuenta> cuentasList = new ArrayList<>();
		Cuenta cuenta = new Cuenta();
		cuenta.setNmro_documen("11305362");
		cuenta.setSaldo("25.531"); //original
		//cuenta.setSaldo("15.531"); //modificado
		cuentasList.add(cuenta);
		
		cuenta = new Cuenta();
		cuenta.setNmro_documen("11330966");
		cuenta.setSaldo("300");
		cuentasList.add(cuenta);
		
		log.info("Inicializando validacion de montos seleccionados contra montos obtenidos desde PDR");
		Boolean result = true;
		for(Cuenta c : cuentasList) {
			//recorre respuesta
			for(SucursalDocument sucursal : headDocument.getSucursalList()) {
				for(BodyDocument body : sucursal.getDocumentList()) {
					BigInteger v1 = new BigInteger("0");
					BigInteger v2 = new BigInteger("0");
					if(c.getNmro_documen().equals(body.getNumeroDocumento())) {
						log.info("Numero de documento...:" + body.getNumeroDocumento());
						log.info("Saldo PDR.............:" + body.getMonto());
						log.info("Saldo seleccionado....:" + c.getSaldo());
						v1 = v1.add(new BigInteger(body.getMonto().replace(".", "")));
						v2 = v2.add(new BigInteger(c.getSaldo().replace(".", "")));
						if(v1.compareTo(v2) != 0) {
							log.info("El valor seleccionado no coincide con el obtenido desde PDR.");
							result = false;
						}
						log.info("---------------------------------------");
					}
				}
			}
		}
		
		log.info("Coinciden los valores seleccionados con los obtenidos desde PDR ? : {}", result);
		
	}

	/**
	 * Lista los beans registrados en application context
	 * @author bverad
	 * @throws Exception 
	 * @since 16/05/2018
	 */
	@Test
	public void consultaBeansRegistrados() throws Exception {
		log.info("transaccion dao test : " + transaccionDao.obtenerTransaccionPorIdGMP("155456"));
		
		String[] beans = appContext.getBeanDefinitionNames();
		Arrays.sort(beans);
		for (String bean : beans) {
			log.info(bean);
		}
	}
	
	/**
	 * Test para consultar botones por transaccion.
	 * @throws Exception 
	 * @throws RemoteException 
	 * 
	 */
	//@Test 
	public void consultaBotonesMP() throws RemoteException, Exception {
		log.info("Creando transaccion");
		Transaccion transaccion = new Transaccion();
		transaccion.setMonto("10000");
		transaccion.setIdTrx("1234");
		transaccion.setIdCliente("10015241K");
		transaccion.setIdEmpresa("000024");
		transaccion.setIdCanal("16");
		
		log.info("Obteniendo botones de medios de pago");
		String empresa = "TELSUR";
		GmpManager gmpManager = pagosService.prepararGMP(empresa, transaccion);
		
		List<BotonMedioPago> botones;
		botones = pagosService.obtenerBotones(gmpManager, transaccion, ConstantsEnum.GMP_PAGRE.getId());
		if(!botones.isEmpty()) {
			for (BotonMedioPago boton : botones) {
				log.info("id............: " + boton.getId());
				log.info("url...........: " + boton.getUrl());
				log.info("img...........: " + boton.getImg());
				log.info("modo pago.....: " + boton.getModoPago());
				log.info("nombre........: " + boton.getNombre());
				log.info("estado........: " + boton.getEstado());
				log.info("solicitud enc.: " + boton.getSolicitudEnc());
				
			}
		}
	}
	
	/**
	 * Testea agrupacion en base a un valor especifico de la lista mediante
	 * stream collector.
	 * @author bvera
	 * 
	 */
	@Test
	public void groupByListTest() {
		List<BodyDocument> bodyList = new ArrayList<BodyDocument>();
		//generando datos de prueba
		BodyDocument bodyDocument = new BodyDocument();
		bodyDocument.setFechaDocumento(new Date());
		bodyDocument.setFechaVencimiento(new Date());
		bodyDocument.setMonto("100");
		bodyDocument.setNumeroDocumento("1");
		bodyDocument.setTipoDocumento("TELSUR");
		bodyList.add(bodyDocument);
		
		bodyDocument = new BodyDocument();
		bodyDocument.setFechaDocumento(new Date());
		bodyDocument.setFechaVencimiento(new Date());
		bodyDocument.setMonto("200");
		bodyDocument.setNumeroDocumento("2");
		bodyDocument.setTipoDocumento("TELCOY");
		bodyList.add(bodyDocument);
		
		//stream de agrupacion.
		Map<String, List<BodyDocument>> result = bodyList.stream().
				collect(Collectors.groupingBy(BodyDocument::getTipoDocumento));
		
		for(BodyDocument body : result.get("TELSUR")) {
			log.info("fecha documento 	: " + body.getFechaDocumento());
			log.info("fecha vencimiento : " + body.getFechaVencimiento());
		    log.info("monto 			: " + body.getMonto());
			log.info("numero documento 	: " + body.getNumeroDocumento());
			log.info("tipo documento 	: " + body.getTipoDocumento());
		}
	}
	
	@Test
	public void cuerpoMailTest() {
        final String symbol = "=";
        final String endsym = ";";
        final String endlinedet = ",";
        final String enddet = "|";
        
		final String[] __a  = new String[]{"MONTO","RUT","NUMCOMPROBANTE","CODIGOAUTMPAGO","FINALNUMTARJETA","TIPOCUOTA","NUMCUOTA","COMERCIO","URLCOM","DETALLE"};
		StringBuilder cuerpo = new StringBuilder();
        cuerpo = cuerpo.append( __a[0] ).append( symbol ).append( "452.258" ).append( endsym );
		cuerpo = cuerpo.append( __a[1] ).append( symbol ).append( "1-9" ).append( endsym );
		cuerpo = cuerpo.append( __a[2] ).append( symbol ).append( "12345678" ).append( endsym );
		cuerpo = cuerpo.append( __a[3] ).append( symbol ).append( "321" ).append( endsym );
		cuerpo = cuerpo.append( __a[4] ).append( symbol ).append( "4556" ).append( endsym );
		cuerpo = cuerpo.append( __a[5] ).append( symbol ).append( "tipo cuotas" ).append( endsym );
		cuerpo = cuerpo.append( __a[6] ).append( symbol ).append( "0" ).append( endsym );
		cuerpo = cuerpo.append( __a[7] ).append( symbol ).append( "Telefonica Telsur" ).append( endsym );
		cuerpo = cuerpo.append( __a[8] ).append( symbol ).append( "www.telsur.cl" ).append( endsym );
		
        //calcular detalle e incluirlo en el cuerpo.
        cuerpo = cuerpo.append( __a[9] ).append( symbol );    
    	//definir campo que contendra tipo de documento
    	cuerpo.append("TIPODOC").append(symbol).append("boleta").append(endlinedet);
    	cuerpo.append("DOC").append(symbol).append("0001").append(endlinedet);
    	cuerpo.append("DOCEMI").append(symbol).append(new Date()).append(endlinedet);
    	cuerpo.append("DOCVEN").append(symbol).append(new Date()).append(endlinedet);
    	cuerpo.append("MONTO").append(symbol).append("400.000");
    	cuerpo.append(enddet);
    	
    	cuerpo.append("TIPODOC").append(symbol).append("factura").append(endlinedet);
    	cuerpo.append("DOC").append(symbol).append("0002").append(endlinedet);
    	cuerpo.append("DOCEMI").append(symbol).append(new Date()).append(endlinedet);
    	cuerpo.append("DOCVEN").append(symbol).append(new Date()).append(endlinedet);
    	cuerpo.append("MONTO").append(symbol).append("52.258");
    	cuerpo.append(enddet);
        
        //punto y coma final
        cuerpo.append(endsym);
        
        log.info("Largo cuerpo mail : " + cuerpo.length());
        log.info("Valor cuerpo mail : \n" + cuerpo.toString());
	}
}
