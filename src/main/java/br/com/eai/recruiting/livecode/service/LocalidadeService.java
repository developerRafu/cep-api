package br.com.eai.recruiting.livecode.service;

import br.com.eai.recruiting.livecode.clients.ApiCepClient;
import br.com.eai.recruiting.livecode.clients.ViaCepClient;
import br.com.eai.recruiting.livecode.domain.Address;
import br.com.eai.recruiting.livecode.mappers.AddressMapper;
import br.com.eai.recruiting.livecode.request.AddressRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LocalidadeService {
    private final ViaCepClient viaCepClient;
    private final ApiCepClient apiCepClient;
    private final AddressMapper mapper;
    private final static Integer VIA_CEP = 1;
    private final static Integer API_CEP = 2;

    @Autowired
    public LocalidadeService(ViaCepClient viaCepClient, ApiCepClient apiCepClient, AddressMapper mapper) {
        this.viaCepClient = viaCepClient;
        this.apiCepClient = apiCepClient;
        this.mapper = mapper;
    }

    public Address getAddressByCep(AddressRequest addressRequest) {
        if (Objects.equals(VIA_CEP, addressRequest.getVersion())) {
            return getAddressViacep(addressRequest);
        }
        if (Objects.equals(API_CEP, addressRequest.getVersion())) {
            return getAddressApiCep(addressRequest);
        }
        throw new IllegalArgumentException("Versão inválida");
    }

    private Address getAddressApiCep(AddressRequest addressRequest) {
        final var apiCepResponse = apiCepClient.getByZipCode(String.format("%s.json", getZipCodeFormatted(addressRequest)));
        return mapper.toAddressEntity(addressRequest, apiCepResponse);
    }

    private static String getZipCodeFormatted(AddressRequest addressRequest) {
        return Optional.ofNullable(addressRequest.getZipCode()).map(zipCode -> {
            if (zipCode.length() == 8) {
                return String.format("%s-%s", zipCode.substring(0, 5), zipCode.substring(5, 8));
            }
            return zipCode;
        }).orElse("");
    }

    private Address getAddressViacep(AddressRequest addressRequest) {
        final var viaCepResponse = viaCepClient.getByZipCode(addressRequest.getZipCode());
        return mapper.toAddressEntity(addressRequest, viaCepResponse);
    }

}
