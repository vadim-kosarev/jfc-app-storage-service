package dev.vk.jfc.app.storage.appstorage.cmd;

import dev.vk.jfc.app.storage.appstorage.dto.Contained;
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

@Component
@AllArgsConstructor
public class TestCommand implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TestCommand.class);
    private final ContainedRepository containedRepository;

    @Transactional
    protected Pair<UUID, UUID> step01() {
        Contained ssParent = Contained.builder()
                .label("THE_PARENT: ")
                .build();
        ssParent = containedRepository.save(ssParent);
        logger.info("CC: {}", ssParent);

        Contained c2 = Contained.builder()
                .label("__CHILD__")
                .parent(ssParent)
                .build();
        c2 = containedRepository.save(c2);
        logger.info("C2: {}", c2.getId());
        return Pair.with(ssParent.getId(), c2.getId());
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    protected void step02(UUID ccID) {
        Optional<Contained> opt = containedRepository.findById(ccID);
        if (opt.isPresent()) {
            Contained cont = opt.get();
            logger.info("CONTAINED: {}", cont.getId());
            Contained parent = cont.getParent();
            if (parent != null) {
                logger.info(" -> parent: {} / {}", parent.getId(), parent.getLabel());
            } else {
                logger.info(" -> NO PARENT");
            }

            Collection<Contained> children = cont.getChildren();
            if (children != null) {
                for (Contained child : children) {
                    logger.info(" ---> child: {} / {}", child.getId(), child.getLabel());
                }
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        var res = step01();
        step02(res.getValue0());
        step02(res.getValue1());
    }
}
