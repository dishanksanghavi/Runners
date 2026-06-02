package com.dishank.runnerz.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

@Component
public class RunJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RunJsonDataLoader.class);
    private final ObjectMapper objectMapper;
    private final RunRepository runRepository;

    public RunJsonDataLoader(ObjectMapper objectMapper, RunRepository runRepository) {
        this.objectMapper = objectMapper;
        this.runRepository = runRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(runRepository.count() == 0) {
            try (InputStream inputStream = getClass().getResourceAsStream("/data/runs.json")){
                Runs runs = objectMapper.readValue(inputStream, new TypeReference<Runs>() {});
                runRepository.saveAll(runs.runs());
            }
            catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
            } else {
                log.info("Not loading Runs from JSON data because the collection contains data.");
            }
        }
}
