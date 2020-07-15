package com.alben.ministop.services.impl;


import com.alben.ministop.models.Client;
import com.alben.ministop.repositories.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class DefaultClientServiceTest {
    @InjectMocks
    private DefaultClientService defaultClientService;

    @Mock
    private ClientRepository clientRepository;

    @Test
    @DisplayName("Should generate unique id and secret if service does not exist yet.")
    public void test1() {
        Mockito.when(clientRepository.getClientByServiceName(Mockito.eq("user-profile"))).thenReturn(Optional.empty());
        Client client = defaultClientService.register("user-profile");
        assertThat(client.getId()).isNotEmpty();
        assertThat(client.getSecret()).isNotEmpty();
        assertThat(client.getServiceName()).isEqualTo("user-profile");
    }

    @Test
    @DisplayName("Should throw error when service name exists.")
    public void test1() {
        Mockito.when(clientRepository.getClientByServiceName(Mockito.eq("user-profile")))
                .thenReturn(Optional.of(Client.builder().id("3fdsfsdfds3432").secret("324234234324").serviceName("user-profile").build()));

        ClientExistsException clientExistsException = assertThrows(ClientExistsException.class,
                () -> defaultClientService.register("user-profile"));

        Client client = defaultClientService.register("user-profile");
        assertThat(client.getId()).isNotEmpty();
        assertThat(client.getSecret()).isNotEmpty();
        assertThat(client.getServiceName()).isEqualTo("user-profile");
    }
}