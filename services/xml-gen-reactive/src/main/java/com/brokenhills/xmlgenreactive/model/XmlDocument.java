package com.brokenhills.xmlgenreactive.model;

public interface XmlDocument {

    XmlDocument withParams(XmlGenParams params);

    Object build();
}
