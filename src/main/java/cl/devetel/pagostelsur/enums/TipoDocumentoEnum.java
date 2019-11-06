package cl.devetel.pagostelsur.enums;

/**
 * Enumeracion que contiene los codigos de retorno disponibles para la consulta a PDR.
 * @author bvera
 *
 */
public enum TipoDocumentoEnum {
	
	CODE_01("01","Boleta Comp."),
	CODE_02("02","Factura Comp.");
	
	String codigo;
	String descripcion;
	
	/**
	 * Retorna valor de enum segun codigo
	 * @param codigo
	 * @return
	 * @author bvera
	 * @since 02/05/2018
	 */
	public static TipoDocumentoEnum getById(String codigo) {
	    for(TipoDocumentoEnum e : values()) {
	        if(e.codigo.equals(codigo)) 
	        	return e;
	    }
	    
	    return null;
	}
	
	
	private TipoDocumentoEnum(String codigo, String descripcion) {
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
