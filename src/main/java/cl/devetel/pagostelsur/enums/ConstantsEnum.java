package cl.devetel.pagostelsur.enums;

/**
 * Constantes para parametros web
 * @author bvera
 * @since 05/2018
 *
 */
public enum ConstantsEnum {
	//servicio web pdr
	WS_URL("PDR_CONSULTA_DEUDA_WSURL"),
	
	BACKBTN("PORTAL_BOTON_VOLVER"),
	BACKBTNGTD("PORTAL_BOTON_VOLVER_GTD"),
    MONTOMIN("PORTAL_MONTO_MINIMO_PAGO"),
	
	//parametros adicionales de llamada a PDR
	PDR_CONSULTA_DEUDA_WSURL("PDR_CONSULTA_DEUDA_WSURL"),
	PDR_CONSULTA_DEUDA_IDREC("PDR_CONSULTA_DEUDA_IDREC"),
	PDR_CONSULTA_DEUDA_IDSUC("PDR_CONSULTA_DEUDA_IDSUC"),
	PDR_CONSULTA_DEUDA_IDCNL("PDR_CONSULTA_DEUDA_IDCNL"),
	PDR_CONSULTA_DEUDA_IDTER("PDR_CONSULTA_DEUDA_IDTER"),
	PDR_CONSULTA_DEUDA_IDEMP_TELSUR("PDR_CONSULTA_DEUDA_IDEMP_TELSUR"),
	PDR_CONSULTA_DEUDA_IDEMP_TELCOY("PDR_CONSULTA_DEUDA_IDEMP_TELCOY"),
	PDR_CONSULTA_DEUDA_TIIDC("PDR_CONSULTA_DEUDA_TIIDC"),
	PDR_CONSULTA_DEUDA_TICON("PDR_CONSULTA_DEUDA_TICON"),
	PDR_CONSULTA_DEUDA_POP("PDR_CONSULTA_DEUDA_POP"),
	WITHPROXY("PORTAL_SYSTEM_PROXY"),
	
	//parametros para GMP
	GMP_IDCNL("GMP_canal"),
	GMP_IDEMP("GMP_empresa"),
	GMP_NCLI("GMP_numcliente"),
	GMP_KEY_1("GMP_KEY_1"),
	GMP_KEY_2("GMP_KEY_2"),
	GMP_KEY_3("GMP_KEY_3"),
	GMP_PASSW("GMP_password"),
	GMP_PAGRE("GMP_pagResp"),
	GMP_PAGRE_GTD("GMP_pagResp_GTD"),
	GMP_PAGVOUCHER("GMP_pagVoucher"),
	GMP_PAGVOUCHER_GTD("GMP_pagVoucher_GTD"),
	GMP_WSURL("GMP_endpoint_acceso"),
	GMP_PROXY("GMP_proxy"),
	GMP_PROXY_HOST("GMP_proxy_host"),
	GMP_PROXY_PORT("GMP_proxy_port"),

	
	
	//constantes imagenes medios de pago
	MP_BTNID_WEBPAY_TELSUR("MP_BTNID_WEBPAY_TELSUR"),
	MP_BTNID_WEBPAY_TELCOY("MP_BTNID_WEBPAY_TELCOY"),
    MP_ESTADO("MP_ESTADO_"),
    MP_MODO("MP_MODO_"),
    MP_ALTO("MP_ALTO_"),
    MP_ANCHO("MP_ANCHO_"),
    MP_IMG_URL("MP_IMG_URL_"),
    
    //constantes notificaciones
    IDPLAMAIL("idpla_mail"),
    IDEMPMAIL("idemp_mail"),
    REMITENTEMAIL("remite_mail"),
    ASUNTOMAIL("asunto_mail"),
    
	//servicio no disponible
    IS_SERVICIO_DISPONIBLE("IS_SERVICIO_DISPONIBLE"),
    MENSAJE_FUERA_SERVICIO("MENSAJE_FUERA_SERVICIO"),
    
	//utils
	FORMATDATE("yyyyMMddHHmmss"),
	TRXOBJECT("TRXSESSION");
	

	
	
	private String id;
	
	private ConstantsEnum(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
