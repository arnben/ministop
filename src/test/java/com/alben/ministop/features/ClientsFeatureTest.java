package com.alben.ministop.features;

import com.alben.ministop.clients.payloads.*;
import com.alben.ministop.clients.repositories.*;
import com.alben.ministop.models.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClientsFeatureTest {

    private static final String CLIENT_NAME = "user-service";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUpClass() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Register client should respond successfully if name is not yet taken.")
    public void registerClient() throws Exception {
       MvcResult result = mockMvc.perform(
                post("/su/v1/client")
                        .header("Content-Type", "application/json")
                        .header("apiKey", TestConstants.RW_KEY)
                        .content("{ \"name\":\"" + CLIENT_NAME + "\", \"emails\":[\"empoy.wurm@gmail.com\"]}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value(CLIENT_NAME))
                .andExpect(jsonPath("$.emails", hasSize(1)))
                .andExpect(jsonPath("$.key").isNotEmpty())
                .andReturn();

        ClientResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ClientResponse.class);

        Optional<Client> client = clientRepository.getClientByName(CLIENT_NAME);
        assertThat(client.get().getEmails()).contains("empoy.wurm@gmail.com");
        assertThat(client.get().getKey()).isEqualTo(response.getKey());
    }

    @Test
    @DisplayName("Registration throws Validation Exception if name contains space.")
    public void registerClientWithValidationException() throws Exception {
        mockMvc.perform(
                post("/su/v1/client")
                        .header("Content-Type", "application/json")
                        .header("apiKey", TestConstants.RW_KEY)
                        .content("{ \"name\":\"User Profile\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("View all clients names.")
    public void getAllClients() throws Exception {
        clientRepository.register(Client.builder()
                .name("client1")
                .emails(Arrays.asList("war.pig.coc1@gmail.com"))
                .key("randomKey1")
                .build());

        clientRepository.register(Client.builder()
                .name("client2")
                .emails(Arrays.asList("war.pig.coc2@gmail.com"))
                .key("randomKey2")
                .build());

        clientRepository.register(Client.builder()
                .name("client3")
                .emails(Arrays.asList("war.pig.coc3@gmail.com"))
                .key("randomKey3")
                .build());

        mockMvc.perform(
                get("/su/v1/client")
                        .header("Content-Type", "application/json")
                        .header("Accepts", "application/json")
                        .header("apiKey", TestConstants.READ_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clients.length()", is(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$.clients", hasItems("client1", "client2", "client3")));
    }

    @Test
    @DisplayName("View specific client and details.")
    public void getClient() throws Exception {
        clientRepository.register(Client.builder()
                .name("war-service")
                .emails(Arrays.asList("war.pig.coc@gmail.com"))
                .key("randomKey")
                .build());

        mockMvc.perform(
                get("/su/v1/client/war-service")
                        .header("Content-Type", "application/json")
                        .header("apiKey", TestConstants.READ_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("war-service"))
                .andExpect(jsonPath("$.emails", containsInAnyOrder("war.pig.coc@gmail.com")))
                .andExpect(jsonPath("$.key").value("randomKey"));
    }


    @Test
    @DisplayName("Registration throws Validation Exception if name already exists.")
    public void nameExists() throws Exception {

        clientRepository.register(Client.builder()
                .name("existing-client")
                .emails(Arrays.asList("war.pig.coc@gmail.com"))
                .key("randomKey")
                .build());

        mockMvc.perform(
                post("/su/v1/client")
                        .header("Content-Type", "application/json")
                        .header("apiKey", TestConstants.RW_KEY)
                        .content("{ \"name\":\"existing-client\", \"emails\":[\"empoy.wurm@gmail.com\"]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isNotEmpty());
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
                get("/su/v1/client")
                        .header("Content-Type", "application/json")
                        .content("{ \"name\":\"user-service\"}"))
                .andExpect(status().isUnauthorized());
    }
}
