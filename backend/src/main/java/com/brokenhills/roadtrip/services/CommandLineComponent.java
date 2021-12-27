package com.brokenhills.roadtrip.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class CommandLineComponent implements ApplicationRunner {

    private static final String INIT_ARGS_ERROR = "Init command requires --admin=, --password= and --department= option values";

    private final DBInitService dbInitService;

    public CommandLineComponent(DBInitService dbInitService) {
        this.dbInitService = dbInitService;
    }

    @Override
    public void run(ApplicationArguments args) throws RuntimeException {
        log.info("Check for command line args.");

        if (args.getNonOptionArgs().contains("init")) {
            log.info("Init command provided. Trying to init DB...");
            String admin = null;
            String dep = null;
            String password = null;
            if (args.getOptionNames().containsAll(Arrays.asList("admin", "password", "department"))) {
                admin = args.getOptionValues("admin")
                        .stream().findAny().orElseThrow(() -> new RuntimeException(INIT_ARGS_ERROR));
                dep = args.getOptionValues("department")
                        .stream().findAny().orElseThrow(() -> new RuntimeException(INIT_ARGS_ERROR));
                password = args.getOptionValues("password")
                        .stream().findAny().orElseThrow(() -> new RuntimeException(INIT_ARGS_ERROR));
            }

            if (admin == null || dep == null || password == null) {
                throw new RuntimeException(INIT_ARGS_ERROR);
            }

            dbInitService.createAdminWithDepartment(admin, password, dep);
            log.info("DB initialization completed.");
        } else {
            log.info("No arguments provided.");
        }
    }
}
