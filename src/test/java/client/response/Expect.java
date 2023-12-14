package client.response;

import io.micronaut.http.HttpStatus;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;

import java.util.List;
import java.util.function.Function;

public class Expect {

    public static ResponseSpecification status(HttpStatus httpStatus) {
        return new ResponseSpecBuilder()
                .expectStatusCode(httpStatus.getCode())
                .build();
    }

    public static Function<ValidatableResponse, List<ErrorDto>> error(HttpStatus httpStatus) {
        return validatableResponse -> validatableResponse.statusCode(httpStatus.getCode())
                .extract().body()
                .jsonPath()
                .getList("_embedded.errors", ErrorDto.class);
    }
}
