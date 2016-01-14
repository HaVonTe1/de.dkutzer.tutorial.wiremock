package de.dkutzer.mywiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class WebservicePersonControllerTest {

    @ClassRule
    public static WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.wireMockConfig().dynamicPort());

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        WebservicePersonController.serviceUrl = "http://localhost:" + wireMockRule.port() + "/person";
        PersonDTO ringo = new PersonDTO();
        ringo.setName("ringo");
        ringo.setLastName("starr");
        ringo.setAge(1);
        PersonDTO oldRingo = new PersonDTO();
        oldRingo.setName("ringo");
        oldRingo.setLastName("starr");
        oldRingo.setAge(2);
        ObjectMapper mapper = new ObjectMapper();
        final String ringoAsJson = mapper.writeValueAsString(ringo);
        final String oldRingoAsJson = mapper.writeValueAsString(oldRingo);

        List<PersonDTO> emptyList = Lists.newArrayList();

        final String emptyPersonListJson = mapper.writeValueAsString(emptyList);
        // stub for get single person
        wireMockRule.stubFor(get(WireMock.urlEqualTo("/person/ringo")).willReturn(
                aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(ringoAsJson)));
        // stub for getting all persons
        wireMockRule.stubFor(get(WireMock.urlEqualTo("/person")).willReturn(
                aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(emptyPersonListJson)));
        // stub for creating a person
        wireMockRule.stubFor(post(WireMock.urlEqualTo("/person")).willReturn(aResponse().withStatus(200)));
        // stub for updateting a person
        wireMockRule.stubFor(put(WireMock.urlEqualTo("/person/ringo")).willReturn(
                aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(oldRingoAsJson)));
        // stub for deleting a person
        wireMockRule.stubFor(delete(WireMock.urlEqualTo("/person/ringo")).willReturn(aResponse().withStatus(200)));

    }



    @Test
    public void testController() throws Exception {

        PersonDTO ringo = new PersonDTO();
        ringo.setName("ringo");
        ringo.setLastName("starr");
        ringo.setAge(1);
        WebservicePersonController.create(ringo);

        final PersonDTO personDTO = WebservicePersonController.get("ringo");
        assertThat(personDTO.getName()).isEqualTo("ringo");
        assertThat(personDTO.getLastName()).isEqualTo("starr");
        assertThat(personDTO.getAge()).isEqualTo(1);

        ringo.setAge(2);
        final PersonDTO updatedPerson = WebservicePersonController.update(ringo);
        assertThat(updatedPerson.getName()).isEqualTo("ringo");
        assertThat(updatedPerson.getLastName()).isEqualTo("starr");
        assertThat(updatedPerson.getAge()).isEqualTo(2);

        WebservicePersonController.delete(ringo);

        final List<PersonDTO> list = WebservicePersonController.get();
        assertThat(list).isEmpty();

    }

}
