package br.com.helpdev.webhook.model.db;

public class Filter {

  public String filter;
  public String whCallback;

  @Override
  public String toString() {
    return "Filter{" +
        "filter='" + filter + '\'' +
        ", whCallback='" + whCallback + '\'' +
        '}';
  }
}
