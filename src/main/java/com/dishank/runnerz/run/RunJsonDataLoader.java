package com.dishank.runnerz.run;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;

@Component
public class RunJsonDataLoader implements CommandLineRunner {

    private final RunRepository runRepository;

    public RunJsonDataLoader(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = getClass().getResourceAsStream("/data/runs.json");
        Runs runs = mapper.readValue(inputStream, new TypeReference<Runs>() {});

        runs.runs().forEach(runRepository::create);
    }
}
