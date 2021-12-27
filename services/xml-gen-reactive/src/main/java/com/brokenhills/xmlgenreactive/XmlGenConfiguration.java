package com.brokenhills.xmlgenreactive;

import com.brokenhills.xmlgenreactive.model.WorkflowDocument;
import com.brokenhills.xmlgenreactive.model.XmlDocument;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class XmlGenConfiguration {

    @Bean
    public Map<String, XmlDocument> xmlDocumentMap() {
        return new HashMap<String, XmlDocument>() {{
            put("workflow", new WorkflowDocument());
        }};
    }
}
