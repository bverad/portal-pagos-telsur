package cl.devetel.pagostelsur.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.devetel.gmp.core.model2.Transaccion;
import cl.devetel.gmp.model.core.Comprobante;
import cl.devetel.pagostelsur.core.domain.DvtTlTrxHead;
import cl.devetel.pagostelsur.core.repositories.TrxHeadRepository;
import cl.devetel.pagostelsur.core.to.ComprobantesAnteriores;
import cl.devetel.pagostelsur.enums.ConstantsEnum;
import cl.devetel.pagostelsur.services.PagosService;

@RestController
@RequestMapping("/api")
public class PagosRestController {

	private final Logger log = LoggerFactory.getLogger(PagosRestController.class);

	@Autowired
	private TrxHeadRepository repository;

	@Autowired
	private PagosService pagosService;

	@Autowired
	@Qualifier("messageSource")
	private ReloadableResourceBundleMessageSource messageSource;

	/**
	 * retorna comprobantes
	 * 
	 * @param idcli
	 * @param empresa
	 * @return objeto json con comprobantes anteriores
	 */
	@RequestMapping(value = "/pagos/listar_comprobantes/{idcli}/{empresa}", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public List<ComprobantesAnteriores> listarComprobantes(@PathVariable("idcli") String idcli,
			@PathVariable("empresa") String empresa) {
		List<ComprobantesAnteriores> caList = pagosService.listarComprobantes(idcli, empresa);
		return caList;

	}

	/**
	 * Ingresa comprobante anterior a cola para posterior envio
	 * 
	 * @param idtrx
	 * @param empresa
	 * @param mail
	 * @return true o false en caso de que funcione o falle la transaccion.
	 * @author bverad
	 * @since 14/11/2018
	 */
	@RequestMapping(value = "/pagos/sendComprobante/{idtrx}/{mail}", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<Map<String, Boolean>> sendComprobante(@PathVariable("idtrx") String idtrx,
			@PathVariable("mail") String mail) {
		log.info("Enviando email a remitente {}, para la transaccion : {}", mail, idtrx);
		Map<String, Boolean> response = new HashMap<>();
		Boolean resultInsert = true;

		try {
			Optional<DvtTlTrxHead> optionalHead = repository.findById(idtrx);
			DvtTlTrxHead head = optionalHead.get();
			Transaccion transaccion = new Transaccion();
			transaccion.setIdTrx(idtrx);
			transaccion.setIdRecaudador(head.getIdrec());
			transaccion.setIdSucursal(head.getIdsuc());
			transaccion.setIdCanal(head.getIdcnl());
			transaccion.setIdTerminal(head.getIdter());
			transaccion.setIdEmpresa(head.getIdemp());
			transaccion.setIdTrxMedioPago(head.getIdtrxMp());

			Comprobante c = pagosService.obtenerComprobanteParaMail(transaccion);

			String idpla = pagosService.obtenerParametro(ConstantsEnum.IDPLAMAIL.getId());
			String idemp = pagosService.obtenerParametro(ConstantsEnum.IDEMPMAIL.getId());
			String remitente = pagosService.obtenerParametro(ConstantsEnum.REMITENTEMAIL.getId());
			String asunto = pagosService.obtenerParametro(ConstantsEnum.ASUNTOMAIL.getId());
			String cuerpo = "";

			cuerpo = pagosService.generarCuerpoMail(c,
					pagosService.obtenerTransaccionConIDGMP(transaccion.getIdTrxMedioPago()));
			
			pagosService.enviarCorreoSinAdjuntos(c.getIdentificador(), idpla, idemp, remitente, mail, asunto, cuerpo);

			response.put("ok", resultInsert);
		} catch (Exception e) {
			log.error("{} ", e);
			resultInsert = false;
			response.put("ok", resultInsert);
		}

		
		return new ResponseEntity<Map<String, Boolean>>(response, HttpStatus.OK);
		// return new String("hola");
	}

	/**
	 * Actualiza mail del cliente para posterior notificacion al realizar pago.
	 * 
	 * @param transaccion
	 * @param mail
	 * @return codigo y mensaje. En el caso de que exista un error retornara el
	 *         codigo 99 de lo contrario el codigo 0.
	 */
	@RequestMapping(value = "/pagos/actualizar_mailcliente", method = {RequestMethod.POST, RequestMethod.GET})
	public ResponseEntity<Map<String, String>> actualizarMailCliente(
			@RequestParam(name = "transaccion") String transaccion, @RequestParam(name = "mail") String mail) {
		Map<String, String> hashMap = new ConcurrentHashMap<String, String>();
		try {
			// actualizando mail para transaccion en contexto.
			DvtTlTrxHead trxHead = repository.findByIdtrx(transaccion);
			trxHead.setMailCli(mail);
			repository.save(trxHead);
			hashMap.put("retorno", "0");
			hashMap.put("mensaje", "El registro fue actualizado correctamente");
		} catch (Exception e) {
			log.info("Ocurrio un problema al actualizar mail del cliente : " + e.getMessage());
			hashMap.put("retorno", "99");
			hashMap.put("mensaje", "Hubo un problema al actualizar registro :" + e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<Map<String, String>>(hashMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/pagos/test", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String, Boolean>> test() {
		Map<String, Boolean> response = new HashMap<>();
		response.put("ok", true);
		return new ResponseEntity<Map<String, Boolean>>(response, HttpStatus.OK);

	}

}
