
package cl.devetel.pagostelsur.controllers;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import cl.devetel.common.utils.Validator;
import cl.devetel.gmp.core.facade.GmpManager;
import cl.devetel.gmp.core.model.SolicitarCreditoResponse;
import cl.devetel.gmp.core.model2.Transaccion;
import cl.devetel.gmp.model.core.Comprobante;
import cl.devetel.pagostelsur.config.ApplicationProperties;
import cl.devetel.pagostelsur.core.domain.DvtTlTrxBody;
import cl.devetel.pagostelsur.core.domain.DvtTlTrxHead;
import cl.devetel.pagostelsur.enums.CodigoRetornoGMPEnum;
import cl.devetel.pagostelsur.enums.CodigoRetornoPDREnum;
import cl.devetel.pagostelsur.enums.ConstantsEnum;
import cl.devetel.pagostelsur.enums.EmpresaEnum;
import cl.devetel.pagostelsur.enums.EstadoTransaccionEnum;
import cl.devetel.pagostelsur.responses.BodyDocument;
import cl.devetel.pagostelsur.responses.BotonMedioPago;
import cl.devetel.pagostelsur.responses.Cuenta;
import cl.devetel.pagostelsur.responses.HeadDocument;
import cl.devetel.pagostelsur.responses.SucursalDocument;
import cl.devetel.pagostelsur.services.PagosService;
import cl.devetel.pagostelsur.services.SessionService;
import cl.devetel.ws.consultadeuda.ConsultaDeudaResponse;

@Controller
@SessionAttributes("TRXSESSION")
public class PagosController {

	private final Logger log = LoggerFactory.getLogger(PagosController.class);

	@Autowired
	private PagosService pagosService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ApplicationProperties applicationProperties;

	@ModelAttribute("TRXSESSION")
	public Transaccion transaccion() {
		return sessionService.obtieneTransaccion();
	}

	@ModelAttribute("documento")
	public HeadDocument headDocument() {
		return sessionService.getDocument();
	}

	/**
	 * Determina url de header and footer segun url request, este puede corresponder
	 * a telsur o a GTD.
	 * 
	 * @param request
	 * @return map con header y footer
	 * @author bverad
	 * @since 26/11/2018
	 */
	@ModelAttribute("headerAndfooter")
	public HashMap<String, String> setUpHeaderAndFooter(HttpServletRequest request) {
		HashMap<String, String> headerAndFooterMap = new HashMap<>();
		String urlTelsur = applicationProperties.getUrl().get("telsur");
		if (request.getServerName().equals(urlTelsur)) {
			headerAndFooterMap.put("tipoHeaderAndFooter", "telsur");
			headerAndFooterMap.put("header", applicationProperties.getHeader().get("telsur"));
			headerAndFooterMap.put("footer", applicationProperties.getFooter().get("telsur"));
		} else {
			headerAndFooterMap.put("tipoHeaderAndFooter", "gtd");
			headerAndFooterMap.put("header", applicationProperties.getHeader().get("gtd"));
			headerAndFooterMap.put("footer", applicationProperties.getFooter().get("gtd"));
		}
		return headerAndFooterMap;
	}


	/**
	 * Inicializa transacciones y despliega pantalla de consulta de deudas por rut.
	 * 
	 * @param model
	 * @author bvera
	 * @return
	 * 
	 */
	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request, Model model,
			@ModelAttribute("TRXSESSION") Transaccion transaccion,
			@ModelAttribute("headerAndfooter") HashMap<String, String> headerAndFooter,
			@RequestParam(value = "mensaje", required = false) String mensaje,
			@RequestParam(value = "regresar", required = false) String regresar) {
		log.info("------------------------------------------------------------------------------");
		log.info("Ingresando a consulta de deuda");
		sessionService.inicializaTransaccion();
		sessionService.limpiarValores();
		// limpia valores de session al inicio
		ModelAndView mv = new ModelAndView("index");
		if (mensaje != null) {
			mv.addObject("mensaje", mensaje);
		}
		
		String isServicioDisponible = pagosService.obtenerParametro(ConstantsEnum.IS_SERVICIO_DISPONIBLE.getId());
		String mensajeFueraServicio = pagosService.obtenerParametro(ConstantsEnum.MENSAJE_FUERA_SERVICIO.getId());
		
		log.info("isServicioDisponible : {}", isServicioDisponible);
		log.info("mensajeFueraServicio : {}", mensajeFueraServicio);
		
		// header y footer
		mv.addObject("isServicioDisponible", isServicioDisponible);
		mv.addObject("mensajeFueraServicio", mensajeFueraServicio);
		mv.addObject("header", headerAndFooter.get("header"));
		mv.addObject("footer", headerAndFooter.get("footer"));

		return mv;
	}

	private void sessionData(Model model, HttpServletRequest request, HttpSession session) {
		Transaccion transaccion = sessionService.obtieneTransaccion();
		if (transaccion == null) {
			DvtTlTrxHead head = pagosService.obtenerUltimaTransaccion();
			transaccion = new Transaccion();
			transaccion.setIdTrx(head.getIdtrx());
			transaccion.setIdCanal(head.getIdcnl());
			transaccion.setIdEmpresa(head.getIdemp());
			transaccion.setMonto(String.valueOf(head.getMnt()));
			transaccion.setIdCliente(head.getIdcli());
			transaccion.setIdTrxMedioPago(head.getIdtrxMp());
		}
		log.info("[{}] : ------------------------------------------------------------------------------",
				transaccion.getIdTrx());
		log.info("[{}] : Listando datos de entrada.", transaccion.getIdTrx());
		log.info("[{}] : --- INPUT --------------------------------------------------------------------",
				transaccion.getIdTrx());
		Map modelMap = model.asMap();
		for (Object modelKey : modelMap.keySet()) {
			Object modelValue = modelMap.get(modelKey);
			log.info("[{}] : ----- " + modelKey + " : " + modelValue, transaccion.getIdTrx());
		}

		/*
		 * log.info("=== Request data ==="); java.util.Enumeration<String> reqEnum =
		 * request.getAttributeNames(); while (reqEnum.hasMoreElements()) { String s =
		 * reqEnum.nextElement(); log.info(s); log.info("==" + request.getAttribute(s));
		 * }
		 */

		log.info("[{}] : --- SESSION ------------------------------------------------------------------",
				transaccion.getIdTrx());
		Enumeration<String> e = session.getAttributeNames();
		while (e.hasMoreElements()) {
			String s = e.nextElement();
			log.info("[{}] : " + s, transaccion.getIdTrx());
			log.info("[{}] : ----- " + session.getAttribute(s), transaccion.getIdTrx());
		}
	}

	/**
	 * Lista pagos por defecto posterior a busqueda mediante rut.
	 * 
	 * @param rut
	 * @param empresa
	 * @param model
	 * @return
	 * @author bvera
	 * @throws ParseException
	 * @since 11/05/2018
	 * 
	 */
	@RequestMapping(value = "/pagos/lista_deudas", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView list(@RequestParam(value = "txtRut", required = false) String rut,
			@ModelAttribute("TRXSESSION") Transaccion transaccion, @ModelAttribute("documento") HeadDocument hdocument,
			@ModelAttribute("headerAndfooter") HashMap<String, String> headerAndFooter, HttpServletRequest request,
			HttpServletResponse response, Model model) throws ParseException {

		// para conservar valores al presionar boton atras en browser
		String headerValue = CacheControl.maxAge(60, TimeUnit.SECONDS).getHeaderValue();
		response.addHeader("Cache-Control", headerValue);

		// No permite llamadas de tipo GET directamente.
		ModelAndView mv = new ModelAndView("lista_deudas");
		if (request.getMethod().equals("GET")) {
			mv.setViewName("redirect:/");
			return mv;
		}

		String mensaje = "";

		// header y footer
		mv.addObject("header", headerAndFooter.get("header"));
		mv.addObject("footer", headerAndFooter.get("footer"));

		// crea una nueva transaccion al realizar una nueva consulta
		transaccion = sessionService.getTransaccion();
		transaccion.setIdCliente(rut.toUpperCase());
		log.info("[{}] : -----------------------------------------------------------------------",
				transaccion.getIdTrx());
		log.info("[{}] Iniciando obtencion de deudas para cliente : {} ", transaccion.getIdTrx(), rut);
		// ubicar en el mismo orden tanto los ids como los enums para la descripcion de
		// empresa
		LinkedHashMap<String, ConsultaDeudaResponse> deudaResponseMap = null;
		try {
			deudaResponseMap = pagosService.obtenerPagos(transaccion,
					ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELSUR.getId(),
					ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELCOY.getId());
		} catch (Exception e) {

			log.error("[{}] Error al obtener pagos desde webservice.", transaccion.getIdTrx());
			e.printStackTrace();
			mensaje = "En este momento tenemos inconvenientes para atenderte. Por favor intenta más tarde.";
			mv.addObject("mensaje", mensaje);
			mv.setViewName("forward:/");
			return mv;

		}

		HeadDocument document = null;
		try {
			document = pagosService.obtenerDocumento(deudaResponseMap, transaccion, EmpresaEnum.TELSUR.getDescripcion(),
					EmpresaEnum.TELCOY.getDescripcion());
			// asigna valor a sesion para comparativa de montos antes de desplegar botones
			// de pago
			sessionService.setDocument(document);

		} catch (Exception e) {

			log.error("[{}] : Error al generar objeto de deudas", transaccion.getIdTrx());
			e.printStackTrace();
			mensaje = "En este momento tenemos inconvenientes para atenderte. Por favor intenta más tarde.";
			mv.addObject("mensaje", mensaje);
			mv.setViewName("forward:/");
			return mv;

		}

		// validacion de existencia o de codigo de retorno erroneo
		if (!document.getCodigoRetornoTelsur().equals(CodigoRetornoPDREnum.CODE_00.getCodigo())
				&& !document.getCodigoRetornoTelcoy().equals(CodigoRetornoPDREnum.CODE_00.getCodigo())) {

			if (document.getCodigoRetornoTelsur().equals(CodigoRetornoPDREnum.CODE_03.getCodigo())
					|| document.getCodigoRetornoTelcoy().equals(CodigoRetornoPDREnum.CODE_03.getCodigo())) {
				mensaje = "SIN DOCUMENTOS PENDIENTES";
			} else {
				mensaje = "En este momento tenemos inconvenientes para atenderte. Por favor intenta más tarde.";
				log.error("[{}] : ERROR EN CONSULTA A PDR : {}", transaccion.getIdTrx(),
						CodigoRetornoPDREnum.getById(document.getCodigoRetornoTelsur()));
			}

			// redirige al inicio si no existen registros
			mv.addObject("mensaje", mensaje);
			mv.setViewName("forward:/");
			// si no hay inconvenientes setea flag de lista de deudas en el caso de volver
			// atras en la historia a traves de browser
		}

		// agregando datos a response
		mv.addObject("rut", rut);
		mv.addObject("rutFormateado", Validator.formatearRUT(rut));
		mv.addObject("response", document);

		sessionData(model, request, request.getSession());

		return mv;
	}

	/**
	 * Recibe el total a pagar para el registro de transacciones correspondiente
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/pagos/seleccion_mp", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView seleccionarMedioPago(@RequestParam(value = "rut", required = false) String rut,
			@RequestParam(value = "_token", required = false) String token,
			@RequestParam(value = "company", required = false) String empresa,
			@RequestParam(value = "montoTotal", required = false) String montoTotal,
			@RequestParam(value = "cuentas", required = false) String cuentas,
			@ModelAttribute("TRXSESSION") Transaccion transaccion, @ModelAttribute("documento") HeadDocument hdocument,
			@ModelAttribute("headerAndfooter") HashMap<String, String> headerAndFooter, HttpServletRequest request,
			HttpServletResponse response, Model model) throws UnsupportedEncodingException {

		// para conservar valores al presionar boton atras en browser
		String headerValue = CacheControl.maxAge(60, TimeUnit.SECONDS).getHeaderValue();
		response.addHeader("Cache-Control", headerValue);

		transaccion = sessionService.obtieneTransaccion();
		log.info("[{}] : -----------------------------------------------------------------------",transaccion.getIdTrx());

		ModelAndView mv = new ModelAndView("seleccion_mp");
		// segun url establece el parametro para contexto de retorno
		String parametro = headerAndFooter.get("tipoHeaderAndFooter").equalsIgnoreCase(
				EmpresaEnum.TELSUR.getDescripcion()) ? ConstantsEnum.BACKBTN.getId() : ConstantsEnum.BACKBTNGTD.getId();
		String backBtn = pagosService.obtenerParametro(parametro);

		// redirige ante una llamada GET directa o si ya se efectuo el pago de la
		// transaccion previamente
		if (request.getMethod().equals("GET")) {
			if (transaccion != null)
				log.info("[{}] : Accediendo a seleccion medio de pago a traves de url", transaccion.getIdTrx());
			else
				log.info("Accediendo a seleccion medio de pago a traves de url");
			mv.setViewName("redirect:/");
			return mv;

		}


		log.info("[{}] : Inicializando seleccion de medio de pago para empresa {}", transaccion.getIdTrx(), empresa);
		log.info("[{}] : Obteniendo parametro para boton de retorno", transaccion.getIdTrx());
		log.info("[{}] : Generando lista de cuentas........", transaccion.getIdTrx());
		List<Cuenta> cuentasList;
		try {
			log.info("[{}] : cuentas : {}", transaccion.getIdTrx(), cuentas);
			String decoded = URLDecoder.decode(cuentas, StandardCharsets.UTF_8.toString());
			cuentasList = Arrays.asList(new Gson().fromJson(decoded, Cuenta[].class));

			log.info("[{}] : Inicializando validacion de montos seleccionados contra montos obtenidos desde PDR",
					transaccion.getIdTrx());
			Boolean result = true;
			BigInteger totalSeleccionado = BigInteger.valueOf(0);
			for (Cuenta c : cuentasList) {
				// recorre respuesta
				for (SucursalDocument sucursal : hdocument.getSucursalList()) {
					for (BodyDocument body : sucursal.getDocumentList()) {
						BigInteger v1 = BigInteger.valueOf(0);
						BigInteger v2 = BigInteger.valueOf(0); 
						if (c.getNmro_documen().equals(body.getNumeroDocumento())) {
							if(transaccion != null) {
								String monto = body.getMonto().replace(".", "").replace(",", "");
								String saldo = c.getSaldo().replace(".", "").replace(",", ""); 
								log.info("[{}] : Numero de documento...: {}", transaccion.getIdTrx(), body.getNumeroDocumento());
								log.info("[{}] : Saldo PDR.............: {}", transaccion.getIdTrx(), monto);
								log.info("[{}] : Saldo seleccionado....: {}", transaccion.getIdTrx(), saldo);
							}
							
							v1 = v1.add(new BigInteger(body.getMonto().replace(".", "").replace(",", "")));
							v2 = v2.add(new BigInteger(c.getSaldo().replace(".", "").replace(",", "")));
							totalSeleccionado = totalSeleccionado.add(v2);
							if (v1.compareTo(v2) != 0) {
								log.info("[{}] : El valor seleccionado no coincide con el obtenido desde PDR", transaccion.getIdTrx());
								result = false;
							}
							log.info("[{}] : -----------------------------------------------------------------------", transaccion.getIdTrx());
						}
					}
				}
			}

			// compara totales
			BigInteger totalPdr = new BigInteger(hdocument.getMontoTotal().replace(".", "").replace(",", ""));
			log.info("[{}] : Comparando totales.", transaccion.getIdTrx());
			log.info("[{}] : Total PDR............: {}", transaccion.getIdTrx(), totalPdr);
			log.info("[{}] : Total seleccionado...: {}", transaccion.getIdTrx(), totalSeleccionado);
			if (totalPdr.compareTo(totalSeleccionado) != 0) {
				result = false;
			}

			log.info("[{}] : Coinciden los valores seleccionados con los obtenidos desde PDR ? : {}",
					transaccion.getIdTrx(), result);
			if (!result) {
				mv.addObject("mensaje",
						"Los montos seleccionados no coinciden con los registrados en la plataforma de deudas.");
				mv.setViewName("forward:/");
				return mv;
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			String errorMsg = "[{}] : Las cuentas seleccionadas no han sido procesadas con éxito.";
			log.error(errorMsg, transaccion.getIdTrx());
			mv.addObject("error", errorMsg);
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = "[{}] : Las cuentas seleccionadas no han sido procesadas con éxito.";
			log.error(errorMsg, transaccion.getIdTrx());
			mv.addObject("error", errorMsg);
			return mv;
		}

		log.info("[{}] : Seteando valores de transaccion.", transaccion.getIdTrx());
		try {
			// verifica si la transaccion existe antes de guardarla, en tal caso la crea
			log.info("[{}] : Verificando existencia de transaccion y si medio de pago retorno respuesta",
					transaccion.getIdTrx());
			if (pagosService.obtenerTransaccion(transaccion.getIdTrx())) {
				Transaccion t = sessionService.resetTransaccion();
				log.info("[{}] : Se obtuvo nueva transaccion : {} ", t.getIdTrx(), t.getIdTrx());
				t.setIdCliente(rut.toUpperCase());
				t.setMonto(montoTotal.replace(".", "").replace(",", ""));
				t.setIdEmpresa(empresa.equals(EmpresaEnum.TELSUR.getDescripcion())
						? pagosService.obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELSUR.getId())
						: pagosService.obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELCOY.getId()));
				t.setNumeroDocumentos(String.valueOf(cuentasList.size()));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String fechaTransaccion = sdf.format(new Date());
				t.setFechaTransaccion(fechaTransaccion);
				t.setIdCanal(pagosService.obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDCNL.getId()));
				t.setTipoIdCliente(pagosService.obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_TIIDC.getId()));
				t.setIdSucursal(pagosService.obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDSUC.getId()));
				t.setIdRecaudador(pagosService.obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDREC.getId()));
				t.setIdSess(request.getSession().getId());
				log.info("[{}] : Calculando monto total y seteando cuentas en objeto transaccion", t.getIdTrx());
				pagosService.obtenerMontoTotal(t, cuentasList);

				// seteando transaccion con nuevo valor
				sessionService.setTransaccion(t);
				// obtiene transaccion
				transaccion = sessionService.obtieneTransaccion();

				log.info("[{}] : Almacenando transaccion en base de datos. Transaccion en contexto existente : {}",
						t.getIdTrx(), t.toString());
				pagosService.guardarTransaccion(t);

			} else {
				log.info("[{}] : Almacenando transaccion", transaccion.getIdTrx());
				transaccion.setIdCliente(rut.toUpperCase());
				transaccion.setMonto(montoTotal.replace(".", "").replace(",", ""));
				transaccion.setIdEmpresa(empresa.equals(EmpresaEnum.TELSUR.getDescripcion())
						? pagosService.obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELSUR.getId())
						: pagosService.obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELCOY.getId()));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String fechaTransaccion = sdf.format(new Date());
				transaccion.setFechaTransaccion(fechaTransaccion);
				transaccion.setIdCanal(pagosService.obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDCNL.getId()));
				transaccion.setNumeroDocumentos(String.valueOf(cuentasList.size()));
				transaccion.setTipoIdCliente(
						pagosService.obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_TIIDC.getId()));
				transaccion.setIdSess(request.getSession().getId());
				log.info("[{}] : Calculando monto total y seteando cuentas en objeto transaccion",
						transaccion.getIdTrx());
				pagosService.obtenerMontoTotal(transaccion, cuentasList);
				sessionService.setTransaccion(transaccion);
				// obtiene transaccion
				transaccion = sessionService.obtieneTransaccion();

				log.info("[{}] : Almacenando transaccion en base de datos : {}.", transaccion.getIdTrx(), transaccion);
				pagosService.guardarTransaccion(transaccion);
			}

			log.info("[{}] : Estado transaccion............: {}", transaccion.getIdTrx(), transaccion.getEstado());
			log.info("[{}] : Codigo retorno medio de pago..: {}", transaccion.getIdTrx(),
					transaccion.getCodigoRetornoMedioPago());
			log.info("[{}] : Valor transaccion : {} ", transaccion.getIdTrx(), transaccion);

		} catch (Exception e) {
			// si hay alguna falla se quita la transaccion en contexto para que al regresar
			// se trabaje con una nueva.
			String errorMsg = "La transacción no se registró con éxito";
			log.error(errorMsg);
			e.printStackTrace();
			mv.addObject("error", errorMsg);
			return mv;
		}

		GmpManager gmpManager = pagosService.prepararGMP(empresa, transaccion);
		// agregando boton volver
		mv.addObject("btnVolver", backBtn);
		log.info("[{}] : Obteniendo botones de medios de pago", transaccion.getIdTrx());
		List<BotonMedioPago> botonesList;
		try {
			// calcula pagina de respuesta de GMP segun url de acceso a sitio.
			String paginaRespuesta = headerAndFooter.get("tipoHeaderAndFooter")
					.equalsIgnoreCase(EmpresaEnum.TELSUR.getDescripcion()) ? ConstantsEnum.GMP_PAGRE.getId()
							: ConstantsEnum.GMP_PAGRE_GTD.getId();
			botonesList = pagosService.obtenerBotones(gmpManager, transaccion, paginaRespuesta);
		} catch (RemoteException e) {

			String errorMsg = "En este momento tenemos inconvenientes para atenderte. Por favor intenta mas tarde.";
			log.error("[{}] : No se logró obtener los botones de medio de pago. [REMOTE_EXCEPTION]",
					transaccion.getIdTrx());
			e.printStackTrace();
			mv.addObject("error", errorMsg);
			return mv;
		} catch (Exception e) {
			String errorMsg = "En este momento tenemos inconvenientes para atenderte. Por favor intenta mas tarde.";
			log.error("[{}] : No se logró obtener los botones de medio de pago. [EXCEPTION]", transaccion.getIdTrx());
			e.printStackTrace();
			mv.addObject("error", errorMsg);
			return mv;
		}

		log.info("[{}] : Listado de botones de medios de pago para empresa {}", transaccion.getIdTrx(), empresa);
		log.info("[{}] : -----------------------------------------------------------------------",
				transaccion.getIdTrx());
		if (!botonesList.isEmpty()) {
			// flag seleccion medio de pago para determinar que ya fue accesado en la
			// historia en el caso de que se vuelva atras mediante browser
			for (BotonMedioPago boton : botonesList) {
				log.info("[{}] : id.......... : {}", transaccion.getIdTrx(), boton.getId());
				log.info("[{}] : canal....... : {}", transaccion.getIdTrx(), boton.getIdCanal());
				log.info("[{}] : img......... : {}", transaccion.getIdTrx(), boton.getImg());
				log.info("[{}] : modo de pago : {}", transaccion.getIdTrx(), boton.getModoPago());
				log.info("[{}] : nombre...... : {}", transaccion.getIdTrx(), boton.getNombre());
				log.info("[{}] : url......... : {}", transaccion.getIdTrx(), boton.getUrl());
				log.info("[{}] : -----------------------------------------------------------------------",
						transaccion.getIdTrx());
			}
		} else {
			log.info("[{}] No existen botones de pago para la empresa : {}", transaccion.getIdTrx(), empresa);
		}

		// ordena botones por id
		botonesList = botonesList.stream().sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
				.collect(Collectors.toList());

		// boton webpay para validacion de preseleccion
		String btnWebpay = empresa.equals(EmpresaEnum.TELSUR.getDescripcion())
				? pagosService.obtenerParametro(ConstantsEnum.MP_BTNID_WEBPAY_TELSUR.getId())
				: pagosService.obtenerParametro(ConstantsEnum.MP_BTNID_WEBPAY_TELCOY.getId());
		log.info("[{}] : btnWebpay... : {}", transaccion.getIdTrx(), btnWebpay);

		mv.addObject("btnWebpay", btnWebpay);

		// header y footer
		mv.addObject("header", headerAndFooter.get("header"));
		mv.addObject("footer", headerAndFooter.get("footer"));

		mv.addObject("rut", rut);
		mv.addObject("rutFormateado", Validator.formatearRUT(rut));

		mv.addObject("montoTotal", montoTotal);
		mv.addObject("transaccion", transaccion.getIdTrx());
		mv.addObject("botonesList", botonesList);

		sessionData(model, request, request.getSession());

		return mv;
	}

	/**
	 * Procesa retorno una vez se efectua un pago en GMP.
	 * 
	 * @param RespuestaCredito
	 * @param transaccion
	 * @param request
	 * @param s
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pagos/retornoGMP", method = RequestMethod.POST)
	public String recepcionCRE(@RequestParam(value = "RespuestaCredito", required = false) String respuestaCredito,
			@ModelAttribute("TRXSESSION") Transaccion transaccion,
			@ModelAttribute("headerAndfooter") HashMap<String, String> headerAndFooter, HttpServletRequest request,
			Model m, ModelMap model) {

		// en el caso que haya expirado la sesion mientras se encontraba abierta la
		// ventana del medio de pago
		if (transaccion == null) {
			DvtTlTrxHead head = pagosService.obtenerUltimaTransaccion();
			log.info("[{}] Sesion expirada : obteniendo ultima transaccion", head.getIdtrx());
			transaccion = new Transaccion();
			transaccion.setIdTrx(head.getIdtrx());
			transaccion.setIdCanal(head.getIdcnl());
			transaccion.setIdEmpresa(head.getIdemp());
			transaccion.setMonto(String.valueOf(head.getMnt()));
			transaccion.setIdCliente(head.getIdcli());
		}

		log.info("[{}] : -----------------------------------------------------------------------",
				transaccion.getIdTrx());
		log.info("[{}] : Ingresando a /pagos/retornoGMP, cliente: {}", transaccion.getIdTrx(), transaccion);
		String urlRespuestaCredito = "";
		try {

			// segun url establece el parametro para contexto de pagina de respuesta gmp
			String parametro = headerAndFooter.get("tipoHeaderAndFooter")
					.equalsIgnoreCase(EmpresaEnum.TELSUR.getDescripcion()) ? ConstantsEnum.GMP_PAGVOUCHER.getId()
							: ConstantsEnum.GMP_PAGVOUCHER_GTD.getId();
			urlRespuestaCredito = pagosService.obtenerParametro(parametro);
		} catch (Exception e) {
			log.error("[{}] : Ocurrio un error al obtener pagina de retorno voucher : {} ", transaccion.getIdTrx(),
					e.getMessage());
			e.printStackTrace();
		}

		// header y footer
		model.put("header", headerAndFooter.get("header"));
		model.put("footer", headerAndFooter.get("footer"));

		model.put("path", request.getContextPath());
		model.put("urlRespuestaCredito", urlRespuestaCredito);
		model.put("respuestaCredito", respuestaCredito);

		sessionData(m, request, request.getSession());

		return "respuesta_credito";
	}

	/**
	 * Procesa respuesta final de GMP
	 * 
	 * @param r
	 * @param transaccion
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "/pagos/respuesta", method = RequestMethod.POST)
	public String retornoGMP(@RequestParam(value = "respuestaCredito", required = false) String r,
			@ModelAttribute("TRXSESSION") Transaccion transaccion,
			@ModelAttribute("headerAndfooter") HashMap<String, String> headerAndFooter, HttpServletRequest request,
			Model model, ModelMap m) {
	
		// si la transaccion no existe significa que ya finalizo el proceso de pago
		if (transaccion == null) {
			DvtTlTrxHead head = pagosService.obtenerUltimaTransaccion();
			log.info("[{}] Sesion expirada : obteniendo ultima transaccion", head.getIdtrx());
			transaccion = new Transaccion();
			transaccion.setIdTrx(head.getIdtrx());
			transaccion.setIdCanal(head.getIdcnl());
			transaccion.setIdEmpresa(head.getIdemp());
			transaccion.setMonto(String.valueOf(head.getMnt()));
			transaccion.setIdCliente(head.getIdcli());

		} else {
			log.info("[{}] : Ingresando a /pagos/retornoGMP", transaccion.getIdTrx());
			log.info("[{}] : Método de la solicitud: {}", transaccion.getIdTrx(), request.getMethod());
		}

		log.info("[{}] : Obteniendo respuesta desde medio de pago ", transaccion.getIdTrx());
		// decodificando respuesta GMP
		log.info("[{}] : Estado transacción..: {}", transaccion.getIdTrx(), transaccion);
		log.info("[{}] : Respuesta credito...: {}", transaccion.getIdTrx(), r);
		SolicitarCreditoResponse sc = pagosService.decodificarRespuestaGMP(transaccion, r);
		Transaccion t = null;

		log.info("[{}] : Analizando respuesta de crédito para empresa : [{}] idcliente: [{}], estado trx: [{}] ",
				transaccion.getIdTrx(), transaccion.getIdEmpresa(), transaccion.getIdCliente(),
				transaccion.getEstado());
		log.info("[{}] : -----------------------------------------------------------------------",
				transaccion.getIdTrx());
		log.info("[{}] : Respuesta.............: ", transaccion.getIdTrx());
		// log.info("[{}] Codigo autorizacion...: {}", transaccion.getIdTrx(),
		// sc.getCodigoAutorizacion());
		log.info("[{}] : Codigo respuesta..... : {}", transaccion.getIdTrx(), sc.getCodRespuesta());
		log.info("[{}] : Fecha................ : {}", transaccion.getIdTrx(), sc.getFechaHora());
		log.info("[{}] : Canal.................: {}", transaccion.getIdTrx(), sc.getIdCanal());
		log.info("[{}] : Medio de pago.........: {}", transaccion.getIdTrx(), sc.getIdMediopago());
		log.info("[{}] : Transaccion gmp.......: {}", transaccion.getIdTrx(), sc.getIdTrxGmp());
		log.info("[{}] : Mensaje respuesta.....: {}", transaccion.getIdTrx(), sc.getMensajeRespuesta());
		log.info("[{}] : Monto.................: {}", transaccion.getIdTrx(), sc.getMonto());
		log.info("[{}] : Número orden..........: {}", transaccion.getIdTrx(), sc.getNumeroOrden());
		log.info("[{}] : -----------------------------------------------------------------------",
				transaccion.getIdTrx());

		// segun url establece el parametro para contexto de retorno
		String parametro = headerAndFooter.get("tipoHeaderAndFooter").equalsIgnoreCase(
				EmpresaEnum.TELSUR.getDescripcion()) ? ConstantsEnum.BACKBTN.getId() : ConstantsEnum.BACKBTNGTD.getId();
		String backBtn = pagosService.obtenerParametro(parametro);
		m.put("btnVolver", backBtn);
		m.put("respuesta", true);
		// header y footer
		m.put("header", headerAndFooter.get("header"));
		m.put("footer", headerAndFooter.get("footer"));

		try {
			transaccion.setIdTrxMedioPago(sc.getIdTrxGmp());
			transaccion.setCodigoAutorizacionMedioPago(sc.getCodigoAutorizacion());
			transaccion.setCodigoRetornoMedioPago(sc.getCodRespuesta());
			t = pagosService.obtenerTransaccionConIDGMP(sc.getIdTrxGmp());
		} catch (Exception e) {
			log.error("[{}] : No se logró obtener la transacción desde la base de datos con el ID GMP: {}",
					transaccion.getIdTrx(), sc.getIdTrxGmp());
			transaccion.setEstado(EstadoTransaccionEnum.NOK.getCodigo());
			log.info("[{}] : ESTADO OK : {}", transaccion.getIdTrx(), transaccion.toString());
			pagosService.actualizarEstadoTransaccionDB(transaccion);
			String errorMsg = "La transacción actual no se logró validar con éxito.";
			m.put("monto", Validator.separarMontoMiles(new BigInteger(transaccion.getMonto())));
			m.put("rut", Validator.formatearRUT(transaccion.getIdCliente()));
			m.put("error", errorMsg);

			sessionService.limpiarValores();

			return "anulacion_pago";
		}

		sessionData(model, request, request.getSession());

		if (sc != null && t != null)
			log.info("[{}] : CODIGO RESPUESTA: [{}] - ESTADO: [{}]", transaccion.getIdTrx(), sc.getCodRespuesta(),
					t.getEstado());

		// codigo aprobado por GMP
		if (CodigoRetornoGMPEnum.CODE_00.getCodigo().equals(sc.getCodRespuesta())
				&& EstadoTransaccionEnum.ENP.getCodigo().equalsIgnoreCase(t.getEstado())) {

			transaccion.setEstado(EstadoTransaccionEnum.OK.getCodigo());
			log.info("[{}] : ESTADO OK : {}", transaccion.getIdTrx(), transaccion);
			pagosService.actualizarEstadoTransaccionDB(transaccion);

			// Solicitud de pago aprobada para medio de pago
		} else if (CodigoRetornoGMPEnum.CODE_21.getCodigo().equals(sc.getCodRespuesta())) {
			transaccion.setEstado(EstadoTransaccionEnum.NOK.getCodigo());
			log.info("[{}] : ESTADO NOK : {}", transaccion.getIdTrx(), transaccion);
			pagosService.actualizarEstadoTransaccionDB(transaccion);
			m.put("codigo_error", CodigoRetornoGMPEnum.CODE_21.getCodigo());
			m.put("orden", sc.getNumeroOrden());
			m.put("monto", Validator.separarMontoMiles(new BigInteger(transaccion.getMonto())));
			m.put("rut", Validator.formatearRUT(transaccion.getIdCliente()));
			m.put("mensaje", CodigoRetornoGMPEnum.CODE_21.getDescripcion());

			try {
				sessionService.limpiarValores();
			} catch (Exception e) {
				log.error("Ocurrio un error al finalizar sesion.");
				e.printStackTrace();
			}

			return "anulacion_pago";

			// Transacción no aprobada por medio de pago
		} else if (CodigoRetornoGMPEnum.CODE_48.getCodigo().equals(sc.getCodRespuesta())) {
			log.error("[{}] : Transacción no aprobada por medio de pago ", transaccion.getIdTrx());
			transaccion.setEstado(EstadoTransaccionEnum.NOK.getCodigo());
			log.info("[{}] : ESTADO NOK : {}", transaccion.getIdTrx(), transaccion);
			pagosService.actualizarEstadoTransaccionDB(transaccion);
			m.put("codigo_error", CodigoRetornoGMPEnum.CODE_48.getCodigo());
			m.put("orden", sc.getNumeroOrden());
			m.put("monto", Validator.separarMontoMiles(new BigInteger(transaccion.getMonto())));
			m.put("rut", Validator.formatearRUT(transaccion.getIdCliente()));
			m.put("mensaje", CodigoRetornoGMPEnum.CODE_48.getDescripcion());
			// limpia objeto transaccion para crear una nueva en siguiente iteracion.
			try {
				
				sessionService.limpiarValores();

			} catch (Exception e) {
				log.error("Ocurrio un error al finalizar sesion.");
				e.printStackTrace();
			}

			return "anulacion_pago";

			// Transacción rechazada por WS portal
		} else if (CodigoRetornoGMPEnum.CODE_57.getCodigo().equals(sc.getCodRespuesta())) {
			log.error("[{}] : Transacción rechazada por WS portal ", transaccion.getIdTrx());
			transaccion.setEstado(EstadoTransaccionEnum.NOK.getCodigo());
			log.info("[{}] : ESTADO NOK : {}", transaccion.getIdTrx(), transaccion);
			pagosService.actualizarEstadoTransaccionDB(transaccion);
			m.put("codigo_error", CodigoRetornoGMPEnum.CODE_57.getCodigo());
			m.put("orden", sc.getNumeroOrden());
			m.put("monto", Validator.separarMontoMiles(new BigInteger(transaccion.getMonto())));
			m.put("rut", Validator.formatearRUT(transaccion.getIdCliente()));
			m.put("mensaje", CodigoRetornoGMPEnum.CODE_57.getDescripcion());
			// limpia objeto transaccion para crear una nueva en siguiente iteracion.
			try {
				
				sessionService.limpiarValores();
				
			} catch (Exception e) {
				log.error("Ocurrio un error al finalizar sesion.");
				e.printStackTrace();
			}

			return "anulacion_pago";
			// otros errores
		} else {
			transaccion.setEstado(EstadoTransaccionEnum.NOK.getCodigo());
			log.info("[{}] : ESTADO NOK : {}", transaccion.getIdTrx(), transaccion);
			pagosService.actualizarEstadoTransaccionDB(transaccion);
			String errorMsg = "El pago no se realizó debido a inconvenientes con el sistema de pago.";
			m.put("error", errorMsg);
			m.put("orden", sc.getNumeroOrden());
			m.put("monto", Validator.separarMontoMiles(new BigInteger(transaccion.getMonto())));
			m.put("rut", Validator.formatearRUT(transaccion.getIdCliente()));
			m.put("mensaje", errorMsg);
			// limpia objeto transaccion para crear una nueva en siguiente iteracion.
			try {
				
				sessionService.limpiarValores();
				
			} catch (Exception e) {
				log.error("Ocurrio un error al finalizar sesion.");
				e.printStackTrace();
			}

			return "anulacion_pago";
		}

		log.debug("[{}] : [{}] : Transaccion SESSION.: {}", transaccion.getIdTrx(), transaccion.getIdCliente(), transaccion);
		log.debug("[{}] : [{}] : Transaccion BDDATOS.: {}", transaccion.getIdTrx(), transaccion.getIdCliente(), t);
		log.debug("[{}] : [{}] : Respuesta GMP.......: {}", transaccion.getIdTrx(), transaccion.getIdCliente(), sc);

		// generacion de voucher para respuesta final exitosa.
		Comprobante c = pagosService.obtenerComprobante(t);

		log.info("[{}] :  VOUCHER: {}", transaccion.getIdTrx(), c);
		m.put("medio_pago", c.getNombre_medio_pago());
		m.put("monto", c.getMonto());
		m.put("comercio", "Telefonica Telsur");
		m.put("urlComercio", "www.telsur.cl");
		m.put("numero_orden", c.getNumero_orden());
		m.put("id_transaccion", c.getIdentificador());
		m.put("codigo_autorizacion", c.getCodigo_autorizacion());
		m.put("fecha_pago", c.getFecha());
		m.put("hora_pago", c.getHora());
		m.put("tipo_pago", c.getDesc_tipo_pago());
		m.put("numero_tarjeta", c.getDigitos_tarjeta());
		m.put("rut", Validator.formatearRUT(transaccion.getIdCliente()));
		m.put("numero_cuotas", c.getNumero_cuotas());
		m.put("tipo_cuotas", c.getTipo_cuotas());
		m.put("mail", c.getEmail_cliente());
		// detalle de documentos pagados a incluir en voucher.
		List<DvtTlTrxBody> trxBodyList = pagosService.obtenerTransaccionesPagadas(transaccion);
		m.put("trxBodyList", trxBodyList);
		// envia notificacion
		pushMail(t, c.getEmail_cliente());
		// limpia objeto transaccion para crear una nueva en siguiente iteracion.
		try {
			
			sessionService.limpiarValores();
			
		} catch (Exception e) {
			log.error("Ocurrio un error al finalizar sesion.");
			e.printStackTrace();
		}

		return "comprobante_pago";
	}

	/**
	 * Retorno comprobantes de pago en pantalla
	 * 
	 * @param rut
	 * @param empresa
	 * @param m
	 * @return
	 * @author bvera
	 * @since 14/11/2018
	 */
	@RequestMapping(value = "/pagos/comprobantes", method = RequestMethod.GET)
	public String comprobantes(@RequestParam(value = "rut", required = false) String rut,
			@RequestParam(value = "empresa", required = false) String empresa,
			@ModelAttribute("headerAndfooter") HashMap<String, String> headerAndFooter,
			@ModelAttribute("TRXSESSION") Transaccion transaccion, ModelMap m) {
		log.info("[{}] : -----------------------------------------------------------------------",
				transaccion.getIdTrx());
		log.info("Accediendo a /pagos/comprobantes");
		log.info("Transaccion : {}", transaccion);
		log.info("[{}] : Obteniendo comprobantes para rut {} de la empresa {} ", transaccion.getIdTrx(), rut, empresa);
		// header y footer
		m.put("header", headerAndFooter.get("header"));
		m.put("footer", headerAndFooter.get("footer"));

		m.put("rut", rut);
		m.put("empresa", empresa);
		return "lista_comprobantes";
	}

	/**
	 * test para comprobante de pago
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pagos/comprobante_pago", method = RequestMethod.GET)
	public String comprobantePago(ModelMap m,
			@ModelAttribute("headerAndfooter") HashMap<String, String> headerAndFooter) {
		// m.put("medio_pago", c.getNombre_medio_pago());
		m.put("monto", "$31.165");
		m.put("comercio", "Telefonica Telsur");
		m.put("urlComercio", "www.telsur.cl");
		m.put("numero_orden", "5746801");
		m.put("id_transaccion", "d895a2acbcb958510da33ee8046b70d3");
		m.put("codigo_autorizacion", "003910-003911");
		// m.put("fecha_pago", c.getFecha());
		// m.put("hora_pago", c.getHora());
		m.put("tipo_pago", "Venta débito");
		m.put("numero_tarjeta", "1866");
		m.put("rut", Validator.formatearRUT("161483877"));
		m.put("numero_cuotas", "0");
		// m.put("tipo_cuotas", c.getTipo_cuotas());
		// m.put("mail", c.getEmail_cliente());
		Transaccion transaccion = new Transaccion();
		transaccion.setIdTrx("538570911000000060");
		List<DvtTlTrxBody> trxBodyList = pagosService.obtenerTransaccionesPagadas(transaccion);
		m.put("trxBodyList", trxBodyList);

		// header y footer
		m.put("header", headerAndFooter.get("header"));
		m.put("footer", headerAndFooter.get("footer"));

		return "comprobante_pago";
	}

	/**
	 * Envia notificacion.
	 * 
	 * @param transaccion
	 * @param mail
	 * @return
	 */
	private boolean pushMail(Transaccion transaccion, String mail) {
		log.info("[{}] : -----------------------------------------------------------------------",
				transaccion.getIdTrx());
		log.info("[{}] : Registrando notificacion a {} ", transaccion.getIdTrx(), mail);

		Comprobante c = pagosService.obtenerComprobanteParaMail(transaccion);

		String idpla = pagosService.obtenerParametro(ConstantsEnum.IDPLAMAIL.getId());
		String idemp = pagosService.obtenerParametro(ConstantsEnum.IDEMPMAIL.getId());
		String remitente = pagosService.obtenerParametro(ConstantsEnum.REMITENTEMAIL.getId());
		String asunto = pagosService.obtenerParametro(ConstantsEnum.ASUNTOMAIL.getId());
		String cuerpo = "";
		try {
			cuerpo = pagosService.generarCuerpoMail(c,
					pagosService.obtenerTransaccionConIDGMP(transaccion.getIdTrxMedioPago()));
		} catch (Exception e) {
			log.error("[{}] : Ocurrio al registrar notificacion : {} ", transaccion.getIdTrx(), e.getMessage());
			e.printStackTrace();

			return false;
		}

		pagosService.enviarCorreoSinAdjuntos(c.getIdentificador(), idpla, idemp, remitente, c.getEmail_cliente(),
				asunto, cuerpo);

		return true;
	}

	// obtener transaccion
	// obtener remitente de mail desde pantalla

}
