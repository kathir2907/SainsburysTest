package com.sainsburys.test.unit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ParserHelperTestUtil {

	private static final String testUrl = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";
	
	public static Element createProductElement() {
		Element productElement = null;
		try{
			Document doc = Jsoup.connect(testUrl).get();
			productElement = doc.getElementsByClass("productInfo").first();
		}catch(Exception e){
			e.getMessage();
		}
		return productElement;
	}

	public static Element createPriceElement() {
		Element productElement = null;
		try{
			Document doc = Jsoup.connect(testUrl).get();
			productElement = doc.getElementsByClass("pricePerUnit").first();
		}catch(Exception e){
			e.getMessage();
		}
		return productElement;
	}
	
}
