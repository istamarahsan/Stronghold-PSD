package com.notbinusgdc.stronghold.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notbinusgdc.stronghold.domain.sessionlogs.SessionLogService;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.SessionLogPushDetails;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.Snowflake;
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
      SessionLogPushDetails sessionLog = parseDto(body);
      try {
        sessionLogService.pushLog(sessionLog);
        ctx.status(HttpStatus.OK);
      } catch (IllegalArgumentException e) {
        ctx.status(HttpStatus.BAD_REQUEST);
        ctx.json(e.getMessage());
      } catch (Exception e) {
        ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
        ctx.json(e.getMessage());
      }
    } catch (Exception e) {
      ctx.status(HttpStatus.BAD_REQUEST);
    }
  }

  private SessionLogPushDetails parseDto(SessionLogPushDto dto) {
    return new SessionLogPushDetails(
        dto.topicId,
        Instant.parse(dto.sessionTimeIso),
        Duration.parse(dto.durationIso),
        dto.recorderName,
        Arrays.stream(dto.mentorDiscordUserIds).map(Long::parseLong).map(Snowflake::of).toList(),
        Arrays.stream(dto.attendees)
            .collect(
                Collectors.<SessionLogPushDto.Attendee, Snowflake, Duration>toMap(
                    att -> Snowflake.of(Long.parseLong(att.discordUserId)),
                    att -> Duration.parse(att.attendanceDurationIso))));
  }
}
