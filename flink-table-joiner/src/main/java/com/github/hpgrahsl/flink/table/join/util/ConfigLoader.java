package com.github.hpgrahsl.flink.table.join.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

  public static Properties loadProperties() {
    try (InputStream input = ConfigLoader.class
        .getClassLoader().getResourceAsStream("table_api_sql.properties")) {
      Properties props = new Properties();
      if (input == null) {
        throw new RuntimeException("error: config could not be loaded");
      }
      props.load(input);
      return props;
    } catch (IOException ex) {
      throw new RuntimeException("error: config could not be loaded",ex);
    }
  }

}
