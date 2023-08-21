package br.com.eai.recruiting.livecode.controller;

import br.com.eai.recruiting.livecode.request.AddressRequest;
import br.com.eai.recruiting.livecode.request.AddressesRequest;
import br.com.eai.recruiting.livecode.response.AddressResponse;
import br.com.eai.recruiting.livecode.response.AddressesResponse;
import br.com.eai.recruiting.livecode.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(final AddressService addressService) {
        this.addressService = addressService;
    }

    /*
        TODO: Cadastrar um novo endereço e busca conforme a versao abaixo:
         v1 - viacep => https://viacep.com.br/
         v2 - correiosapi => https://apicep.com/api-de-consulta/
     */
    @PostMapping
    public ResponseEntity<AddressResponse> create(@RequestBody AddressRequest addressRequest) {
        return ResponseEntity.ok().body(addressService.create(addressRequest));
    }

    /*
        TODO: CRIAÇAO DE ENDEREÇOS EM LOTE(LISTA) E NAO PODE TER ENDEREÇO DUPLICADO NA LISTA!
         CASO EXISTA, RETORNAR ERRO
     */
    @PostMapping("/create_batch")
    public ResponseEntity<AddressesResponse> creationBatch(@RequestBody AddressesRequest addressesRequest) {
        return ResponseEntity.ok(addressService.createBatch(addressesRequest));
    }


    /*
        TODO: Buscar todos os endereços cadastrados no BD pelo CEP informado
         e com paginação
     */
    @GetMapping
    public ResponseEntity<AddressesResponse> getAllByZipCode(@RequestParam String zipCode,
                                                             @RequestParam(defaultValue = "0") Integer pageCurrent,
                                                             @RequestParam(defaultValue = "10") Integer limit) {

        return ResponseEntity.ok(addressService.findAllByZipCode(zipCode, pageCurrent, limit));
    }


}
