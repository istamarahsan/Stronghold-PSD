package com.notbinusgdc.stronghold.api;

public class SessionLogPushDto {
  public String topicId;
  public String sessionTimeIso;
  public String durationIso;
  public String recorderName;
  public String mentorDiscordUserId;
  public Attendee[] attendees;

  public static class Attendee {
    public String discordUserId;
    public String attendanceDurationIso;
  }
}
