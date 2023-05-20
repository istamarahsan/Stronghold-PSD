package com.binusgdc.strongholde.bgdc.sessionlogs.models;

public class Snowflake {
  private final long val;

  private Snowflake(long val) {
    this.val = val;
  }

  public static Snowflake of(long l) {
    return new Snowflake(l);
  }

  public long asLong() {
    return val;
  }

  public String asString() {
    return String.valueOf(val);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    return val == ((Snowflake) o).val;
  }

  @Override
  public int hashCode() {
    return (int) (val ^ (val >>> 32));
  }
}
