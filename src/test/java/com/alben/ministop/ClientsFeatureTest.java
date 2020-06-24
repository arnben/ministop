package com.alben.ministop;

import com.alben.ministop.controllers.ClientController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {ClientController.class})
@ActiveProfiles({"test"})
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
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error.id").value(1))
                .andExpect(jsonPath("$.error.message").value("Missing or invalid credentials."));
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
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error.id").value(1))
                .andExpect(jsonPath("$.error.message").value("Missing or invalid credentials."));
    }
}
