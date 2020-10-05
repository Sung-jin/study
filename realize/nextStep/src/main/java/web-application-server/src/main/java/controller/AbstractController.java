package controller;

import model.http.HttpRequest;
import model.http.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse);
    abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse);
}
