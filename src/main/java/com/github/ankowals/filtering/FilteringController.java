package com.github.ankowals.filtering;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;

import java.util.List;

@Validated
@Controller("/excluded-values")
public class FilteringController {

    private final FilteringServiceImpl filteringService;

    public FilteringController(FilteringServiceImpl filteringService) {
        this.filteringService = filteringService;
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<String>> fetchExcludedPersons() {
        return HttpResponse.status(HttpStatus.OK).body(this.filteringService.fetchExcluded());
    }
}
