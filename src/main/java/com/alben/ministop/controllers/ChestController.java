package com.alben.ministop.controllers;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;

@Slf4j
@RestController
@RequestMapping("/${ministop.api.rootPath}/v1/chest")
public class ChestController {

    @GetMapping("/{realm}")
    public ResponseEntity<Map<String, Object>> getAllDuos(@RequestHeader("client") String clientName,
                                                      @PathVariable String realm) {
        Map<String, Object> response = new TreeMap<>();
        response.put("downStreamUrl", "http://localhost:8080/");
        response.put("allowedUser", "10");
        response.put("keywords",
                Arrays.asList("tito", "vic", "joey"));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{realm}/{propertyName}")
    public ResponseEntity<SinglePropertyResponse> getDuoByKey(@RequestHeader("client") String clientName,
                                                      @PathVariable String realm, @PathVariable String propertyName) {

        return ResponseEntity.ok(new SinglePropertyResponse("true"));
    }

    @Data
    public static class SinglePropertyResponse {
        private Object value;
        public SinglePropertyResponse(Object value) {
            this.value = value;
        }
    }

}
