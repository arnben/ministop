package com.alben.ministop;

import com.alben.ministop.controllers.ClientController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientsFeatureTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Register client should return clientId and clientSecret")
    public void registerClient() throws Exception {
        mockMvc.perform(
                post("/su/v1/client")
                        .header("Content-Type", "application/json")
                        .header("adminKey", "Thanos")
                        .content("{ \"name\":\"User Service\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.client_id").isNotEmpty())
                .andExpect(jsonPath("$.client_secret").isNotEmpty());
    }

    @Test
    @DisplayName("Register client throw HTTP 401")
    public void registerClientWithNoAdminKey() throws Exception {
        mockMvc.perform(
                post("/su/v1/client")
                        .header("Content-Type", "application/json")
                        .content("{ \"name\":\"User Service\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Rest passcode test")
    public void resetClientSecret() throws Exception {
        mockMvc.perform(
                post("/su/v1/client/someClientId123/secret")
                        .header("Content-Type", "application/json")
                        .header("adminKey", "Thanos")
                        .content("{ \"name\":\"User Service\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.client_id").isNotEmpty())
                .andExpect(jsonPath("$.client_secret").isNotEmpty());
    }

    @Test
    @DisplayName("Returns HTTP 401 when admin key is missing")
    public void resetClientSecretNoKey() throws Exception {
        mockMvc.perform(
                post("/su/v1/client/someClientId123/secret")
                        .header("Content-Type", "application/json")
                        .content("{ \"name\":\"User Service\"}"))
                .andExpect(status().isUnauthorized());
    }
}
