package br.com.game.movies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@ComponentScan(basePackages = "br.com.game.movies.*")
public class MvcConfig {
	
	
    @Bean
    public DataSource dataSource()
    {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:mem:testdb");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("");        
        return dataSourceBuilder.build();
    }
    
    @Bean(name="CardGameSession")
    public Map<String, List<Integer>> getCardGameSessionInfo() {
    	return new HashMap<String, List<Integer>>();
    }    
}
