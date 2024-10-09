package com.vedasole.flight_service.repository;

import com.vedasole.flight_service.config.MongoDBConfig;
import com.vedasole.flight_service.entity.Country;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@Slf4j
@DataMongoTest
@ContextConfiguration(classes = {MongoDBConfig.class})
class CountryRepoTest {

    @Autowired CountryRepo countryRepo;

    @BeforeEach
    void setUp() {
        log.info("Setting up test data...");
        Country country1 = new Country("66fcd4647feba23f124549ef", "Andorra la Vella", "EUR", "AN", "AD", "AND", "EU", "Andorra", "Euro", "20", "376", 84000L);
        Country country2 = new Country("66fcd4647feba23f124549f0", "Abu Dhabi", "AED", "AE", "AE", "ARE", "AS", "United Arab Emirates", "Dirham", "784", "971", 4975593L);
        Country country3 = new Country("66fcd4647feba23f124549f1", "Kabul", "AFN", "AF", "AF", "AFG", "AS", "Afghanistan", "Afghani", "4", "93", 29121286L);
        Country country4 = new Country("66fcd4647feba23f124549f2", "St. John's", "XCD", "AC", "AG", "ATG", "NA", "Antigua and Barbuda", "Dollar", "28", "1-268", 86754L);
        Country country5 = new Country("66fcd4647feba23f124549f3", "The Valley", "XCD", "AV", "AI", "AIA", "NA", "Anguilla", "Dollar", "660", "1-264", 13254L);
        Country country6 = new Country("66fcd4647feba23f124549f4", "Tirana", "ALL", "AL", "AL", "ALB", "EU", "Albania", "Lek", "8", "355", 2986952L);
        List<Country> countries = List.of(country1, country2, country3, country4, country5, country6);
        countryRepo.saveAll(countries)
                .forEach(country -> log.info("Saved initial country: {}", country));
    }

    @AfterEach
    void tearDown() {
        log.info("Tearing down test data...");
        countryRepo.deleteAll();
    }

    @Test
    void whenCountryIsPassedToSave_ThenCountryIsSavedSuccessfully() {
        Country country = new Country();
        country.setCountryName("NewCountry");
        country.setCountryIso2("NC");
        log.info("Country before saving: {}", country);
        Country savedCountry = countryRepo.save(country);
        log.info("Country after saving: {}", savedCountry);
        assertThat(savedCountry).isNotNull();
        assertThat(savedCountry.getCountryId()).isNotNull();
        assertThat(savedCountry.getCountryName()).isEqualTo("NewCountry");
        assertThat(savedCountry.getCountryIso2()).isEqualTo("NC");
        assertThat(savedCountry.getCreatedDate()).isNotNull().isInThePast();
        assertThat(savedCountry.getUpdatedDate()).isNotNull().isInThePast();
    }

    @Test
    void testFindCountryById() {
        Country expected = new Country("66fcd4647feba23f124549ef", "Andorra la Vella", "EUR", "AN", "AD", "AND", "EU", "Andorra", "Euro", "20", "376", 84000L);
        Optional<Country> actual = countryRepo.findById("66fcd4647feba23f124549ef");
        assertThat(actual).isPresent();
        assertThat(actual.get())
                .isExactlyInstanceOf(Country.class)
                .usingRecursiveComparison()
                .ignoringFields("createdDate", "updatedDate")
                .isEqualTo(expected);
        assertThat(actual.get().getUpdatedDate()).isNotNull().isInThePast();
    }

    @Test
    void testUpdateCountry() {
        Optional<Country> country = countryRepo.findById("66fcd4647feba23f124549ef");
        assertThat(country).isPresent();
        Country updatedCountry = country.get();
        updatedCountry.setCountryName("UpdatedCountry");
        countryRepo.save(updatedCountry);
        Optional<Country> updatedCountryFromDb = countryRepo.findById("66fcd4647feba23f124549ef");
        assertThat(updatedCountryFromDb).isPresent();
        assertThat(updatedCountryFromDb.get().getCountryName()).isEqualTo("UpdatedCountry");
    }

    @Test
    void testDeleteCountry() {
        countryRepo.deleteById("1");
        Optional<Country> country = countryRepo.findById("1");
        assertThat(country).isNotPresent();
    }

    @Test
    void existsByCountryId_WithValidId_ReturnsTrue() {
        // Given
        String validId = "66fcd4647feba23f124549ef";

        // When
        boolean exists = countryRepo.existsByCountryId(validId);

        // Then
        assertThat(exists).isTrue();
    }
    
    @Test
    void existsByCountryIso2_WithValidIso2Code_ReturnsTrue() {
        // Given
        String validIso2Code = "AD";
    
        // When
        boolean exists = countryRepo.existsByCountryIso2(validIso2Code);
    
        // Then
        assertThat(exists).isTrue();
    }
    
    @Test
    void existsByCountryIso2_WithInvalidIso2Code_ReturnsFalse() {
        // Given
        String invalidIso2Code = "XX";
    
        // When
        boolean exists = countryRepo.existsByCountryIso2(invalidIso2Code);
    
        // Then
        assertThat(exists).isFalse();
    }
    
    @Test
    void countByCountryIso2AndCountryIdNot_WithValidIso2CodeAndValidCountryId_ReturnsCorrectCount() {
        // Given
        String validIso2Code = "AD";
        String validCountryId = "66fcd4647feba23f124549ef";
    
        // When
        long count = countryRepo.countByCountryIso2AndCountryIdNot(validIso2Code, validCountryId);
    
        // Then
        assertThat(count).isZero();
    }
    
    @Test
    void countByCountryIso2AndCountryIdNot_WithValidIso2CodeAndInvalidCountryId_ReturnsCorrectCount() {
        // Given
        String validIso2Code = "AD";
        String invalidCountryId = "wrong-id";
    
        // When
        long count = countryRepo.countByCountryIso2AndCountryIdNot(validIso2Code, invalidCountryId);
    
        // Then
        assertThat(count).isEqualTo(1);
    }
    
    @Test
    void countByCountryIso2AndCountryIdNot_WithInvalidIso2CodeAndValidCountryId_ReturnsCorrectCount() {
        // Given
        String invalidIso2Code = "XX";
        String validCountryId = "66fcd4647feba23f124549ef";
    
        // When
        long count = countryRepo.countByCountryIso2AndCountryIdNot(invalidIso2Code, validCountryId);
    
        // Then
        assertThat(count).isZero();
    }
    
    @Test
    void countByCountryIso2AndCountryIdNot_WithInvalidIso2CodeAndInvalidCountryId_ReturnsCorrectCount() {
        // Given
        String invalidIso2Code = "XX";
        String invalidCountryId = "wrong-id";
    
        // When
        long count = countryRepo.countByCountryIso2AndCountryIdNot(invalidIso2Code, invalidCountryId);
    
        // Then
        assertThat(count).isZero();
    }
    
    @Test
    void save_WithCountryObjectWithNullCountryId_ShouldSaveSuccessfully() {
        // Given
        Country country = new Country();
        country.setCountryName("NewCountry");
        country.setCountryIso2("NC");
    
        // When
        Country savedCountry = countryRepo.save(country);
    
        // Then
        assertThat(savedCountry).isNotNull();
        assertThat(savedCountry.getCountryId()).isNotNull();
        assertThat(savedCountry.getCountryName()).isEqualTo("NewCountry");
        assertThat(savedCountry.getCountryIso2()).isEqualTo("NC");
        assertThat(savedCountry.getCreatedDate()).isNotNull().isInThePast();
        assertThat(savedCountry.getUpdatedDate()).isNotNull().isInThePast();
    }
    
    @Test
    void save_WithCountryObjectWithExistingCountryName_ShouldThrowDataIntegrityViolationException() {
        // Given
        Country existingCountry = countryRepo.findById("66fcd4647feba23f124549f2").orElseThrow();
        Country countryWithExistingName = Country.builder()
                .countryId(null)
                .countryName(existingCountry.getCountryName())
                .countryIso2("NC")
                .build();
    
        // When
        Throwable exception = catchThrowable(() -> countryRepo.save(countryWithExistingName));
    
        // Then
        assertThat(exception).isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("duplicate key error collection: crimsonSky-flights-db.countries index: idx_country_name dup key: { countryName: \"Antigua and Barbuda\" }");
    }
    
    @Test
    void save_WithCountryObjectWithExistingCountryIso2_ShouldThrowDataIntegrityViolationException() {
        // Given
        Country existingCountry = countryRepo.findById("66fcd4647feba23f124549ef").orElseThrow();
        Country countryWithExistingIso2 = Country.builder()
                .countryId(null)
                .countryName("NewCountry")
                .countryIso2(existingCountry.getCountryIso2())
                .build();

        // When
        Throwable exception = catchThrowable(() -> countryRepo.save(countryWithExistingIso2));

        // Then
        assertThat(exception).isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("duplicate key error collection: crimsonSky-flights-db.countries index: idx_country_iso2 dup key: { countryIso2: \"AD\" }");
    }

    @Test
    void testExistsByCountryName() {
        boolean exists = countryRepo.existsByCountryName("United Arab Emirates");
        assertThat(exists).isTrue();
    }

    @Test
    void testCountByCountryNameAndCountryIdNot() {
        long count = countryRepo.countByCountryNameAndCountryIdNot("Antigua and Barbuda", "wrong-id");
        assertThat(count).isEqualTo(1);
    }

}