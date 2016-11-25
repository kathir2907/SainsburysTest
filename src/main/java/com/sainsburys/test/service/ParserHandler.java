package com.sainsburys.test.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sainsburys.test.model.ProductItem;
import com.sainsburys.test.model.Products;

/**
 * This handles class is responsible for parsing the given web page to retrieve the products object
 * with the help of ParserHelper utility class  
 *
 * @see ParserHelper
 */

@Service
public class ParserHandler {

	private final Logger logger = LoggerFactory.getLogger(ParserHandler.class);
	private static final String REG_EX_NUM_DIGIT = "[^\\d.]+";
	
	@Value("${test_url}") String testUrl;
	
	@Autowired
	private ParserHelper parserHelper;

    /**
     * Load the given page, process list of products and then creates Products object 
     *
     * @return Products - with list of all ProductItem and total sum
     */
	public Products processPage() throws Exception {
		Products products = new Products();
		
		List<ProductItem> productItems =  new ArrayList<ProductItem>();
		Document doc = Jsoup.connect(testUrl).get();
		Elements productNodes = doc.getElementsByClass("product");

		productNodes.stream().forEach(product -> {
			try{
				ProductItem productItem = new ProductItem();
				
				productItem.setTitle(parserHelper.getElementDataByClass(product,"productInfo",false));
				productItem.setSize(parserHelper.calculateLinkedPageSize(product));

				String priceString = parserHelper.getElementDataByClass(product,"pricePerUnit",true);
				if(priceString != null){
					productItem.setUnitPrice(Double.valueOf(priceString.replaceAll(REG_EX_NUM_DIGIT, "")));
				}
				productItem.setDescription(parserHelper.getLinkedDocumentDescription(product));
				
				productItems.add(productItem);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		});
		products.setProductItems(productItems);
		products.setTotal(productItems.stream().mapToDouble(d -> d.getUnitPrice()).sum());
		return products;
	}
	
}
