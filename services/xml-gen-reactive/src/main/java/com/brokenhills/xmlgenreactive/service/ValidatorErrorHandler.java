package com.brokenhills.xmlgenreactive.service;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValidatorErrorHandler implements ErrorHandler {

    private final List<SAXParseException> errorList = new ArrayList<>();

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        errorList.add(exception);
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        errorList.add(exception);
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        errorList.add(exception);
    }

    public boolean hasErrors() {
        return !errorList.isEmpty();
    }

    public String getErrors() {
        if (!hasErrors()) {
            return "OK";
        }
        return errorList.stream()
                .map(SAXException::getMessage)
                .collect(Collectors.joining("\t"));
    }
}
