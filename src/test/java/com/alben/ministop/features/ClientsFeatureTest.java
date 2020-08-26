package com.alben.ministop.features;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientsFeatureTest {

    private static final String CLIENT_NAME = "user-service";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Register client should respond successfully if name is not yet taken.")
    public void registerClient() throws Exception {
        mockMvc.perform(
                post("/su/v1/client")
                        .header("Content-Type", "application/json")
                        .header("adminKey", TestConstants.RW_KEY)
                        .content("{ \"name\":\"" + CLIENT_NAME + "\", \"emails\":[\"empoy.wurm@gmail.com\"]}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value(CLIENT_NAME))
                .andExpect(jsonPath("$.emails", hasSize(1)));
    }

    @Test
    @DisplayName("Registration throws Validation Exception if name contains space.")
    public void registerClientWithValidationException() throws Exception {
        mockMvc.perform(
                post("/su/v1/client")
                        .header("Content-Type", "application/json")
                        .header("adminKey", TestConstants.RW_KEY)
                        .content("{ \"name\":\"User Profile\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.clients").value(1))
                .andExpect(jsonPath("$.error.message").value("Name cannot contain spaces."));
    }

    @Test
    @DisplayName("View all clients and details.")
    public void getAllClients() throws Exception {
        mockMvc.perform(
                get("/su/v1/client")
                        .header("Content-Type", "application/json")
                        .header("adminKey", TestConstants.READ_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clients").isNotEmpty());
    }

    @Test
    @DisplayName("Registration throws Validation Exception if name already exists.")
    public void viewClientDetails() throws Exception {
        mockMvc.perform(
                post("/su/v1/client")
                        .header("Content-Type", "application/json")
                        .header("adminKey", TestConstants.RW_KEY)
                        .content("{ \"name\":\"existing-name\", \"emails\":[\"empoy.wurm@gmail.com\"]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value(2))
                .andExpect(jsonPath("$.error.message").value("Name 'existing-name' already exists."));
    }

    @Test
    @DisplayName("Register client throw HTTP 401")
    public void registerClientWithNoAdminKey() throws Exception {
        mockMvc.perform(
                post("/su/v1/client")
                        .header("Content-Type", "application/json")
                        .content("{ \"name\":\"" + CLIENT_NAME + "\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Returns HTTP 401 when admin key is missing")
    public void resetClientSecretNoKey() throws Exception {
        mockMvc.perform(
                post("/su/v1/client/someClientId123/secret")
                        .header("Content-Type", "application/json")
                        .content("{ \"name\":\"user-service\"}"))
                .andExpect(status().isUnauthorized());
    }
}
