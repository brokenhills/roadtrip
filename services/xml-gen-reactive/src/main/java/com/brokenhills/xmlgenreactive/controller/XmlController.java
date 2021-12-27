package com.brokenhills.xmlgenreactive.controller;

import com.brokenhills.xmlgenreactive.model.GenerationResult;
import com.brokenhills.xmlgenreactive.model.ValidationRequest;
import com.brokenhills.xmlgenreactive.model.ValidationResult;
import com.brokenhills.xmlgenreactive.model.XmlGenParams;
import com.brokenhills.xmlgenreactive.service.XmlGenerator;
import com.brokenhills.xmlgenreactive.service.XmlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/private")
public class XmlController {

    private final XmlValidator validator;
    private final XmlGenerator generator;

    @Autowired
    public XmlController(XmlValidator validator, XmlGenerator generator) {
        this.validator = validator;
        this.generator = generator;
    }

    @PostMapping("/validate")
    public Mono<ValidationResult> validateXml(@RequestBody Mono<ValidationRequest> request) {
        return request.flatMap(r -> validator.validate(r.getXmlName(), r.getXmlBase64()));
    }

    @PostMapping("/generate")
    public Mono<GenerationResult> generateXml(@RequestBody Mono<XmlGenParams> paramsMono) {
        return paramsMono.flatMap(p -> generator.generate(p)
                .map(g -> GenerationResult.builder().xmlBase64(g).message("OK").build()));
    }
}
