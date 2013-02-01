package org.lokvin.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DefaultControllerTest {
    private DefaultController controller;
    
    private class SimpleRequest implements Request {
        @Override
        public String getName() {
            return "Test";
        }
    }
    
    private class SimpleRequestHandler implements RequestHandler {

        @Override
        public Response process(Request request) throws Exception {
            return new SimpleResponse();
        }
        
    }
    
    private class SimpleResponse implements Response {
        
    }
    
    @Before
    public void initiate() throws Exception {
        controller = new DefaultController();
    }
    
    @Test
    public void testAddHandler() {
        Request request = new SimpleRequest();
        RequestHandler handler = new SimpleRequestHandler();
        controller.addRequestHandler(request, handler);
        
        RequestHandler handler2 = controller.getHandler(request);
        
        assertSame("handler we set to controller should be the same we get", handler, handler2);
    }
    
    @Test
    public void testProcessRequest() {
        Request request = new SimpleRequest();
        RequestHandler handler = new SimpleRequestHandler();
        controller.addRequestHandler(request, handler);
        Response response = controller.processRequest(request);
        
        assertNotNull("must not return a null response", response);
        assertEquals("response should be the type of SimpleResponse", 
                SimpleResponse.class, response.getClass());
    }

}
