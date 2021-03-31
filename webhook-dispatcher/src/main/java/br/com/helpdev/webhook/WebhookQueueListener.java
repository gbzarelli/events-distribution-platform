package br.com.helpdev.webhook;

import br.com.helpdev.webhook.config.RabbitMQConnector;
import br.com.helpdev.webhook.model.in.EventDispatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@Startup
@ApplicationScoped
public class WebhookQueueListener {

  private final RabbitMQConnector connector;
  private final ObjectMapper objectMapper;
  private final WebhookDispatcher dispatcher;

  public WebhookQueueListener(final RabbitMQConnector connector,
                              final ObjectMapper objectMapper,
                              final WebhookDispatcher dispatcher) {
    this.connector = connector;
    this.objectMapper = objectMapper;
    this.dispatcher = dispatcher;
  }

  protected void onStart(@Observes final StartupEvent ev) throws IOException {
    final var channel = connector.getChannel();
    channel.basicConsume("webhook-dispatcher-queue",
        false,
        this.getClass().getSimpleName(),
        getConsumer(channel)
    );
  }

  private DefaultConsumer getConsumer(final Channel channel) {
    return new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(final String consumerTag,
                                 final Envelope envelope,
                                 final AMQP.BasicProperties properties,
                                 final byte[] body)
          throws IOException {
        try {
          dispatcher.accept(objectMapper.readValue(body, EventDispatcher.class));
        } catch (Throwable tr) {
          tr.printStackTrace();
          getChannel().basicReject(envelope.getDeliveryTag(), false);
          return;
        }
        getChannel().basicAck(envelope.getDeliveryTag(), false);
      }
    };
  }

}