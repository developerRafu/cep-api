package br.com.eai.recruiting.livecode.clients;

import br.com.eai.recruiting.livecode.response.ViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br", name = "viacep")
public interface ViaCepClient {

    @GetMapping("/ws/{cep}/json")
    ViaCepResponse getByZipCode(@PathVariable final String cep);
}
