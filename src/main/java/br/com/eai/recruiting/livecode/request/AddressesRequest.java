package br.com.eai.recruiting.livecode.request;

import java.util.List;

public class AddressesRequest {

    private List<AddressRequest> addresses;

    public List<AddressRequest> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressRequest> addresses) {
        this.addresses = addresses;
    }
}
