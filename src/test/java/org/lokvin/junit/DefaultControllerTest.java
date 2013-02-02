package org.lokvin.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DefaultControllerTest {
    
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
        private static final String NAME = "Test";
        public String getName() {
            return NAME;
        }
        
        public boolean equals(Object object) {
            boolean result = false;
            if (object instanceof SimpleResponse) {
                result = ((SimpleResponse) object).getName().equals(getName());
            }
            return result;
        }
        
        public int hashCode() {
            return NAME.hashCode();
        }
    }
    
    private DefaultController controller;
    private Request request;
    private RequestHandler handler;
    
    @Before
    public void initiate() throws Exception {
        controller = new DefaultController();
        request = new SimpleRequest();
        handler = new SimpleRequestHandler();
        controller.addRequestHandler(request, handler);
    }
    
    @Test
    public void testAddHandler() {
        RequestHandler handler2 = controller.getHandler(request);
        assertSame("handler we set to controller should be the same we get", handler, handler2);
    }
    
    @Test
    public void testProcessRequest() {
        Response response = controller.processRequest(request);
        assertNotNull("must not return a null response", response);
        assertEquals("response should be same response", 
                new SimpleResponse(), response);
    }

}
