package com.sola.reviewyme.resume_ai.cloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CloudGatewayApplication {
    public static void main(String [] args ){SpringApplication.run(CloudGatewayApplication.class,args);}
}
