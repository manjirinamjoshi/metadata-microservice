package com.pac.msm.config.repository;

import java.net.InetSocketAddress;

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
import com.datastax.driver.core.policies.AddressTranslator;

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
        final String template = "*.21.*.*:*.17.*.*,*.22.*.*:*.18.*.*";
        cluster.setAddressTranslator(new AddressTranslator() {
			
			@Override
			public InetSocketAddress translate(InetSocketAddress address) {
				String newIp = translateIP(address.getAddress()
						.getHostAddress(), template);
				return new InetSocketAddress(newIp, address.getPort());
			}
			
			@Override
			public void init(Cluster cluster) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void close() {
				// TODO Auto-generated method stub
				
			}
		});
        return cluster;
    }

    /*@Bean
    public CassandraMappingContext mappingContext(Cluster cluster) {
        BasicCassandraMappingContext mappingContext = new BasicCassandraMappingContext();
        mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster,
                environment.getProperty("cassandra.keyspace")));
        return mappingContext;
    }*/
    
    //@Bean
    public CassandraMappingContext mappingContext() {
        BasicCassandraMappingContext basicCassandraMappingContext = new BasicCassandraMappingContext();
        basicCassandraMappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster().getObject(), "catalog"));
        return basicCassandraMappingContext;
    }

    /*@Bean
    public CassandraConverter converter(Cluster cluster) {
        return new MappingCassandraConverter(mappingContext(cluster));
    }*/
    
    @Bean
    public CassandraConverter converter() throws ClassNotFoundException {
        return new MappingCassandraConverter(mappingContext());
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
        CassandraTemplate cassandraTemplate = new CassandraTemplate(session.getObject(), session.getConverter());
        return cassandraTemplate;
    }
    
    public static String translateIP(String orgIp, String template) {
		String translatedIP = orgIp;
		if (template!=null && !template.isEmpty()
				&& orgIp!=null && !orgIp.isEmpty()) {
			String[] templates = template.split(",");
			for (String t : templates) {
				String[] tmp = t.split(":");
				if (tmp.length == 2) {
					String templateFrom = tmp[0];
					String templateTo = tmp[1];
					String[] orgIpToken = orgIp.split("\\.");
					String[] templateFromToken = templateFrom.split("\\.");
					String[] templateToToken = templateTo.split("\\.");
					StringBuilder newIP = new StringBuilder("");
					boolean isMatch = true;
					for (int i = 0; i < orgIpToken.length; i++) {
						if (templateFromToken[i].equals("*")) {
							newIP.append(orgIpToken[i]).append(".");
						} else if (templateFromToken[i].equals(orgIpToken[i])) {
							newIP.append(templateToToken[i]).append(".");
						} else {
							isMatch = false;
							break;
						}
					}
					if (isMatch) {
						translatedIP = newIP.toString();
						translatedIP = translatedIP.substring(0,
								translatedIP.length() - 1);
						break;
					}

				}
			}
		}
		return translatedIP;
	}
}

