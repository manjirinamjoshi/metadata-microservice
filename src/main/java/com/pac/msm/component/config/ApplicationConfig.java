package com.pac.msm.component.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
	
@Configuration
public class ApplicationConfig {
	@Bean
	public static AutoJsonRpcServiceImplExporter autoJsonRpcServiceImplExporter() {
	        AutoJsonRpcServiceImplExporter exp = new AutoJsonRpcServiceImplExporter();
	        //in here you can provide custom HTTP status code providers etc. eg:
	        //exp.setHttpStatusCodeProvider();
	        //exp.setErrorResolver();
	        return exp;
	}
}

