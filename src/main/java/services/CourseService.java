package services;

import app.Course;
import controllers.CourseController;
import org.omg.CORBA.NO_RESPONSE;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * Created by Sander on 10-10-2016.
 */

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
public class CourseService {
    CourseController courseController= new CourseController();

    @Context
    UriInfo uriInfo;

    @GET
    public Response getCourses() {
        return Response.ok(courseController.getCourses()).build();
    }

    @GET
    @Path("/{id}")
    public Response getCourse(@PathParam("id") Integer id) {
        return Response.ok(courseController.getCourse(id)).build();
    }

    @POST
    @Consumes("application/json")
    public Response addCourse(Course course) {
        try {
            Integer id = courseController.addCourse(course);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(id.toString());
            return Response.created(builder.build()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes("text/plain")
    public Response addCourses(String text){
        try {
            courseController.addCourses(text);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            return Response.created(builder.build()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
        }
    }

}
