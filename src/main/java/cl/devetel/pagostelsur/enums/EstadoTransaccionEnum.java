package cl.devetel.pagostelsur.enums;

/**
 * Enumeracion que contiene los codigos de retorno disponibles para la consulta a PDR.
 * @author bvera
 *
 */
public enum EstadoTransaccionEnum {
	
	N("N","Nuevo"),
	EVA("EVA","Exitoso validación de acceso."),
	FVA("FVA","Fallido validación de acceso."),
	ECP("ECP","Exitoso confirmación pago GMP."),
	FCP("FCP","Fallido confirmación pago GMP."),
	ENP("ENP","Exitoso notificación de pago Devetel y WOM."),
	FNP("FNP","Fallido notificación de pago Devetel y WOM."),
	OK("OK","OK"),
	NOK("NOK","NOK");
	
	String codigo;
	String descripcion;
	
	/**
	 * Retorna valor de enum segun codigo
	 * @param codigo
	 * @return
	 * @author bvera
	 * @since 02/05/2018
	 */
	public static EstadoTransaccionEnum getById(String codigo) {
	    for(EstadoTransaccionEnum e : values()) {
	        if(e.codigo.equals(codigo)) 
	        	return e;
	    }
	    
	    return null;
	}
	
	
	private EstadoTransaccionEnum(String codigo, String descripcion) {
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
