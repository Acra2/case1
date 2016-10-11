package services;

import controllers.StudentController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Sander on 10-10-2016.
 */
@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentService {


    StudentController studentController= new StudentController();

    @Context
    UriInfo uriInfo;

    @GET
    public Response getStudents() {
        return Response.ok(studentController.getAllStudents()).build();
    }

    @GET
    @Path("/{id}")
    public Response getStudent(@PathParam("id") Integer id) {
        return Response.ok(studentController.getStudent(id)).build();
    }
}
