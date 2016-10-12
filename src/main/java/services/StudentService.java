package services;

import app.Course;
import app.Student;
import controllers.StudentController;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * Created by Sander on 10-10-2016.
 */
@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentService {


    StudentController studentController= StudentController.getInstance();

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

    @POST
    @Consumes("application/json")
    public Response addCourse(Student student) {
        try {
            Integer id = studentController.addStudent(student);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(id.toString());
            return Response.created(builder.build()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
        }
    }

}
