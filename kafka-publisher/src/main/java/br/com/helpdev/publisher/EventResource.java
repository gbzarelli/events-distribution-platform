package br.com.helpdev.publisher;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/event")
public class EventResource {

  private static final AtomicInteger atomicInteger = new AtomicInteger(0);

  @Channel("events")
  Emitter<Event> emitter;

  public enum EventType {
    EVENT_A, EVENT_B, EVENT_C;

    static EventType getRandom() {
      return EventType.values()[new SecureRandom().nextInt(EventType.values().length)];
    }
  }

  public enum Orgs {
    HELPDEV, GITHUB, ZARELLI;

    static Orgs getRandom() {
      return Orgs.values()[new SecureRandom().nextInt(Orgs.values().length)];
    }
  }

  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public Response createEvent() {
    final var event = new Event();
    event.event = EventType.getRandom().name();
    event.org = Orgs.getRandom().name();
    event.id = atomicInteger.incrementAndGet();

    emitter.send(event);

    return Response.status(Response.Status.CREATED).entity(event).build();
  }
}