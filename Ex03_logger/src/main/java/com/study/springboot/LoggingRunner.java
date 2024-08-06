package com.study.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class LoggingRunner implements ApplicationRunner {
	
	/* private static final  알아서 컴파일 해줌*/Logger logger = LoggerFactory.getLogger(LoggingRunner.class);
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.trace("Trace 레벨 로그");
		logger.debug("debug 레벨 로그");
		logger.info("info 레벨 로그");
		logger.warn("warn 레벨 로그");
		logger.error("error 레벨 로그");
	}

}
