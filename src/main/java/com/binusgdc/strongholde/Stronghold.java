package com.binusgdc.strongholde;

import com.binusgdc.strongholde.api.PushSessionLogHandler;
import com.binusgdc.strongholde.bgdc.sessionlogs.SessionLogService;
import com.binusgdc.strongholde.data.InMemory;
import io.javalin.Javalin;

public class Stronghold {
  private final PushSessionLogHandler pushSessionLogHandler;

  private Stronghold(PushSessionLogHandler pushSessionLogHandler) {
    this.pushSessionLogHandler = pushSessionLogHandler;
  }

  public static Stronghold from() {
    var db = new InMemory();
    var pushSessionLogHandler = new PushSessionLogHandler(new SessionLogService(db, db, db, db));
    return new Stronghold(pushSessionLogHandler);
  }

  public Javalin create() {
    return Javalin.create().post("/push", pushSessionLogHandler);
  }
}
