package org.lokvin.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class DefaultControllerTest {
    
    private class SimpleRequest implements Request {
        private static final String DEFAULT_NAME = "Test";
        private String name;
        
        public SimpleRequest() {
            this(DEFAULT_NAME);
        }
        
        public SimpleRequest(String name) {
            this.name = name;
        }
        
        @Override
        public String getName() {
            return name;
        }
    }
    
    private class SimpleRequestHandler implements RequestHandler {

        @Override
        public Response process(Request request) throws Exception {
            return new SimpleResponse();
        }
        
    }
    
    private class SimpleExceptionHandler implements RequestHandler {

        @Override
        public Response process(Request request) throws Exception {
            throw new Exception("error processing request");
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
    
    @Test
    public void testProcessRequestAnswerErrorResponse() {
        Request request = new SimpleRequest("testError");
        RequestHandler handler = new SimpleExceptionHandler();
        controller.addRequestHandler(request, handler);
        Response response = controller.processRequest(request);
        assertNotNull("must not return a null response", response);
        assertEquals("should return type of ErrorResponse response", ErrorResponse.class, response.getClass());
    }
    
    @Test (expected=RuntimeException.class)
    public void testGetHendlerNotDefined() {
        Request request = new SimpleRequest("testNotDefined");
        controller.getHandler(request);
    }
    
    @Test (expected=RuntimeException.class)
    public void testAddDuplicateRequestHander() {
        Request request = new SimpleRequest();
        RequestHandler requestHandler = new SimpleRequestHandler();
        controller.addRequestHandler(request, requestHandler);
    }
    
    @Test(timeout=150)
    @Ignore(value="ignore now util decide timeout limit")
    public void testProcessMultiRequestTimeout() {
        Request request;
        Response response;
        RequestHandler handler = new SimpleRequestHandler();
        for (int i=0; i<100000; i++) {
            request = new SimpleRequest("simplerequest" + i);
            controller.addRequestHandler(request, handler);
            response = controller.processRequest(request);
            assertNotNull("response must not be null", response);
            assertSame("the response type shoule same with SimpleResponse", 
                    SimpleResponse.class, response.getClass());
        }
    }
}
