package br.com.erudio.controller;

import br.com.erudio.dto.ExchangeDTO;
import br.com.erudio.enviroment.InstanceInformationService;
import br.com.erudio.model.Book;
import br.com.erudio.proxy.IExchangeProxy;
import br.com.erudio.repository.IBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping(value = "book-service")
@Slf4j
public class BookController {
    private final String FROM_CURRENCY = "USD";

    @Autowired
    private InstanceInformationService instanceInformationService;

    @Autowired
    private IExchangeProxy exchangeProxy;

    @Autowired
    private IBookRepository bookRepository;

    @GetMapping(value = "/{id}/{currency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findBook(
            @PathVariable("id")
            Long id,
            @PathVariable("currency")
            String currency
    ){
        try {
            log.info("Chamada recebida");
            var port = instanceInformationService.retrieveServerPort();

            var book = bookRepository.findById(id).orElseThrow();

            log.info("Fazendo chamada para o serviço de exchange");
            ExchangeDTO exchange = exchangeProxy.getExchange(book.getPrice(), FROM_CURRENCY, currency);
            log.info("Livro retornado com sucesso");
            book.setEnviroment(String.format("PORT: %s", port));
            book.setPrice(exchange.getConvertedValue());
            book.setCurrency(currency);

            return ResponseEntity
                    .ok(book);
        }
        catch (Exception e) {
            log.error("Ocorreu um erro durante a execução da busca: {}", e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(String.format("ERRO NA APLICAÇÃO: {%s}", e.getMessage()));
        }
    }
}
