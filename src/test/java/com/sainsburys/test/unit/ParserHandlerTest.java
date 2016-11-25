package com.sainsburys.test.unit;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import com.sainsburys.test.model.Products;
import com.sainsburys.test.service.ParserHandler;
import com.sainsburys.test.service.ParserHelper;

@RunWith(MockitoJUnitRunner.class)
public class ParserHandlerTest extends ParserCommonTestUtil {
	
	   @Mock private Document document;
	   @Mock private Element element;
	   @Mock private ParserHelper parserHelper;
       @InjectMocks private ParserHandler parserHandler;
       
       @Before
       public void setUp() throws Exception {
    	   MockitoAnnotations.initMocks(this);
       }       
       
       @Test
       public void testProcessPage() throws Exception {

    	   org.springframework.test.util.ReflectionTestUtils.setField(parserHandler, "testUrl", testUrl);
    	   when(document.getElementsByClass("product")).thenReturn(ParserHandlerTestUtil.createProducts());
    	   when(element.select("div.productText")).thenReturn(ParserHandlerTestUtil.createLinkedElements());

    	   Products products = parserHandler.processPage();
    	   
    	   assertEquals(products.getProductItems().size(), 7);
    	   assertEquals(products.getTotal(), 15.1, 15.1);
    	   
    	   verify(parserHelper, atLeastOnce()).getElementDataByClass(any(Element.class), anyString(),anyBoolean());
    	   verify(parserHelper, atLeastOnce()).calculateLinkedPageSize(any(Element.class));
    	   verify(parserHelper, atLeastOnce()).getLinkedDocumentDescription(any(Element.class));
       }
       
}
