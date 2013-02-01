package org.lokvin.junit;

public interface Controller {
    Response processRequest(Request request);
    void addRequestHandler(Request request, RequestHandler handler);
}
