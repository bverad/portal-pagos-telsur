package cl.devetel.pagostelsur.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 * Clase de configuracion para datasource core
 * @author bvera
 *
 */
@Configuration
//@PropertySource({ "classpath:application.properties" })
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "cl.devetel.pagostelsur.core.repositories", 
						entityManagerFactoryRef = "coreEntityManager", 
						transactionManagerRef = "coreTransactionManager")
public class CoreDbConfig {

	private final Logger log = LoggerFactory.getLogger(CoreDbConfig.class);
	
	@Autowired
	private Environment env;
	
    @Autowired
    private ApplicationProperties applicationProperties;
    
	
	@Bean
	public LocalContainerEntityManagerFactoryBean coreEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(coreDataSource());
		em.setPackagesToScan(new String[] { "cl.devetel.pagostelsur.core.domain" });
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", applicationProperties.getDbcore().get("ddl-auto"));
		properties.put("hibernate.dialect", applicationProperties.getDbcore().get("dialect"));
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
	@Bean(name="dataSourceCORE")
	public DataSource coreDataSource() {
		log.info("Profile activo en la creacion de datasource CORE : " + env.getActiveProfiles()[0]);
		//environment default
		if(env.getActiveProfiles()[0].equals(EnvironmentEnum.DEFAULT.getDescripcion())) {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(applicationProperties.getDbcore().get("driver-class-name"));
			dataSource.setUrl(applicationProperties.getDbcore().get("url"));
			dataSource.setUsername(applicationProperties.getDbcore().get("username"));
			dataSource.setPassword(applicationProperties.getDbcore().get("password"));
			
			return dataSource;
		}else {
			//environment dev and prod
			JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
	        DataSource dataSource = dataSourceLookup.getDataSource(applicationProperties.getDbcore().get("jndi-name"));

	        return dataSource;
		}
			
	}

	@Bean
	public PlatformTransactionManager coreTransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(coreEntityManager().getObject());
		return transactionManager;
	}

}
