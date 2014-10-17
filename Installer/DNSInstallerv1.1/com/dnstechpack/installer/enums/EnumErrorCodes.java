package com.dnstechpack.installer.enums;

public enum EnumErrorCodes
{
  CLIENT_QUIT(-1), 
  INSTALL_ERROR(1, "Installation Failed At A Critical Point"), 
  FORGE_ERROR(2, "Forge Could Not Be Installed");

  public int code;
  public String description;

  private EnumErrorCodes(int code) { this.code = code; }


  private EnumErrorCodes(int code, String description)
  {
    this.code = code;
    this.description = description;
  }
}
