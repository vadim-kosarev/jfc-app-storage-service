package dev.vk.jfc.app.storage.appstorage.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VersionDTO {

    private String artifact;
    private String version;

}
