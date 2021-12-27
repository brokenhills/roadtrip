package com.brokenhills.xmlgenreactive.controller;

import com.brokenhills.xmlgenreactive.model.XsdSchema;
import com.brokenhills.xmlgenreactive.repo.XsdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/xsd")
public class XsdController {

    private final XsdRepository xsdRepository;

    @Autowired
    public XsdController(XsdRepository xsdRepository) {
        this.xsdRepository = xsdRepository;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<XsdSchema> addSchema(@RequestBody Mono<XsdSchema> xsdSchema) {
        return xsdRepository.saveAll(xsdSchema).next();
    }

    @GetMapping
    public Flux<XsdSchema> getAllSchemas() {
        return xsdRepository.findAll();
    }

    @GetMapping("/{name}")
    public Mono<XsdSchema> getSchemaByName(@PathVariable String name) {
        return xsdRepository.findByXsdName(name);
    }
}
