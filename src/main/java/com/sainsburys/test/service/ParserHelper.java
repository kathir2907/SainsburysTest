package com.sainsburys.test.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * This utility class helps to parse the web page based upon the selector class
 * and given element node.
 */

@Service
public class ParserHelper {

	private final Logger logger = LoggerFactory.getLogger(ParserHelper.class);

    /**
     * Parse the element for the given selector class  
     *
     * @param element - product node
     * @param class - name of the selector class
     * @param boolean - If the value is false it returns the product title otherwise price string
     * @return String - product title / price string
     */
	public String getElementDataByClass(Element element, String classData, boolean ownText){
		String returnData = null;
		Elements elements = element.getElementsByClass(classData);
		for(Element elementDataElement : elements){
			if(ownText)
				returnData = elementDataElement.ownText();
			else
				returnData = elementDataElement.text();
		}
		return returnData;
	}

    /**
     * Calculates the size of the linked HTML excluding image asset   
     *
     * @param element - product node
     * @return String - size string
     */
	public double calculateLinkedPageSize(Element element) throws Exception {
		long tempSize=0;
		Elements linkedDocumentElements = getLinkedElements(element);
		for(Element linkedDocumentElement: linkedDocumentElements){
			if(!linkedDocumentElement.tagName().equals("img")){
				tempSize+= linkedDocumentElement.html().length();
			}
		}
		return tempSize/1024;
	}

    /**
     * Retrieves the product description of the linked HTML  
     * Retrieving productText first class occurrence as productText class is also used in other places 
     *
     * @param element - product node
     * @return String - product description
     */
	public String getLinkedDocumentDescription(Element element) throws Exception {
		String description = null;
		Elements linkedDocumentElements = getLinkedElements(element);
		int count=0;
		for(Element linkedDocumentElement: linkedDocumentElements){
			Elements descriptionElements = linkedDocumentElement.select("div.productText");
			for(Element descriptionElement : descriptionElements){
				if(descriptionElement.hasText() && count==0){
					description = descriptionElement.text();
					count++;
				}
			}
		}
		return description;
	}

	private Elements getLinkedElements(Element element) throws Exception {
		Element linkedElement = element.select("a").first();
		String absHref = linkedElement.attr("abs:href"); 
		logger.info("Linked document URL --> " + absHref);

		Document linkedDocument = Jsoup.connect(absHref).get();
		return linkedDocument.getAllElements();
	}
	
}