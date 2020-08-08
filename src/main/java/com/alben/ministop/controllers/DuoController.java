package com.alben.ministop.controllers;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;

@RestController
@RequestMapping("/${ministop.api.rootPath}/v1/duo")
public class DuoController {

    @GetMapping("/{clientId}")
    public ResponseEntity<AllDuosResponse> getAllDuos(@PathVariable String clientId,
                                                      @RequestHeader("Authorization") Optional<String> token) {
        Map<String, Object> response = new TreeMap<>();
        response.put("stringKey", "foo1");
        response.put("numberKey", 123453432432L);
        response.put("collectionKey",
                Arrays.asList("tito", "vic", "joey"));
        return ResponseEntity.ok(AllDuosResponse.builder().duos(response).build());
    }

    @GetMapping("/{clientId}/{duoKeyName}")
    public ResponseEntity<Map<String, Object>> getDuoByKey(@PathVariable String clientId,
                                                      @PathVariable String duoKeyName,
                                                      @RequestHeader("Authorization") Optional<String> token) {
        if(token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Map<String, Object> response = new TreeMap<>();
        if("stringKey".equals(duoKeyName))
            response.put(duoKeyName, "foo1");

        if("numberKey".equals(duoKeyName))
            response.put(duoKeyName, 123453432432L);

        if("collectionKey".equals(duoKeyName))
            response.put("collectionKey",
                    Arrays.asList("tito", "vic", "joey"));
        return ResponseEntity.ok(response);
    }

    @Builder
    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class AllDuosResponse {
        private Map<String, Object> duos;
    }
}
