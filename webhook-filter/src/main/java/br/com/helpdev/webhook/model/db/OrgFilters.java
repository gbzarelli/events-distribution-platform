package br.com.helpdev.webhook.model.db;

import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import java.util.List;
import java.util.Optional;

@MongoEntity(collection = "wh_filters")
public class OrgFilters extends PanacheMongoEntity {

  public String org;
  public List<Filter> filters;

  @CacheResult(cacheName = "find-by-org-cache")
  public static Optional<OrgFilters> findByOrg(final @CacheKey String org) {
    return find("org", org).firstResultOptional();
  }

  public List<Filter> getFilters() {
    return filters;
  }
}
