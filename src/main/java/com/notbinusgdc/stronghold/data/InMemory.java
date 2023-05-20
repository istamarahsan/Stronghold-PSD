package com.notbinusgdc.stronghold.data;

import com.notbinusgdc.stronghold.domain.sessionlogs.data.SessionLogsData;
import com.notbinusgdc.stronghold.domain.sessionlogs.data.TopicsData;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.SessionLog;
import java.util.HashMap;
import java.util.Map;

public class InMemory implements TopicsData, SessionLogsData {

  private final Map<String, SessionLog> sessionLogs = new HashMap<>();

  @Override
  public boolean sessionWithTopicExists(String topicId) {
    return sessionLogs.containsKey(topicId);
  }

  @Override
  public void insertSessionLog(SessionLog log) {
    sessionLogs.put(log.topicId(), log);
  }

  @Override
  public boolean topicIdExists(String id) {
    return true;
  }
}
