package com.dishank.runnerz.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RunRepository {

    private static final Logger log = LoggerFactory.getLogger(RunRepository.class);
    private final JdbcClient jdbcClient;

    public RunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll(){
        return jdbcClient.sql("Select * From run")
                .query(Run.class)
                .stream().toList();
    }

    public Optional<Run> findById(Integer id){
        return jdbcClient.sql("Select * From run where id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run){
        jdbcClient.sql("INSERT INTO Run(id, title, started_on, completed_on, miles, location) VALUES(?,?,?,?,?,?)")
                .params(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString())
                .update();
    }

}
