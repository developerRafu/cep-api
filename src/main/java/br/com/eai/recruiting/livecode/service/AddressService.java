package br.com.eai.recruiting.livecode.service;

import br.com.eai.recruiting.livecode.domain.Address;
import br.com.eai.recruiting.livecode.errors.InvalidRequest;
import br.com.eai.recruiting.livecode.mappers.AddressMapper;
import br.com.eai.recruiting.livecode.repository.AddressRepository;
import br.com.eai.recruiting.livecode.request.AddressRequest;
import br.com.eai.recruiting.livecode.request.AddressesRequest;
import br.com.eai.recruiting.livecode.response.AddressResponse;
import br.com.eai.recruiting.livecode.response.AddressesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final LocalidadeService localidadeService;
    private final AddressMapper mapper;

    @Autowired
    public AddressService(final AddressRepository addressRepository, LocalidadeService localidadeService, AddressMapper mapper) {
        this.addressRepository = addressRepository;
        this.localidadeService = localidadeService;
        this.mapper = mapper;
    }

    /*
        TODO: Cadastrar um novo endereço e busca conforme a versao abaixo:
         v1 - viacep => https://viacep.com.br/
         v2 - correiosapi => https://apicep.com/api-de-consulta/
     */
    public AddressResponse create(AddressRequest addressRequest) {
        return mapper.toResponse(
                addressRepository.save(
                        localidadeService.getAddressByCep(addressRequest)
                )
        );
    }

    public AddressesResponse createBatch(AddressesRequest addressesRequest) {
        final var addressesSize = Optional.ofNullable(addressesRequest.getAddresses())
                .map(List::size)
                .orElseThrow(() -> InvalidRequest.instance);
        final var zipCodeSizes = addressesRequest.getAddresses().stream().map(AddressRequest::getZipCode).collect(Collectors.toSet());
        if (addressesSize != zipCodeSizes.size()) {
            throw InvalidRequest.invalidZipCodes;
        }
        List<CompletableFuture<Address>> futures = addressesRequest.getAddresses().stream()
                .map(addressRequest -> CompletableFuture.supplyAsync(() -> addressRepository.save(localidadeService.getAddressByCep(addressRequest))))
                .collect(Collectors.toList());

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        return allOf.thenApplyAsync(
                ignoredVoid -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        ).thenApplyAsync(mapper::toAddresses).join();
    }

    public AddressesResponse findAllByZipCode(String zipCode, Integer pageCurrent, Integer limit) {
        final var page = PageRequest.of(pageCurrent, limit);
        final var addresses = addressRepository.findAllByZipCode(zipCode, page);
        return mapper.toAddresses(addresses);
    }
}
