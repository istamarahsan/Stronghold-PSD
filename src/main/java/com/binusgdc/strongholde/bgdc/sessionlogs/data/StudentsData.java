package com.binusgdc.strongholde.bgdc.sessionlogs.data;

import com.binusgdc.strongholde.bgdc.sessionlogs.models.Snowflake;
import com.binusgdc.strongholde.bgdc.sessionlogs.models.Student;
import java.util.List;
import java.util.Set;

public interface StudentsData {
  Student studentOfDiscordUserId(Snowflake id);

  List<Student> studentOfDiscordUserId(Set<Snowflake> ids);
}
