package com.binusgdc.strongholde.data;

import com.binusgdc.strongholde.bgdc.sessionlogs.data.MentorsData;
import com.binusgdc.strongholde.bgdc.sessionlogs.data.SessionLogsData;
import com.binusgdc.strongholde.bgdc.sessionlogs.data.StudentsData;
import com.binusgdc.strongholde.bgdc.sessionlogs.data.TopicsData;
import com.binusgdc.strongholde.bgdc.sessionlogs.models.Mentor;
import com.binusgdc.strongholde.bgdc.sessionlogs.models.SessionLog;
import com.binusgdc.strongholde.bgdc.sessionlogs.models.Snowflake;
import com.binusgdc.strongholde.bgdc.sessionlogs.models.Student;
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
