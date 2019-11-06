package cl.devetel.pagostelsur.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties("app")
public class ApplicationProperties {

	private Map<String, String> dbCore;
	private Map<String, String> dbProv;
	private Map<String, String> url;
	private Map<String, String> header;
	private Map<String, String> footer;

	private String email;
	
	private String maxInactiveInterval;

	public Map<String, String> getUrl() {
		return url;
	}

	public void setUrl(Map<String, String> url) {
		this.url = url;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	public Map<String, String> getFooter() {
		return footer;
	}

	public void setFooter(Map<String, String> footer) {
		this.footer = footer;
	}

	public Map<String, String> getDbcore() {
		return dbCore;
	}

	public void setDbcore(Map<String, String> dbcore) {
		this.dbCore = dbcore;
	}

	public Map<String, String> getDbprov() {
		return dbProv;
	}

	public void setDbprov(Map<String, String> dbprov) {
		this.dbProv = dbprov;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public void setMaxInactiveInterval(String maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}
	
	
}
