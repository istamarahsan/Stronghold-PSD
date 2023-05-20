package com.notbinusgdc.stronghold;

import com.notbinusgdc.stronghold.api.PushSessionLogHandler;
import com.notbinusgdc.stronghold.config.StrongholdConfig;
import com.notbinusgdc.stronghold.config.StrongholdDataConfig;
import com.notbinusgdc.stronghold.data.InMemory;
import com.notbinusgdc.stronghold.data.JooqData;
import com.notbinusgdc.stronghold.domain.sessionlogs.SessionLogService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.javalin.Javalin;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;

public class Stronghold {
  private final PushSessionLogHandler pushSessionLogHandler;

  private Stronghold(PushSessionLogHandler pushSessionLogHandler) {
    this.pushSessionLogHandler = pushSessionLogHandler;
  }

  public static Stronghold from(StrongholdConfig config) {
    if (config.dataConfig instanceof StrongholdDataConfig.InMemory) {
      var db = new InMemory();
      var pushSessionLogHandler = new PushSessionLogHandler(new SessionLogService(db, db));
      return new Stronghold(pushSessionLogHandler);
    } else if (config.dataConfig instanceof StrongholdDataConfig.Mariadb) {
      var db = new JooqData(initDb((StrongholdDataConfig.Mariadb) config.dataConfig));
      var pushSessionLogHandler = new PushSessionLogHandler(new SessionLogService(db, db));
      return new Stronghold(pushSessionLogHandler);
    } else throw new IllegalArgumentException();
  }

  public Javalin create() {
    return Javalin.create().post("/push", pushSessionLogHandler);
  }

  private static DSLContext initDb(StrongholdDataConfig.Mariadb cfg) {
    var hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(cfg.getConnectionString());
    hikariConfig.setUsername(cfg.user);
    hikariConfig.setPassword(cfg.password);
    var hikari = new HikariDataSource(hikariConfig);
    var config = new DefaultConfiguration();
    config.set(new DataSourceConnectionProvider(hikari));
    config.set(SQLDialect.MARIADB);
    return DSL.using(config);
  }
}
