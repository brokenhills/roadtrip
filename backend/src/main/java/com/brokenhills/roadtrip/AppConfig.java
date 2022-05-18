package com.brokenhills.roadtrip;

import com.brokenhills.roadtrip.entities.Workflow;
import com.brokenhills.roadtrip.services.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableJpaAuditing
public class AppConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RepresentationModelProcessor<PagedModel<EntityModel<Workflow>>>
    workflowProcessor(EntityLinks links) {
        return new RepresentationModelProcessor<PagedModel<EntityModel<Workflow>>>() {
            @Override
            public PagedModel<EntityModel<Workflow>> process(
                    PagedModel<EntityModel<Workflow>> resource) {
                resource.add(
                        links.linkFor(Workflow.class)
                                .slash("recent")
                                .withRel("recent"));
                return resource;
            }
        };
    }
}
