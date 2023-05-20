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
                      case "mysql" -> getMysqlConfig().get();
                      default -> throw new IllegalArgumentException(
                          "Unknown data source type '" + arg + "'");
                    })
            .orElse(StrongholdDataConfig.inMemory());

    var config = new StrongholdConfig(data);

    Stronghold.from(config).create().start(8080);
  }

  private static Optional<StrongholdDataConfig> getMysqlConfig() {
    try {
      var mySqlHost = Optional.ofNullable(System.getenv("MYSQLHOST")).get();
      var mySqlPort = Integer.parseInt(Optional.ofNullable(System.getenv("MYSQLPORT")).get());
      var mySqlUser = Optional.ofNullable(System.getenv("MYSQLUSER")).get();
      var mySqlPassword = Optional.ofNullable(System.getenv("MYSQLPASSWORD")).get();
      var mySqlDatabase = Optional.ofNullable(System.getenv("MYSQLDATABASE")).get();
      return Optional.of(
          StrongholdDataConfig.mysql(
              mySqlHost, mySqlPort, mySqlUser, mySqlPassword, mySqlDatabase));
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
