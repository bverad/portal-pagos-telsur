package cl.devetel.pagostelsur.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cl.devetel.pagostelsur.enums.EnvironmentEnum;


/**
 * Clase de configuracion para datasource prov
 * @author bvera
 *
 */
@Configuration
@EnableTransactionManagement
//@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(basePackages = "cl.devetel.pagostelsur.prov.repositories", 
						entityManagerFactoryRef = "provEntityManager", 
						transactionManagerRef = "provTransactionManager")
public class ProvDbConfig {
	
	private final Logger log = LoggerFactory.getLogger(ProvDbConfig.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
    private ApplicationProperties applicationProperties;
	
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean provEntityManager() {
		log.info("Creando entity manager : " + env.getActiveProfiles()[0]);
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(provDataSource());
		em.setPackagesToScan(new String[] { "cl.devetel.pagostelsur.prov.domain" });
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", applicationProperties.getDbprov().get("ddl-auto"));
		properties.put("hibernate.dialect", applicationProperties.getDbprov().get("dialect"));
		em.setJpaPropertyMap(properties);

		return em;
	}

	/**
	 * Retorna datasource segun profiles activos.
	 * En el caso que corresponda al profile default se autentica mediante JDBC,
	 * de lo contrario via JNDI.
	 * @return
	 * @author bvera
	 */
	@Primary
	@Bean(name="dataSourcePROV")
	public DataSource provDataSource() {
		//environment default
		if(env.getActiveProfiles()[0].equals(EnvironmentEnum.DEFAULT.getDescripcion())) {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			//environment default
			dataSource.setDriverClassName(applicationProperties.getDbprov().get("driver-class-name"));
			dataSource.setUrl(applicationProperties.getDbprov().get("url"));
			dataSource.setUsername(applicationProperties.getDbprov().get("username"));
			dataSource.setPassword(applicationProperties.getDbprov().get("password"));

			return dataSource;
		}else {
			//environment dev and prod
			JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
	        DataSource dataSource = dataSourceLookup.getDataSource(applicationProperties.getDbprov().get("jndi-name"));
	        
	        return dataSource;
		}	
	}

	@Primary
	@Bean
	public PlatformTransactionManager provTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(provEntityManager().getObject());
		return transactionManager;
	}
}
