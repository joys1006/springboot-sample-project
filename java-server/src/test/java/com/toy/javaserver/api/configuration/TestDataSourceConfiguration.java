package com.toy.javaserver.api.configuration;

import com.toy.javaserver.api.common.utils.ReplicationRoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@TestConfiguration
public class TestDataSourceConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "database.datasource-read")
    public DataSource readDataSource() { return DataSourceBuilder.create().type(HikariDataSource.class).build(); }

    @Bean
    @ConfigurationProperties(prefix = "database.datasource-write")
    public DataSource writeDataSource() { return DataSourceBuilder.create().type(HikariDataSource.class).build(); }

    @Bean
    @DependsOn({"readDataSource", "writeDataSource"})
    public DataSource routingDataSource(@Qualifier("readDataSource") DataSource readDataSource,
                                        @Qualifier("writeDataSource") DataSource writeDataSource
    ) {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();

        dataSourceMap.put("read", readDataSource);
        dataSourceMap.put("write", writeDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(readDataSource);

        return routingDataSource;
    }

    @Primary
    @Bean
    @DependsOn("routingDataSource")
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
