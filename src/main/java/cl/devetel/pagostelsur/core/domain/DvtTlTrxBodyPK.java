package cl.devetel.pagostelsur.core.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the dvt__tl_trx_body database table.
 * 
 */
@Embeddable
public class DvtTlTrxBodyPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String idtrx;

	private String doc;

	private String io1;

	public DvtTlTrxBodyPK() {
	}
	public String getIdtrx() {
		return this.idtrx;
	}
	public void setIdtrx(String idtrx) {
		this.idtrx = idtrx;
	}
	public String getDoc() {
		return this.doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	public String getIo1() {
		return this.io1;
	}
	public void setIo1(String io1) {
		this.io1 = io1;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DvtTlTrxBodyPK)) {
			return false;
		}
		DvtTlTrxBodyPK castOther = (DvtTlTrxBodyPK)other;
		return 
			this.idtrx.equals(castOther.idtrx)
			&& this.doc.equals(castOther.doc)
			&& this.io1.equals(castOther.io1);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idtrx.hashCode();
		hash = hash * prime + this.doc.hashCode();
		hash = hash * prime + this.io1.hashCode();
		
		return hash;
	}
}