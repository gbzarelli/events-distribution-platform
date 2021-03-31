package br.com.helpdev.webhook;

import br.com.helpdev.webhook.config.WebhookDispatcherPublisher;
import br.com.helpdev.webhook.model.db.OrgFilters;
import br.com.helpdev.webhook.model.in.Event;
import br.com.helpdev.webhook.model.out.EventDispatcher;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class KafkaWebhookFilter {

  private final WebhookDispatcherPublisher dispatcherPublisher;

  @Inject
  public KafkaWebhookFilter(final WebhookDispatcherPublisher dispatcherPublisher) {
    this.dispatcherPublisher = dispatcherPublisher;
  }

  @Incoming("events")
  @Counted(name = "performedChecks", description = "How many primality checks have been performed.")
  @Timed(name = "checksTimer", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
  public void listener(final Event event) {
    OrgFilters.findByOrg(event.org.toLowerCase())
        .ifPresent(m -> m.filters.parallelStream()
            .filter(filter -> event.event.equalsIgnoreCase(filter.filter))
            .map(filter -> new EventDispatcher(event, filter))
            .parallel().forEach(dispatcherPublisher::publish));
  }

}