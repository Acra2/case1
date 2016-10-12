package services;

import controllers.StudentController;
import controllers.SubscribeController;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Sander on 11-10-2016.
 */

@Path("/subscribe")
@Produces(MediaType.APPLICATION_JSON)
public class SubscribeService {
    SubscribeController subscribeController= SubscribeController.getInstance();

    @POST
    public Response subscribe(@QueryParam("course") Integer courseId,
                          @QueryParam("student") Integer studentId){
        if (courseId == null)
            return Response.status(Response.Status.BAD_REQUEST).type("text/plain").entity("Missing query parameter 'course'").build();
        if (studentId == null)
            return Response.status(Response.Status.BAD_REQUEST).type("text/plain").entity("Missing query parameter 'student'").build();

        try {
            subscribeController.subscribe(courseId, studentId);
            return Response.status(200).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
        }
    }

}
