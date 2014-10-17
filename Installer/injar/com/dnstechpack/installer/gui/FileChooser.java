package com.dnstechpack.installer.gui;

import com.dnstechpack.installer.settings.Settings;
import com.dnstechpack.installer.util.InstallerUtils;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FileChooser extends JFrame
{
  protected static FileBrowser browser;
  public static FileChooser INSTANCE;

  public FileChooser(JTextField field, File defaultDir)
  {
    setTitle("Choose Directory");
    setSize(new Dimension(500, 400));
    setLocationRelativeTo(null);
    setResizable(true);
    setVisible(true);

    browser = new FileBrowser(defaultDir, field);
    browser.setFileSelectionMode(1);
    getContentPane().add(browser);

    INSTANCE = this;
  }

  public class FileBrowser extends JFileChooser
  {
    private JTextField textBox;
    private File defaultDir;

    public FileBrowser(File defaultDir, JTextField textBox) {
      super();
      this.textBox = textBox;
      this.defaultDir = defaultDir;
      setupCustom();
    }

    public void setupCustom()
    {
      addActionListener(new ActionListener()
      {
        boolean allowed = true;

        public void actionPerformed(ActionEvent e)
        {
          if (e.getActionCommand().equals("ApproveSelection"))
          {
            try
            {
              if (FileChooser.FileBrowser.this.defaultDir == InstallerUtils.mcDefault)
              {
                File file = new File(FileChooser.browser.getSelectedFile(), "versions/" + InstallerUtils.settings.getMCVersion() + "/" + InstallerUtils.settings.getMCVersion() + ".jar");

                if (!file.exists())
                {
                  this.allowed = false;
                  JOptionPane.showMessageDialog(null, "Make Sure You Have Run The Launcher Once");
                  return;
                }
              }

              if (this.allowed)
              {
                FileChooser.browser.textBox.setText(FileChooser.browser.getSelectedFile().getCanonicalPath());
                FileChooser.INSTANCE.setVisible(false);
              }
            }
            catch (IOException e1) {
              e1.printStackTrace();
            }
          }
          else if (e.getActionCommand().equals("CancelSelection"))
          {
            FileChooser.INSTANCE.setVisible(false);
          }
        }
      });
    }
  }
}
