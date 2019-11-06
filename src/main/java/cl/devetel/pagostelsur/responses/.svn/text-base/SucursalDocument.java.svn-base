package cl.devetel.pagostelsur.responses;

import java.io.Serializable;
import java.util.List;



/**
 * Body del documento
 * 
 * @author bvera
 *
 */
public class SucursalDocument implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer correlativo;
	private String codigo;
	private String tipo;
	private String total;
	private String empresa;
	private List<BodyDocument> documentList;
	
	public Integer getCorrelativo() {
		return correlativo;
	}
	public void setCorrelativo(Integer correlativo) {
		this.correlativo = correlativo;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public List<BodyDocument> getDocumentList() {
		return documentList;
	}
	
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public void setDocumentList(List<BodyDocument> documentList) {
		this.documentList = documentList;
	}
	
	public void addDocument(BodyDocument bodyDocument) {
		documentList.add(bodyDocument);
	}
	
	
	
	
	
}