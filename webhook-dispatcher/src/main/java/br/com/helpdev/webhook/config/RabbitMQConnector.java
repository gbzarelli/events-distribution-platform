package br.com.helpdev.webhook.config;

import br.com.helpdev.webhook.config.properties.RabbitMQConfigProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.Startup;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@Startup
@ApplicationScoped
public class RabbitMQConnector {

  public static final String WEBHOOK_EXCHANGE = "webhook-exchange";

  private final Connection connection;
  private final Channel channel;

  @Inject
  public RabbitMQConnector(final RabbitMQConfigProperties config) throws IOException, TimeoutException {
    final var factory = new ConnectionFactory();
    factory.setHost(config.host);
    factory.setPort(config.port);
    factory.setVirtualHost(config.vhost);
    factory.setUsername(config.username);
    factory.setPassword(config.password);
    factory.setAutomaticRecoveryEnabled(true);
    connection = factory.newConnection();
    channel = connection.createChannel();
  }

  protected void onStop(@Observes final ShutdownEvent ev) {
    try {
      channel.close();
      connection.close();
    } catch (final Exception ignored) {
    }
  }

  public Channel getChannel() {
    return channel;
  }

}
