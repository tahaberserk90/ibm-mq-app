package com.ibmmq.backend.service;

import com.ibm.jms.JMSMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import static org.mockito.Mockito.*;

class MQConsumerServiceTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MQConsumerService mqConsumerService;

    @Mock
    private TextMessage textMessage;

    @Mock
    private JMSMessage genericMessage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReceiveMessage_Success() throws JMSException {
        String content = "Test message content";
        String messageId = "12345";
        String correlationId = "67890";

        when(textMessage.getText()).thenReturn(content);
        when(textMessage.getJMSMessageID()).thenReturn(messageId);
        when(textMessage.getJMSCorrelationID()).thenReturn(correlationId);

        mqConsumerService.receiveMessage(textMessage);

        verify(messageService, times(1)).saveMessage(argThat(msg -> msg.getContent().equals(content) &&
                msg.getMessageId().equals(messageId) &&
                msg.getCorrelationId().equals(correlationId)));
    }

    @Test
    void testReceiveMessage_NonTextMessage() {

        mqConsumerService.receiveMessage(genericMessage);

        verifyNoInteractions(messageService);
    }

    @Test
    void testReceiveMessage_JMSException() throws JMSException {

        when(textMessage.getText()).thenThrow(new JMSException("Simulated exception"));

        mqConsumerService.receiveMessage(textMessage);

        verifyNoInteractions(messageService);
    }
}