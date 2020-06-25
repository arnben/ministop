package com.alben.ministop;

import com.alben.ministop.controllers.DuoController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {DuoController.class})
@ActiveProfiles({"test"})
public class DuoFeatureTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Get Duos For The Given Client Id In Token ")
    public void getDuo() throws Exception {
        mockMvc.perform(
                get("/api/v1/duo/someClientId")
                        .header("Content-Type", "application/json")
                        .header("Authorization", "32rcsser23refsdfdsfdsfdsf"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.duos.stringKey").value("foo1"))
                .andExpect(jsonPath("$.duos.numberKey").value(123453432432L))
                .andExpect(jsonPath("$.duos.collectionKey").isArray())
                .andExpect(jsonPath("$.duos.collectionKey.*", hasSize(3)))
                .andExpect(jsonPath("$.duos.collectionKey", hasItems("tito", "joey", "vic")));
    }

    @Test
    @DisplayName("Call Duos with Invalid Token ")
    public void getDuoNoToken() throws Exception {
        mockMvc.perform(
                get("/api/v1/duo/some-client-id")
                        .header("Content-Type", "application/json"))
                .andExpect(status().isUnauthorized());
    }
}
