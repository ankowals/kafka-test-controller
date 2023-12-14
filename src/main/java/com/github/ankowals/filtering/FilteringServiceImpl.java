package com.github.ankowals.filtering;

import jakarta.inject.Singleton;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.stream.IntStream;

@Singleton
public class FilteringServiceImpl implements FilteringService {

    @Override
    public List<String> fetchExcluded() {
        return IntStream.range(1, RandomUtils.nextInt(5,15))
                .mapToObj(n -> RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(8,16)))
                .toList();
    }
}
