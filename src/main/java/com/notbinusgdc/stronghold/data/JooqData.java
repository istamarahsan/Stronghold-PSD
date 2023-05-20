package com.notbinusgdc.stronghold.data;

import static org.jooq.impl.DSL.*;

import com.notbinusgdc.stronghold.domain.sessionlogs.data.SessionLogsData;
import com.notbinusgdc.stronghold.domain.sessionlogs.data.TopicsData;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.SessionLog;
import java.sql.Date;
import org.jooq.DSLContext;

public class JooqData implements TopicsData, SessionLogsData {

  private final DSLContext db;

  public JooqData(DSLContext db) {
    this.db = db;
  }

  @Override
  public boolean sessionWithTopicExists(String topicId) {
    return db.select().from(table("session")).where(field("id").eq(topicId)).limit(1).execute()
        == 1;
  }

  @Override
  public void insertSessionLog(SessionLog log) {
    db.insertInto(
            table("session"),
            field("id"),
            field("session_date"),
            field("time_started"),
            field("duration_seconds"),
            field("recorder_name"))
        .values(
            log.topicId(),
            date(Date.from(log.timeStarted())),
            time(Date.from(log.timeStarted())),
            log.duration().toSeconds(),
            log.recorderName())
        .execute();
    for (var mentorId : log.mentorDiscordUserIds()) {
      db.insertInto(table("session_mentor"), field("session_id"), field("discord_uid"))
          .values(log.topicId(), mentorId.asString())
          .execute();
    }
    for (var attendance : log.attendance().entrySet()) {
      db.insertInto(
              table("attendance"),
              field("session_id"),
              field("discord_uid"),
              field("attendance_duration_seconds"))
          .values(log.topicId(), attendance.getKey().asString(), attendance.getValue().toSeconds())
          .execute();
    }
  }

  @Override
  public boolean topicIdExists(String id) {
    return db.select().from(table("topic")).where(field("id").eq(id)).limit(1).execute() == 1;
  }
}
