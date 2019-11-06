package cl.devetel.pagostelsur.services;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.naming.LinkLoopException;

import cl.devetel.gmp.core.facade.GmpManager;
import cl.devetel.gmp.core.model.SolicitarCreditoResponse;
import cl.devetel.gmp.core.model2.Transaccion;
import cl.devetel.gmp.model.core.Comprobante;
import cl.devetel.pagostelsur.core.domain.DvtTlTrxBody;
import cl.devetel.pagostelsur.core.domain.DvtTlTrxHead;
import cl.devetel.pagostelsur.core.to.ComprobantesAnteriores;
import cl.devetel.pagostelsur.responses.BotonMedioPago;
import cl.devetel.pagostelsur.responses.Cuenta;
import cl.devetel.pagostelsur.responses.HeadDocument;
import cl.devetel.ws.consultadeuda.ConsultaDeudaResponse;

public interface PagosService {
	
	LinkedHashMap<String, ConsultaDeudaResponse> obtenerPagos(Transaccion transaccion, String... empresas) throws MalformedURLException;
	
	String obtenerParametro(String paramNom);
	
    GmpManager prepararGMP(String empresa, Transaccion transaccion);
    
    List<BotonMedioPago> obtenerBotones(GmpManager gmp, Transaccion transaccion, String paginaRespuesta) throws RemoteException, Exception;
    
    void guardarTransaccion( Transaccion transaccion ) throws Exception;
    
    BigInteger obtenerMontoTotal( Transaccion trx, List<Cuenta> cuentas);
    
    SolicitarCreditoResponse decodificarRespuestaGMP(Transaccion transaccion, String r);
    
    Transaccion obtenerTransaccionConIDGMP( String id ) throws Exception;
    
    String actualizarEstadoTransaccionDB(Transaccion transaccion);
    
    Comprobante obtenerComprobante(Transaccion transaccion);
    
    Comprobante obtenerComprobanteParaMail(Transaccion transaccion);
    
    void enviarCorreoSinAdjuntos(String idtrx, String idpla, String idemp, String remitente, String destinatario, String asunto, String cuerpo);
    
    String generarCuerpoMail(Comprobante c, Transaccion t);
    
    List<DvtTlTrxBody> obtenerTransaccionesPagadas(Transaccion transaccion);
    
    HeadDocument obtenerDocumento(LinkedHashMap<String, ConsultaDeudaResponse> deudaResponseMap, Transaccion transaccion, String... empresas) throws ParseException;
    
    List<ComprobantesAnteriores> listarComprobantes(String idcli, String empresa);
    
    Boolean obtenerTransaccion(String idTrx);
    
    DvtTlTrxHead obtenerUltimaTransaccion();
    
}
