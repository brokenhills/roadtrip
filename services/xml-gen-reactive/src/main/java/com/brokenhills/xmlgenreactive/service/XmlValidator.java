package com.brokenhills.xmlgenreactive.service;

import com.brokenhills.xmlgenreactive.model.ValidationResult;
import com.brokenhills.xmlgenreactive.repo.XsdRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import reactor.core.publisher.Mono;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bson.internal.Base64;

@Slf4j
@Service
public class XmlValidator {

    private static final SchemaFactory schemaFactory = SchemaFactory
            .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    private final XsdRepository xsdRepository;

    @Autowired
    public XmlValidator(XsdRepository xsdRepository) {
        this.xsdRepository = xsdRepository;
    }

    public Mono<ValidationResult> validate(String name, String base64Xml) {
        return this.xsdRepository.findByXsdName(name)
                .map(xsdSchema -> {
                    try {
                        String schemaString = xsdSchema.getXsdBase64();
                        ValidatorErrorHandler errorHandler = validateXmlBase64String(schemaString, base64Xml);
                        return ValidationResult.builder()
                                .result(!errorHandler.hasErrors())
                                .message(errorHandler.getErrors()).build();
                    } catch (IOException | IllegalArgumentException| SAXException e) {
                        log.error("Validation error.", e);
                        return ValidationResult.builder()
                                .result(false)
                                .message(e.getMessage())
                                .build();
                    }
                })
                .switchIfEmpty(Mono.just(ValidationResult.builder()
                        .result(false)
                        .message("No such schema!")
                        .build()));
    }

    private ValidatorErrorHandler validateXmlBase64String(String schemaString, String base64Xml) throws IOException, SAXException {
        Schema schema;
        try (ByteArrayInputStream xsdInputStream = new ByteArrayInputStream(Base64.decode(schemaString))) {
            schema = schemaFactory.newSchema(new StreamSource(xsdInputStream));
        }
        Validator validator = schema.newValidator();
        ValidatorErrorHandler errorHandler = new ValidatorErrorHandler();
        validator.setErrorHandler(errorHandler);
        try (ByteArrayInputStream xmlInputStream = new ByteArrayInputStream(Base64.decode(base64Xml))) {
            validator.validate(new StreamSource(xmlInputStream));
        }
        return errorHandler;
    }
}
