package com.notbinusgdc.stronghold.domain.sessionlogs.data;

import com.notbinusgdc.stronghold.domain.sessionlogs.models.SessionLog;

public interface SessionLogsData {
  void insertSessionLog(SessionLog log);
}
