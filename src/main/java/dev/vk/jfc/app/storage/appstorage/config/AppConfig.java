package dev.vk.jfc.app.storage.appstorage.config;

import dev.vk.jfc.app.storage.appstorage.mq.IndexedDataReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sound.midi.Receiver;

@Configuration
public class AppConfig {

    private final static Logger logger = LoggerFactory.getLogger(AppConfig.class);

//    @Bean
//    @Profile("sender")
//    public IndexedDataReceiver getAdapter() {
//        return new IndexedDataReceiver();
//    }

}
