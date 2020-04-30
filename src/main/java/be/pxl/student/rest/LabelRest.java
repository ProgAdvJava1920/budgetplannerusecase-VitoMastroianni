package be.pxl.student.rest;

import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;
import be.pxl.student.rest.resource.AccountCreateResource;
import be.pxl.student.rest.resource.LabelCreateResource;
import be.pxl.student.service.AccountService;
import be.pxl.student.service.LabelService;

import javax.inject.Inject;
import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Path("labels")
public class LabelRest {

    @Inject
    private LabelService labelService;

    @GET
    @Produces("application/json")
    public Response getAllLabels() {
        try {
            List<Label> labels = labelService.findAllLabels();
            return Response.ok(labels).build();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response addLabel(LabelCreateResource labelCreateResource) {
        try {
            labelService.addLabel(labelCreateResource.getName());
            return Response.created(UriBuilder.fromPath("/labels/" + labelCreateResource.getName()).build()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
    }
}
