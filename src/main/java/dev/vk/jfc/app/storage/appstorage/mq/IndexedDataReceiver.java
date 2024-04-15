package dev.vk.jfc.app.storage.appstorage.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.events.Event;

@Component
//@RabbitListener(queues = "q-indexed-data")
public class IndexedDataReceiver {

    private final static Logger logger = LoggerFactory.getLogger(IndexedDataReceiver.class);

    @RabbitListener(queues = "q-indexed-data")
    public void bar(Message message) {
        logger.info("uuid: {}", message.getMessageProperties().getHeaders().get("uuid"));
    }

}
