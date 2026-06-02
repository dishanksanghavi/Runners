package com.dishank.runnerz.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JDBCRunRepository {

    private static final Logger log = LoggerFactory.getLogger(JDBCRunRepository.class);
    private final JdbcClient jdbcClient;

    public JDBCRunRepository(JdbcClient jdbcClient) {
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
    public void update(Run run, Integer id) {
        var updated = jdbcClient.sql("update run set title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? where id = ?")
                .params(List.of(run.title(),run.startedOn(),run.completedOn(),run.miles(),run.location().toString(), id))
                .update();
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from run where id = :id")
                .param("id", id)
                .update();
    }

}
