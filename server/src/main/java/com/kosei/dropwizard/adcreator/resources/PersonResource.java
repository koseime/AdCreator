package com.kosei.dropwizard.adcreator.resources;

/**
 * Created by lanceriedel on 9/4/14.
 */
import com.kosei.dropwizard.adcreator.core.Person;
import com.kosei.dropwizard.adcreator.views.PersonView;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/people/{personId}")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {


    public PersonResource() {

    }


    @GET
    @Path("/view_freemarker")
    @Produces(MediaType.TEXT_HTML)
    public PersonView getPersonViewFreemarker(@PathParam("personId") LongParam personId) {
        Person p = new Person();
        p.setFullName("TEST NAME");
        p.setId(123l);
        p.setJobTitle("UGH");
        return new PersonView(PersonView.Template.FREEMARKER,  p);
    }


}