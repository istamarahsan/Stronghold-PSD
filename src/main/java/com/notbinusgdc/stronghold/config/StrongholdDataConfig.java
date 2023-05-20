package com.notbinusgdc.stronghold.config;

public sealed interface StrongholdDataConfig
    permits StrongholdDataConfig.InMemory, StrongholdDataConfig.Mariadb {

  final class InMemory implements StrongholdDataConfig {
    InMemory() {}
  }

  final class Mariadb implements StrongholdDataConfig {

    public final String host;
    public final int port;
    public final String user;
    public final String password;
    public final String database;

    Mariadb(String host, int port, String user, String password, String database) {
      this.host = host;
      this.port = port;
      this.user = user;
      this.password = password;
      this.database = database;
    }

    public String getConnectionString() {
      return "jdbc:mariadb://" + host + ":" + port + "/" + database;
    }
  }

  static StrongholdDataConfig inMemory() {
    return new InMemory();
  }

  static StrongholdDataConfig mysql(
      String host, int port, String user, String password, String database) {
    return new Mariadb(host, port, user, password, database);
  }
}
