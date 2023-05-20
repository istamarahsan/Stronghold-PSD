package com.notbinusgdc.stronghold.domain.sessionlogs.data;

import com.notbinusgdc.stronghold.domain.sessionlogs.models.Mentor;
import com.notbinusgdc.stronghold.domain.sessionlogs.models.Snowflake;

public interface MentorsData {
  Mentor mentorOfDiscordUserId(Snowflake id);
}
