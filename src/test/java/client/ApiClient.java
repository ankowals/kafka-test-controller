package client;

import client.request.GetExcludedWordsRequest;
import io.restassured.builder.RequestSpecBuilder;

import java.util.function.Supplier;

public class ApiClient {

    private final Supplier<RequestSpecBuilder> requestSpecBuilderSupplier;

    public ApiClient(Supplier<RequestSpecBuilder> requestSpecBuilderSupplier) {
        this.requestSpecBuilderSupplier = requestSpecBuilderSupplier;
    }

    public GetExcludedWordsRequest getExcluded() {
        return new GetExcludedWordsRequest(this.requestSpecBuilderSupplier.get());
    }
}
