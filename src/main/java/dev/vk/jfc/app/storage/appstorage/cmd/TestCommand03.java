package dev.vk.jfc.app.storage.appstorage.cmd;

import dev.vk.jfc.app.storage.appstorage.entities.FloatArrayItemEntity;
import dev.vk.jfc.app.storage.appstorage.entities.IndexedDataEntity;
import dev.vk.jfc.app.storage.appstorage.entities.data.ArrayItemId;
import dev.vk.jfc.app.storage.appstorage.entities.IndexedDataItemEntity;
import dev.vk.jfc.app.storage.appstorage.repository.ImageDataItemRepository;
import dev.vk.jfc.app.storage.appstorage.repository.ImageDataRepository;
import dev.vk.jfc.app.storage.appstorage.repository.IndexedDataRepository;
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
    private final IndexedDataRepository indexedDataRepository;
    private final Random rnd = new Random();

    private void runTest_02() {
        IndexedDataEntity imgData = new IndexedDataEntity();
        imgData.setId(UUID.randomUUID());
        imgData.setLabel("Label: TEST: ImageData");
        imgData = indexedDataRepository.save(imgData);

        IndexedDataItemEntity dataItem = new IndexedDataItemEntity();
        dataItem.setId(UUID.randomUUID());
        dataItem.setLabel("Label: TEST: IndexedDataItemEntity");
        dataItem.setFaceIndex(777);
        dataItem.setImgBox_p1_x(10);
        dataItem.setImgBox_p1_y(10);
        dataItem.setImgBox_p2_x(600);
        dataItem.setImgBox_p2_y(400);
        dataItem.setDetection(0.887766f);
        dataItem.setParentImageData(imgData);

        dataItem = repository.save(dataItem);

        ArrayList<FloatArrayItemEntity> faceVector = new ArrayList<>();

        for (int i = 0; i < 128; i++) {
            addFloatItem(faceVector, dataItem, i);
        }
        dataItem.setFaceVector(faceVector);

        repository.save(dataItem);
    }

    private void addFloatItem(ArrayList<FloatArrayItemEntity> faceVector, IndexedDataItemEntity dataItem, int i) {
        faceVector.add(new FloatArrayItemEntity(new ArrayItemId(dataItem.getId(), i), rnd.nextFloat()));
    }

    @Override
    public void run(String... args) throws Exception {
        if (true) return;
        runTest_02();
    }
}
