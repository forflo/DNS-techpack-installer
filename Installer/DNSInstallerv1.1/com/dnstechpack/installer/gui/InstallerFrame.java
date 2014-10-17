package com.dnstechpack.installer.gui;

import com.dnstechpack.installer.util.InstallerUtils;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class InstallerFrame extends JFrame {
  public InstallerFrame()
    throws Exception
  {
    super("DNS Techpack Installer");
    setSize(new Dimension(365, 450));
    setDefaultCloseOperation(0);
    addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
        InstallerUtils.shutdown(null);
      }
    });
    setLocationRelativeTo(null);
    setResizable(true);
    setVisible(true);

    InstallerPanel mainPanel = new InstallerPanel();
    this.add(mainPanel);
  }
}
