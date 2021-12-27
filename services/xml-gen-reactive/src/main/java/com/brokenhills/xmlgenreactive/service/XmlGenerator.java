package com.brokenhills.xmlgenreactive.service;

import com.brokenhills.xmlgenreactive.model.XmlDocument;
import com.brokenhills.xmlgenreactive.model.XmlGenParams;
import com.brokenhills.xmlgenreactive.model.XmlMarshaller;
import org.bson.internal.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class XmlGenerator {

    private final Map<String, XmlDocument> xmlDocumentMap;

    @Autowired
    public XmlGenerator(Map<String, XmlDocument> xmlDocumentMap) {
        this.xmlDocumentMap = xmlDocumentMap;
    }

    public Mono<String> generate(XmlGenParams params) {
        Object xmlDocument = xmlDocumentMap.get(params.getSchemaName()).withParams(params).build();
        byte[] doc = new XmlMarshaller(xmlDocument).marshal();
        return doc != null ? Mono.just(Base64.encode(doc)) : Mono.empty();
    }
}
