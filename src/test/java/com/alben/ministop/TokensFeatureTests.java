package com.alben.ministop;

import com.alben.ministop.controllers.ClientController;
import com.alben.ministop.controllers.TokenController;
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
@WebMvcTest(controllers = {TokenController.class})
@ActiveProfiles({"test"})
public class TokensFeatureTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Get token when credentials are valid")
	void getToken() throws Exception {
		mockMvc.perform(
				post("/v1/oauth2/token")
						.header("Content-Type", "application/json")

				.content("{ \"client_id\":\"24353344\", " +
						"\"client_secret\": \"432rers432rwrr\", " +
						"\"grant_type\":\"client_credentials\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.access_token").isNotEmpty())
				.andExpect(jsonPath("$.expires_at").isNotEmpty());
	}

}
