package com.dnstechpack.installer;

import com.dnstechpack.installer.gui.InstallerFrame;
import com.dnstechpack.installer.settings.Settings;
import com.dnstechpack.installer.util.InstallerUtils;
import java.io.PrintStream;
import javax.swing.SwingUtilities;


/*old
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          new InstallerFrame();
        }
        catch (Exception e) {
          e.printStackTrace();
          InstallerUtils.shutdown(e);
        }
      }
    });

*/

/* Modified Version
	Author: Florian Mayer

Added some messages for commandline users
*/

public class Installer {
	public static void main(String[] args) {
		try {
			System.out.println("[main] Initialize GUI ...");
		  	new InstallerFrame();
		} catch (Exception e) {
			System.out.println("[main] A Critical error has occurred");
			e.printStackTrace();
			InstallerUtils.shutdown(e);
		}
	System.out.println("ModPack Is For Version " + InstallerUtils.settings.getMCVersion());
  }
}
