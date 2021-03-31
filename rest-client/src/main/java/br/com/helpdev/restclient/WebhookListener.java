package br.com.helpdev.restclient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/wh")
public class WebhookListener {

  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public Response hook(final String body) {
    System.out.println("*********** webhook received **************");
    System.out.println(body);
    System.out.println("*******************************************");
    return Response.ok().build();
  }
}