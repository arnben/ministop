package com.alben.ministop.chest;

import com.alben.ministop.chest.services.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/${ministop.api.rootPath}/v1/chest")
public class ChestController {

    @Autowired
    private ChestService chestService;

    @GetMapping("/{realm}")
    public ResponseEntity<Map<String, Object>> getAllDuos(@RequestHeader("client") String clientName,
                                                      @PathVariable String realm) {
        return ResponseEntity.ok(chestService.getAllProperties(clientName, realm));
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
