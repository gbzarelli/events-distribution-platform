package br.com.helpdev.webhook.config;

import br.com.helpdev.webhook.model.in.Event;
import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class EventDeserializer extends JsonbDeserializer<Event> {

  public EventDeserializer() {
    super(Event.class);
  }
}
