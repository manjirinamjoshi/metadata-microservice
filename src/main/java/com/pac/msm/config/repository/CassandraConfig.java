package com.pac.msm.config.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.mapping.SimpleUserTypeResolver;

import com.datastax.driver.core.Cluster;

/**
 * Configuration for cassandra database.
 */
@Configuration
@PropertySource(value = {"classpath:cassandra.properties"})
public class CassandraConfig {
    @Autowired
    private Environment environment;

    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(environment.getProperty("cassandra.contactpoints"));
        cluster.setPort(Integer.parseInt(environment.getProperty("cassandra.port")));
        cluster.setUsername(environment.getProperty("cassandra.username"));
        cluster.setPassword(environment.getProperty("cassandra.password"));
        return cluster;
    }

    /*@Bean
    public CassandraMappingContext mappingContext(Cluster cluster) {
        BasicCassandraMappingContext mappingContext = new BasicCassandraMappingContext();
        mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster,
                environment.getProperty("cassandra.keyspace")));
        return mappingContext;
    }*/
    
    @Bean
    public CassandraMappingContext mappingContext() {
        return new BasicCassandraMappingContext();
    }

    /*@Bean
    public CassandraConverter converter(Cluster cluster) {
        return new MappingCassandraConverter(mappingContext(cluster));
    }*/
    
    @Bean
    public CassandraConverter converter() throws ClassNotFoundException {
        return new MappingCassandraConverter();
    }

    @Bean
    public CassandraSessionFactoryBean session() throws Exception {
        Cluster cluster = cluster().getObject();

        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
        session.setCluster(cluster);
        session.setKeyspaceName(environment.getProperty("cassandra.keyspace"));
        session.setConverter(converter(/*cluster*/));
        session.setSchemaAction(SchemaAction.NONE);
        return session;
    }

    @Bean
    public CassandraOperations cassandraTemplate() throws Exception {
        CassandraSessionFactoryBean session = session();
        return new CassandraTemplate(session.getObject(), session.getConverter());
    }
}

