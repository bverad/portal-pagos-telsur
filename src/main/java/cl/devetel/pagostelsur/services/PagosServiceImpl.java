package cl.devetel.pagostelsur.services;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import cl.devetel.app.ws.WSConsultaDeuda;
import cl.devetel.canalgmp.jdbc.dao.TransaccionDao;
import cl.devetel.canalgmp.jdbc.dao.mysql.Impl.TransaccionDaoImpl;
import cl.devetel.common.utils.Validator;
import cl.devetel.gmp.core.facade.GmpManager;
import cl.devetel.gmp.core.facade.GmpManagerImpl;
import cl.devetel.gmp.core.model.RespuestaGMP;
import cl.devetel.gmp.core.model.SolicitarCreditoResponse;
import cl.devetel.gmp.core.model2.Pago;
import cl.devetel.gmp.core.model2.Transaccion;
import cl.devetel.gmp.model.core.Comprobante;
import cl.devetel.gmp.services.validaAcceso.ws.jax.MedioPago;
import cl.devetel.gmp.services.validaAcceso.ws.jax.ValidaAccesoRespuesta;
import cl.devetel.pagostelsur.core.domain.DvtTlTrxBody;
import cl.devetel.pagostelsur.core.domain.DvtTlTrxHead;
import cl.devetel.pagostelsur.core.repositories.TrxBodyRepository;
import cl.devetel.pagostelsur.core.repositories.TrxHeadRepository;
import cl.devetel.pagostelsur.core.to.ComprobantesAnteriores;
import cl.devetel.pagostelsur.enums.CodigoRetornoGMPEnum;
import cl.devetel.pagostelsur.enums.ConstantsEnum;
import cl.devetel.pagostelsur.enums.EmpresaEnum;
import cl.devetel.pagostelsur.enums.EstadoMedioPagoEnum;
import cl.devetel.pagostelsur.enums.EstadoTransaccionEnum;
import cl.devetel.pagostelsur.enums.TipoDocumentoEnum;
import cl.devetel.pagostelsur.prov.domain.DvtTpParamWeb;
import cl.devetel.pagostelsur.prov.repositories.ParametrosWebRepository;
import cl.devetel.pagostelsur.responses.BodyDocument;
import cl.devetel.pagostelsur.responses.BotonMedioPago;
import cl.devetel.pagostelsur.responses.Cuenta;
import cl.devetel.pagostelsur.responses.HeadDocument;
import cl.devetel.pagostelsur.responses.SucursalDocument;
import cl.devetel.ws.consultadeuda.ConsultaDeudaResponse;
import cl.devetel.ws.consultadeuda.Documento;

@Service
public class PagosServiceImpl implements PagosService {
	private final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(this);
	
	@Autowired
	private ParametrosWebRepository repository;
	
	@Autowired
	private TrxBodyRepository coreRepository;
	
	@Autowired
	private TrxHeadRepository headRepository;
	
    @Autowired 
    private TransaccionDao t;
    
	@Bean
	public TransaccionDao t() {
		return new TransaccionDaoImpl();
	}
	
	/**
	 * Obtiene los pagos desde WS de PDR
	 * @param rut
	 * @author bvera
	 * @throws MalformedURLException 
	 * @since 02/05/2018
	 */
	public LinkedHashMap<String, ConsultaDeudaResponse> obtenerPagos(Transaccion transaccion, String... empresas) throws MalformedURLException {
        com.google.gson.Gson gson = new com.google.gson.Gson();
		//obtencion de parametros desde tabla.
        
        String _WSDL  = obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_WSURL.getId());
        String _IDREC = obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDREC.getId());
        String _IDSUC = obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDSUC.getId());
        String _IDTER = obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDTER.getId());
        String _IDCNL = obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDCNL.getId());
        //obtiene empresa desde controlador.
        String _IDEMP = obtenerParametro(empresas[0]);
        String _TIIDC = obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_TIIDC.getId());
        String _TICON = obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_TICON.getId());
        //String _POP   = obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_POP.getId());
        String _PROXY = obtenerParametro(ConstantsEnum.WITHPROXY.getId());
        
        SimpleDateFormat formato = new SimpleDateFormat(ConstantsEnum.FORMATDATE.getId());
        String _FECTR = formato.format( new Date() );
        String _SECTR = transaccion.getIdTrx();
        String _IDCLI = transaccion.getIdCliente();
        
        //se setean los datos de la transaccion con los parametros obtenidos.
        transaccion.setIdRecaudador( _IDREC );
        transaccion.setIdSucursal( _IDSUC );
        transaccion.setIdTerminal( _IDTER );
        transaccion.setIdCanal( _IDCNL );
        transaccion.setIdEmpresa( _IDEMP );
        transaccion.setTipoIdCliente( _TIIDC );
        transaccion.setFechaTransaccion( _FECTR );
        
        LinkedHashMap<String, ConsultaDeudaResponse> deudaResponseMap = new LinkedHashMap<>();
        //si existe más de una empresa consulta la cantidad de veces necesaria segun la lista.
        if(empresas.length >= 1) {
        	//for(String empresa : empresas) {
        	for(int i = 0; i < empresas.length ; i++) {
        		_IDEMP = obtenerParametro(empresas[i]);
                //consulta a servicio web
                WSConsultaDeuda servicio = new WSConsultaDeuda(_WSDL, 10L, new Boolean(_PROXY));
                ConsultaDeudaResponse response = servicio.consultaDeuda(_IDREC, _IDSUC, _IDCNL, _IDTER, _FECTR, _SECTR, _IDEMP, _TIIDC, _IDCLI, _TICON);
                //concatena id de empresa con codigo de retorno
                deudaResponseMap.put(empresas[i] + ":" +response.getRRetcod(), response);
        	}
        }
	
        //retorna map con resultado de consultas a telsur y telcoy.
        return deudaResponseMap;
	}
	
	
	/**
	 * Formatea respuesta de webservice PDR a respuesta estandar para vista.
	 * @param response
	 * @return
	 * @throws ParseException 
	 */
	public HeadDocument obtenerDocumento(LinkedHashMap<String, ConsultaDeudaResponse> deudaResponseMap,
										Transaccion transaccion,
										String... empresas) throws ParseException {
		int correlativo = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		HeadDocument headDocument = new HeadDocument();
		List<SucursalDocument> sucursalDocumentList =  new ArrayList<>();
		
		//Integer i = 0;
		for (Map.Entry<String, ConsultaDeudaResponse> e : deudaResponseMap.entrySet()){
			ConsultaDeudaResponse response = e.getValue();
			//agrupa por campo RIo1
			List<Documento> documentoList = response.getRDocs();
			Map<String, List<Documento>> documentoByRIo2 = documentoList.stream()
					.collect(Collectors.groupingBy(Documento::getRIo2));
			
			headDocument.setTransaccionId(transaccion.getIdTrx());
			//setea codigos de retorno
			if(e.getKey().split(":")[0].split("_")[4].equals(EmpresaEnum.TELSUR.getDescripcion())) {
				//corresponde a telsur
				headDocument.setSucursal(empresas[0]);
				headDocument.setCodigoRetornoTelsur(e.getKey().split(":")[1]);
			}else {
				//corresponde a telcoy
				headDocument.setSucursal(empresas[1]);
				headDocument.setCodigoRetornoTelcoy(e.getKey().split(":")[1]);
			}	
			
			for (Map.Entry<String, List<Documento>> entry : documentoByRIo2.entrySet()){
				SucursalDocument sucursalDocument = new SucursalDocument();
				sucursalDocument.setCorrelativo(correlativo);
				sucursalDocument.setEmpresa(headDocument.getSucursal().equals(empresas[0]) ? empresas[0] : empresas[1]);
				List<Documento> dctoList = (List<Documento>) entry.getValue();
				List<BodyDocument> bodyDocumentList = new ArrayList<>();
				BigInteger ts = new BigInteger("0");
				for(Documento d : dctoList) {
					BodyDocument bodyDocument = new BodyDocument();
					bodyDocument.setFechaDocumento(formatter.parse(d.getRFem()));
					bodyDocument.setFechaVencimiento(formatter.parse(d.getRFve()));
					
					//calculando monto
					String monto = d.getRMnt().substring(0, d.getRMnt().length() - 2);	 
					monto = Validator.separarMontoMiles(new BigInteger(monto));
					ts = ts.add(new BigInteger(monto.replace(".", "")));
					
					bodyDocument.setMonto(monto);
					//el tipo de documento sera obtenido desde la carga de deudas provista por telsur.
					bodyDocument.setCodigoTipoDocumento(d.getRTdo());
					bodyDocument.setTipoDocumento(TipoDocumentoEnum.getById(d.getRTdo()).getDescripcion());
					bodyDocument.setNumeroDocumento(d.getRDoc());
					bodyDocumentList.add(bodyDocument);
				}
				
				
				//int total = dctoList.stream().filter(o -> Integer.parseInt(o.getRMnt().substring(0, o.getRMnt().length() - 2)) > 0).mapToInt(o -> Integer.parseInt(o.getRMnt())).sum();

				correlativo++;
				//si corresponde a 1:telsur o 2:telcoy
				sucursalDocument.setCodigo(headDocument.getSucursal().equals(empresas[0]) ? "1" : "2");
				sucursalDocument.setTipo(entry.getKey());
				
				//calcula el total por sucursal
				String totalSucursal = String.valueOf(ts);
				//totalSucursal = totalSucursal.equals("0") ? totalSucursal : (totalSucursal.substring(0, totalSucursal.length() - 2));
				sucursalDocument.setTotal(totalSucursal.equals("0") ? totalSucursal : Validator.separarMontoMiles(new BigInteger(totalSucursal)));
				//log.info("total sucursal {} : {}", sucursalDocument.getCorrelativo(), sucursalDocument.getTotal());
				//ordena por fecha de documento
				bodyDocumentList = bodyDocumentList.stream().sorted((o1, o2)->o1.getFechaDocumento().
		                compareTo(o2.getFechaDocumento())).
		                collect(Collectors.toList());
				sucursalDocument.setDocumentList(bodyDocumentList);
				sucursalDocumentList.add(sucursalDocument);
				
			}
			//contador para empresa
			//i++;
		}
		//cuenta cantidad de registros por empresa
		Long cantidadRegistrosTelsur = sucursalDocumentList.stream().filter(s-> s.getEmpresa().equals(EmpresaEnum.TELSUR.getDescripcion())).count();
		Long cantidadRegistrosTelcoy = sucursalDocumentList.stream().filter(s-> s.getEmpresa().equals(EmpresaEnum.TELCOY.getDescripcion())).count();
		
		headDocument.setCantidadRegistrosTelsur(cantidadRegistrosTelsur);
		headDocument.setCantidadRegistrosTelcoy(cantidadRegistrosTelcoy);
		headDocument.setSucursalList(sucursalDocumentList);
		
		return headDocument;
				
	}
	

	/**
	 * Aplica separador de miles a una cadena de caracteres
	 * @param monto
	 * @return
	 * @author bvera
	 * @since
	 */
	private String getMontoSeparadoPorMiles(String monto) {
		BigInteger result = BigInteger.ZERO; 
		result = result.add(new BigInteger(monto));
		String montoFinal = Validator.separarMontoMiles(result);
		return montoFinal;
	}
	
	   
    /**
     * Autenticacion inicial a GMP.
     * @since 15/05/2018
     */
    public GmpManager prepararGMP(String empresa, Transaccion transaccion){
        
        long timeOut   = 100000L;
        String _WSURL  = obtenerParametro(ConstantsEnum.GMP_WSURL.getId());
        String _KEY1   = obtenerParametro(ConstantsEnum.GMP_KEY_1.getId() + "_" + empresa);
        String _KEY2   = obtenerParametro(ConstantsEnum.GMP_KEY_2.getId() + "_" + empresa);
        String _KEY3   = obtenerParametro(ConstantsEnum.GMP_KEY_3.getId() + "_" + empresa);
        String _IDCNL  = obtenerParametro(ConstantsEnum.GMP_IDCNL.getId() + "_" + empresa);
        String _PASS   = obtenerParametro(ConstantsEnum.GMP_PASSW.getId() + "_" + empresa);
        
        log.info("[{}] : -----------------------------------------------------------------------", transaccion.getIdTrx());
        log.info("[{}] : Preparando GMP.............. : {}", transaccion.getIdTrx(), empresa);
        log.info("[{}] : Canal .......................: {}", transaccion.getIdTrx(), _IDCNL);
        
        boolean _PROXY = "true".equalsIgnoreCase(obtenerParametro(ConstantsEnum.GMP_PROXY.getId()));
        return new GmpManagerImpl( _WSURL, timeOut, _PROXY, _KEY1, _KEY2, _KEY3, _IDCNL, _PASS );
    }
	
	/**
	 * Retorna el valor de un id de parametro.
	 * @param paramId
	 * @return string con el valor asociado al id de parametro.
	 */
	public String obtenerParametro(String paramNom) {
		DvtTpParamWeb param = repository.findByIdParamNom(paramNom);
		return param.getParamVal();
	}
	
	
	/**
	 * Guarda transaccion en base de datos core.
	 * @param transaccion
	 * @throws Exception
	 */
    public void guardarTransaccion( Transaccion transaccion ) throws Exception{
        t.guardarTransaccion(transaccion);
        for( Pago p : transaccion.getPagos() ){
            t.guardarPagoTransaccionV2( transaccion.getIdTrx(), p );
        }
        
        //setea y guarda id session
        Optional<DvtTlTrxHead> optional = headRepository.findById(String.valueOf(transaccion.getIdTrx()));
        if(optional.isPresent()) {
        	DvtTlTrxHead head = optional.get();
            head.setIdSess(transaccion.getIdSess());
            headRepository.save(head);
        }
        
        
        
    }
	
	
	/**
	 * Obtiene los botones de medios de pago
	 * @param gmp
	 * @param transaccion
	 * @return
	 * @throws RemoteException
	 * @throws Exception
	 */
    public List<BotonMedioPago> obtenerBotones(GmpManager gmp, Transaccion transaccion, String paginaRespuesta) throws RemoteException, Exception {
        List<BotonMedioPago> botones = new ArrayList<>();
        RespuestaGMP rGmp = new RespuestaGMP();
        String _PRES   = this.obtenerParametro(paginaRespuesta);
        String _ESTADEF = "A";
        String _MODODEF = "M";
        String _ALTODEF = "100";
        String _ANCHDEF = "100";
        
        DvtTlTrxHead head = headRepository.findByIdtrx(transaccion.getIdTrx());
		if(!head.getIdemp().equals(transaccion.getIdEmpresa()) || !head.getIdcnl().equals(transaccion.getIdCanal())) {
			log.info("[{}] : [{}] Modificando valores de empresa distintos a los almacenados inicialmente en transaccion : {} , {} ", transaccion.getIdTrx(), transaccion.getIdCliente(), transaccion.getIdEmpresa(), transaccion.getIdCanal());
			transaccion.setIdCanal(head.getIdcnl());
			transaccion.setIdEmpresa(head.getIdemp());
			
		}else {
			log.info("[{}] : [{}] Parametros de entrada para GMP coinciden con valores almacenados inicialmente en la transaccion.", transaccion.getIdTrx(), transaccion.getIdCliente());
		}     
		
		log.info("Determinando parametros para empresa y canal");
        String empresa = (transaccion.getIdEmpresa().equals(this.obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELSUR.getId())) ?
															EmpresaEnum.TELSUR.getDescripcion() :
															EmpresaEnum.TELCOY.getDescripcion()); 
        String parametroConsultaEmp = (transaccion.getIdEmpresa().equals(this.obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELSUR.getId())) ?
        								ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELSUR.getId() :
        								ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELCOY.getId());
        
        //obtiene parametros segun la empresa a la que corresponde la deuda
        String _IDCNL  = this.obtenerParametro(ConstantsEnum.GMP_IDCNL.getId() + "_" + empresa);
        String _IDEMP  = this.obtenerParametro(ConstantsEnum.GMP_IDEMP.getId() + "_" + empresa);
        log.info("[{}] : -----------------------------------------------------------------------", transaccion.getIdTrx());
        log.info("[{}] : Empresa input..............................: {}", transaccion.getIdTrx(), transaccion.getIdEmpresa());
        log.info("[{}] : Empresa parametro..........................: {}", transaccion.getIdTrx(), this.obtenerParametro(parametroConsultaEmp));
        log.info("[{}] : Valor parametro {} ........................: {}", transaccion.getIdTrx() ,ConstantsEnum.GMP_IDEMP.getId() + "_" + empresa , _IDEMP);
        log.info("[{}] : Valor parametro {} ........................: {}", transaccion.getIdTrx() ,ConstantsEnum.GMP_IDCNL.getId() + "_" + empresa , _IDCNL);        
		log.info("[{}] : Ejecutando valida acceso para empresa......: {}", transaccion.getIdTrx(), empresa);
        //objeto transaccion para gmp
        cl.devetel.gmp.core.model2.Transaccion trx = new cl.devetel.gmp.core.model2.Transaccion();
        trx.setIdEmpresa(_IDEMP);
        trx.setIdCliente(transaccion.getIdCliente());
        trx.setIdCanal(_IDCNL); 
        trx.setMonto(transaccion.getMonto() );
        trx.setIdTrx(transaccion.getIdTrx() );
        //validando respuesta
        ValidaAccesoRespuesta _RESVA = gmp.validaAccesoWSAxis(trx , _PRES);
        log.debug("[{}] : Respuesta GMP : Codigo : {} , Glosa : {} ", transaccion.getIdTrx(), _RESVA.getAUTENTIFICACION().getCODRESPUESTA(), CodigoRetornoGMPEnum.getById(_RESVA.getAUTENTIFICACION().getCODRESPUESTA()).getDescripcion());        
        if (_RESVA == null || _RESVA.getAUTENTIFICACION() == null){
            rGmp.setCodigoRespuesta("97");
            log.error("[{}] : El objeto de retorno viene vacío ", transaccion.getIdTrx());
            return botones;
        }
            
        if (_RESVA.getMEDIOSPAGOLIST() == null || _RESVA.getMEDIOSPAGOLIST().getMEDIOPAGO().isEmpty()) {
            rGmp.setCodigoRespuesta("96");
            log.error("[{}] : El objeto de medios de pago viene vacío.", transaccion.getIdTrx());
            return botones;
        }

        String _idGMP = _RESVA.getAUTENTIFICACION().getIDTRX();

        for (MedioPago medioDePago : _RESVA.getMEDIOSPAGOLIST().getMEDIOPAGO()) {

            String idmp 	= medioDePago.getIDMEDIOPAGO();
            String activo 	= this.obtenerParametro(ConstantsEnum.MP_ESTADO.getId().concat(idmp));
            String modo 	= this.obtenerParametro(ConstantsEnum.MP_MODO.getId().concat(idmp));
            String alto 	= this.obtenerParametro(ConstantsEnum.MP_ALTO.getId().concat(idmp));
            String ancho 	= this.obtenerParametro(ConstantsEnum.MP_ANCHO.getId().concat(idmp));
            String estado 	= this.obtenerParametro(ConstantsEnum.MP_ESTADO.getId().concat(idmp));
            String img 		= this.obtenerParametro(ConstantsEnum.MP_IMG_URL.getId().concat(idmp));
            
            //despliega medio de pago siempre y cuando este activo
            if(estado.equals(EstadoMedioPagoEnum.ACTIVO.getCodigo())) {
            	log.info("[{}] : Valor idemp GMP : {}", transaccion.getIdTrx(), idmp);
            	BotonMedioPago btn = new BotonMedioPago();
                btn.setId(medioDePago.getIDMEDIOPAGO());
                //btn.setImg(medioDePago.getIMGMEDIOPAGO());
                btn.setImg(img);
                btn.setNombre(medioDePago.getNOMBREMEDIOPAGO());
                btn.setUrl(medioDePago.getURLMEDIOPAGO());
                btn.setSolicitudEnc(gmp.obtenerSolicitarCreditoPorMedioPago(_RESVA.getAUTENTIFICACION(), medioDePago));
                btn.setIdCanal(_RESVA.getAUTENTIFICACION().getIDCANAL());
                btn.setEstado(activo == null || activo.isEmpty() ? _ESTADEF : activo);
                btn.setModoPago(modo == null || modo.isEmpty() ? _MODODEF : modo);
                btn.setAlto(alto == null || alto.isEmpty() ? _ALTODEF : alto);
                btn.setAncho(ancho == null || ancho.isEmpty() ? _ANCHDEF : ancho);
                botones.add(btn);
            }
            

        }

        transaccion.setIdTrxMedioPago(_idGMP);
        transaccion.setCodigoValidacionMedioPago(_RESVA.getAUTENTIFICACION().getCODRESPUESTA());
        
        String IDCLI = transaccion.getIdCliente();
        String IDTRX = transaccion.getIdTrx();
        
        try{
            transaccion.setEstado(EstadoTransaccionEnum.EVA.getCodigo());
            t.actualizarEstadoGMP(transaccion);
        }catch ( Exception e ){
            log.debug("[{}] : [{}] : {}", transaccion.getIdTrx(), transaccion.getIdCliente(), e);
            log.catching(e);
            log.debug("[{}] : [{}] : -------------------------------------------------------------------------------", IDTRX, IDCLI);
        }finally {
        	//limpia objeto de consulta.
        	_RESVA = null;
        }
        
        return botones;
    }
    
    /**
     * setea cuentas y numero de documentos de transaccion vigente
     * @param trx
     * @param cuentas
     * @return
     */
    public BigInteger obtenerMontoTotal( Transaccion trx, List<Cuenta> cuentas ) {
        int ndoc = 0;
        List<Pago> pagos = new ArrayList<>();
        BigInteger monto = BigInteger.ZERO;
        for(Cuenta c : cuentas ){
            Pago p = new Pago();
            p.setNumeroDocumento(c.getNmro_documen());
            p.setTipoDocumento(c.getTipo_doc());
            p.setFechaEmision(c.getFech_documen());
            p.setFechaVencimiento(c.getFech_vencmto());
            p.setMonto(c.getSaldo().replace(".", ""));
            p.setOpcional1("");
            p.setOpcional2(c.getIo2());
            p.setOpcional3("");
            p.setOpcional4("");
            p.setOpcional5("");
            pagos.add(p);
            ndoc++;
            log.info("[{}] : [{}] :---- Nuevo detalle: ", trx.getIdTrx(), trx.getIdCliente());
            log.info("[{}] : [{}] :        doc: {}", trx.getIdTrx(), trx.getIdCliente(), c.getNmro_documen());
            log.info("[{}] : [{}] :        ven: {}", trx.getIdTrx(), trx.getIdCliente(), c.getFech_vencmto());
            log.info("[{}] : [{}] :        mnt: {}", trx.getIdTrx(), trx.getIdCliente(), c.getSaldo().replace(".", ""));
            log.info("[{}] : [{}] :        ref: {}", trx.getIdTrx(), trx.getIdCliente(), trx.getIdCliente());
            log.info("[{}] : [{}] :---------------------------------- ", trx.getIdTrx(), trx.getIdCliente());
            
            monto = monto.add( new BigInteger(c.getSaldo().replace(".", "")));
        }
        trx.setNumeroDocumentos(Integer.toString(ndoc));
        trx.setPagos(pagos);
        return monto;
    }
    

    /**
     * Decodificacion de respuesta final de GMP
     * @param transaccion
     * @param r
     * @return
     */
    public SolicitarCreditoResponse decodificarRespuestaGMP(Transaccion transaccion, String r) {
        String empresa = transaccion.getIdEmpresa().equals(this.obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELSUR.getId())) ? 
				EmpresaEnum.TELSUR.getDescripcion() :
				EmpresaEnum.TELCOY.getDescripcion();
				
	
		log.info("[{}] : -----------------------------------------------------------------------", transaccion.getIdTrx());
    	log.info("[{}] : [{}] Decodificando respuesta GMP.", transaccion.getIdTrx(), transaccion.getIdCliente());
    	log.info("[{}] : [{}] Obteniendo empresa y canal correspondiente a la transaccion en contexto : {} , {} ", transaccion.getIdTrx(), transaccion.getIdCliente(), transaccion.getIdEmpresa(), transaccion.getIdCanal());
        log.info("[{}] : Canal..........: {}", transaccion.getIdTrx(), transaccion.getIdCanal());
        log.info("[{}] : Empresa........: {}", transaccion.getIdTrx(), transaccion.getIdEmpresa());
	    GmpManager gmp = prepararGMP(empresa, transaccion);
        return gmp.tripleDesToSolicitarCreditoResponse(r.trim());
    }
    
    /**
     * Obtiene la transaccion desde GMP para posterior actualizacion.
     * @param id
     * @return
     * @throws Exception
     */
    public Transaccion obtenerTransaccionConIDGMP(String id) throws Exception {
        return t.obtenerTransaccionPorIdGMP(id);
    }
    
    /**
     * Actualiza transaccion con los datos retornados por gmp.
     * @param transaccion
     * @return
     */
    public String actualizarEstadoTransaccionDB( Transaccion transaccion ){
        try {
            return t.actualizarEstado(transaccion);
        } catch (Exception e) {
            log.error("[{}] : [{}] : No logramos actualizar la transaccion [{}] con estado {}", transaccion.getIdTrx(), transaccion.getIdCliente(), transaccion.getIdTrx(), transaccion.getEstado());
            return "99";
        }
    }
    
    /**
     * Obtiene objeto voucher para comprobante.
     * @param transaccion
     * @return
     */
    public Comprobante obtenerComprobante(Transaccion transaccion) {
        try {
            return t.obtenerComprobanteFinal( transaccion.getIdTrxMedioPago() );
        } catch (Exception e) {
            log.error("[{}] : [{}] : No se logró obtener el comprobante de pago.", transaccion.getIdTrx(), transaccion.getIdCliente());
            log.error(e);
            return new Comprobante();
        }
    }
    
    /**
     * Obtiene objeto para construccion de mail.
     * @param transaccion
     * @return
     */
    public Comprobante obtenerComprobanteParaMail(Transaccion transaccion) {
        try {
            return t.obtenerComprobanteParaMail(transaccion.getIdTrxMedioPago() );
        } catch (Exception e) {
            log.error("[{}] : [{}] : No se logró obtener el comprobante de pago.", transaccion.getIdTrx(), transaccion.getIdCliente());
            return new Comprobante();
         }
    }
    
    /**
     * Inserta contenido de mail en base de datos.
     * @param idtrx
     * @param idpla
     * @param idemp
     * @param remitente
     * @param destinatario
     * @param asunto
     * @param cuerpo
     */
    public void enviarCorreoSinAdjuntos( String idtrx, String idpla, String idemp, String remitente, String destinatario, String asunto, String cuerpo ){
        try {
            t.insertarCorreoSinAdjuntos(idtrx, idpla, idemp, remitente, destinatario, asunto, cuerpo);
        } catch (Exception e) {
            log.error("[{}] : No se logramos enviar el correo.", idtrx );
            e.printStackTrace();
        }
    }

    /**
     * Genera contenido de notificacion una vez se efectua el pago.
     * @param c
     * @param t
     * @return
     */
    public String generarCuerpoMail(Comprobante c, Transaccion t) {
        
        final String symbol = "=";
        final String endsym = ";";
        final String endlinedet = ",";
        final String enddet = "|";
		final String[] __a  = new String[]{"MONTO","RUT","NUMCOMPROBANTE","IDTRX","CODIGOAUTMPAGO","FINALNUMTARJETA","TIPOCUOTA","TIPOPAGO","NUMCUOTA","COMERCIO","URLCOM","DETALLE"};
		StringBuilder cuerpo = new StringBuilder();
		cuerpo = cuerpo.append( __a[0] ).append( symbol ).append( c.getMonto() ).append( endsym );
		cuerpo = cuerpo.append( __a[1] ).append( symbol ).append( t.getIdCliente() ).append( endsym );
		cuerpo = cuerpo.append( __a[2] ).append( symbol ).append( c.getNumero_orden() ).append( endsym );
		cuerpo = cuerpo.append( __a[3] ).append( symbol ).append( t.getIdTrxMedioPago() ).append( endsym );
		cuerpo = cuerpo.append( __a[4] ).append( symbol ).append( c.getCodigo_autorizacion() ).append( endsym );
		cuerpo = cuerpo.append( __a[5] ).append( symbol ).append( c.getDigitos_tarjeta() ).append( endsym );
		cuerpo = cuerpo.append( __a[6] ).append( symbol ).append( c.getTipo_cuotas() ).append( endsym );
		cuerpo = cuerpo.append( __a[7] ).append( symbol ).append( c.getDesc_tipo_pago() ).append( endsym );
		cuerpo = cuerpo.append( __a[8] ).append( symbol ).append( c.getNumero_cuotas() ).append( endsym );
		cuerpo = cuerpo.append( __a[9] ).append( symbol ).append( "Telefonica Telsur" ).append( endsym );
		cuerpo = cuerpo.append( __a[10] ).append( symbol ).append( "www.telsur.cl" ).append( endsym );
		
        //calcular detalle e incluirlo en el cuerpo.
        cuerpo = cuerpo.append( __a[11] ).append( symbol );
        List<DvtTlTrxBody> transaccionList = obtenerTransaccionesPagadas(t);
        for (DvtTlTrxBody transaccion : transaccionList) {
        	//definir campo que contendra tipo de documento
        	cuerpo.append("GRUPOFAC").append(symbol).append(transaccion.getIo2().replaceAll(",", ".")).append(endlinedet);
        	cuerpo.append("TIPODOC").append(symbol).append( TipoDocumentoEnum.getById(transaccion.getTidoc()).getDescripcion() ).append(endlinedet);
        	cuerpo.append("DOC").append(symbol).append(transaccion.getId().getDoc()).append(endlinedet);
        	cuerpo.append("DOCEMI").append(symbol).append(transaccion.getFeemi()).append(endlinedet);
        	cuerpo.append("DOCVEN").append(symbol).append(transaccion.getFeven()).append(endlinedet);
        	cuerpo.append("MONTO").append(symbol).append(transaccion.getMnt());
        	cuerpo.append(enddet);
        }
        //punto y coma final
        cuerpo.append(endsym);
        
        return cuerpo.toString();
    }

    /**
     * Retorna transacciones ya pagadas con el proposito de incluirlas en el voucher.
     * @author bvera
     * @since 31/06/2018
     */
	@Override
		// TODO Auto-generated method stub
	public List<DvtTlTrxBody> obtenerTransaccionesPagadas(Transaccion transaccion) {
		log.info("[{}] Obteniendo transacciones ya pagadas para incluirlas en voucher.", transaccion.getIdTrx());
		List<DvtTlTrxBody> trxBodyList = coreRepository.findAllByIdIdtrx(transaccion.getIdTrx());
		return trxBodyList;
	}

	/**
     * Retorna lista de comprobantes asociada a un idcliente
     * @param idcli
     * @author bvera
     * @since 13/11/2018
     */
	public List<ComprobantesAnteriores> listarComprobantes(String idcli, String empresa){
		String codigoEmpresa = empresa.equals(EmpresaEnum.TELSUR.getDescripcion()) ? 
								this.obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELSUR.getId()) : 
								this.obtenerParametro(ConstantsEnum.PDR_CONSULTA_DEUDA_IDEMP_TELCOY.getId());
	    log.info("[{}] : Listando comprobantes para empresa : {} , codigo: {}", idcli, empresa, codigoEmpresa );
		List<ComprobantesAnteriores> caList = headRepository.findComprobantesAnteriores(idcli, codigoEmpresa);
		log.info("[{}] Cantidad de comprobantes asociados : {} ", idcli, caList.size());
		return caList;
	}
	
	
	/**
	 * Determina si existe transaccion
	 * @param idTrx
	 * @return
	 * @author bvera
     * @since 10/05/2019
	 */
	public Boolean obtenerTransaccion(String idTrx) {
		DvtTlTrxHead head = headRepository.findByIdtrx(idTrx);
		return head == null ? false : true;
	}
	
	/**
	 * obtiene ultuima transaccion
	 * @return
	 * @author bvera
     * @since 03/09/2019
	 */
	public DvtTlTrxHead obtenerUltimaTransaccion() {
		return headRepository.findTopByOrderByIdtrxDesc();
	}


    
		
}
