package br.com.eai.recruiting.livecode.response;

import java.util.List;

public class AddressesResponse {

    private List<AddressResponse> addresses;

    public List<AddressResponse> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressResponse> addresses) {
        this.addresses = addresses;
    }
}
