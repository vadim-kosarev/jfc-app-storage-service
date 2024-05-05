package dev.vk.jfc.app.storage.appstorage.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vk.jfc.app.storage.appstorage.dto.FaceBox;
import dev.vk.jfc.app.storage.appstorage.dto.FloatArrayItemDto;
import dev.vk.jfc.app.storage.appstorage.dto.IndexedDataItemDto;
import dev.vk.jfc.app.storage.appstorage.entities.FloatArrayItemEntity;
import dev.vk.jfc.app.storage.appstorage.entities.IndexedDataItemEntity;
import io.minio.MinioClient;
import org.modelmapper.*;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class AppConfig {

    private final static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("${app.minio.endpoint}")
    private String minioEndpoint;

    @Value("${app.minio.username}")
    private String minioUsername;

    @Value("${app.minio.password}")
    private String minioPassword;

    public static <K> K nNull(K val, K dflt) {
        return val == null ? dflt : val;
    }

    public static FaceBox calcFaceBox(IndexedDataItemEntity e) {
        return new FaceBox();
    }

    @Bean
    public MinioClient getMinioClient() {
        return MinioClient
                .builder()
                .endpoint(minioEndpoint)
                .credentials(minioUsername, minioPassword)
                .build();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // -------------------------------------------------------------------------------------------------------------
        PropertyMap<IndexedDataItemDto, IndexedDataItemEntity> imageBoxMappingMap =
                new PropertyMap<IndexedDataItemDto, IndexedDataItemEntity>() {
                    @Override
                    protected void configure() {
                        map().setImgBox_p1_x(source.getFaceBox().getP1().getX());
                        map().setImgBox_p1_y(source.getFaceBox().getP1().getY());
                        map().setImgBox_p2_x(source.getFaceBox().getP2().getX());
                        map().setImgBox_p2_y(source.getFaceBox().getP2().getY());
                    }
                };
        modelMapper.addMappings(imageBoxMappingMap);

        // -------------------------------------------------------------------------------------------------------------
        TypeMap<IndexedDataItemEntity, IndexedDataItemDto> tMapDataItem =
                modelMapper.createTypeMap(IndexedDataItemEntity.class, IndexedDataItemDto.class);

        // FaceBox mapping
        PropertyMap<IndexedDataItemEntity, IndexedDataItemDto> tMapFaceBox = new PropertyMap<IndexedDataItemEntity, IndexedDataItemDto>() {
            @Override
            protected void configure() {
                map().getFaceBox().getP1().setX(source.getImgBox_p1_x());
                map().getFaceBox().getP1().setY(source.getImgBox_p1_y());
                map().getFaceBox().getP2().setX(source.getImgBox_p2_x());
                map().getFaceBox().getP2().setY(source.getImgBox_p2_y());
            }
        };
        tMapDataItem.addMappings(tMapFaceBox);

        // faceVector mapping
        tMapDataItem.addMappings(
                mapper -> mapper.using(new FFListConverter())
                        .map(IndexedDataItemEntity::getFaceVector, IndexedDataItemDto::setFaceVector)
        );

        // -------------------------------------------------------------------------------------------------------------
        Converter<FloatArrayItemEntity, Float> convFf = new Converter<FloatArrayItemEntity, Float>() {
            @Override
            public Float convert(MappingContext<FloatArrayItemEntity, Float> mappingContext) {
                return mappingContext.getSource().getVal();
            }
        };
        modelMapper.addConverter(convFf);

        // -------------------------------------------------------------------------------------------------------------
        PropertyMap<FloatArrayItemEntity, FloatArrayItemDto> floatItemMap =
                new PropertyMap<FloatArrayItemEntity, FloatArrayItemDto>() {
                    @Override
                    protected void configure() {
                        map().setInd(source.getItemId().getInd());
                        map().setVal(source.getVal());
                    }
                };
        modelMapper.addMappings(floatItemMap);

        // -------------------------------------------------------------------------------------------------------------
        return modelMapper;
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.basePackage("dev.vk.jfc.app.storage.appstorage"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    public static class Prov extends AbstractProvider<FaceBox> {
        @Override
        protected FaceBox get() {
            return new FaceBox();
        }
    }

    public class FFListConverter extends AbstractConverter<List<FloatArrayItemEntity>, List<Float>> {
        @Override
        protected List<Float> convert(List<FloatArrayItemEntity> src) {
            return src.stream()
                    .sorted((o2, o1) -> o2.getItemId().getInd() - o1.getItemId().getInd())
                    .map(FloatArrayItemEntity::getVal).collect(Collectors.toList());
        }
    }

}
