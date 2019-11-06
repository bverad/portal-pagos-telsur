package cl.devetel.pagostelsur.prov.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the dvt__tp_param_web database table.
 * 
 */
@Entity
@Table(name="dvt__tp_param_web")
@NamedQuery(name="DvtTpParamWeb.findAll", query="SELECT d FROM DvtTpParamWeb d")
public class DvtTpParamWeb implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DvtTpParamWebPK id;

	@Column(name="param_val")
	private String paramVal;

	public DvtTpParamWeb() {
	}

	public DvtTpParamWebPK getId() {
		return this.id;
	}

	public void setId(DvtTpParamWebPK id) {
		this.id = id;
	}

	public String getParamVal() {
		return this.paramVal;
	}

	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}

}