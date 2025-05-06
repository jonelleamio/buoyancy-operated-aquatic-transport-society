package com.kata.boats.cucumber.features;

import com.kata.boats.infrastructure.in.dto.BoatDto;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ManageBoatsIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String jwtToken;
    private HttpHeaders headers;
    private BoatDto createdBoat;

    @Before
    public void authenticate() {
        headers = new HttpHeaders();
        HttpHeaders loginHeaders = new HttpHeaders();
        loginHeaders.setContentType(MediaType.APPLICATION_JSON);
        loginHeaders.setAccept(List.of(MediaType.APPLICATION_JSON, MediaType.valueOf("application/hal+json")));
        Map<String, String> creds = Map.of("username", "root", "password", "root");
        HttpEntity<Map<String, String>> loginRequest = new HttpEntity<>(creds, loginHeaders);
        ResponseEntity<Map> loginResponse = restTemplate.exchange(
                getLoginUrl(), HttpMethod.POST, loginRequest, Map.class);
        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        jwtToken = (String) loginResponse.getBody().get("token");
        assertThat(jwtToken).isNotBlank();
        headers.setBearerAuth(jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON, MediaType.valueOf("application/hal+json")));
    }

    private String getLoginUrl() {
        return String.format("http://localhost:%d/api/auth/login", port);
    }

    private String getBaseUrl() {
        return String.format("http://localhost:%d/api/boats", port);
    }

    @Given("the member is signed in")
    public void theMemberIsSignedIn() {
        authenticate();
    }

    @When("boat named {string} with description {string} is added")
    public void boatNamedWithDescriptionIsAdded(String name, String description) {
        BoatDto dto = new BoatDto(null, name, description);
        HttpEntity<BoatDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<EntityModel<BoatDto>> response = restTemplate.exchange(
                getBaseUrl(), HttpMethod.POST, request,
                new ParameterizedTypeReference<EntityModel<BoatDto>>() {
                }
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        createdBoat = response.getBody().getContent();
        assertThat(createdBoat.name()).isEqualTo(name);
    }

    @Then("the boat {string} appears in the fleet list")
    public void theBoatAppearsInTheFleetList(String name) {
        List<BoatDto> boats = fetchBoats();
        assertThat(boats).extracting(BoatDto::name).contains(name);
    }

    @And("the fleet contains at least one boat")
    public void theFleetContainsAtLeastOneBoat() {
        List<BoatDto> boats = fetchBoats();
        assertThat(boats).isNotEmpty();
    }

    @When("they view the fleet overview")
    public void theyViewTheFleetOverview() {
    }

    @Then("they see each boat’s name and description listed")
    public void theySeeEachBoatsNameAndDescriptionListed() {
        List<BoatDto> boats = fetchBoats();
        assertThat(boats).allSatisfy(b -> {
            assertThat(b.name()).isNotBlank();
            assertThat(b.description()).isNotBlank();
        });
    }

    @Given("the boat {string} exists in the fleet")
    public void theBoatExistsInTheFleet(String name) {
        if (jwtToken == null || jwtToken.isBlank()) {
            authenticate();
        }
        List<BoatDto> boats = fetchBoats();
        createdBoat = boats.stream()
                .filter(b -> b.name().equals(name))
                .findFirst()
                .orElseGet(() -> {
                    boatNamedWithDescriptionIsAdded(name, "not important detail");
                    return createdBoat;
                });
    }

    @When("they rename the boat to {string} with description {string}")
    public void theyRenameTheBoatToWithDescription(String newName, String newDescription) {
        BoatDto dto = new BoatDto(createdBoat.id(), newName, newDescription);
        HttpEntity<BoatDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<EntityModel<BoatDto>> response = restTemplate.exchange(
                getBaseUrl(), HttpMethod.PUT, request,
                new ParameterizedTypeReference<EntityModel<BoatDto>>() {
                }
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        createdBoat = response.getBody().getContent();
        assertThat(createdBoat.name()).isEqualTo(newName);
    }

    @Then("the fleet list shows {string} with {string}")
    public void theFleetListShowsWith(String expectedName, String expectedDescription) {
        List<BoatDto> boats = fetchBoats();
        assertThat(boats).anySatisfy(b -> {
            assertThat(b.name()).isEqualTo(expectedName);
            assertThat(b.description()).isEqualTo(expectedDescription);
        });
    }

    @When("they remove the boat from the fleet")
    public void theyRemoveTheBoatFromTheFleet() {
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Void> response = restTemplate.exchange(
                getBaseUrl() + "/" + createdBoat.id(), HttpMethod.DELETE, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Then("{string} no longer appears in the fleet list")
    public void boatNoLongerAppearsInTheFleetList(String name) {
        List<BoatDto> boats = fetchBoats();
        assertThat(boats).extracting(BoatDto::name).doesNotContain(name);
    }

    private List<BoatDto> fetchBoats() {
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<CollectionModel<EntityModel<BoatDto>>> response = restTemplate.exchange(
                getBaseUrl(), HttpMethod.GET, request,
                new ParameterizedTypeReference<CollectionModel<EntityModel<BoatDto>>>() {
                }
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody().getContent().stream()
                .map(EntityModel::getContent)
                .collect(Collectors.toList());
    }
}
