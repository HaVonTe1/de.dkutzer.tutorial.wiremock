package de.dkutzer.mywiremock;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class WebservicePersonController {

    private final static Client client = ClientBuilder.newClient();
    static String serviceUrl = "http://some.server.com:8080/person";

    public static void create(PersonDTO p) {
        client.target(serviceUrl).request(MediaType.APPLICATION_JSON).post(Entity.json(p));
    }

    public static PersonDTO get(String name) {
        final Response response = client.target(serviceUrl + "/" + name).request(MediaType.APPLICATION_JSON).get(Response.class);
        final PersonDTO p = response.readEntity(PersonDTO.class);
        return p;
    }

    public static List<PersonDTO> get() {
        final List<PersonDTO> list = client.target(serviceUrl).request(MediaType.APPLICATION_JSON).get(new GenericType<List<PersonDTO>>() {
        });
        return list;
    }

    public static PersonDTO update(final PersonDTO p) {
        final Response put = client.target(serviceUrl + "/" + p.getName()).request(MediaType.APPLICATION_JSON).put(Entity.json(p));
        final PersonDTO res = put.readEntity(PersonDTO.class);
        return res;
    }

    public static void delete(final PersonDTO p) {
        client.target(serviceUrl + "/" + p.getName()).request().delete();

    }

}
