package fi.nls.oskari.search.channel;

import fi.mml.portti.service.search.ChannelSearchResult;
import fi.mml.portti.service.search.SearchCriteria;
import fi.mml.portti.service.search.SearchResultItem;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class TM35LehtijakoSearchChannelTest {
    
    public TM35LehtijakoSearchChannelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testDoSearch() {
        System.out.println("doSearch");
        
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setSearchString("U52");
        searchCriteria.setSRS("EPSG:3067");
        
        TM35LehtijakoSearchChannel instance = new TM35LehtijakoSearchChannel();
        ChannelSearchResult result = instance.doSearch(searchCriteria);
        SearchResultItem item = result.getSearchResultItems().get(0);
        
        assertEquals(548000.0d, Double.parseDouble(item.getLon()), 0.0d);
        assertEquals(7506000.0d, Double.parseDouble(item.getLat()), 0.0d);
    }

    @Test
    public void testReverseGeocode() throws Exception {
        System.out.println("reverseGeocode");
        
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setSRS("EPSG:3067");
        //searchCriteria.setReverseGeocode(548000.0d, 7506000.0d);
        searchCriteria.setReverseGeocode(7506000.0d, 548000.0d);
        searchCriteria.addParam("scale", "100000");
        
        TM35LehtijakoSearchChannel instance = new TM35LehtijakoSearchChannel();
        ChannelSearchResult result = instance.reverseGeocode(searchCriteria);
        SearchResultItem item = result.getSearchResultItems().get(0);
        
        assertEquals("U52", item.getTitle());
    }
    
}