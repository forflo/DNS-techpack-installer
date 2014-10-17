package com.dnstechpack.installer.gui;

import com.dnstechpack.installer.Installer;
import com.dnstechpack.installer.install.InstallPack;
import com.dnstechpack.installer.util.InstallerUtils;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class InstallerPanel extends JPanel
{
  public static JTextField installDir;
  public static JTextField mcDir;
  public static JButton install;
  public static JProgressBar progress;

  public InstallerPanel()
  {
    setLayout(null);

    installDir = new JTextField();
    mcDir = new JTextField();

    JLabel insLabel = new JLabel("ModPack Install Directory");
    JLabel mcLabel = new JLabel("Base Minecraft Directory");

    insLabel.setBounds(20, 240, 200, 20);
    add(insLabel);

    mcLabel.setBounds(20, 300, 200, 20);
    add(mcLabel);
    try
    {
      installDir.setText(InstallerUtils.dnsDefault.getCanonicalPath());
      mcDir.setText(InstallerUtils.mcDefault.getCanonicalPath());
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    installDir.setEditable(true);
    installDir.setBounds(20, 260, 200, 20);
    add(installDir);

    JButton installBrowse = new JButton();
    installBrowse.setText("Browse");
    installBrowse.setBounds(240, 260, 100, 20);
    installBrowse.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        if (e.getButton() == 1)
        {
          new FileChooser(InstallerPanel.installDir, InstallerUtils.dnsDefault);
        }
      }
    });
    add(installBrowse);

    JButton mcBrowse = new JButton();
    mcBrowse.setText("Browse");
    mcBrowse.setBounds(240, 320, 100, 20);
    mcBrowse.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        if (e.getButton() == 1)
        {
          new FileChooser(InstallerPanel.mcDir, InstallerUtils.mcDefault);
        }
      }
    });
    add(mcBrowse);

    mcDir.setEditable(true);
    mcDir.setBounds(20, 320, 200, 20);
    add(mcDir);

    install = new JButton();
    install.setText("Install");
    install.setBounds(240, 380, 100, 20);
    install.addMouseListener(new InstallPack());
    add(install);

    progress = new JProgressBar(0, 100);
    progress.setBounds(20, 355, 320, 10);
    add(progress);
  }

  public void paint(Graphics g)
  {
    super.paint(g);
    try
    {
      Image image = ImageIO.read(Installer.class.getResourceAsStream("/images/dnsImage.png"));

      if (g != null)
      {
        g.drawImage(image, 30, 20, 300, 200, null);
        g.dispose();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
