package cl.devetel.pagostelsur;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import cl.devetel.app.ws.WSConsultaDeuda;
import cl.devetel.pagostelsur.enums.TipoDocumentoEnum;
import cl.devetel.pagostelsur.responses.BodyDocument;
import cl.devetel.pagostelsur.responses.HeadDocument;
import cl.devetel.pagostelsur.responses.SucursalDocument;
import cl.devetel.ws.consultadeuda.ConsultaDeudaResponse;
import cl.devetel.ws.consultadeuda.Documento;

@RunWith(SpringRunner.class)
public class WSConsultaDeudaTest {
	private final Logger log = LoggerFactory.getLogger(WSConsultaDeudaTest.class);

	@Test
	public void consultaDeudaTest() throws MalformedURLException {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMddHHmmss");

		String wsdl = "http://sd34.d4.idev.cl:8080/dvt420-congen/ws/consultaDeuda/pdr.wsdl";
		String proxy = "false";

		String pIdrec = "000004";// "000003";
		String pIdsuc = "000001";
		String pIdcnl = "000001";// "000301";
		String pIdter = "000000000000000001";
		String pFectr = formato.format(new Date());
		String pSectr = "494039461007105354";
		String pIdemp = "000025"; // 24: telsur, 25:telcoy
		String pTiidc = "01";
		String pIdcli = "79605557";
		String pTicon = "02";

		WSConsultaDeuda servicio = new WSConsultaDeuda(wsdl, 10L, new Boolean(proxy));
		ConsultaDeudaResponse response = servicio.consultaDeuda(pIdrec, pIdsuc, pIdcnl, pIdter, pFectr, pSectr, pIdemp,
				pTiidc, pIdcli, pTicon);
		log.info("--------------------------- ");
		log.info("resultados : ");
		log.info("--> " + response.getRNdoc());
		log.info("--> " + response.getRRet());
		log.info("--> " + response.getRRetcod());
		log.info("--> " + response.getRRetmsg());
		log.info("--> " + response.getRTid());
		log.info("--> " + response.getRDocs().size() + " documentos : ");
		
		for (Documento documento : response.getRDocs()) {
			log.info("----> " + documento.getRDoc());
			log.info("----> " + documento.getRFem());
			log.info("----> " + documento.getRFve());
			log.info("----> o1 : " + documento.getRIo1());
			log.info("----> o2 :" + documento.getRIo2());
			log.info("----> o3 :" + documento.getRIo3());
			log.info("----> o4 :" + documento.getRIo4());
			log.info("----> o5 :" + documento.getRIo5());
			log.info("----> " + documento.getRMnt());
			log.info("----> " + documento.getRMnv());
			log.info("----> " + documento.getRNom());
			log.info("----> " + documento.getRSrv());
			log.info("----> " + documento.getRTdo());
			log.info("--------------------------- ");
		}
	}

	/**
	 * Testea la correcta agrupacion de deudas mediante objeto stream utilizando
	 * la consulta de deudas a webservice PDR.
	 * @throws MalformedURLException
	 * @throws ParseException 
	 */
	@Test
	public void consultaDeudaAgrupadaTest() throws MalformedURLException, ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMddHHmmss");

		String wsdl = "http://sd34.d4.idev.cl:8080/dvt420-congen/ws/consultaDeuda/pdr.wsdl";
		String proxy = "false";

		String pIdrec = "000004";// "000003";
		String pIdsuc = "000001";
		String pIdcnl = "000001";// "000301";
		String pIdter = "000000000000000001";
		String pFectr = formato.format(new Date());
		String pSectr = "494039461007105354";
		String pIdemp = "000025"; // 24: telsur, 25:telcoy
		String pTiidc = "01";
		String pIdcli = "79605557";
		String pTicon = "02";

		WSConsultaDeuda servicio = new WSConsultaDeuda(wsdl, 10L, new Boolean(proxy));
		ConsultaDeudaResponse response = servicio.consultaDeuda(pIdrec, pIdsuc, pIdcnl, pIdter, pFectr, pSectr, pIdemp,
				pTiidc, pIdcli, pTicon);

		//agrupa por campo RIo1
		List<Documento> documentoList = response.getRDocs();
		Map<String, List<Documento>> documentoByRIo2 = documentoList.stream()
				.collect(Collectors.groupingBy(Documento::getRIo2));
		
		int montoTotal = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		HeadDocument headDocument = new HeadDocument();
		headDocument.setTransaccionId("1252854561");
		List<SucursalDocument> sucursalDocumentList =  new ArrayList<>();
		
		for (Map.Entry<String, List<Documento>> entry : documentoByRIo2.entrySet()){
			SucursalDocument sucursalDocument = new SucursalDocument();
			List<Documento> dctoList = (List<Documento>) entry.getValue();
			List<BodyDocument> bodyDocumentList = new ArrayList<>();
			for(Documento d : dctoList) {
				BodyDocument bodyDocument = new BodyDocument();
				bodyDocument.setFechaDocumento(formatter.parse(d.getRFem()));
				bodyDocument.setFechaVencimiento(formatter.parse(d.getRFve()));
				bodyDocument.setMonto(d.getRMnt());
				bodyDocument.setTipoDocumento(TipoDocumentoEnum.getById(d.getRTdo()).getDescripcion());
				bodyDocument.setNumeroDocumento(d.getRDoc());
				bodyDocumentList.add(bodyDocument);
			}
			
			int total = dctoList.stream().filter(o -> Integer.parseInt(o.getRMnt().substring(0, o.getRMnt().length() - 2)) > 10).mapToInt(o -> Integer.parseInt(o.getRMnt())).sum();
			montoTotal =+ total;
			sucursalDocument.setTipo(entry.getKey());
			sucursalDocument.setTotal(String.valueOf(total));
			sucursalDocument.setDocumentList(bodyDocumentList);
			sucursalDocumentList.add(sucursalDocument);
			
		}
		
		headDocument.setSucursalList(sucursalDocumentList);
		log.info("Head:");
		log.info("transaccion prueba : " + headDocument.getTransaccionId());
		log.info("codigo retorno : " + headDocument.getCodigoRetornoTelcoy());
		
		//recorre respuesta
		for(SucursalDocument sucursal : headDocument.getSucursalList()) {
			log.info("Sucursal");
			log.info("--> " +  sucursal.getCodigo());
			log.info("--> " +  sucursal.getTipo());
			log.info("--> " +  sucursal.getTotal());

			log.info("Documentos :");
			for(BodyDocument body : sucursal.getDocumentList()) {
				log.info("----> " +  body.getMonto());
				log.info("----> " +  body.getNumeroDocumento());
				log.info("----> " +  body.getTipoDocumento());
				log.info("----> " +  body.getFechaDocumento());
				log.info("----> " +  body.getFechaVencimiento());
				log.info("------------------------------------");
			}
		}
		
		
			
		
	}
}
