// MQConfig.java
package com.ibmmq.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.ConnectionFactory;

@Configuration
public class MQConfig {

    @Value("${ibm.mq.host}")
    private String host;

    @Value("${ibm.mq.port}")
    private int port;

    @Value("${ibm.mq.queueManager}")
    private String queueManager;

    @Value("${ibm.mq.channel}")
    private String channel;

    @Value("${ibm.mq.username}")
    private String username;

    @Value("${ibm.mq.password}")
    private String password;

    @Value("${ibm.mq.queue}")
    private String queue;

    @Bean
    public ConnectionFactory connectionFactory() {
        MQQueueConnectionFactory connectionFactory = new MQQueueConnectionFactory();
        try {
            connectionFactory.setHostName(host);
            connectionFactory.setPort(port);
            connectionFactory.setQueueManager(queueManager);
            connectionFactory.setChannel(channel);
            connectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            connectionFactory.setStringProperty(WMQConstants.USERID, username);
            connectionFactory.setStringProperty(WMQConstants.PASSWORD, password);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create MQ connection factory", e);
        }

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
        cachingConnectionFactory.setSessionCacheSize(10);
        return cachingConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestinationName(queue);
        jmsTemplate.setReceiveTimeout(5000);
        return jmsTemplate;
    }
}