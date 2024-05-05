package dev.vk.jfc.app.storage.appstorage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@Data
@NoArgsConstructor
public class IndexedDataDto {
    private UUID id;
    private String label;
    private Collection<IndexedDataItemDto> elements;
}
