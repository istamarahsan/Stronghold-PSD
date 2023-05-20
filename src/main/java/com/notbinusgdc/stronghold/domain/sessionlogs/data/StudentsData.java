package com.notbinusgdc.stronghold.domain.sessionlogs.data;

import com.notbinusgdc.stronghold.domain.sessionlogs.models.Snowflake;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.Student;
import java.util.List;
import java.util.Set;

public interface StudentsData {
  Student studentOfDiscordUserId(Snowflake id);

  List<Student> studentOfDiscordUserId(Set<Snowflake> ids);
}
