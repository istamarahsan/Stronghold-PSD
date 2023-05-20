package com.notbinusgdc.stronghold;

import com.notbinusgdc.stronghold.config.StrongholdConfig;
import com.notbinusgdc.stronghold.config.StrongholdDataConfig;
import java.util.Optional;

public class Main {
  public static void main(String[] args) {

    var data =
        Optional.ofNullable(System.getenv("DATA"))
            .map(
                arg ->
                    switch (arg.toLowerCase()) {
                      case "mariadb" -> getMariadbConfig().get();
                      default -> throw new IllegalArgumentException(
                          "Unknown data source type '" + arg + "'");
                    })
            .orElse(StrongholdDataConfig.inMemory());

    var config = new StrongholdConfig(data);

    Stronghold.from(config).create().start(8080);
  }

  private static Optional<StrongholdDataConfig> getMariadbConfig() {
    try {
      var host = Optional.ofNullable(System.getenv("MARIADBHOST")).get();
      var port = Integer.parseInt(Optional.ofNullable(System.getenv("MARIADBPORT")).get());
      var user = Optional.ofNullable(System.getenv("MARIADBUSER")).get();
      var password = Optional.ofNullable(System.getenv("MARIADBPASSWORD")).get();
      var database = Optional.ofNullable(System.getenv("MARIADBDATABASE")).get();
      return Optional.of(
          StrongholdDataConfig.mariadb(
              host, port, user, password, database));
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
