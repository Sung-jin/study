package model.http;

import java.util.Arrays;

public enum HttpVersion {
    V0_9("HTTP/0.9"), V1_0("HTTP/1.0"), V1_1("HTTP/1.1"), V2_0("HTTP/2.0"), UNDEFINED("UNDEFINED");

    private String text;

    HttpVersion(String text) {
        this.text = text;
    }

    public static HttpVersion getVersionByString(String value) {
        return Arrays
                .stream(HttpVersion.values())
                .filter(httpVersion -> httpVersion.getText().equals(value))
                .findFirst()
                .orElse(UNDEFINED);
    }

    public String getText() {
        return text;
    }
}
