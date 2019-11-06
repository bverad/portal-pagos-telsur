package cl.devetel.pagostelsur.responses;
/**
 * Response para la obtencion de botones de medios de pago desde GMP.
 * @author bvera
 *
 */
public class BotonMedioPago {

	
	private String id;
	private String img;
	private String nombre;
	private String url;
	private String solicitudEnc;
	private String idCanal;
	private String modoPago;
	private String estado;
	private String alto;
	private String ancho;

	public BotonMedioPago() { }

	public BotonMedioPago(String id, String img, String nombre, String url, String solicitudEnc, String idCanal, String modoPago, String estado, String alto, String ancho) {
		this.id = id;
		this.img = img;
		this.nombre = nombre;
		this.url = url;
		this.solicitudEnc = solicitudEnc;
		this.idCanal = idCanal;
		this.modoPago = modoPago;
		this.estado = estado;
		this.alto = alto;
		this.ancho = ancho;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSolicitudEnc() {
		return solicitudEnc;
	}

	public void setSolicitudEnc(String solicitudEnc) {
		this.solicitudEnc = solicitudEnc;
	}

	public String getIdCanal() {
		return idCanal;
	}

	public void setIdCanal(String idCanal) {
		this.idCanal = idCanal;
	}
	
	
	public String getModoPago() {
		return modoPago;
	}

	public void setModoPago(String modoPago) {
		this.modoPago = modoPago;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAlto() {
		return alto;
	}

	public void setAlto(String alto) {
		this.alto = alto;
	}

	public String getAncho() {
		return ancho;
	}

	public void setAncho(String ancho) {
		this.ancho = ancho;
	}

	@Override
	public String toString() {
		return "BotonMedioPago [id=" + id + ", img=" + img + ", nombre=" + nombre + ", url=" + url + ", solicitudEnc=" + solicitudEnc + ", idCanal=" + idCanal + 
				", modoPago=" + modoPago + ", estado=" + estado + ", alto="+ alto + ", ancho="+ ancho +"]";
	}

}
