package services;

import app.Invoice;
import controllers.InvoiceController;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Sander on 11-10-2016.
 */
@Path("/invoices")
@Produces(MediaType.APPLICATION_JSON)
public class InvoiceService {
    InvoiceController invoiceController = InvoiceController.getInstance();

    @POST
    public Response subscribe(@QueryParam("week") Integer week,
                              @QueryParam("year") Integer year) {
        if (week == null)
            return Response.status(Response.Status.BAD_REQUEST).type("text/plain").entity("Missing query parameter 'week'").build();
        if (year == null)
            return Response.status(Response.Status.BAD_REQUEST).type("text/plain").entity("Missing query parameter 'year'").build();
        return Response.ok(invoiceController.getInvoice(week, year)).build();
    }
}
