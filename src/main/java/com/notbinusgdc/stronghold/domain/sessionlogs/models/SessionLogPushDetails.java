package com.notbinusgdc.stronghold.domain.sessionlogs.models;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public record SessionLogPushDetails(
    String topicId,
    Instant timeStarted,
    Duration duration,
    String recorderName,
    List<Snowflake> mentorDiscordUserIds,
    Map<Snowflake, Duration> attendance) {}
