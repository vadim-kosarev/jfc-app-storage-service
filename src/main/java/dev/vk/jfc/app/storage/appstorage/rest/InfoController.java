package dev.vk.jfc.app.storage.appstorage.rest;

import dev.vk.jfc.app.storage.appstorage.rest.dto.VersionDTO;
import lombok.AllArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class InfoController {

    private final BuildProperties buildProperties;

    @GetMapping("/version")
    public VersionDTO getVersion() {
        return VersionDTO.builder()
                .artifact(buildProperties.getArtifact())
                .version(buildProperties.getVersion())
                .build();
    }

}
