
package com.ibmmq.backend.service;

import com.ibmmq.backend.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Service
public class MQConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(MQConsumerService.class);
    
    private final MessageService messageService;

    public MQConsumerService(MessageService messageService) {
        this.messageService = messageService;
    }

    @JmsListener(destination = "${ibm.mq.queue}")
    public void receiveMessage(javax.jms.Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String content = textMessage.getText();
                
                Message msg = new Message();
                msg.setContent(content);
                msg.setMessageId(message.getJMSMessageID());
                msg.setCorrelationId(message.getJMSCorrelationID());
                
                messageService.saveMessage(msg);
                logger.info("message recu et sauvgarder: {}", msg.getId());
            }
        } catch (JMSException e) {
            logger.error("erreur appel", e);
        }
    }
}