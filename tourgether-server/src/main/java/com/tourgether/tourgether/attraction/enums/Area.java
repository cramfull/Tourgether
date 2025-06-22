package com.tourgether.tourgether.attraction.enums;

public enum Area {
  CENTRAL("도심권"),
  SOUTHWEST("서남권"),
  NORTHWEST("서북권"),
  NORTHEAST("동북권"),
  SOUTHEAST("동남권");

  private final String areaType;

  Area(String areaType) {
    this.areaType = areaType;
  }

  public String getAreaType() {
    return areaType;
  }
}