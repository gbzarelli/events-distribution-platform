package br.com.helpdev.kafka;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class EventDeserializer extends JsonbDeserializer<Event> {

  public EventDeserializer() {
    super(Event.class);
  }
}
