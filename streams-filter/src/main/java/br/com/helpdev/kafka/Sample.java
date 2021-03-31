package br.com.helpdev.kafka;

import io.quarkus.kafka.client.serialization.ObjectMapperSerde;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class Sample {

  private static final ObjectMapperSerde<Event> eventSerde = new ObjectMapperSerde<>(Event.class);

  @ConfigProperty(name = "stream.event.filter")
  String eventFilter;
  @ConfigProperty(name = "stream.event.input")
  String inputTopic;
  @ConfigProperty(name = "stream.event.output")
  String outputTopic;

  @Produces
  public Topology eventsFilter() {
    final StreamsBuilder builder = new StreamsBuilder();

    builder.stream(inputTopic, Consumed.with(Serdes.String(), eventSerde))
        .filter((key, event) -> event.event.equalsIgnoreCase(eventFilter))
        .to(outputTopic, Produced.with(Serdes.String(), eventSerde));

    return builder.build();
  }


}