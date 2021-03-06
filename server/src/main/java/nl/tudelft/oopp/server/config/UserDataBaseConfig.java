package nl.tudelft.oopp.server.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class UserDataBaseConfig {
    @Autowired
    private AbstractEnvironment environment;

    /**
     * Set up the connection to the database.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/database_OOPP?createDatabaseIfNotExist=true&"
            + "useLegacyDatetimeCode=false&serverTimezone=Europe/Amsterdam&amp");
        dataSource.setUsername("springuser");
        dataSource.setPassword("krabbypatty");

        return dataSource;
    }
}
