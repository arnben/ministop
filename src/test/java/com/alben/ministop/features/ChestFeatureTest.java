package com.alben.ministop.features;

import com.alben.ministop.clients.repositories.*;
import com.alben.ministop.models.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChestFeatureTest {
    public static final String TOKEN = "32rcsser23refsdfdsfdsfdsf";
    public static final String CHEST_BASE_URI = "/api/v1/chest/";
    private static final String CLIENT_NAME = "resource-service";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    public void setUp() {
        clientRepository.register(Client.builder().name(CLIENT_NAME).key(TOKEN).emails(Collections.EMPTY_LIST).build());
    }

    @AfterEach
    public void cleanUp() {
       clientRepository.deleteByName(CLIENT_NAME);
    }

    @Test
    @DisplayName("Get All Properties For The Given Client Name and Realm ")
    public void getProperties() throws Exception {
        mockMvc.perform(
                get(CHEST_BASE_URI + "/prod")
                        .header("Content-Type", "application/json")
                        .header("client", CLIENT_NAME)
                        .header("apiKey", TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.downStreamUrl").value("http://localhost:8080/"))
                .andExpect(jsonPath("$.allowedUser").value("10"))
                .andExpect(jsonPath("$.keywords.*", hasSize(3)))
                .andExpect(jsonPath("$.keywords", hasItems("tito", "joey", "vic")));
    }

    @Test
    @DisplayName("HTTP 401 when no api key in request.")
    public void getPropertiesNoKey() throws Exception {
        mockMvc.perform(
                get("/api/v1/chest/dev")
                        .header("Content-Type", "application/json"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Get Single Property")
    public void getSingleProperty() throws Exception {
        mockMvc.perform(
                get(CHEST_BASE_URI + "/qa/customFlag")
                        .header("Content-Type", "application/json")
                        .header("client", "resource-service")
                        .header("apiKey", TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("true"));
    }

}
