package dev.vk.jfc.app.storage.appstorage.tmp;

import dev.vk.jfc.app.storage.appstorage.entities.data.ArrayItemId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


@Service
@AllArgsConstructor
public class OneToOneService {

    private final static Logger logger = LoggerFactory.getLogger(OneToOneService.class.getName());

    private final LeftEntityRepository leftEntityRepository;
    private final RightEntityRepository rightEntityRepository;

    public UUID run01() {
        // Step one -- create left and right objects
        UUID leftUuid = UUID.randomUUID();
        OneToOneLeftEntity left = leftEntityRepository.findById(leftUuid).orElseGet(OneToOneLeftEntity::new);
        left.setId(leftUuid);
        left.setLeftData("Left Data");
        leftEntityRepository.save(left);

        UUID rightUuid = UUID.randomUUID();
        OneToOneRightEntity right = rightEntityRepository.findById(rightUuid).orElseGet(OneToOneRightEntity::new);
        right.setId(rightUuid);
        right.setRightData("Right Data");
        //rightEntityRepository.save(right);

        // step two - tie entities by one-to-one relation
        // Option one - not working
//        left.setRight(right);
//        leftEntityRepository.save(left);

        // option two - who has JoinColumn, that should be saved
        right.setLeftEntity(left);
//        rightEntityRepository.save(right);

        // step three - fill in array data
        ArrayList<RightEntityItem> items = new ArrayList<>();
        int cnt = -1;
        items.add(new RightEntityItem(
                new ArrayItemId(rightUuid, ++cnt),
                "Data with index %d".formatted(cnt))
        );
        items.add(new RightEntityItem(
                new ArrayItemId(rightUuid, ++cnt),
                "Data with index %d".formatted(cnt))
        );
        items.add(new RightEntityItem(
                new ArrayItemId(rightUuid, ++cnt),
                "Data with index %d".formatted(cnt))
        );

        right.setRightItems(items);
        rightEntityRepository.save(right);

        return leftUuid;
    }

    public void run02(UUID leftUuid) {
        OneToOneLeftEntity left = leftEntityRepository.findById(leftUuid).orElseThrow();
        logger.info("Left: {} --> {}", left.getId(), left.getLeftData());

        OneToOneRightEntity right = left.getRight();
        logger.info("  --> Right: {} / {}", right.getId(), right.getRightData());

        Collection<RightEntityItem> items = right.getRightItems();
        logger.info("    ----> Right items: {}", items);
    }
}
