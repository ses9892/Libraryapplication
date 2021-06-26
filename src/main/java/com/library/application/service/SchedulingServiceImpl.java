package com.library.application.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
public class SchedulingServiceImpl implements SchedulingService {

    public int second = 0;

    @Override
    public void test() {
    }
}
