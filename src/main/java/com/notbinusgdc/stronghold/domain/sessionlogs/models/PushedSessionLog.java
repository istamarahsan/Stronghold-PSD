package com.notbinusgdc.stronghold.domain.sessionlogs.models;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public record PushedSessionLog(
    String topicId,
    Instant timeStarted,
    Duration duration,
    String recorderName,
    Snowflake mentorDiscordUserId,
    Map<Snowflake, AttendanceDetail> attendance) {
  public PushedSessionLog {
    if (duration.isNegative()) throw new IllegalArgumentException();
  }

  public record AttendanceDetail(Duration duration) {
    public AttendanceDetail {
      if (duration.isNegative()) throw new IllegalArgumentException();
    }
  }
}
