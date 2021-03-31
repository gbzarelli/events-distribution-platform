package br.com.helpdev.webhook.model.in;

public class EventDispatcher {

  public Event event;
  public Filter filter;

  public EventDispatcher() {
  }

  public EventDispatcher(final Event event, final Filter filter) {
    this.event = event;
    this.filter = filter;
  }

  @Override
  public String toString() {
    return "EventDispatcher{" +
        "event=" + event +
        ", filter=" + filter +
        '}';
  }
}
