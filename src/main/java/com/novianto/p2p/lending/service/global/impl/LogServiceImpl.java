package com.novianto.p2p.lending.service.global.impl;

import com.novianto.p2p.lending.model.LogEntry;
import com.novianto.p2p.lending.repository.LogEntryRepository;
import com.novianto.p2p.lending.service.global.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogServiceImpl implements LogService {

    private final LogEntryRepository logEntryRepository;

    @Autowired
    public LogServiceImpl(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    @Override
    @Async
    public void log(String level, String logger, String message, String thread, String exception) {
        LogEntry logEntry = LogEntry.builder()
                .timestamp(LocalDateTime.now())
                .level(level)
                .logger(logger)
                .message(message)
                .thread(thread)
                .exception(exception)
                .build();
        logEntryRepository.save(logEntry);
    }

    @Override
    public void log(String level, String logger, String message, String thread) {
        log(level, logger, message, thread, null);
    }
}
