package br.com.helpdev.webhook.config;

import br.com.helpdev.webhook.model.in.EventDispatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

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
