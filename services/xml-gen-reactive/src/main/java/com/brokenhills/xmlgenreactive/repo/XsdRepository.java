package com.brokenhills.xmlgenreactive.repo;

import com.brokenhills.xmlgenreactive.model.XsdSchema;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface XsdRepository extends ReactiveCrudRepository<XsdSchema, String> {

    Mono<XsdSchema> findByXsdName(String name);
}
