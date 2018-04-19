package com.pac.msm;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.pac.msm.component.repository.search.CustomElasticsearchRepositoryFactoryBean;

@SpringBootApplication
@EnableCassandraRepositories(basePackages = "com.pac.msm.component.repository")
@EnableElasticsearchRepositories(basePackages = "com.pac.msm.component.repository.search", repositoryFactoryBeanClass = CustomElasticsearchRepositoryFactoryBean.class)
public class Application {
	
	private static final Integer DEFAULT_ZOOKEEPER_TIMEOUT = new Integer("20000");
    private static final String ZK_SERVERS = "ZK_SERVERS";
    private static final String ZK_SESSION_TIMEOUT = "ZK_SESSION_TIMEOUT";

	public static void main(String[] args) {
		String zkUri = "dev-usw-r1-def-h1:2181,dev-usw-r2-def-h2:2181,dev-usw-r3-def-h3:2181"; // dev zookeeper as default.
        zkUri = Optional.ofNullable(System.getenv(ZK_SERVERS)).orElse(zkUri);
        Integer zkSessionTimeout = Integer.getInteger(System.getenv(ZK_SESSION_TIMEOUT),
                DEFAULT_ZOOKEEPER_TIMEOUT);
        String serverType = Optional.ofNullable(System.getenv("server.type")).orElse("dev");
        System.setProperty("zk.servers", zkUri);
        System.setProperty("zk.session.timeout", zkSessionTimeout.intValue()+"");
        System.setProperty("server.type", serverType);
        
        SpringApplication.run(Application.class, args);
	}
	
	/*private void initZk() {
		String zkUri = System.getenv(ZK_SERVERS);
		if (!TextUtils.isNotNullNotEmpty(zkUri)) {
			zkUri = System.getProperty(ZK_SERVERS, "dev-usw-r1-def-h1:2181/dev");
		}
		String zkSessionTimeoutStr = System.getenv(ZK_SESSION_TIMEOUT);
		if (!TextUtils.isNotNullNotEmpty(zkSessionTimeoutStr)) {
			zkSessionTimeoutStr = System.getProperty(ZK_SESSION_TIMEOUT);
		}

		int zkSessionTimeout = TextUtils.toInteger(zkSessionTimeoutStr,
				DEFAULT_ZOOKEEPER_TIMEOUT);
		try {
			CoordinateService cs = new CoordinateServiceImpl(zkUri,
					zkSessionTimeout);
			Registry.register(CoordinateService.class.getName(),
					Registry.DEFAULT, cs);
			cs.init();
			Logger.info(SysRequestContext.get(), "zookeeper inited.");
		} catch (PacException e) {
			Logger.error(SysRequestContext.get(),
					"failed to connect to zookeeper, zkuri=" + zkUri, e);
			System.exit(-1);
		}
	}*/

}