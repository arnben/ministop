package com.alben.ministop.clients.services.impl.impl;


import com.alben.ministop.exceptions.ErrorDetails;
import com.alben.ministop.exceptions.MinistopAppException;
import com.alben.ministop.exceptions.ValidationException;
import com.alben.ministop.models.Client;
import com.alben.ministop.clients.repositories.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DefaultClientServiceTest {
    @InjectMocks
    private DefaultClientService defaultClientService;

    @Mock
    private ClientRepository clientRepository;

    @Test
    @DisplayName("Should return client instance when client name is valid and not yet taken.")
    public void happyPath() throws Exception {
        when(clientRepository.getClientById(eq("user-profile"))).thenReturn(Optional.empty());
        Client client = defaultClientService.register(Client.builder().name("user-profile").emails(Arrays.asList("empoy.wurm@gmail.com")).build());
        assertThat(client.getName()).isEqualTo("user-profile");
        assertThat(client.getEmails()).contains("empoy.wurm@gmail.com");
    }

    @Test
    @DisplayName("Should throw error when service name exists.")
    public void sadPath1() {
        String input = "user-profile";
        when(clientRepository.getClientById(eq(input)))
                .thenReturn(Optional.of(Client.builder().name(input).build()));

       ValidationException ex = assertThrows(ValidationException.class,
                () -> defaultClientService.register(Client.builder().name(input).build()));

       assertErrorDetails(ex, ErrorDetails.CLIENT_EXISTS, input);
    }

    private void assertErrorDetails(MinistopAppException e, ErrorDetails expectedErrorDetails, String ... args) {
        assertThat(e.getMessage()).isEqualTo(expectedErrorDetails.getMessage(args));
        assertThat(e.getCode()).isEqualTo(expectedErrorDetails.getCode());
    }

    @Test
    @DisplayName("Should throw error when service name has whitespaces.")
    public void sadPath2() {

        ValidationException e = assertThrows(ValidationException.class,
                () -> defaultClientService.register(Client.builder().name("user profile").build()));

        assertErrorDetails(e, ErrorDetails.CLIENT_NAME_HAS_SPACES, "user profile");
    }
}