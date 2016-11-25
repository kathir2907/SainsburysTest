package com.sainsburys.test.unit;

import static org.junit.Assert.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.sainsburys.test.service.ParserHelper;

@RunWith(MockitoJUnitRunner.class)
public class ParserHelperTest extends ParserCommonTestUtil {
	
	private ParserHelper parserHelper;
	private Document document;
    @Before
    public void setUp() throws Exception {
    	parserHelper = new ParserHelper();
		document = Jsoup.connect(testUrl).get();
    }       

    @Test
    public void testProductElement() {
    	String productString = parserHelper.getElementDataByClass(ParserHelperTestUtil.createProductElement(), "productInfo", false);
    	assertEquals(productString, "Sainsbury's Apricot Ripe & Ready x5");
    }

    @Test
    public void testPriceElement() {
    	String priceString = parserHelper.getElementDataByClass(ParserHelperTestUtil.createPriceElement(), "pricePerUnit", true);
    	assertEquals(priceString, "&pound3.50");
    }

    @Test
    public void testProductLinkedPageSize() throws Exception {
    	double size = parserHelper.calculateLinkedPageSize(document.getElementsByClass("product").first());
    	assertEquals(size, 293.0, 293.0);
    }

    @Test
    public void testProductLinkedDescription() throws Exception {
    	String description = parserHelper.getLinkedDocumentDescription(document.getElementsByClass("product").first());
    	assertEquals(description, "Apricots");
    }
    
}
