package com.alben.ministop.features;

import com.alben.ministop.clients.repositories.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;
import org.springframework.test.web.servlet.*;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RealmFeatureTest {

    public static final String REALM_BASE_URI = "/web/v1/realm/";
    public static final String PERSONAL_TOKEN = "yIWiVxx4fg";
    private static final String CLIENT_NAME = "resource-service";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(
                post(REALM_BASE_URI)
                        .header("Content-Type", "application/json")
                        .header("client", CLIENT_NAME)
                        .header("apiKey", PERSONAL_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.realms").value("http://localhost:8080/"))
                .andExpect(jsonPath("$.allowedUser").value("10"))
                .andExpect(jsonPath("$.keywords.*", hasSize(3)))
                .andExpect(jsonPath("$.keywords", hasItems("tito", "joey", "vic")));
    }
}
