package dev.vk.jfc.app.storage.appstorage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class IndexedDataItemDto {
    private final String cls = IndexedDataItemDto.class.getName();
    private String label;
    private Integer faceIndex;
    private Float detection;
    private FaceBox faceBox;
    private List<Float> faceVector;

    public static class FloatList extends ArrayList<Float> {

        public FloatList() {
        }

        public FloatList(int initialCapacity) {
            super(initialCapacity);
        }

        public FloatList(@NotNull Collection<? extends Float> c) {
            super(c);
        }
    }
}
