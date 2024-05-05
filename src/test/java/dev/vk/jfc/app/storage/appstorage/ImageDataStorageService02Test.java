package dev.vk.jfc.app.storage.appstorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.dto.*;
import dev.vk.jfc.app.storage.appstorage.entities.*;
import dev.vk.jfc.app.storage.appstorage.entities.data.ArrayItemId;
import dev.vk.jfc.app.storage.appstorage.repository.ImageRepository;
import dev.vk.jfc.app.storage.appstorage.services.ImageDataStorageService02;
import dev.vk.jfc.app.storage.appstorage.services.StorageService;
import dev.vk.jfc.jfccommon.Jfc;
import lombok.SneakyThrows;
import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ImageDataStorageService02Test {

    private static final Logger logger = LoggerFactory.getLogger(ImageDataStorageService02Test.class);

    @Autowired
    private ObjectMapper jsonObjectMapper;
    @Autowired
    private ImageDataStorageService02 dataStorageService02;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private StorageService storageService;
    @Autowired
    private ImageDataStorageService02 imageDataStorageService02;
    @Autowired
    private ModelMapper modelMapper;

    @AfterAll
    static void afterAll() {
        logger.info("Finished");
    }

    @Test
    @SneakyThrows
    void test_02_onFrameImage() {
        // setup
        String headersFile = "/msg02-q-indexed-images-processed-frame-headers.json";
        String payloadFile = "/msg02-q-indexed-images-processed-frame-payload.txt";
        HashMap<String, Object> headers = jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
        byte[] payload = Base64.decode(getClassPathInputStream(payloadFile).readAllBytes());

        // run
        dataStorageService02.onFrameImage(headers, payload);
        storageService.putObject(String.valueOf(headers.get(Jfc.K_S3PATH)), payload);

        // Check
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));
        ImageEntity found = imageRepository.findById(uuid).orElseThrow();
        assertEquals(uuid, found.getId());
        logger.info("Found image {}", jsonObjectMapper.writeValueAsString(found));

        byte[] getImage = storageService.getObject(found.getS3Path());
        assertEquals(payload.length, getImage.length);
        logger.info("Checked images size: OK");
    }

    @Test
    @SneakyThrows
    void test_05_onFrameFaces() {
        // setup
        String headersFile = "/msg05-q-indexed-images-processed-frame-faces-headers.json";
        String payloadFile = "/msg05-q-indexed-images-processed-frame-faces-payload.txt";
        test_onFrameFace_Arg(headersFile, payloadFile);
    }

    @Test
    @SneakyThrows
    void test_onFrameFace_03() {
        // setup
        String headersFile = "/msg03-q-indexed-images-processed-frame-face-headers.json";
        String payloadFile = "/msg03-q-indexed-images-processed-frame-face-payload.txt";
        test_onFrameFace_Arg(headersFile, payloadFile);
    }

    @Test
    @SneakyThrows
    void test_onFrameFace_04() {
        // setup
        String headersFile = "/msg04-q-indexed-images-processed-frame-face-headers.json";
        String payloadFile = "/msg04-q-indexed-images-processed-frame-face-payload.txt";
        test_onFrameFace_Arg(headersFile, payloadFile);
    }


    private void test_onFrameFace_Arg(String headersFile, String payloadFile) throws IOException {
        HashMap<String, Object> headers = jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
        byte[] payload = Base64.decode(getClassPathInputStream(payloadFile).readAllBytes());

        // run
        dataStorageService02.onFrameFacesImage(headers, payload);
        storageService.putObject(String.valueOf(headers.get(Jfc.K_S3PATH)), payload);
    }

    @Test
    @SneakyThrows
    void test_faces05_then_parent02() {
        test_05_onFrameFaces();
        test_02_onFrameImage();
    }

    @Test
    @SneakyThrows
    void test_5_4_3_2() {
        test_05_onFrameFaces();
        test_onFrameFace_04();
        test_onFrameFace_03();
        test_02_onFrameImage();
    }

    @Test
    @SneakyThrows
    void test_2_3_4_5() {
        test_02_onFrameImage();
        test_onFrameFace_03();
        test_onFrameFace_04();
        test_05_onFrameFaces();
    }

    @Test
    @SneakyThrows
    void test_55_4_3_2() {
        test_05_onFrameFaces();
        test_05_onFrameFaces();
        test_onFrameFace_04();
        test_onFrameFace_03();
        test_02_onFrameImage();
    }

    @Test
    @SneakyThrows
    void test_555_222() {
        test_05_onFrameFaces();
        test_05_onFrameFaces();
        test_05_onFrameFaces();
        test_02_onFrameImage();
        test_02_onFrameImage();
        test_02_onFrameImage();
    }

    private InputStream getClassPathInputStream(String path) {
        return getClass().getResourceAsStream(path);
    }

    @Test
    @SneakyThrows
    void test_onIndexedDataMessage() {
        // setup
        String headersFile = "/msg01-q-indexed-data-processed-frame-data-headers.json";
        String payloadFile = "/msg01-q-indexed-data-processed-frame-data-payload.json";
        HashMap<String, Object> headers = jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
        byte[] payload = getClassPathInputStream(payloadFile).readAllBytes();

        // run
        imageDataStorageService02.onIndexedDataMessage(headers, payload);

        // check
        ImageEntity rootImage = imageRepository.findById(UUID.fromString(String.valueOf(headers.get(Jfc.K_PARENT_UUID)))).get();
        logger.info("Root Image: {}", jsonObjectMapper.writeValueAsString(rootImage));

        var indexedDataEntity = rootImage.getIndexedDataEntity();
        logger.info("Indexed data: {}", jsonObjectMapper.writeValueAsString(indexedDataEntity));

        var elements = indexedDataEntity.getElements();
        logger.info("Elements: {}", elements.size());
        assertEquals(2, elements.size());

        for (var element : elements) {
            IndexedDataItemEntity indexedDataItemEntity = (IndexedDataItemEntity) element;
            logger.info("Indexed data item: {}", indexedDataItemEntity);
            assertEquals(128, indexedDataItemEntity.getFaceVector().size());
        }

    }

    @Test
    @SneakyThrows
    void test_getImageDTO() {
        test_2_3_4_5();
        test_onIndexedDataMessage();

        String headersFile = "/msg02-q-indexed-images-processed-frame-headers.json";
        HashMap<String, Object> headers = jsonObjectMapper.readValue(getClassPathInputStream(headersFile), HashMap.class);
        UUID uuid = UUID.fromString(String.valueOf(headers.get(Jfc.K_UUID)));

        ImageEntity entity = imageRepository.findById(uuid).orElseThrow();
        IndexedDataEntity dataEntity = entity.getIndexedDataEntity();

        for (BaseEntity be : dataEntity.getElements()) {
            IndexedDataItemEntity die = (IndexedDataItemEntity) be;

            // ###
            die.getFaceVector().sort((o1, o2) -> o1.getItemId().getInd() - o2.getItemId().getInd());

            List<FloatArrayItemEntity> faceVecList = die.getFaceVector();

            List<Float> res = die.getFaceVector().stream().sorted((o1, o2) -> o1.getItemId().getInd() - o2.getItemId().getInd())
                    .map(i -> i.getVal()).collect(Collectors.toUnmodifiableList());

            for (FloatArrayItemEntity ie : faceVecList) {
                FloatArrayItemDto fia = modelMapper.map(ie, FloatArrayItemDto.class);
                logger.info("fia: {}", fia);
            }

            IndexedDataItemDto dit = modelMapper.map(die, IndexedDataItemDto.class);

            // ###
            dit.setFaceBox(new FaceBox(
                    new ImagePoint(die.getImgBox_p1_x(), die.getImgBox_p1_y()),
                    new ImagePoint(die.getImgBox_p2_x(), die.getImgBox_p2_y())
            ));
            logger.info("dit: {}", dit);
        }

        ImageDto dto = modelMapper.map(entity, ImageDto.class);
        logger.info("Dto: {}", jsonObjectMapper.writeValueAsString(dto));
    }

    @Test
    @SneakyThrows
    void test_modelMapper() {
        int cnt = 5;
        UUID uuid = UUID.fromString(String.valueOf(UUID.randomUUID()));
        IndexedDataItemEntity di =
                IndexedDataItemEntity.builder()
                        .id(uuid)
                        .faceIndex(888)
                        .detection(0.333333666666f)
                        .imgBox_p1_x(111)
                        .imgBox_p1_y(222)
                        .imgBox_p2_x(333)
                        .imgBox_p2_y(444)
                        .faceVector(List.of(
                                new FloatArrayItemEntity(new ArrayItemId(uuid, --cnt), 0.1111f * cnt),
                                new FloatArrayItemEntity(new ArrayItemId(uuid, --cnt), 0.1111f * cnt),
                                new FloatArrayItemEntity(new ArrayItemId(uuid, --cnt), 0.1111f * cnt),
                                new FloatArrayItemEntity(new ArrayItemId(uuid, --cnt), 0.1111f * cnt),
                                new FloatArrayItemEntity(new ArrayItemId(uuid, --cnt), 0.1111f * cnt)
                        ))
                        .build();
        IndexedDataItemDto diDto = modelMapper.map(di, IndexedDataItemDto.class);
        FaceBox fb = modelMapper.map(di, FaceBox.class);
        logger.info("diDto: {}, fb: ", diDto, fb);

        List<Float> fList = di.getFaceVector().stream().sorted(
                (o1, o2) -> o1.getItemId().getInd() - o2.getItemId().getInd()
        ).map(e -> e.getVal()).collect(Collectors.toUnmodifiableList());
        logger.info("fList: {}", fList);
    }

}
