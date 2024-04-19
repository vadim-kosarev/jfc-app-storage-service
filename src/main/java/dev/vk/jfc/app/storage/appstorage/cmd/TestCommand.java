package dev.vk.jfc.app.storage.appstorage.cmd;

import dev.vk.jfc.app.storage.appstorage.dto.BaseObject;
import dev.vk.jfc.app.storage.appstorage.dto.Child;
import dev.vk.jfc.app.storage.appstorage.dto.Container;
import dev.vk.jfc.app.storage.appstorage.repository.BaseObjectRepository;
import dev.vk.jfc.app.storage.appstorage.repository.ChildRepository;
import dev.vk.jfc.app.storage.appstorage.repository.ContainedRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@AllArgsConstructor
public class TestCommand implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TestCommand.class);
    private static final AtomicInteger cnt = new AtomicInteger(0);
    private final ContainedRepository containedRepository;
    private final ChildRepository childRepository;
    private final BaseObjectRepository baseObjectRepository;

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
//            Container parent = cont.getParent();
//            if (parent != null) {
//                logger.info(" -> parent: {} / {}", parent.getId(), parent.getLabel());
//            } else {
//                logger.info(" -> NO PARENT");
//            }

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

    protected BaseObject createBaseObject() {
        BaseObject parent = new BaseObject();
        parent.setLabel("PARENT - %d".formatted(cnt.incrementAndGet()));
        parent = baseObjectRepository.save(parent);
        logger.info("parent id: {}", parent.getId());
        return parent;
    }

    protected BaseObject creaetChild(BaseObject parent) {
        BaseObject child = new BaseObject();
        child.setLabel("CHILD - %d".formatted(cnt.incrementAndGet()));
        child.setBaseParent(parent);
        baseObjectRepository.save(child);
        logger.info("Created child: {}", child.getId());
        return child;
    }

    protected void step03() {

        BaseObject base1 = createBaseObject();
        BaseObject base2 = createBaseObject();
        BaseObject base3 = createBaseObject();

        BaseObject child1 = creaetChild(base3);
        BaseObject child2 = creaetChild(base3);
        BaseObject child3 = creaetChild(base3);
        BaseObject child4 = creaetChild(base3);

        findParent(base3.getId());
        findParent(child2.getId());
        findParent(child3.getId());
        findParent(child4.getId());

        findChildren(base1.getId());
        findChildren(base2.getId());
        findChildren(child1.getId());

    }

    private void findParent(UUID objID) {
        logger.info("Looking for parent {}", objID);
        Optional<BaseObject> obj = baseObjectRepository.findById(objID);
        if (!obj.isPresent()) {
            logger.info("CAN'T FIND `{}`", objID);
        } else {
            BaseObject baseObject = obj.get();
            logger.info("Found : `{}` / `{}` / `{}`",
                    (baseObject.getBaseParent() == null) ? "NULL" : baseObject.getBaseParent().getLabel(),
                    baseObject.getLabel(),
                    baseObject.getId());
        }

    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    private void findChildren(UUID objID) {
        logger.info("Looking for children {}", objID);
        Optional<BaseObject> foundObject = baseObjectRepository.findById(objID);
        if (!foundObject.isPresent()) {
            logger.info("CAN'T FIND `{}`", objID);
        } else {
            BaseObject baseObject = foundObject.get();
            Collection<BaseObject> foundChildren = baseObject.getBaseChildren();
            if (foundChildren == null || foundChildren.size() == 0) {
                logger.info("NO Children found for `{}`", baseObject.getLabel());
            } else {
                for (BaseObject child : foundChildren) {
                    logger.info("Found child: `{}` / `{}`",
                            child.getLabel(),
                            child.getId());
                }
            }

        }
    }

    @Override
    public void run(String... args) throws Exception {
        var res = step01();
        step02(res.getValue0());
//        step02(res.getValue1());
        step03();
    }
}
