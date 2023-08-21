package br.com.eai.recruiting.livecode.mappers;

import br.com.eai.recruiting.livecode.domain.Address;
import br.com.eai.recruiting.livecode.request.AddressRequest;
import br.com.eai.recruiting.livecode.response.AddressResponse;
import br.com.eai.recruiting.livecode.response.AddressesResponse;
import br.com.eai.recruiting.livecode.response.ApiCepResponse;
import br.com.eai.recruiting.livecode.response.ViaCepResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResponse toResponse(Address address);

    default Address toAddressEntity(AddressRequest addressRequest, ApiCepResponse apiCepResponse) {
        final var addressEntity = new Address();
        addressEntity.setStreet(apiCepResponse.getAddress());
        addressEntity.setNumber(addressRequest.getNumber());
        addressEntity.setNeighborhood(apiCepResponse.getDistrict());
        addressEntity.setCity(apiCepResponse.getCity());
        addressEntity.setState(apiCepResponse.getState());
        addressEntity.setZipCode(addressRequest.getZipCode());
        return addressEntity;
    }

    default Address toAddressEntity(AddressRequest addressRequest, ViaCepResponse viaCepResponse) {
        final var addressEntity = new Address();
        addressEntity.setStreet(viaCepResponse.getLogradouro());
        addressEntity.setNumber(addressRequest.getNumber());
        addressEntity.setNeighborhood(viaCepResponse.getBairro());
        addressEntity.setCity(viaCepResponse.getLocalidade());
        addressEntity.setState(viaCepResponse.getUf());
        addressEntity.setZipCode(addressRequest.getZipCode());
        return addressEntity;
    }

    default AddressesResponse toAddresses(List<Address> addresses) {
        final var response = new AddressesResponse();
        response.setAddresses(addresses.stream().map(this::toResponse).collect(Collectors.toList()));
        return response;
    }

    default AddressesResponse toAddresses(Page<Address> addresses) {
        final var response = new AddressesResponse();
        response.setAddresses(addresses.getContent().stream().map(this::toResponse).collect(Collectors.toList()));
        return response;
    }
}
