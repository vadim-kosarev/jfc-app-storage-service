package dev.vk.jfc.app.storage.appstorage.cmd;

import dev.vk.jfc.app.storage.appstorage.entities.*;
import dev.vk.jfc.app.storage.appstorage.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@AllArgsConstructor
public class TestCommand implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TestCommand.class);
    private static final AtomicInteger cnt = new AtomicInteger(0);
    private final ContainedRepository containedRepository;
    private final ChildRepository childRepository;
    private final BaseObjectRepository baseObjectRepository;
    private final ProcessedImageRepository processedImageRepository;
    private final BoxedImageRepository boxedImageRepository;

    private static @NotNull BoxedImage getBoxedImage(int faceNoArg, ProcessedImage obj) {
        BoxedImage bxImage = new BoxedImage();
        bxImage.setFaceNo(faceNoArg);
        bxImage.setContainer(obj);
        bxImage.setLabel("Label:`BoxedImage:%d`".formatted(faceNoArg));
        return bxImage;
    }

    protected Pair<UUID, UUID> step01_1(Container ssParent) {
        Child c = new Child();
        c.setLabel("++ CHILD %d".formatted(cnt.incrementAndGet()));
        c.setContainer(ssParent);
        c = childRepository.save(c);
        logger.info("c2: {}", c.getId());
        return Pair.with(ssParent.getId(), c.getId());
    }

    @Transactional
    protected Pair<UUID, UUID> step01() {

        Container ssParent = new Container();
        ssParent.setLabel("THE PARENT label");
        ssParent = containedRepository.save(ssParent);
        logger.info("ssParent: {}", ssParent);

        step01_1(ssParent);
        step01_1(ssParent);
        step01_1(ssParent);
        step01_1(ssParent);
        step01_1(ssParent);
        return step01_1(ssParent);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    protected void step02(UUID ccID) {
        Optional<Container> opt = containedRepository.findById(ccID);
        if (opt.isPresent()) {
            Container cont = opt.get();
            logger.info("CONTAINED: {}", cont.getId());
            Collection<Child> children = cont.getChildren();
            if (children != null) {
                int sz = children.size();
                for (Child child : children) {
                    logger.info(" ---> child: {} / {}", child.getId(), child.getLabel());
                }
            } else {
                logger.info("Children is NULL");
            }
        }
    }

    protected BaseEntity createBaseObject() {
        BaseEntity parent = new BaseEntity();
        parent.setLabel("PARENT - %d".formatted(cnt.incrementAndGet()));
        parent = baseObjectRepository.save(parent);
        logger.info("parent id: {}", parent.getId());
        return parent;
    }

    protected BaseEntity creaetChild(BaseEntity parent) {
        BaseEntity child = new BaseEntity();
        child.setLabel("CHILD - %d".formatted(cnt.incrementAndGet()));
        child.setContainer(parent);
        baseObjectRepository.save(child);
        logger.info("Created child: {}", child.getId());
        return child;
    }

    @Transactional
    protected void step03() {

        List<UUID> args = step03_01();

        for (UUID arg : args) {
            findChildren(arg);
        }

    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    protected List<UUID> step03_01() {
        BaseEntity base1 = createBaseObject();
        BaseEntity base2 = createBaseObject();
        BaseEntity base3 = createBaseObject();

        BaseEntity child1 = creaetChild(base1);
        BaseEntity child2 = creaetChild(base2);
        BaseEntity child3 = creaetChild(base2);
        BaseEntity child4 = creaetChild(base3);
        BaseEntity child5 = creaetChild(base3);
        BaseEntity child6 = creaetChild(base3);

        findParent(base3.getId());
        findParent(child2.getId());
        findParent(child3.getId());
        findParent(child4.getId());

        return List.of(base1.getId(), base2.getId(), base3.getId(), child1.getId());
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    protected void findParent(UUID objID) {
        logger.info("\n================= Looking for parent {}", objID);
        BaseEntity foundObject = baseObjectRepository.findById(objID).orElseThrow();
        if (foundObject.getContainer() != null) {
            logger.info("Found : parent:`{}` / thisObject:`{}` / parentID`{}`", foundObject.getContainer().getLabel(), foundObject.getLabel(), foundObject.getContainer().getId());
        }

    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    protected void findChildren(UUID objID) {
        logger.info("\n========================= Looking for children {}", objID);
        BaseEntity foundObj = baseObjectRepository.findById(objID).orElseThrow();
        logger.info("\n\n ======== -------- {}", foundObj.getLabel());
        Collection<BaseEntity> foundChildren = foundObj.getElements();
        if (foundChildren == null || foundChildren.isEmpty()) {
            logger.info("NO Children found for `{}`", foundObj.getLabel());
        } else {
            for (BaseEntity child : foundChildren) {
                logger.info("Found child: `{}` / `{}`", child.getLabel(), child.getId());
            }
        }
    }

    @Transactional
    protected ProcessedImage step04() {
        ProcessedImage processedImage = createProcessedImage();
        processedImage.setLabel("!LABEL:`ProcessedImage`");

        int faceNoArg = -334455;
        int a = 10;

        List<BaseEntity> boxes = new ArrayList<>();
        boxes.add(getBoxedImage(faceNoArg + (a+=10), processedImage));
        boxes.add(getBoxedImage(faceNoArg + (a+=10), processedImage));
        boxes.add(getBoxedImage(faceNoArg + (a+=10), processedImage));
        boxes.add(getBoxedImage(faceNoArg + (a+=10), processedImage));
        processedImage.setElements(boxes);

        return processedImageRepository.save(processedImage);
    }

    private ProcessedImage createProcessedImage() {
        ProcessedImage order = new ProcessedImage();
        order.setLabel("Processed image");
        order.setS3Path("s3Path://jpgdata/bucket/image.jpg");
        return processedImageRepository.save(order);
    }

    @Override
    public void run(String... args) throws Exception {
        var res = step01();
        step02(res.getValue0());
        step03();
        UUID objID = step04().getId();

        logger.info("/// TestCommand finished");
    }


}
