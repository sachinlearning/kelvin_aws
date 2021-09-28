package com.heatmap.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PropertiesFileUtility {

	@Autowired
	private Environment env;

	@Autowired
	private ExceptionUtil expUtil;
	private static final Logger logger = LoggerFactory.getLogger(PropertiesFileUtility.class);

	public String getProperty(String pPropertyKey) {
		logger.info("Executing method of class PropertiesFileUtility");
		return env.getProperty(pPropertyKey);
	}

	public String getValueFromPropertiesFile(String propertiesFileName,String key) {
		logger.info("Executing method getValueFromPropertiesFile of class PropertiesFileUtility");
		String value = null;
		String resourceName = propertiesFileName;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try (InputStream input = loader.getResourceAsStream(resourceName)) {
			Properties prop = new Properties();
			prop.load(input);
			value = prop.getProperty(key);
		} catch (IOException ex) {
			logger.error(expUtil.getExceptionMessage(ex));
		}
		return value;
	}
}
