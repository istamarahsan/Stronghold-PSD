package com.notbinusgdc.stronghold.domain.sessionlogs;

import com.notbinusgdc.stronghold.domain.sessionlogs.data.MentorsData;
import com.notbinusgdc.stronghold.domain.sessionlogs.data.SessionLogsData;
import com.notbinusgdc.stronghold.domain.sessionlogs.data.StudentsData;
import com.notbinusgdc.stronghold.domain.sessionlogs.data.TopicsData;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.PushedSessionLog;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.SessionLog;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.Student;
import java.util.stream.Collectors;

public class SessionLogService {
  private final StudentsData students;
  private final MentorsData mentors;
  private final TopicsData topics;
  private final SessionLogsData sessionLogs;

  public SessionLogService(
      StudentsData students, MentorsData mentors, TopicsData topics, SessionLogsData sessionLogs) {
    this.students = students;
    this.mentors = mentors;
    this.topics = topics;
    this.sessionLogs = sessionLogs;
  }

  public SessionLog pushLog(PushedSessionLog pushedSessionLog) {
    if (!topics.topicIdExists(pushedSessionLog.topicId())
        || pushedSessionLog.duration().isNegative()
        || pushedSessionLog.attendance().values().stream()
            .anyMatch(attendance -> attendance.duration().isNegative())) {
      throw new IllegalArgumentException();
    }
    var mentor = mentors.mentorOfDiscordUserId(pushedSessionLog.mentorDiscordUserId());
    var attendees =
        students.studentOfDiscordUserId(pushedSessionLog.attendance().keySet()).stream()
            .collect(
                Collectors.toMap(
                    Student::nim,
                    student ->
                        pushedSessionLog.attendance().get(student.discordUserId()).duration()));

    var log =
        new SessionLog(
            pushedSessionLog.topicId(),
            pushedSessionLog.timeStarted(),
            pushedSessionLog.duration(),
            pushedSessionLog.recorderName(),
            mentor,
            attendees);

    sessionLogs.insertSessionLog(log);

    return log;
  }
}