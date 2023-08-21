package br.com.eai.recruiting.livecode.clients;

import br.com.eai.recruiting.livecode.response.ApiCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://cdn.apicep.com/file/apicep", name = "apicep")
public interface ApiCepClient {
    @GetMapping("/{cep}")
    ApiCepResponse getByZipCode(@PathVariable final String cep);
}
