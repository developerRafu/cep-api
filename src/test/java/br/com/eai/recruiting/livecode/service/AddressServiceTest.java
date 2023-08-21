package br.com.eai.recruiting.livecode.service;

import br.com.eai.recruiting.livecode.domain.Address;
import br.com.eai.recruiting.livecode.mappers.AddressMapper;
import br.com.eai.recruiting.livecode.repository.AddressRepository;
import br.com.eai.recruiting.livecode.request.AddressRequest;
import br.com.eai.recruiting.livecode.request.AddressesRequest;
import br.com.eai.recruiting.livecode.response.AddressResponse;
import br.com.eai.recruiting.livecode.response.AddressesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private LocalidadeService localidadeService;

    @Mock
    private AddressMapper mapper;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {

        AddressRequest request = new AddressRequest();
        Address address = new Address();
        AddressResponse response = new AddressResponse();

        when(localidadeService.getAddressByCep(request)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(mapper.toResponse(address)).thenReturn(response);

        AddressResponse result = addressService.create(request);

        assertEquals(response, result);
        verify(localidadeService, times(1)).getAddressByCep(request);
        verify(addressRepository, times(1)).save(address);
        verify(mapper, times(1)).toResponse(address);
    }

    @Test
    public void testCreateBatch() {

        AddressesRequest request = new AddressesRequest();
        AddressRequest addressRequest = new AddressRequest();
        Address address = new Address();

        request.setAddresses(List.of(addressRequest));

        when(localidadeService.getAddressByCep(addressRequest)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(mapper.toAddresses(anyList())).thenReturn(new AddressesResponse());

        AddressesResponse result = addressService.createBatch(request);

        assertNotNull(result);
        verify(localidadeService, times(1)).getAddressByCep(addressRequest);
        verify(addressRepository, times(1)).save(address);
        verify(mapper, times(1)).toAddresses(anyList());
    }
}