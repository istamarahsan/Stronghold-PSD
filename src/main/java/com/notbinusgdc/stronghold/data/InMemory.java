package com.notbinusgdc.stronghold.data;

import com.notbinusgdc.stronghold.domain.sessionlogs.data.MentorsData;
import com.notbinusgdc.stronghold.domain.sessionlogs.data.SessionLogsData;
import com.notbinusgdc.stronghold.domain.sessionlogs.data.StudentsData;
import com.notbinusgdc.stronghold.domain.sessionlogs.data.TopicsData;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.Mentor;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.SessionLog;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.Snowflake;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.Student;
import java.util.*;

public class InMemory implements TopicsData, StudentsData, MentorsData, SessionLogsData {

  private final Map<String, SessionLog> sessionLogs = new HashMap<>();
  private int increment = 0;

  @Override
  public Mentor mentorOfDiscordUserId(Snowflake id) {
    return new Mentor(id);
  }

  @Override
  public void insertSessionLog(SessionLog log) {
    sessionLogs.put(log.topicId(), log);
  }

  @Override
  public Student studentOfDiscordUserId(Snowflake id) {
    increment++;
    return new Student(String.valueOf(increment), id);
  }

  @Override
  public List<Student> studentOfDiscordUserId(Set<Snowflake> ids) {
    var students = new ArrayList<Student>(ids.size());
    for (var id : ids) {
      students.add(studentOfDiscordUserId(id));
    }
    return students;
  }

  @Override
  public boolean topicIdExists(String id) {
    return true;
  }
}
