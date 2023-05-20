package com.notbinusgdc.stronghold.domain.sessionlogs.data;

import com.notbinusgdc.stronghold.domain.sessionlogs.models.SessionLog;

public interface SessionLogsData {
  boolean sessionWithTopicExists(String topicId);

  void insertSessionLog(SessionLog log);
}
