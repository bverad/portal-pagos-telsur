package cl.devetel.pagostelsur.responses;

import java.io.Serializable;
import java.util.List;

/**
 * Clase dummy para head del documento a presentar
 * @author bvera
 *
 */
public class HeadDocument implements Serializable {
	private String transaccionId;
	private String sucursal;
	private String montoTotal;
	private Long cantidadRegistrosTelsur;
	private Long cantidadRegistrosTelcoy;
	private String codigoRetornoTelsur;
	private String codigoRetornoTelcoy;
	
	private List<SucursalDocument> sucursalList;
	
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(String montoTotal) {
		this.montoTotal = montoTotal;
	}
	public String getTransaccionId() {
		return transaccionId;
	}
	public void setTransaccionId(String transaccionId) {
		this.transaccionId = transaccionId;
	}
	public Long getCantidadRegistrosTelsur() {
		return cantidadRegistrosTelsur;
	}
	public void setCantidadRegistrosTelsur(Long cantidadRegistrosTelsur) {
		this.cantidadRegistrosTelsur = cantidadRegistrosTelsur;
	}
	public Long getCantidadRegistrosTelcoy() {
		return cantidadRegistrosTelcoy;
	}
	public void setCantidadRegistrosTelcoy(Long cantidadRegistrosTelcoy) {
		this.cantidadRegistrosTelcoy = cantidadRegistrosTelcoy;
	}
	public String getCodigoRetornoTelsur() {
		return codigoRetornoTelsur;
	}
	public void setCodigoRetornoTelsur(String codigoRetornoTelsur) {
		this.codigoRetornoTelsur = codigoRetornoTelsur;
	}
	public String getCodigoRetornoTelcoy() {
		return codigoRetornoTelcoy;
	}
	public void setCodigoRetornoTelcoy(String codigoRetornoTelcoy) {
		this.codigoRetornoTelcoy = codigoRetornoTelcoy;
	}
	public List<SucursalDocument> getSucursalList() {
		return sucursalList;
	}
	public void setSucursalList(List<SucursalDocument> sucursalList) {
		this.sucursalList = sucursalList;
	}
	@Override
	public String toString() {
		return "HeadDocument [transaccionId=" + transaccionId + ", sucursal=" + sucursal + ", montoTotal=" + montoTotal
				+ ", cantidadRegistrosTelsur=" + cantidadRegistrosTelsur + ", cantidadRegistrosTelcoy="
				+ cantidadRegistrosTelcoy + ", codigoRetornoTelsur=" + codigoRetornoTelsur + ", codigoRetornoTelcoy="
				+ codigoRetornoTelcoy + ", sucursalList=" + sucursalList + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cantidadRegistrosTelcoy == null) ? 0 : cantidadRegistrosTelcoy.hashCode());
		result = prime * result + ((cantidadRegistrosTelsur == null) ? 0 : cantidadRegistrosTelsur.hashCode());
		result = prime * result + ((codigoRetornoTelcoy == null) ? 0 : codigoRetornoTelcoy.hashCode());
		result = prime * result + ((codigoRetornoTelsur == null) ? 0 : codigoRetornoTelsur.hashCode());
		result = prime * result + ((montoTotal == null) ? 0 : montoTotal.hashCode());
		result = prime * result + ((sucursal == null) ? 0 : sucursal.hashCode());
		result = prime * result + ((sucursalList == null) ? 0 : sucursalList.hashCode());
		result = prime * result + ((transaccionId == null) ? 0 : transaccionId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HeadDocument other = (HeadDocument) obj;
		if (cantidadRegistrosTelcoy == null) {
			if (other.cantidadRegistrosTelcoy != null)
				return false;
		} else if (!cantidadRegistrosTelcoy.equals(other.cantidadRegistrosTelcoy))
			return false;
		if (cantidadRegistrosTelsur == null) {
			if (other.cantidadRegistrosTelsur != null)
				return false;
		} else if (!cantidadRegistrosTelsur.equals(other.cantidadRegistrosTelsur))
			return false;
		if (codigoRetornoTelcoy == null) {
			if (other.codigoRetornoTelcoy != null)
				return false;
		} else if (!codigoRetornoTelcoy.equals(other.codigoRetornoTelcoy))
			return false;
		if (codigoRetornoTelsur == null) {
			if (other.codigoRetornoTelsur != null)
				return false;
		} else if (!codigoRetornoTelsur.equals(other.codigoRetornoTelsur))
			return false;
		if (montoTotal == null) {
			if (other.montoTotal != null)
				return false;
		} else if (!montoTotal.equals(other.montoTotal))
			return false;
		if (sucursal == null) {
			if (other.sucursal != null)
				return false;
		} else if (!sucursal.equals(other.sucursal))
			return false;
		if (sucursalList == null) {
			if (other.sucursalList != null)
				return false;
		} else if (!sucursalList.equals(other.sucursalList))
			return false;
		if (transaccionId == null) {
			if (other.transaccionId != null)
				return false;
		} else if (!transaccionId.equals(other.transaccionId))
			return false;
		return true;
	}
	
	
	
}
