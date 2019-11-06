package cl.devetel.pagostelsur.enums;

/**
 * Enumeracion que contiene los codigos de retorno disponibles para la consulta a PDR.
 * @author bvera
 *
 */
public enum CodigoRetornoPDREnum {
	
	CODE_00("00","Transaccion exitosa"),
	CODE_01("01","Transaccion exitosa ya informada"),
	CODE_02("02","ID Cliente (p_idcli) desconocido"),
	CODE_03("03","ID Cliente (p_idcli) no posee deuda"),
	CODE_04("04","ID Recaudador (p_idrec) desconocido o fuera de servicio"),
	CODE_05("05","ID Empresa o convenio (p_idemp) desconocido o fuera de servicio"),
	CODE_06("06","ID Recaudador sin acceso a ID Empresa"),
	CODE_07("07","Documento se encuentra Pagado"),
	CODE_08("08","Documento duplicado"),
	CODE_09("09","Monto pago difiere al de deuda"),
	CODE_10("10","Documento desconocido"),
	CODE_11("11","ID Canal (p_idcnl) desconocido o fuera de servicio"),
	
	CODE_30("30","p_ver fuera de formato"),
	CODE_31("31","p_gwa fuera de formato"),
	CODE_32("32","p_tid fuera de formato"),
	CODE_33("33","p_idrec fuera de formato"),
	CODE_34("34","p_idsuc fuera de formato"),
	CODE_35("35","p_idcnl fuera de formato"),
	CODE_36("36","p_idter fuera de formato"),
	CODE_37("37","p_fectr fuera de formato o invalida"),
	CODE_38("38","p_sectr fuera de formato"),
	CODE_39("39","p_idemp fuera de formato"),
	
	CODE_40("40","p_ticon fuera de formato o invalido"),
	CODE_41("41","p_tiidc fuera de formato o invalido"),
	CODE_42("42","p_idcli fuera de formato"),
	CODE_43("43","p_fepag fuera de formato o invalida"),
	CODE_44("44","p_mpti fuera de formato o invalido"),
	CODE_45("45","p_mpem fuera de formato"),
	CODE_46("46","p_ndoc fuera de formato"),
	
	CODE_59("59","p_pop fuera de formato"),
	
	CODE_60("60","Comando formato invalido - decodificación fallida"),
	CODE_61("61","Comando con parámetro ausente o invalido (fuera de formato)"),
	CODE_62("62","Error de procesamiento plataforma. Comando no procesado"),
	CODE_63("63","Error de procesamiento plataforma. Comando no procesado (timeout)"),
	CODE_64("64","Sistema Fuera de Servicio"),
	
	CODE_95("95","Error de timeout en la respuesta."),
	CODE_99("99","Ocurrió un inconveniente al intentar procesar su solicitud. Intente más tarde.");
	
	String codigo;
	String descripcion;
	
	/**
	 * Retorna valor de enum segun codigo
	 * @param codigo
	 * @return
	 * @author bvera
	 * @since 02/05/2018
	 */
	public static CodigoRetornoPDREnum getById(String codigo) {
	    for(CodigoRetornoPDREnum e : values()) {
	        if(e.codigo.equals(codigo)) 
	        	return e;
	    }
	    
	    return null;
	}
	
	
	private CodigoRetornoPDREnum(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
