package com.hotmart.api.subscription.ddd.infrastructure.configuration.persistence;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.hotmart.api.subscription.ddd.infrastructure.product.persistence",
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager"
)
public class PrimaryDataSourceConfig {

    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primarydb")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("primaryDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.hotmart.api.subscription.ddd.infrastructure.product.persistence")
                .persistenceUnit("primarydb")
                .build();
    }

    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager db1TransactionManager(
            @Qualifier("primaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory
    ) {
        return new JpaTransactionManager(primaryEntityManagerFactory.getObject());
    }

}
