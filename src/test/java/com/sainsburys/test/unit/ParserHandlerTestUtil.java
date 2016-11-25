package com.sainsburys.test.unit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ParserHandlerTestUtil extends ParserCommonTestUtil {

	
	public static Elements createProducts() {
		Elements productNodes = null;
		try{
			Document doc = Jsoup.connect(testUrl).get();
			productNodes = doc.getElementsByClass("product");
		}catch(Exception e){
			e.getMessage();
		}
		return productNodes;
	}

	public static Elements createLinkedElements() {
		Elements productNodes = null;
		try{
			Document linkedDocument = Jsoup.connect(linkedDocumentUrl).get();
			productNodes = linkedDocument.getAllElements().first().select("div.productText");
		}catch(Exception e){
			e.getMessage();
		}
		return productNodes;
	}
	
}
