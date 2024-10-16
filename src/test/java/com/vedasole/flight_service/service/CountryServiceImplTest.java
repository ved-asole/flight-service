package com.vedasole.flight_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedasole.flight_service.entity.Country;
import com.vedasole.flight_service.exception.ResourceNotFoundException;
import com.vedasole.flight_service.payload.CountryDto;
import com.vedasole.flight_service.repository.CountryRepo;
import com.vedasole.flight_service.service.service_interface.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
class CountryServiceImplTest {

    @MockBean
    private CountryRepo countryRepo;

    @Autowired
    CountryService countryService;

    @Autowired
    public ObjectMapper objectMapper;

    @Test
    void whenValidCountryDtoIsPassedToAddCountry_thenCountryIsAddedSuccessfully() {
        // given
        CountryDto countryDto = CountryDto.builder()
                .countryId(UUID.randomUUID().toString())
                .countryName("India")
                .countryIso2("IN")
                .continent("Asia")
                .countryIso3("IND")
                .countryIsoNumeric(356)
                .capital("New Delhi")
                .currencyCode("INR")
                .currencyName("Indian Rupee")
                .fipsCode("IN")
                .phonePrefix("91")
                .population(1380004385L)
                .build();
        Country expectedCountry = objectMapper.convertValue(countryDto, Country.class);

        when(countryRepo.save(expectedCountry)).thenReturn(expectedCountry);

        // when
        CountryDto actualCountryDto = countryService.addCountry(countryDto);
        log.info("actualCountryDto: {}", actualCountryDto);

        // then
        assertThat(actualCountryDto).usingRecursiveComparison()
                .ignoringFields("countryId", "createdDate", "updatedDate")
                .isEqualTo(countryDto);
        verify(countryRepo, times(1)).save(any(Country.class));
    }

    @Test
    void whenAddingCountryWithDuplicateCountryName_thenThrowException() {
        // given
        CountryDto countryDto = CountryDto.builder()
                .countryId(UUID.randomUUID().toString())
                .capital("The Valley")
                .currencyCode("XCD")
                .fipsCode("AV")
                .countryIso2("ZA")
                .countryIso3("AIA")
                .continent("NA")
                .countryName("Anguilla")
                .currencyName("Dollar")
                .countryIsoNumeric(660)
                .phonePrefix("+1-264")
                .population(13254L)
                .build();

        when(countryRepo.existsByCountryName(countryDto.getCountryName())).thenReturn(true);

        // when
        Throwable exception = catchThrowable(() -> countryService.addCountry(countryDto));

        // then
        assertThat(exception).isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("Country name already exists: Anguilla");
        verify(countryRepo, never()).save(any());
    }

    @Test
    void whenAddingCountryWithDuplicateCountryIso2_thenThrowException() {
        // given
        CountryDto countryDto = CountryDto.builder()
                .countryId("66fcd4647feba23f124549f3")
                .capital("The Valley")
                .currencyCode("XCD")
                .fipsCode("AV")
                .countryIso2("AI")
                .countryIso3("AIA")
                .continent("NA")
                .countryName("Anguilla")
                .currencyName("Dollar")
                .countryIsoNumeric(660)
                .phonePrefix("+1-264")
                .population(13254L)
                .build();
        Country existingCountry = objectMapper.convertValue(countryDto, Country.class);
        existingCountry.setCountryId(UUID.randomUUID().toString());

        when(countryRepo.existsByCountryIso2(countryDto.getCountryIso2())).thenReturn(true);

        // when
        Throwable exception = catchThrowable(() -> countryService.addCountry(countryDto));

        // then
        assertThat(exception).isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("Country ISO2 already exists: AI");
        verify(countryRepo, never()).save(any(Country.class));
    }

    @Test
    void whenUpdatingCountry_thenCountryIsUpdatedSuccessfully() {
        // given
        String existingCountryId = "existing-country-id";
        Country existingCountry = Country.builder()
                .countryId(existingCountryId)
                .countryName("Existing Country")
                .build();
        CountryDto countryDto = CountryDto.builder()
                .countryName("New Country")
                .countryId(existingCountryId)
                .build();

        when(countryRepo.findById(existingCountryId)).thenReturn(Optional.of(existingCountry));
        when(countryRepo.save(existingCountry)).thenReturn(existingCountry);

        // when
        CountryDto updatedCountryDto = countryService.updateCountry(existingCountryId, countryDto);

        // then
        assertThat(updatedCountryDto).usingRecursiveComparison()
                .ignoringFields("countryName")
                .isEqualTo(countryDto);
        verify(countryRepo, times(1)).save(existingCountry);

    }

    @Test
    void whenUpdatingCountryWithNonExistingCountryId_thenThrowException() {
        // given
        String nonExistingCountryId = "non-existing-country-id";
        CountryDto countryDto = CountryDto.builder()
                .countryName("New Country")
                .countryId(nonExistingCountryId)
                .build();

        when(countryRepo.findById(nonExistingCountryId)).thenReturn(Optional.empty());

        // when
        Throwable exception = catchThrowable(() -> countryService.updateCountry(nonExistingCountryId, countryDto));

        // then
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Country not found with id: " + nonExistingCountryId);
        verify(countryRepo, never()).save(any(Country.class));
    }
    
    @Test
    void whenDeletingCountry_thenCountryIsRemovedFromDatabase() {
        // given
        String existingCountryId = "existing-country-id";
        Country existingCountry = Country.builder()
                .countryId(existingCountryId)
                .countryName("Existing Country")
                .build();
        when(countryRepo.findById(existingCountryId)).thenReturn(Optional.of(existingCountry));

        // when
        CountryDto countryDto = countryService.deleteCountry(existingCountryId);

        // then
        verify(countryRepo, times(1)).delete(existingCountry);
        assertThat(countryDto).isEqualTo(objectMapper.convertValue(existingCountry, CountryDto.class));
    }

    @Test
    void whenDeletingNonExistingCountry_thenThrowException() {
        // given
        String nonExistingCountryId = "non-existing-country-id";
        when(countryRepo.findById(nonExistingCountryId)).thenReturn(Optional.empty());

        // when
        Throwable exception = catchThrowable(() -> countryService.deleteCountry(nonExistingCountryId));

        // then
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Country not found with id: " + nonExistingCountryId);
        verify(countryRepo, never()).delete(any(Country.class));

    }

    @Test
    void whenGettingCountryById_thenReturnCountry() {
        // given
        String existingCountryId = "existing-country-id";
        Country existingCountry = Country.builder()
                .countryId(existingCountryId)
                .countryName("Existing Country")
                .build();
        when(countryRepo.findById(existingCountryId)).thenReturn(Optional.of(existingCountry));

        // when
        CountryDto countryDto = countryService.getCountryById(existingCountryId);

        // then
        assertThat(countryDto).isEqualTo(objectMapper.convertValue(existingCountry, CountryDto.class));
    }

    @Test
    void whenGettingCountryByIdWithNonExistingCountryId_thenThrowException() {
        // given
        String nonExistingCountryId = "non-existing-country-id";
        when(countryRepo.findById(nonExistingCountryId)).thenReturn(Optional.empty());

        // when
        Throwable exception = catchThrowable(() -> countryService.getCountryById(nonExistingCountryId));

        // then

        assertThat(exception).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Country not found with id: " + nonExistingCountryId);

    }

    @Test
    void whenGettingAllCountries_thenReturnAllCountries() {
        // given
        Country country1 = Country.builder()
                .countryId("country-id")
                .countryName("Country 1")
                .build();
        Country country2 = Country.builder()
                .countryId("country-id")
                .countryName("Country 2")
                .build();

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "countryId");
        when(countryRepo.findAll(pageable)).thenReturn(new PageImpl<>(List.of(country1, country2)));

        // when
        Page<CountryDto> countryDtoPage = countryService.getAllCountries(0, 10, "countryId", "ASC");

        // then
        assertThat(countryDtoPage.getContent()).hasSize(2);
        assertThat(countryDtoPage.getContent().get(0)).isEqualTo(objectMapper.convertValue(country1, CountryDto.class));
        assertThat(countryDtoPage.getContent().get(1)).isEqualTo(objectMapper.convertValue(country2, CountryDto.class));
        verify(countryRepo, times(1)).findAll(pageable);

        }

}