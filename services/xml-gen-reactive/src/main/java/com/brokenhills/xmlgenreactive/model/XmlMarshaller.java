package com.brokenhills.xmlgenreactive.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class XmlMarshaller {

    private final Object document;

    public XmlMarshaller(Object document) {
        this.document = document;
    }

    public byte[] marshal() {
        try (ByteArrayOutputStream xmlStringOutputStream = new ByteArrayOutputStream()) {
            Marshaller marshaller = JAXBContext.newInstance(document.getClass()).createMarshaller();
            marshaller.marshal(document, new StreamResult(xmlStringOutputStream));
            return xmlStringOutputStream.toByteArray();
        } catch (JAXBException | IOException e) {
            throw new RuntimeException("Cannot create xml from object", e);
        }
    }
}
