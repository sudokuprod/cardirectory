package com.sudokuprod.cardirectory.service.impl;

import com.sudokuprod.cardirectory.repo.LogRepo;
import com.sudokuprod.cardirectory.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private final LogRepo logRepo;

    @Override
    public long avgExecutionTime(String path) {
        return logRepo.avgExecutionTime(path);
    }

    @Override
    public long count(String path) {
        return logRepo.countByPath(path);
    }
}
