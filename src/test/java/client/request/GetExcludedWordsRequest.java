package client.request;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

import java.util.List;

public class GetExcludedWordsRequest implements ResponseSpecificationAcceptingExecutableRequest<List<String>> {

    private final RequestSpecBuilder requestSpecBuilder;

    public GetExcludedWordsRequest(RequestSpecBuilder requestSpecBuilder) {
        this.requestSpecBuilder = requestSpecBuilder;
        this.requestSpecBuilder.setContentType("application/json");
    }

    @Override
    public Response execute() {
        return RestAssured.given()
                .spec(this.requestSpecBuilder.build())
                .when()
                .get("/excluded-values");
    }

    @Override
    public List<String> execute(ResponseSpecification responseSpecification) {
        return this.execute()
                .then()
                .spec(responseSpecification)
                .extract().body()
                .jsonPath()
                .getList(".", String.class);
    }
}
