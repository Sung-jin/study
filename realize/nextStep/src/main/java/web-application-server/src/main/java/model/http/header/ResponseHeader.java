package model.http.header;

import util.HttpRequestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static util.HttpRequestUtils.parseHeader;

public class ResponseHeader {
    private static List<String> responseHeaderAllKey = Arrays
            .stream(ResponseField.values())
            .map(ResponseField::getKey)
            .collect(Collectors.toList());

    private HashMap<String, String> header = new HashMap<>();
    private HashMap<String, String> customHeader = new HashMap<>();

    public void addHeader(String line) {
        HttpRequestUtils.Pair parseHeader = parseHeader(line);
        HashMap<String, String> beAddedHeader = responseHeaderAllKey.contains(parseHeader.getKey()) ? header : customHeader;
        beAddedHeader.put(parseHeader.getKey(), parseHeader.getValue());
    }

    public void addHeader(String key, String value) {
        HashMap<String, String> beAddedHeader = responseHeaderAllKey.contains(key) ? header : customHeader;
        beAddedHeader.put(key, value);
    }

    public String getHeaderValue(RequestField field) {
        return header.get(field.getKey());
    }

    public String getCustomHeaderValue(String key) {
        return customHeader.get(key);
    }
}
