package cl.devetel.pagostelsur.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cl.devetel.pagostelsur.config.ApplicationProperties;

@Controller
public class ErrorsController implements ErrorController{
	
	private final Logger log = LoggerFactory.getLogger(ErrorsController.class);
	
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView handleError(HttpServletRequest httpRequest) {
		
		
		ModelAndView errorPage = new ModelAndView("error");
		String errorCodigo = "";
		String errorDescripcion = "";
		int httpErrorCode = getErrorCode(httpRequest);
		//calculando header and footer
		HashMap<String, String> headerAndFooterMap = new HashMap<>();
		String urlTelsur = applicationProperties.getUrl().get("telsur");
		if (httpRequest.getServerName().toString().equals(urlTelsur)) {
			headerAndFooterMap.put("tipoHeaderAndFooter", "telsur");
			headerAndFooterMap.put("header", applicationProperties.getHeader().get("telsur"));
			headerAndFooterMap.put("footer", applicationProperties.getFooter().get("telsur"));
		} else {
			headerAndFooterMap.put("tipoHeaderAndFooter", "gtd");
			headerAndFooterMap.put("header", applicationProperties.getHeader().get("gtd"));
			headerAndFooterMap.put("footer", applicationProperties.getFooter().get("gtd"));
		}
		
		switch (httpErrorCode) {
		case 400: {
			errorCodigo = "Solicitud errónea (400)";
			errorDescripcion = "Se produjo un error, intente mas tarde.";
			break;
		}
		case 401: {
			errorCodigo = "No está autorizado (401)";
			errorDescripcion = "Debe contar con autorización para ejecutar esta solicitud";
			break;
		}
		
		case 403: {
			errorCodigo = "No está autorizado (403)";
			errorDescripcion = "Debe contar con autorización para ejecutar esta solicitud";
			break;
		}
		
		case 404: {
			errorCodigo = "Página no encontrada (404)";
			errorDescripcion = "La página que busca no está disponible, por favor verifique que la dirección este correctamente ingresada o utilice nuestro buscador.";
			break;
		}
		
		case 405: {
			errorCodigo = "Solicitud no permitida (405)";
			errorDescripcion = "No es posible obtener la respuesta mediante esta modalidad.";
			break;
		}
		
		case 500: {
			errorCodigo = "Error interno (500)";
			errorDescripcion = "Se produjo un error, intente mas tarde.";
			break;
		}
		}
		errorPage.addObject("errorCodigo", errorCodigo);
		errorPage.addObject("errorDescripcion", errorDescripcion);
		errorPage.addObject("header", headerAndFooterMap.get("header"));
		errorPage.addObject("footer", headerAndFooterMap.get("footer"));
		return errorPage;
	}

	private int getErrorCode(HttpServletRequest httpRequest) {
		return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
		
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
