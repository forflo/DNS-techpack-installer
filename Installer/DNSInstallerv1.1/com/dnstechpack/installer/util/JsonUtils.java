package com.dnstechpack.installer.util;

import argo.format.PrettyJsonFormatter;
import argo.jdom.JdomParser;
import argo.jdom.JsonField;
import argo.jdom.JsonNode;
import argo.jdom.JsonNodeFactories;
import argo.jdom.JsonRootNode;
import com.dnstechpack.installer.gui.InstallerPanel;
import com.dnstechpack.installer.settings.Settings;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.apache.commons.io.FileUtils;

public class JsonUtils
{
  public static void updateProfile(String mcDir, String installDir)
    throws IOException
  {
    System.out.println(mcDir + "\n" + installDir);

    File oldProfile = new File(mcDir, "/launcher_profiles.json");

    if (!oldProfile.exists())
    {
      JOptionPane.showMessageDialog(null, "Please run minecraft once before trying to install the pack");
    }

    FileUtils.copyFile(oldProfile, new File(mcDir, "/launcher_Profiles_backup.json"), true);
    System.out.println("Creating A Backup Of User Profiles");

    JdomParser profileParser = new JdomParser();
    JsonRootNode profileNode = null;
    try
    {
      Reader reader = new FileReader(oldProfile);

      profileNode = profileParser.parse(reader);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    JsonField[] fields = { JsonNodeFactories.field("name", JsonNodeFactories.string(InstallerUtils.settings.getProfileName())), JsonNodeFactories.field("lastVersionId", JsonNodeFactories.string(InstallerUtils.settings.getJarVersion())), JsonNodeFactories.field("gameDir", JsonNodeFactories.string(installDir)), JsonNodeFactories.field("javaArgs", JsonNodeFactories.string("-XX:MaxPermSize=1024m -Xmx1G")) };

    if (profileNode != null)
    {
      HashMap profileCopy = Maps.newHashMap(profileNode.getNode(new Object[] { "profiles" }).getFields());
      HashMap rootCopy = Maps.newHashMap(profileNode.getFields());
      profileCopy.put(JsonNodeFactories.string(InstallerUtils.settings.getProfileName()), JsonNodeFactories.object(fields));
      JsonRootNode profileJsonCopy = JsonNodeFactories.object(profileCopy);

      rootCopy.put(JsonNodeFactories.string("profiles"), profileJsonCopy);

      profileNode = JsonNodeFactories.object(rootCopy);
    }

    try
    {
      BufferedWriter newWriter = Files.newWriter(oldProfile, Charsets.UTF_8);
      PrettyJsonFormatter.fieldOrderPreservingPrettyJsonFormatter().format(profileNode, newWriter);
      newWriter.close();
    }
    catch (Exception e) {
      e.printStackTrace();
      InstallerUtils.shutdown(e);
    }

    System.out.print("Adding DNS Profile");
    InstallerPanel.progress.setValue(90);
  }
}
