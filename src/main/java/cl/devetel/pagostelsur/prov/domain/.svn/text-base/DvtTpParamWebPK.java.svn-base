package cl.devetel.pagostelsur.prov.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the dvt__tp_param_web database table.
 * 
 */
@Embeddable
public class DvtTpParamWebPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="param_id")
	private int paramId;

	@Column(name="param_nom")
	private String paramNom;

	@Column(name="param_cnl_id")
	private int paramCnlId;

	@Column(name="param_mp_id")
	private int paramMpId;

	public DvtTpParamWebPK() {
	}
	public int getParamId() {
		return this.paramId;
	}
	public void setParamId(int paramId) {
		this.paramId = paramId;
	}
	public String getParamNom() {
		return this.paramNom;
	}
	public void setParamNom(String paramNom) {
		this.paramNom = paramNom;
	}
	public int getParamCnlId() {
		return this.paramCnlId;
	}
	public void setParamCnlId(int paramCnlId) {
		this.paramCnlId = paramCnlId;
	}
	public int getParamMpId() {
		return this.paramMpId;
	}
	public void setParamMpId(int paramMpId) {
		this.paramMpId = paramMpId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DvtTpParamWebPK)) {
			return false;
		}
		DvtTpParamWebPK castOther = (DvtTpParamWebPK)other;
		return 
			(this.paramId == castOther.paramId)
			&& this.paramNom.equals(castOther.paramNom)
			&& (this.paramCnlId == castOther.paramCnlId)
			&& (this.paramMpId == castOther.paramMpId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.paramId;
		hash = hash * prime + this.paramNom.hashCode();
		hash = hash * prime + this.paramCnlId;
		hash = hash * prime + this.paramMpId;
		
		return hash;
	}
}