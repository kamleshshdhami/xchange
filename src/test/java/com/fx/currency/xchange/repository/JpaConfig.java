package com.fx.currency.xchange.repository;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fx.currency.xchange.controller.FxTradingController;
import com.fx.currency.xchange.service.CurrencyConversionService;
import com.fx.currency.xchange.service.FxService;

@Configuration
@EnableJpaRepositories(basePackages = "com.fx.currency.xchange")
@EntityScan("com.fx.currency.xchange.entity")
@EnableTransactionManagement
public class JpaConfig {

	@Primary
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }
	
	
	@Bean
    public FxRepository fxRepository() {
       return new FxRepositoryImpl();
    }
	@Bean
    public CurrencyConversionService currencyConversionService() {
       return new CurrencyConversionService();
    }
	
	@Bean
    public FxService fxService() {
       return new FxService();
    }
	
	@Bean
    public FxTradingController fxTradingController() {
       return new FxTradingController();
    }
	
}
