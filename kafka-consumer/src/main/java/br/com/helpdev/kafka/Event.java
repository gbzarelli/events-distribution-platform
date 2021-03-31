package br.com.helpdev.kafka;

public class Event {

  public String org;
  public String event;
  public Integer id;

  @Override
  public String toString() {
    return "Event{" +
        "org='" + org + '\'' +
        ", event='" + event + '\'' +
        ", id=" + id +
        '}';
  }
}
