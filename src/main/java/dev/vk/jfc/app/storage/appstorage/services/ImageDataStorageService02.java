package dev.vk.jfc.app.storage.appstorage.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Primary
public class ImageDataStorageService02 implements ImageDataStorageService {

    private final static Logger logger = LoggerFactory.getLogger(ImageDataStorageService02.class);

    @Override
    public void onFrameImage(Map<String, Object> headers, byte[] payload) {
        logger.info("onFrameImage");
    }

    @Override
    public void onFrameFacesImage(Map<String, Object> headers, byte[] payload) {
        logger.info("onFrameFacesImage");
    }

    @Override
    public void onIndexedDataMessage(Map<String, Object> headers, byte[] body) {
        logger.info("onIndexedDataMessage");
    }
}
