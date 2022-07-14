package com.sudokuprod.cardirectory.repo;

import com.sudokuprod.cardirectory.model.ApiLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepo extends JpaRepository<ApiLog, Long> {

    @Query("select avg (al.executionTime) from ApiLog al where al.path = :path")
    long avgExecutionTime(String path);

    long countByPath(String path);
}
