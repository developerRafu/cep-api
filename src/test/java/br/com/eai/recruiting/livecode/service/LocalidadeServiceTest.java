package br.com.eai.recruiting.livecode.service;

import br.com.eai.recruiting.livecode.clients.ApiCepClient;
import br.com.eai.recruiting.livecode.clients.ViaCepClient;
import br.com.eai.recruiting.livecode.domain.Address;
import br.com.eai.recruiting.livecode.mappers.AddressMapper;
import br.com.eai.recruiting.livecode.request.AddressRequest;
import br.com.eai.recruiting.livecode.response.ApiCepResponse;
import br.com.eai.recruiting.livecode.response.ViaCepResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
class LocalidadeServiceTest {
    @Mock
    private ViaCepClient viaCepClient;

    @Mock
    private ApiCepClient apiCepClient;

    @Mock
    private AddressMapper mapper;

    @InjectMocks
    private LocalidadeService localidadeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAddressByCepViaCep() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setVersion(1);
        addressRequest.setZipCode("00000000");
        ViaCepResponse viaCepResponse = new ViaCepResponse();
        Address expectedAddress = new Address();

        when(viaCepClient.getByZipCode(anyString())).thenReturn(viaCepResponse);
        when(mapper.toAddressEntity(addressRequest, viaCepResponse)).thenReturn(expectedAddress);

        Address result = localidadeService.getAddressByCep(addressRequest);

        assertEquals(expectedAddress, result);
        verify(viaCepClient, times(1)).getByZipCode(anyString());
        verify(apiCepClient, never()).getByZipCode(anyString());
    }

    @Test
    public void testGetAddressByCepApiCep() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setVersion(2);
        ApiCepResponse apiCepResponse = new ApiCepResponse();
        Address expectedAddress = new Address();

        when(apiCepClient.getByZipCode(anyString())).thenReturn(apiCepResponse);
        when(mapper.toAddressEntity(addressRequest, apiCepResponse)).thenReturn(expectedAddress);

        Address result = localidadeService.getAddressByCep(addressRequest);

        assertEquals(expectedAddress, result);
        verify(viaCepClient, never()).getByZipCode(anyString());
        verify(apiCepClient, times(1)).getByZipCode(anyString());
    }

    @Test
    public void testGetAddressByCepInvalidVersion() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setVersion(3);

        assertThrows(IllegalArgumentException.class, () -> localidadeService.getAddressByCep(addressRequest));
        verify(viaCepClient, never()).getByZipCode(anyString());
        verify(apiCepClient, never()).getByZipCode(anyString());
    }
}