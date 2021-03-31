package br.com.helpdev.webhook.config;

import br.com.helpdev.webhook.model.out.EventDispatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@ApplicationScoped
public class WebhookDispatcherPublisher {

  private final RabbitMQConnector connector;
  private final ObjectMapper objectMapper;

  @Inject
  public WebhookDispatcherPublisher(final RabbitMQConnector connector,
                                    final ObjectMapper objectMapper) {
    this.connector = connector;
    this.objectMapper = objectMapper;
  }

  @Counted(name = "publishPerformedChecks", description = "How many primality checks have been performed.")
  @Timed(name = "publishChecksTimer", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
  public void publish(final EventDispatcher eventDispatcher) {
    try {
      connector.getChannel()
          .basicPublish(RabbitMQConnector.WEBHOOK_EXCHANGE,
              "org." + eventDispatcher.event.org,
              null,
              objectMapper.writeValueAsString(eventDispatcher).getBytes());
      System.out.println("published: " + eventDispatcher);
    } catch (final Exception exception) {
      exception.printStackTrace();
    }
  }
}
