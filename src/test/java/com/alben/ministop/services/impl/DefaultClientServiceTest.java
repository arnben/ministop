package com.alben.ministop.services.impl;


import com.alben.ministop.exceptions.ErrorDetails;
import com.alben.ministop.exceptions.MinistopAppException;
import com.alben.ministop.exceptions.ValidationException;
import com.alben.ministop.models.Client;
import com.alben.ministop.repositories.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
    @DisplayName("Should generate unique id and secret if service does not exist yet.")
    public void happyPath() throws Exception {
        when(clientRepository.getCLientById(eq("user-profile"))).thenReturn(Optional.empty());
        Client client = defaultClientService.register("user-profile");
        assertThat(client).isNotNull();
        assertThat(client.getId()).isEqualTo("user-profile");
        assertThat(client.getSecret()).isNotEmpty();;
    }

    @Test
    @DisplayName("Should throw error when service name exists.")
    public void sadPath1() {
        String input = "user-profile";
        when(clientRepository.getCLientById(eq(input)))
                .thenReturn(Optional.of(Client.builder().id("3fdsfsdfds3432").secret("324234234324").build()));

       ValidationException ex = assertThrows(ValidationException.class,
                () -> defaultClientService.register(input));

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
                () -> defaultClientService.register("user profile"));

        assertErrorDetails(e, ErrorDetails.CLIENT_NAME_HAS_SPACES, "user profile");
    }
}