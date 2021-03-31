package br.com.helpdev.webhook;

import br.com.helpdev.webhook.model.in.EventDispatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@ApplicationScoped
public class WebhookDispatcher implements Consumer<EventDispatcher> {

  private final ObjectMapper objectMapper;

  public WebhookDispatcher(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  @Counted(name = "performedChecks", description = "How many primality checks have been performed.")
  @Timed(name = "checksTimer", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
  public void accept(final EventDispatcher eventDispatcher) {
    try {
      HttpURLConnection httpCon = (HttpURLConnection) new URL(eventDispatcher.filter.whCallback).openConnection();
      httpCon.setRequestMethod("POST");
      httpCon.setDoOutput(true);
      httpCon.connect();
      OutputStream os = httpCon.getOutputStream();
      os.write(objectMapper.writeValueAsString(eventDispatcher.event).getBytes(StandardCharsets.UTF_8));
      os.flush();
      System.out.println("Callback response: " + httpCon.getResponseCode());
      os.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
