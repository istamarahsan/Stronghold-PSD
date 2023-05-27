package com.notbinusgdc.stronghold.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionLogPushDto {
  public String topicId;

  @JsonProperty("sessionDateTime")
  public String sessionTimeIso;

  @JsonProperty("sessionDuration")
  public String durationIso;

  public String recorderName;
  public String[] mentorDiscordUserIds;
  public Attendee[] attendees;

  public static class Attendee {
    public String discordUserId;

    @JsonProperty("attendanceDuration")
    public String attendanceDurationIso;
  }
}
