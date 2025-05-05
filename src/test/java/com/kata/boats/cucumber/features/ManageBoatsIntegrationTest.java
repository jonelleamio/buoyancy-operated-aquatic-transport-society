package com.kata.boats.cucumber.features;


import com.kata.boats.infrastructure.in.dto.BoatDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ManageBoatsIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl(){
        return String.format("http://localhost:%d/api/boats", port);
    }

    private BoatDto createdBoat;

    @Given("the member is signed in")
    public void theMemberIsSignedIn() {
        // Assuming test profile disables security
    }

    @When("boat named {string} with description {string} is added")
    public void boatNamedWithDescriptionIsAdded(String name, String description) {
        BoatDto dto = new BoatDto(null, name, description);
        ResponseEntity<BoatDto> response = restTemplate.postForEntity(baseUrl(), dto, BoatDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        createdBoat = response.getBody();
        assertThat(createdBoat).isNotNull();
        assertThat(createdBoat.name()).isEqualTo(name);
    }

    @Then("the boat {string} appears in the fleet list")
    public void theBoatAppearsInTheFleetList(String name) {
        ResponseEntity<BoatDto[]> response = restTemplate.getForEntity(baseUrl(), BoatDto[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        BoatDto[] boats = response.getBody();
        assertThat(boats).isNotEmpty();
        assertThat(Arrays.stream(boats).map(BoatDto::name)).contains(name);
    }

    @And("the fleet contains at least one boat")
    public void theFleetContainsAtLeastOneBoat() {
        ResponseEntity<BoatDto[]> response = restTemplate.getForEntity(baseUrl(), BoatDto[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        BoatDto[] boats = response.getBody();
        assertThat(boats).isNotEmpty();
    }

    @When("they view the fleet overview")
    public void theyViewTheFleetOverview() {
        // Listing is asserted in following Then
    }

    @Then("they see each boat’s name and description listed")
    public void theySeeEachBoatsNameAndDescriptionListed() {
        ResponseEntity<BoatDto[]> response = restTemplate.getForEntity(baseUrl(), BoatDto[].class);
        BoatDto[] boats = response.getBody();
        assertThat(boats).allSatisfy(b -> {
            assertThat(b.name()).isNotBlank();
            assertThat(b.description()).isNotBlank();
        });
    }

    @Given("the boat {string} exists in the fleet")
    public void theBoatExistsInTheFleet(String name) {
        BoatDto dto = new BoatDto(null, name, "not important detail");
        ResponseEntity<BoatDto> response = restTemplate.postForEntity(baseUrl(), dto, BoatDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        createdBoat = response.getBody();
    }

    @When("they rename the boat to {string} with description {string}")
    public void theyRenameTheBoatToWithDescription(String newName, String newDescription) {
        BoatDto dto = new BoatDto(null, newName, newDescription);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BoatDto> request = new HttpEntity<>(dto, headers);
        String url = baseUrl() + "/" + createdBoat.id();
        ResponseEntity<BoatDto> response = restTemplate.exchange(url, HttpMethod.PUT, request, BoatDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        createdBoat = response.getBody();
        assertThat(createdBoat.name()).isEqualTo(newName);
    }

    @Then("the fleet list shows {string} with {string}")
    public void theFleetListShowsWith(String expectedName, String expectedDescription) {
        ResponseEntity<BoatDto[]> response = restTemplate.getForEntity(baseUrl(), BoatDto[].class);
        BoatDto[] boats = response.getBody();
        assertThat(Arrays.stream(boats)).anySatisfy(b -> {
            assertThat(b.name()).isEqualTo(expectedName);
            assertThat(b.description()).isEqualTo(expectedDescription);
        });
    }

    @When("they remove the boat from the fleet")
    public void theyRemoveTheBoatFromTheFleet() {
        String url = baseUrl() + "/" + createdBoat.id();
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Then("{string} no longer appears in the fleet list")
    public void boatNoLongerAppearsInTheFleetList(String name) {
        ResponseEntity<BoatDto[]> response = restTemplate.getForEntity(baseUrl(), BoatDto[].class);
        BoatDto[] boats = response.getBody();
        assertThat(Arrays.stream(boats).map(BoatDto::name)).doesNotContain(name);
    }

}