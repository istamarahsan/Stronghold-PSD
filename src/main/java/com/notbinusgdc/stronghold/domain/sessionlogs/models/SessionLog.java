package com.notbinusgdc.stronghold.domain.sessionlogs.models;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public record SessionLog(
    String topicId,
    Instant timeStarted,
    Duration duration,
    String recorderName,
    Mentor mentor,
    Map<String, Duration> attendance) {}
