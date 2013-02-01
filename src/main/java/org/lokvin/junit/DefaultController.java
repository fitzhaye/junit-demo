package org.lokvin.junit;

import java.util.HashMap;
import java.util.Map;

public class DefaultController implements Controller {

    private Map<String, RequestHandler> requestHandlers = 
            new HashMap<String, RequestHandler>();
    
    protected RequestHandler getHandler(Request request) {
        if (!requestHandlers.containsKey(request.getName())) {
            throw new RuntimeException("Can't find handler for request name: [" 
        + request.getName() + "]");
        }
        return requestHandlers.get(request.getName());
    }
    
    @Override
    public Response processRequest(Request request) {
        Response response;
        try {
            response = getHandler(request).process(request);
        } catch (Exception ex) {
            response = new ErrorResponse(request, ex);
        }
        return response;
    }

    @Override
    public void addRequestHandler(Request request, RequestHandler handler) {
        if (requestHandlers.containsKey(request.getName())) {
            throw new RuntimeException("a RequestHandler has been registered for request name: [" 
        + request.getName() + "]");
        } else {
            requestHandlers.put(request.getName(), handler);
        }

    }

}
