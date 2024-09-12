package com.testautomation.apitesting.utils;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
 
public class LoggingLogger {
	private static final Logger logger = LogManager.getLogger(LoggingLogger.class);
 
    public static Logger getLogger() {
        return logger;
    }
 
}
