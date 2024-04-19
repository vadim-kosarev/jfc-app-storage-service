package dev.vk.jfc.app.storage.appstorage.cmd;

import dev.vk.jfc.app.storage.appstorage.dto.Contained;
import dev.vk.jfc.app.storage.appstorage.repository.ContainedRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
public class Cmd implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Cmd.class);

    private final ContainedRepository repo2;

    @Override
    public void run(String... args) throws Exception {
        Contained cc = Contained.builder()
                .label("RECORD LABELED: ... %s".formatted(System.currentTimeMillis()))
                .build();
        cc = repo2.save(cc);
        logger.info("CC: {}", cc);

        Contained c2 = Contained.builder()
                .label("Another record %s".formatted(new Date()))
                .parent(cc)
                .build();
        c2 = repo2.save(c2);
        logger.info("C2: {}", c2);


    }
}
