package com.example.biat.InterfacageOGP.config;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@PropertySource("classpath:application.properties")
@RestController
public class DataSourceConfig extends WebMvcConfigurerAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);
	public static DriverManagerDataSource dataSource = new DriverManagerDataSource();
	public static DriverManagerDataSource dataSource_trac = new DriverManagerDataSource();


	@Autowired
	JdbcTemplate jdbcTemplate_Init;
	@Autowired
	JdbcTemplate jdbcTemplate;
/**

add some thing from master branch
*/


	@Autowired
	JdbcTemplate jdbcTemplate_InitDynamic;
	static String username = "root";
	static String password ="";
	static String url = "jdbc:mysql://172.28.201.74:3306/env_trac?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	static String driver = "com.mysql.jdbc.Driver";

	@Primary
	@Bean("dataSourceBuilder_finale")
	public List<Map<String, Object>> dataSource() {

		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

		dataSourceBuilder.username(username);
		dataSourceBuilder.password(password);
		dataSourceBuilder.driverClassName(driver);
		dataSourceBuilder.url(url);
		dataSourceBuilder.build();

		DriverManagerDataSource dataSource_Init = new DriverManagerDataSource();

		dataSource_Init.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource_Init.setUrl(url);
		dataSource_Init.setUsername(username);
		dataSource_Init.setPassword(password);
		System.out.println("CONNEXION 1" + " " + dataSource_Init.getUsername() + " URL " + dataSource_Init.getUrl());

		jdbcTemplate.setDataSource(dataSource_Init);

		List<Map<String, Object>> list = jdbcTemplate_Init.queryForList("SELECT * FROM ov_ref WHERE ACTIVITIE='1' AND ENV='niveauprojet'");

		System.out.println("List Finale" + " " + list.toString());

		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl((String) list.get(0).get("URL"));
		dataSource.setUsername((String) list.get(0).get("USERNAME"));
		dataSource.setPassword((String) list.get(0).get("PASSWORD"));

		DataSourceBuilder dataSourceBuilder_finale = DataSourceBuilder.create();

		dataSourceBuilder_finale.driverClassName("com.mysql.jdbc.Driver");
		dataSourceBuilder_finale.url((String) list.get(0).get("URL"));
		dataSourceBuilder_finale.username((String) list.get(0).get("USERNAME"));
		dataSourceBuilder_finale.password((String) list.get(0).get("PASSWORD"));
		System.out.println(dataSource.toString());
		jdbcTemplate.setDataSource(dataSource);
        jdbcTemplate();


		return list;

	}

	@Bean("OVDataSource")
	public static JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}


}
