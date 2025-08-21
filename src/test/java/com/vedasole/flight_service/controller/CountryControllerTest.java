package com.vedasole.flight_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedasole.flight_service.exception.ResourceNotFoundException;
import com.vedasole.flight_service.payload.ApiResponse;
import com.vedasole.flight_service.payload.CountryDto;
import com.vedasole.flight_service.service.service_interface.CountryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CountryController.class)
@AutoConfigureDataMongo
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should add/create a country successfully")
    void addCountry() throws Exception {
        // given
        CountryDto countryDto = CountryDto.builder()
                .countryId("1")
                .countryName("Test Country")
                .countryIso2("TC")
                .countryIso3("TCT")
                .capital("Test Capital")
                .continent("Test Continent")
                .countryIsoNumeric(123)
                .currencyCode("TCD")
                .currencyName("Test Currency")
                .fipsCode("TF123")
                .phonePrefix("123")
                .population(10000L)
                .build();

        // when
        when(countryService.addCountry(countryDto)).thenReturn(countryDto);

        // then
        mockMvc.perform(post("/api/v1/countries")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(countryDto))
        ).andExpectAll(
                status().isCreated(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(objectMapper.writeValueAsString(countryDto))
        );
    }

    @Test
    @DisplayName("Should throw exception when adding/creating a country with existing id")
    void addCountryWithExistingId() throws Exception {
        // given
        CountryDto countryDto = CountryDto.builder()
                .countryId("1")
                .countryName("Test Country")
                .countryIso2("TC")
                .countryIso3("TCT")
                .capital("Test Capital")
                .continent("Test Continent")
                .countryIsoNumeric(123)
                .currencyCode("TCD")
                .currencyName("Test Currency")
                .fipsCode("TF123")
                .phonePrefix("123")
                .population(10000L)
                .build();

        // when
        when(countryService.addCountry(countryDto)).thenThrow(new RuntimeException("Country already exists"));

        // then
        mockMvc.perform(post("/api/v1/countries")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(countryDto))
        ).andExpectAll(
                status().isInternalServerError(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(objectMapper.writeValueAsString(
                                new ApiResponse("RuntimeException", "Country already exists", false)
                        )
                )
        );
    }

    @Test
    @DisplayName("Should update a country successfully")
    void updateCountry() throws Exception {
        CountryDto countryDto = CountryDto.builder()
                .countryId("1")
                .countryName("Updated Country")
                .countryIso2("UC")
                .countryIso3("UCT")
                .capital("Updated Capital")
                .continent("Updated Continent")
                .countryIsoNumeric(321)
                .currencyCode("UCD")
                .currencyName("Updated Currency")
                .fipsCode("UF123")
                .phonePrefix("321")
                .population(20000L)
                .build();
        when(countryService.updateCountry("1", countryDto)).thenReturn(countryDto);
        mockMvc.perform(put("/api/v1/countries/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(countryDto)))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(objectMapper.writeValueAsString(countryDto))
                );
    }

    @Test
    @DisplayName("Should throw exception when updating a non-existing country")
    void updateNonExistingCountry() throws Exception {
        CountryDto countryDto = CountryDto.builder()
                .countryId("999")
                .countryName("Non Existing Country")
                .countryIso2("NE")
                .countryIso3("NEX")
                .capital("Non Existing Capital")
                .continent("Non Existing Continent")
                .countryIsoNumeric(999)
                .currencyCode("NED")
                .currencyName("Non Existing Currency")
                .fipsCode("NEF12")
                .population(10000L)
                .build();
        when(countryService.updateCountry("999", countryDto)).thenThrow(new ResourceNotFoundException("Country", "id", "999"));
        mockMvc.perform(put("/api/v1/countries/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(countryDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should delete a country successfully")
    void deleteCountry() throws Exception {
        CountryDto countryDto = CountryDto.builder()
                .countryId("1")
                .countryName("Test Country")
                .build();
        when(countryService.deleteCountry("1")).thenReturn(countryDto);
        mockMvc.perform(delete("/api/v1/countries/1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(objectMapper.writeValueAsString(countryDto))
                );
    }

    @Test
    @DisplayName("Should throw exception when deleting a non-existing country")
    void deleteNonExistingCountry() throws Exception {
        when(countryService.deleteCountry("999")).thenThrow(new ResourceNotFoundException("Country", "id", "999"));
        mockMvc.perform(delete("/api/v1/countries/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should get a country by id successfully")
    void getCountryById() throws Exception {
        CountryDto countryDto = CountryDto.builder()
                .countryId("1")
                .countryName("Test Country")
                .build();
        when(countryService.getCountryById("1")).thenReturn(countryDto);
        mockMvc.perform(get("/api/v1/countries/1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(objectMapper.writeValueAsString(countryDto))
                );
    }

    @Test
    @DisplayName("Should throw exception when getting a non-existing country by id")
    void getNonExistingCountryById() throws Exception {
        when(countryService.getCountryById("999")).thenThrow(new ResourceNotFoundException("Country", "id", "999"));
        mockMvc.perform(get("/api/v1/countries/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should get all countries paginated")
    void getAllCountries() throws Exception {
        CountryDto countryDto1 = CountryDto.builder().countryId("1").countryName("Country1").build();
        CountryDto countryDto2 = CountryDto.builder().countryId("2").countryName("Country2").build();
        PageImpl<CountryDto> page = new PageImpl<>(List.of(countryDto1, countryDto2));
        when(countryService.getAllCountries(0, 10, "countryName", "asc")).thenReturn(page);
        mockMvc.perform(get("/api/v1/countries?page=0&size=10&sortBy=countryName"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    @DisplayName("Should fail validation when creating country with missing required fields")
    void addCountryWithMissingFields() throws Exception {
        CountryDto countryDto = CountryDto.builder()
                .countryId("1")
                // missing countryName, countryIso2, countryIso3, etc.
                .build();
        mockMvc.perform(post("/api/v1/countries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(countryDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should fail validation when updating country with invalid data")
    void updateCountryWithInvalidData() throws Exception {
        CountryDto countryDto = CountryDto.builder()
                .countryId("1")
                .countryName("") // invalid: empty name
                .countryIso2("T") // invalid: too short
                .countryIso3("TTTT") // invalid: too long
                .capital("") // invalid: empty
                .continent("") // invalid: empty
                .countryIsoNumeric(-1) // invalid: negative
                .currencyCode("T") // invalid: too short
                .currencyName("") // invalid: empty
                .fipsCode("")
                .phonePrefix("")
                .population(-100L) // invalid: negative
                .build();
        mockMvc.perform(put("/api/v1/countries/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(countryDto)))
                .andExpect(status().isBadRequest());
    }
}