package com.cloud.springcloudweb.config;

import com.mysql.cj.MysqlConnection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

@Configuration
@EnableR2dbcRepositories(basePackages = {"com.cloud.springcloudweb.repository"})
public class R2DBCConfiguration extends AbstractR2dbcConfiguration {

    @Bean
    @Override
    public ConnectionFactory connectionFactory() {

        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "r2dbc:mysql")
                .option(ConnectionFactoryOptions.HOST, "127.0.0.1")
                .option(ConnectionFactoryOptions.PORT, 3306)
                .option(ConnectionFactoryOptions.DATABASE, "spring")
                .option(ConnectionFactoryOptions.USER, "spring")
                .option(ConnectionFactoryOptions.PASSWORD, "spring")
                .build());
    }

    /*@Bean
    public MysqlConnectionFactory connectionFactory() {
            return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                                                    .host(host)
                                                    .port(port)
                                                    .database(database)
                                                    .username(username)
                                                    .password(password)
                                                    .build());
        }*/
}
