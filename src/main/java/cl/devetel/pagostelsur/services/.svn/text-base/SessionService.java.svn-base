package cl.devetel.pagostelsur.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import cl.devetel.canalgmp.jdbc.dao.ConfiguracionDao;
import cl.devetel.concentrador.jdbc.dao.ConcentradorDao;
import cl.devetel.gmp.core.model2.Transaccion;
import cl.devetel.pagostelsur.enums.ConstantsEnum;
import cl.devetel.pagostelsur.responses.HeadDocument;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionService {

	private final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(this);

	private Transaccion transaccion;

	private HeadDocument document;

	@Autowired
	private ConcentradorDao concentradorDao;

	@Autowired
	private ConfiguracionDao configDao;

	@PostConstruct
	public void init() {
		log.info("Inicializando session service : {}, hashcode : {}", this, this.hashCode());
	}

	public HeadDocument getDocument() {
		return document;
	}

	public void setDocument(HeadDocument document) {
		this.document = document;
	}

	public Transaccion inicializaTransaccion() {
		transaccion = null;
		return transaccion;
	}

	public Transaccion getTransaccion() {
		if (this.transaccion == null) {
			generaTransaccion();
		}

		return this.transaccion;
	}

	public Transaccion obtieneTransaccion() {
		return this.transaccion;
	}

	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

	public Transaccion resetTransaccion() {
		generaTransaccion();
		return this.transaccion;
	}

	private void generaTransaccion() {

		String idrec = configDao.obtenerParametroConfiguracion(ConstantsEnum.PDR_CONSULTA_DEUDA_IDREC.getId());
		String idsuc = configDao.obtenerParametroConfiguracion(ConstantsEnum.PDR_CONSULTA_DEUDA_IDSUC.getId());
		String idcnl = configDao.obtenerParametroConfiguracion(ConstantsEnum.PDR_CONSULTA_DEUDA_IDCNL.getId());
		String idter = configDao.obtenerParametroConfiguracion(ConstantsEnum.PDR_CONSULTA_DEUDA_IDTER.getId());

		String idtrx = "0";

		try {
			idtrx = concentradorDao.generarIDTRX("dvt436_prov_01");
		} catch (Exception e) {
			log.error("[ ! ] Problema al generar el IDTRX de la transacción. Reintentar.");
			try {
				idtrx = concentradorDao.generarIDTRX("dvt436_prov_01");
			} catch (Exception e1) {
				log.error("[ ! ] Problema al generar el IDTRX de la transacción. Valor por defecto: 0");
			}
		}

		this.transaccion = new Transaccion();
		this.transaccion.setIdTrx(idtrx);
		this.transaccion.setIdRecaudador(idrec);
		this.transaccion.setIdSucursal(idsuc);
		this.transaccion.setIdCanal(idcnl);
		this.transaccion.setIdTerminal(idter);

		log.info("______________________________________________________");
		log.info("[ OK ] Nueva transacción generada: {}", idtrx);
		log.info("       RECAUDADOR                : {}", idrec);
		log.info("       SUCURSAL                  : {}", idsuc);
		log.info("       CANAL                     : {}", idcnl);
		log.info("       TERMINAL                  : {}", idter);
		log.info("______________________________________________________");
	}

	public void limpiarValores() {
		transaccion = null;
		document = null;
	}

	@Override
	public String toString() {
		return "SessionService [transaccion=" + transaccion + ", document=" + document + "]";
	}

	

}