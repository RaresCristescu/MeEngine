//package com.app.server.config;
//
//import org.apache.catalina.connector.Connector;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration//TODO maybe remove not safe
//public class HttpHttpsConfig {
//	@Bean
//    public TomcatServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory factory =
//                new TomcatServletWebServerFactory();
//        factory.addAdditionalTomcatConnectors(httpConnector());
//        return factory;
//    }
//
//    private Connector httpConnector() {
//        Connector connector =
//                new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setScheme("http");
//        connector.setPort(8080);
//        connector.setSecure(false);
//        connector.setRedirectPort(8443);
//        return connector;
//    }
//}
