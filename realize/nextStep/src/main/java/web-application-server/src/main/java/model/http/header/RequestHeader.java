package model.http.header;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static util.HttpRequestUtils.parseHeader;
import static util.HttpRequestUtils.Pair;

public class RequestHeader {
    private static List<String> requestHeaderAllKey = Arrays
            .stream(RequestField.values())
            .map(RequestField::getKey)
            .collect(Collectors.toList());

    private HashMap<String, String> header = new HashMap<>();
    private HashMap<String, String> customHeader = new HashMap<>();

    private HashMap<String, String> data;

    public void addHeader(String line) {
        Pair parseHeader = parseHeader(line);
        HashMap<String, String> beAddedHeader = requestHeaderAllKey.contains(parseHeader.getKey()) ? header : customHeader;
        beAddedHeader.put(parseHeader.getKey(), parseHeader.getValue());
    }

    public void addHeader(String key, String value) {
        HashMap<String, String> beAddedHeader = requestHeaderAllKey.contains(key) ? header : customHeader;
        beAddedHeader.put(key, value);
    }

    public String getHeaderValue(RequestField field) {
        return header.get(field.getKey());
    }

    public String getCustomHeaderValue(String key) {
        return customHeader.get(key);
    }
}
