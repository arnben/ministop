package com.alben.ministop.features;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;
import org.springframework.test.web.servlet.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RealmFeatureTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreate() {
        mockMvc.perform(
                post("")
                        .header("Content-Type", "application/json")
                        .header("client", CLIENT_NAME)
                        .header("apiKey", TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.downStreamUrl").value("http://localhost:8080/"))
                .andExpect(jsonPath("$.allowedUser").value("10"))
                .andExpect(jsonPath("$.keywords.*", hasSize(3)))
                .andExpect(jsonPath("$.keywords", hasItems("tito", "joey", "vic")));
    }
}
