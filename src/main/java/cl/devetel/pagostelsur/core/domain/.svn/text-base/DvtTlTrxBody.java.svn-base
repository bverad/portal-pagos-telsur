package cl.devetel.pagostelsur.core.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the dvt__tl_trx_body database table.
 * 
 */
@Entity
@Table(name="dvt__tl_trx_body")
@NamedQuery(name="DvtTlTrxBody.findAll", query="SELECT d FROM DvtTlTrxBody d")
public class DvtTlTrxBody implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DvtTlTrxBodyPK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtc;
	
	private String tidoc;
	
	private String feemi;

	private String feven;

	private String io2;

	private String io3;

	private String io4;

	private String io5;

	private String mnt;

	private String srv;

	public DvtTlTrxBody() {
	}

	public DvtTlTrxBodyPK getId() {
		return this.id;
	}

	public void setId(DvtTlTrxBodyPK id) {
		this.id = id;
	}

	public Date getDtc() {
		return this.dtc;
	}

	public void setDtc(Date dtc) {
		this.dtc = dtc;
	}

	public String getTidoc() {
		return tidoc;
	}

	public void setTidoc(String tidoc) {
		this.tidoc = tidoc;
	}

	public String getFeemi() {
		return this.feemi;
	}

	public void setFeemi(String feemi) {
		this.feemi = feemi;
	}

	public String getFeven() {
		return this.feven;
	}

	public void setFeven(String feven) {
		this.feven = feven;
	}

	public String getIo2() {
		return this.io2;
	}

	public void setIo2(String io2) {
		this.io2 = io2;
	}

	public String getIo3() {
		return this.io3;
	}

	public void setIo3(String io3) {
		this.io3 = io3;
	}

	public String getIo4() {
		return this.io4;
	}

	public void setIo4(String io4) {
		this.io4 = io4;
	}

	public String getIo5() {
		return this.io5;
	}

	public void setIo5(String io5) {
		this.io5 = io5;
	}

	public String getMnt() {
		return this.mnt;
	}

	public void setMnt(String mnt) {
		this.mnt = mnt;
	}

	public String getSrv() {
		return this.srv;
	}

	public void setSrv(String srv) {
		this.srv = srv;
	}

}