package br.com.helpdev.kafka;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class Consumer {

  @Incoming("events")
  public void store(Event event) {
    System.out.println(event);
  }
}