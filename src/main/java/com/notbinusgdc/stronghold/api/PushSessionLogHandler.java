package com.notbinusgdc.stronghold.api;

import com.notbinusgdc.stronghold.domain.sessionlogs.SessionLogService;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.PushedSessionLog;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.Snowflake;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class PushSessionLogHandler implements Handler {

  private final ObjectMapper jsonMapper = new ObjectMapper();
  private final SessionLogService sessionLogService;

  public PushSessionLogHandler(SessionLogService sessionLogService) {
    this.sessionLogService = sessionLogService;
  }

  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    SessionLogPushDto body = jsonMapper.readValue(ctx.bodyInputStream(), SessionLogPushDto.class);
    try {
      PushedSessionLog sessionLog = parseDto(body);
      try {
        sessionLogService.pushLog(sessionLog);
        ctx.status(HttpStatus.OK);
      } catch (Exception e) {
        ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } catch (Exception e) {
      ctx.status(HttpStatus.BAD_REQUEST);
    }
  }

  private PushedSessionLog parseDto(SessionLogPushDto dto) {
    return new PushedSessionLog(
        dto.topicId,
        Instant.parse(dto.sessionTimeIso),
        Duration.parse(dto.durationIso),
        dto.recorderName,
        Snowflake.of(Long.parseLong(dto.mentorDiscordUserId)),
        Arrays.stream(dto.attendees)
            .collect(
                Collectors
                    .<SessionLogPushDto.Attendee, Snowflake, PushedSessionLog.AttendanceDetail>
                        toMap(
                            att -> Snowflake.of(Long.parseLong(att.discordUserId)),
                            att ->
                                new PushedSessionLog.AttendanceDetail(
                                    Duration.parse(att.attendanceDurationIso)))));
  }
}
