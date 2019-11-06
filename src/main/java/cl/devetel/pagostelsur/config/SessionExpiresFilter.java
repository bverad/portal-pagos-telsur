package cl.devetel.pagostelsur.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SessionExpiresFilter implements Filter {

	private final Logger log = LoggerFactory.getLogger(SessionExpiresFilter.class);

	private FilterConfig filterConfig;
	
	// URL a las que no les sera aplicado el filtro de expiración
	private String[] urlsWithoutExpiration = {
			"/pagos-telsur/",
			"/pagos-telsur/pagos/retornoGMP",
			"/pagos-telsur/pagos/respuesta"	
	};

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		try {
			//setea content type, por defecto viene nulo
			setContentType(request, response);
			// no crea sesion si no existe
			HttpSession session = request.getSession(false);
			if ((session != null && !session.isNew())) {
				chain.doFilter(request, response);
			} else {
				//redirecciona accion, y los recursos estaticos son enviados a traves de metodo chain
				List<String> list = Arrays.asList(urlsWithoutExpiration);
				if (request.getHeader("referer") != null && response.getContentType() == null && !list.contains(request.getRequestURI())) {
					request.setAttribute("mensaje", "La sesion expiro, consulte nuevamente.");
					request.getRequestDispatcher("/").forward(request, response);
				} else {
					chain.doFilter(request, response);
				}

			}

		} catch (Exception ex) {
			//si no corresponde a una accion el recurso se mapea a traves de chain
			if(response.getContentType() != null) {
				chain.doFilter(request, response);
			}else {
				log.info("Ocurrio un problema con filtro de expiracion de sesion : {}", ex.getMessage());
				//si corresponde al path de la accion es redirigido mediante forward
				request.setAttribute("javax.servlet.error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				request.getRequestDispatcher("/error").forward(request, response);
				ex.printStackTrace();
			}

			

		}

	}

	@Override
	public void destroy() {
		filterConfig = null;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void setContentType(HttpServletRequest request, HttpServletResponse response) {
		//se identifican static resources y se setea su content type correspondiente ya que por defecto son nulos.
		if (request.getServletPath().contains("css")) {
			response.setContentType("text/css");
		} else if (request.getServletPath().contains("js")) {
			response.setContentType("text/javascript");
		} else if (request.getServletPath().contains("img")) {
			response.setContentType("image/png");
		}
	}

}
