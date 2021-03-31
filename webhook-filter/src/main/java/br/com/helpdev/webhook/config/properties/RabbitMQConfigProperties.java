package br.com.helpdev.webhook.config.properties;

import com.rabbitmq.client.ConnectionFactory;
import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix = "rabbitmq")
public class RabbitMQConfigProperties {

  public String host;
  public Integer port = ConnectionFactory.DEFAULT_AMQP_PORT;
  public String vhost;
  public String username;
  public String password;



}