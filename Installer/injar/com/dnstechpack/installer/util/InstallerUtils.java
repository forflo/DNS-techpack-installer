package com.dnstechpack.installer.util;

/* modified version (author: florian mayer) */
/* fixed compatibility bug with System.lineSeparator()
this Method does not exist prior Java 7 */

import com.dnstechpack.installer.settings.Settings;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.io.FileUtils;

/* Modified Version of the original installer utils 
	Author: Florian Mayer

Added compatibility for multiple operating systems. Prior this modification
only windows was supported. (The installer crashed, if you used it outside the
windows platform)
*/

public class InstallerUtils
{
  public static File appData = getAppData();
  public static File dnsDefault = new File(appData, "/dns");
  public static File mcDefault = new File(appData, "/minecraft");
  public static File tmpDir = new File("./tmp.dns");
  public static Settings settings = new Settings();
  public static String newLine = System.getProperty("line.separator");

  public static void moveFolder(File src, File dest)
    throws IOException
  {
    FileUtils.moveDirectory(src, dest);
  }

	public static File getAppData(){
		String temp = System.getProperty("os.name");
		if(temp.equals("Mac OS X")){
			System.out.println("You're running on Mac OS X");
			System.out.println("Setting your path to /Users/florian/Library/Application Support/");
			return new File("/Users/" + System.getProperty("user.name") + "/Library/Application Support/");
		} else if(temp.equals("Windows 7") || temp.equals("Windows 8")) {
			System.out.println("You're running on Windows 7 or Windows 8");
			System.out.println("Setting your path to your standard %APPDATA%-Folder");
			return new File(System.getenv("APPDATA"));
		} else if(temp.equals("Linux")) {
			System.out.println("You're running on Linux. Good choice!");
			System.out.println("Setting your path to your home Folder. You have to adjust it to your needs!");
			return new File("/home/" + System.getProperty("user.name"));
		} else {
			System.out.println("You're currently running on " + temp + "!");
			System.out.println("... and while this shows your good taste it means that you cannot use this software, because it\n has'nt been ported yet. We're sorry...");
			System.exit(0);
			return null;
		}
	}

  public static void shutdown(Exception ex)
  {
	System.out.println("Shutting down...");
    if (ex != null)
    {
      try
      {
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd_kk-mm-ss");
        Date date = new Date();
        String theDate = format.format(date);

        File writeTo = new File("./crash-logs/" + theDate + ".log");
        writeTo.getParentFile().mkdir();
        writeTo.createNewFile();

        FileWriter writer = new FileWriter(writeTo, true);
        PrintWriter print = new PrintWriter(writer);
        writer.write("DNS Installer Crash Report" + newLine);
        writer.write("Please Send To A Rep At http://esper.net/ in channel #dns" + newLine);

        ex.printStackTrace(print);

        print.close();
        writer.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
    System.exit(-1);
  }

  public static void unZip(File zip, File destDir)
  {
    try
    {
      ZipFile zipFile = new ZipFile(zip);
      Enumeration enu = zipFile.entries();

      while (enu.hasMoreElements())
      {
        ZipEntry zipEntry = (ZipEntry)enu.nextElement();

        String name = zipEntry.getName();
        long size = zipEntry.getSize();
        long compressedSize = zipEntry.getCompressedSize();
        System.out.printf("name: %-20s | size: %6d | compressed size: %6d\n", new Object[] { name, Long.valueOf(size), Long.valueOf(compressedSize) });

        File file = null;

        if (destDir == null)
        {
          file = new File(tmpDir.getAbsolutePath(), name);
        }
        else {
          file = new File(destDir, name);
        }
        if (name.endsWith("/"))
        {
          file.mkdirs();
        }
        else
        {
          File parent = file.getParentFile();
          if (parent != null)
          {
            parent.mkdirs();
          }

          InputStream is = zipFile.getInputStream(zipEntry);
          FileOutputStream fos = new FileOutputStream(file);
          byte[] bytes = new byte[1024];
          int length;
          while ((length = is.read(bytes)) >= 0)
          {
            fos.write(bytes, 0, length);
          }
          is.close();
          fos.close();
        }
      }
      zipFile.close();
    }
    catch (Exception e1)
    {
      e1.printStackTrace();
      shutdown(e1);
    }
  }

  public static void unZip(File zip)
  {
    unZip(zip, null);
  }

  static
  {
    if (!dnsDefault.exists())
    {
      dnsDefault.mkdirs();
    }

    if (!mcDefault.exists())
    {
      mcDefault.mkdirs();
    }

    if (!tmpDir.exists())
    {
      tmpDir.mkdirs();
    }
  }
}
