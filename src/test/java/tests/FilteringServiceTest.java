package tests;

import client.ApiClient;
import client.response.Expect;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.util.List;

@MicronautTest
class FilteringServiceTest {

    @Inject
    ApiClient api;

    @Test
    void shouldGetExcludedWords() {
        List<String> actual = this.api.getExcluded().execute(Expect.status(HttpStatus.OK));
        Assertions.assertThat(actual)
                .isNotEmpty()
                .doesNotContainNull();
    }

    @Test
    void shouldNotGetAnyDigits() {
        List<String> actual = this.api.getExcluded().execute(Expect.status(HttpStatus.OK));

        Condition<String> noDigits =
                new Condition<>(s -> !s.matches(".*\\d.*"), "no digits");

        Assertions.assertThat(actual).have(noDigits);
    }

    @Test
    void shouldNotGetDuplicates() {
        List<String> actual = this.api.getExcluded().execute(Expect.status(HttpStatus.OK));
        Assertions.assertThat(actual).doesNotHaveDuplicates();
    }

    @Test
    void shouldContainOnlyLetters() {
        List<String> actual = this.api.getExcluded().execute(Expect.status(HttpStatus.OK));

        Condition<String> onlyLetters =
                new Condition<>(s -> s.chars().allMatch(Character::isLetter), "only letters");

        Assertions.assertThat(actual).have(onlyLetters);
    }
}
