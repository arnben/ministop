package com.alben.ministop.features;

import com.alben.ministop.clients.repositories.*;
import com.alben.ministop.models.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonalAccessCodeFeatureTest {

    private static final String CLIENT_NAME = "my-service";
    private static final String TOKEN = "5cZfn77WaY";
    private static final String VALID_MEMBER_EMAIL = "empoy.wurm@gmail.com";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PersonalCodeRepository personalCodeRepository;

    @BeforeEach
    public void setUp() {
        clientRepository.register(Client.builder().name(CLIENT_NAME).key(TOKEN).emails(Arrays.asList(VALID_MEMBER_EMAIL)).build());
    }

    @AfterEach
    public void cleanUp() {
        clientRepository.deleteByName(CLIENT_NAME);
    }

    @Test
    public void testGeneratePersonalCode() throws Exception {
        mockMvc.perform(
                post("/web/code")//Implement rate limiter
                        .header("Content-Type", "application/json")
                        .header("client", CLIENT_NAME)
                        .header("apiKey", TOKEN)
                        .content("{ \"email\":\""+VALID_MEMBER_EMAIL+"\"}"))
                .andExpect(status().isOk());

        Optional<PersonalAccessCode> accessCode = personalCodeRepository.getAccessCodeByEmail(VALID_MEMBER_EMAIL);
        assertThat(accessCode.isPresent()).isTrue();
        assertThat(accessCode.getCode()).isNotEmpty();
        assertThat(accessCode.getClient().getName()).isEquals(CLIENT_NAME);
    }

    @Test
    public void testGeneratePersonalCodeInvalid() throws Exception {
        mockMvc.perform(
                post("/web/code")//Implement rate limiter
                        .header("Content-Type", "application/json")
                        .header("client", CLIENT_NAME)
                        .header("apiKey", TOKEN)
                        .content("{ \"email\":\"invalidEmail@gmail.com\"}"))
                .andExpect(status().isForbidden());
    }
}
