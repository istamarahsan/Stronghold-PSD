package com.binusgdc.strongholde.bgdc.sessionlogs.data;

import com.binusgdc.strongholde.bgdc.sessionlogs.models.Mentor;
import com.binusgdc.strongholde.bgdc.sessionlogs.models.Snowflake;

public interface MentorsData {
  Mentor mentorOfDiscordUserId(Snowflake id);
}
