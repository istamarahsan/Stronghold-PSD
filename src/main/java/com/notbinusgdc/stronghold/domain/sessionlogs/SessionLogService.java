package com.notbinusgdc.stronghold.domain.sessionlogs;

import com.notbinusgdc.stronghold.domain.sessionlogs.data.SessionLogsData;
import com.notbinusgdc.stronghold.domain.sessionlogs.data.TopicsData;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.SessionLog;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.SessionLogPushDetails;
import java.time.Duration;

public class SessionLogService {
  private final TopicsData topics;
  private final SessionLogsData sessionLogs;

  public SessionLogService(TopicsData topics, SessionLogsData sessionLogs) {
    this.topics = topics;
    this.sessionLogs = sessionLogs;
  }

  public SessionLog pushLog(SessionLogPushDetails sessionLogPushDetails) {
    if (!topics.topicIdExists(sessionLogPushDetails.topicId())
        || sessionLogs.sessionWithTopicExists(sessionLogPushDetails.topicId())
        || sessionLogPushDetails.duration().isNegative()
        || sessionLogPushDetails.attendance().values().stream().anyMatch(Duration::isNegative)) {
      throw new IllegalArgumentException();
    }
    var log =
        new SessionLog(
            sessionLogPushDetails.topicId(),
            sessionLogPushDetails.timeStarted(),
            sessionLogPushDetails.duration(),
            sessionLogPushDetails.recorderName(),
            sessionLogPushDetails.mentorDiscordUserIds(),
            sessionLogPushDetails.attendance());

    sessionLogs.insertSessionLog(log);

    return log;
  }
}
