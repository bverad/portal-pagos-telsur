package cl.devetel.pagostelsur.responses;

/**
 * Objeto que recrea la informacion requerida para las cuentas ya seleccionadas desde 
 * respuesta JSON retornada al seleccionar las cuentas a pagar.
 * @author bvera
 * @since 15/05/2018
 *
 */
public class Cuenta {
	private String nmro_documen;
	private String fech_documen;
	private String fech_vencmto;
	private String valor;
	private String tipo_doc;
	private String saldo;
	private String desc_tipo_documen;
	private String document_id;
	private String io2;
	public String getNmro_documen() {
		return nmro_documen;
	}
	public void setNmro_documen(String nmro_documen) {
		this.nmro_documen = nmro_documen;
	}
	public String getFech_documen() {
		return fech_documen;
	}
	public void setFech_documen(String fech_documen) {
		this.fech_documen = fech_documen;
	}
	public String getFech_vencmto() {
		return fech_vencmto;
	}
	public void setFech_vencmto(String fech_vencmto) {
		this.fech_vencmto = fech_vencmto;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getTipo_doc() {
		return tipo_doc;
	}
	public void setTipo_doc(String tipo_doc) {
		this.tipo_doc = tipo_doc;
	}
	public String getSaldo() {
		return saldo;
	}
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
	public String getDesc_tipo_documen() {
		return desc_tipo_documen;
	}
	public void setDesc_tipo_documen(String desc_tipo_documen) {
		this.desc_tipo_documen = desc_tipo_documen;
	}
	public String getDocument_id() {
		return document_id;
	}
	public void setDocument_id(String document_id) {
		this.document_id = document_id;
	}
	public String getIo2() {
		return io2;
	}
	public void setIo2(String io2) {
		this.io2 = io2;
	}
	@Override
	public String toString() {
		return "Cuenta [nmro_documen=" + nmro_documen + ", fech_documen=" + fech_documen + ", fech_vencmto="
				+ fech_vencmto + ", valor=" + valor + ", tipo_doc=" + tipo_doc + ", saldo=" + saldo
				+ ", desc_tipo_documen=" + desc_tipo_documen + ", document_id=" + document_id + ", io2=" + io2 + "]";
	}
	
	
	
    
	
    
}
