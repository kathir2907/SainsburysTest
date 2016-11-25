package com.sainsburys.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sainsburys.test.model.Products;
import com.sainsburys.test.service.ParserHandler;

/**
 * This class gets the Product model object using handler class and convert it to JSON array 
 *
 */

@SpringBootApplication
public class SainsburysTestApp {
	
	private static final Logger logger = LoggerFactory.getLogger(SainsburysTestApp.class);
	
	public static void main(String args[]){
		ApplicationContext context = SpringApplication.run(SainsburysTestApp.class, args);
		ParserHandler parserHandler = context.getBean(ParserHandler.class);
		try{
			Products products = parserHandler.processPage();
			
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(products);
			
			logger.info(json);
			
		}catch(Exception e){
			logger.error("Error occured : " + e.getMessage());
		}

	}

}
