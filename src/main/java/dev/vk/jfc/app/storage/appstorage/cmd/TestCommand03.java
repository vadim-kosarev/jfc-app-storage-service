package dev.vk.jfc.app.storage.appstorage.cmd;

import dev.vk.jfc.app.storage.appstorage.entities.FloatArrayItem;
import dev.vk.jfc.app.storage.appstorage.entities.ArrayItemId;
import dev.vk.jfc.app.storage.appstorage.entities.ImageDataEntity;
import dev.vk.jfc.app.storage.appstorage.entities.ImageDataItem;
import dev.vk.jfc.app.storage.appstorage.repository.ImageDataItemRepository;
import dev.vk.jfc.app.storage.appstorage.repository.ImageDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

@Component
@AllArgsConstructor
public class TestCommand03 implements CommandLineRunner {

    private final ImageDataItemRepository repository;
    private final ImageDataRepository imageDataRepository;
    private final Random rnd = new Random();

    private void runTest_02() {
        ImageDataEntity imgData = new ImageDataEntity();
        imgData.setId(UUID.randomUUID());
        imgData.setLabel("Label: TEST: ImageData");
        imgData = imageDataRepository.save(imgData);

        ImageDataItem dataItem = new ImageDataItem();
        dataItem.setId(UUID.randomUUID());
        dataItem.setLabel("Label: TEST: ImageDataItem");
        dataItem.setFaceIndex(777);
        dataItem.setImgBox_p1_x(10);
        dataItem.setImgBox_p1_y(10);
        dataItem.setImgBox_p2_x(600);
        dataItem.setImgBox_p2_y(400);
        dataItem.setDetection(0.887766f);
        dataItem.setParentImageData(imgData);

        dataItem = repository.save(dataItem);

        ArrayList<FloatArrayItem> faceVector = new ArrayList<>();

        for (int i = 0; i < 128; i++) {
            addFloatItem(faceVector, dataItem, i);
        }
        dataItem.setFaceVector(faceVector);

        repository.save(dataItem);
    }

    private void addFloatItem(ArrayList<FloatArrayItem> faceVector, ImageDataItem dataItem, int i) {
        faceVector.add(new FloatArrayItem(new ArrayItemId(dataItem.getId(), i), rnd.nextFloat()));
    }

    @Override
    public void run(String... args) throws Exception {
        runTest_02();
    }
}
