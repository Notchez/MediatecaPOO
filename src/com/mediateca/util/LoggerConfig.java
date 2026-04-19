package com.mediateca.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerConfig {

    public static Logger getLogger(Class<?> clase) {
        return LogManager.getLogger(clase);
    }
}