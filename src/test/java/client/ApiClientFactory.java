package client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.runtime.server.EmbeddedServer;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import jakarta.inject.Singleton;

import java.net.URI;

@Factory
public class ApiClientFactory {

    @Bean
    @Primary
    @Singleton
    public ApiClient client(EmbeddedServer embeddedServer) {
        return new ApiClient(() -> this.createBaseRequestSpec(embeddedServer.getURI()));
    }

    private RequestSpecBuilder createBaseRequestSpec(URI baseUri) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .setConfig(RestAssuredConfig.config().objectMapperConfig(
                        ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(
                                (cls, charset) -> this.createMapper())));
    }

    private ObjectMapper createMapper() {
        ObjectMapper om = new ObjectMapper().findAndRegisterModules();

        om.registerModule(new JavaTimeModule());
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return om;
    }
}
