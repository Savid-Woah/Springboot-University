package springboot_university.response.handler;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static Map<String, Object> generateResponse(Object data, HttpStatus status, String message) {

        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("status", status);
        response.put("message", message);
        return response;
    }
}