package com.novianto.p2p.lending.service.global;

public interface LogService {

    void log(String level, String logger, String message, String thread, String exception);

    void log(String level, String logger, String message, String thread);
}
