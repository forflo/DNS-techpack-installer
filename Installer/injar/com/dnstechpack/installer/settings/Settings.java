package com.dnstechpack.installer.settings;

import com.dnstechpack.installer.Installer;
import java.io.IOException;
import java.util.Properties;

public class Settings
{
  private Properties properties;

  public Settings()
  {
    this.properties = new Properties();
    try
    {
      this.properties.load(Installer.class.getClassLoader().getResourceAsStream("internal.properties"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getMCVersion()
  {
    return this.properties.getProperty("MCVersion");
  }

  public String getDNSVersion()
  {
    return this.properties.getProperty("DNSVersion");
  }

  public String getJarVersion()
  {
    return getMCVersion() + "-DNS" + getDNSVersion();
  }

  public String getProfileName()
  {
    return this.properties.getProperty("PackName") + " " + getDNSVersion();
  }
}
