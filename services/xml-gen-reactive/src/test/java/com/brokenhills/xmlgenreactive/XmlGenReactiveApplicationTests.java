package com.brokenhills.xmlgenreactive;

import com.brokenhills.xmlgenreactive.model.ValidationResult;
import com.brokenhills.xmlgenreactive.model.XsdSchema;
import com.brokenhills.xmlgenreactive.repo.XsdRepository;
import com.brokenhills.xmlgenreactive.service.XmlValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class XmlGenReactiveApplicationTests {

	private static final byte[] XSD;
	private static final String XML_OK;
	private static final String XML_BAD;

	@MockBean
	private XsdRepository xsdRepository;

	private XmlValidator validator;

	static  {
		try {
			XSD = Files.readAllBytes(Paths.get(XmlGenReactiveApplication.class.getResource("workflow.xsd").toURI()));
			XML_OK = new String(Files.readAllBytes(Paths.get(XmlGenReactiveApplication.class.getResource("workflow.xml").toURI())));
			XML_BAD = new String(Files.readAllBytes(Paths.get(XmlGenReactiveApplication.class.getResource("workflow-bad.xml").toURI())));
		} catch (IOException | URISyntaxException e) {
			log.error("Cannot find test XSD-schema!");
			throw new RuntimeException(e);
		}
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void validationOkTest() {
		log.info("TEST: validate correct xml against xsd schema.");
		String name = "workflow";
		String base64Xsd = Base64.getEncoder().encodeToString(XSD);
		Mockito.when(xsdRepository.findByXsdName(name))
				.thenReturn(Mono.just(XsdSchema.builder()
						.xsdName("workflow").xsdBase64(base64Xsd).build()));
		validator = new XmlValidator(xsdRepository);
		StepVerifier.create(validator.validate(name, XML_OK))
				.expectNext(ValidationResult.builder().result(true).message("OK").build())
				.verifyComplete();
	}

	@Test
	public void validationFailsTest() {
		String errors = "cvc-pattern-valid: Value '1' is not facet-valid with respect to pattern " +
				"'[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}' for type 'uuid'.\tcvc-attribute.3: " +
				"The value '1' of attribute 'id' on element 'Workflow' is not valid with respect to its type, 'uuid'.\t" +
				"cvc-datatype-valid.1.2.1: '2020.05.30' is not a valid value for 'dateTime'.\tcvc-attribute.3: " +
				"The value '2020.05.30' of attribute 'created' on element 'Workflow' is not valid with respect " +
				"to its type, 'dateTime'.";
		log.info("TEST: validate incorrect xml against xsd schema.");
		String name = "workflow";
		String base64Xsd = Base64.getEncoder().encodeToString(XSD);
		Mockito.when(xsdRepository.findByXsdName(name))
				.thenReturn(Mono.just(XsdSchema.builder()
						.xsdName("workflow").xsdBase64(base64Xsd).build()));
		validator = new XmlValidator(xsdRepository);
		StepVerifier.create(validator.validate(name, XML_BAD))
				.expectNext(ValidationResult.builder().result(false).message(errors).build())
				.verifyComplete();
	}

	@Test
	public void validationInvalidSchemaNameTest() {
		log.info("TEST: validate xml against xsd with invalid schema name.");
		String name = "invalid";
		Mockito.when(xsdRepository.findByXsdName(name))
				.thenReturn(Mono.empty());
		validator = new XmlValidator(xsdRepository);
		StepVerifier.create(validator.validate(name, XML_BAD))
				.expectNext(ValidationResult.builder().result(false).message("No such schema!").build())
				.verifyComplete();
	}
}
