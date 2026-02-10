package br.com.erudio.proxy;

import br.com.erudio.dto.ExchangeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "exchange-service", url = "${service.exchange-service.base-url}")
public interface IExchangeProxy {
    @GetMapping(value = "/{amount}/{from}/{to}")
    public ExchangeDTO getExchange(
            @PathVariable("amount") BigDecimal amount,
            @PathVariable("from") String from,
            @PathVariable("to") String to
    );
}
