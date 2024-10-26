package dev.lone64.roseframework.spigot.util.other;

import com.github.kevinsawicki.http.HttpRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.MalformedURLException;
import java.net.URL;

public class Request {

    public static URL createURL(final String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static JSONObject getObject(final String base_url) {
        try {
            final String response = HttpRequest.get(base_url).body();
            return (JSONObject) new JSONParser().parse(response);
        } catch (final HttpRequest.HttpRequestException | ParseException e) {
            return null;
        }
    }

    public static JSONArray getArray(final String base_url) {
        try {
            final String response = HttpRequest.get(base_url).body();
            return (JSONArray) new JSONParser().parse(response);
        } catch (final HttpRequest.HttpRequestException | ParseException e) {
            return null;
        }
    }

}