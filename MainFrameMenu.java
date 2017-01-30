package saper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrameMenu extends JMenuBar implements ActionListener
{
    GameSettings gameSettings;
    FieldsPanel fieldsPanel;
    
    JMenuItem newGameMenuItem;
    JMenuItem settingsMenuItem;
    JMenuItem exitMenuItem;
    
    JMenuItem aboutMenuItem;
    
    @SuppressWarnings("LeakingThisInConstructor")
    MainFrameMenu(GameSettings gameSettings, FieldsPanel fieldsPanel)
    {
        this.gameSettings = gameSettings;
        this.fieldsPanel = fieldsPanel;
        
        JMenu gameMenu = new JMenu("Game");
            newGameMenuItem = new JMenuItem("New game");
            gameMenu.add(newGameMenuItem);
            settingsMenuItem = new JMenuItem("Settings");
            gameMenu.add(settingsMenuItem);
            exitMenuItem = new JMenuItem("Exit");
            gameMenu.add(exitMenuItem);
        super.add(gameMenu);
        
        JMenu aboutMenu = new JMenu("About");
            aboutMenuItem = new JMenuItem("About");
            aboutMenu.add(aboutMenuItem);
        super.add(aboutMenu);
        
        newGameMenuItem.addActionListener(this);
        settingsMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);
        aboutMenuItem.addActionListener(this);
    }
    
    @Override public void actionPerformed(ActionEvent event)
    {        
        if (event.getSource() == newGameMenuItem)
        {
            fieldsPanel.newGame();
        }
        else if (event.getSource() == settingsMenuItem)
        {
            gameSettings.dialog();
            fieldsPanel.newGame();
        }
        else if (event.getSource() == exitMenuItem)
        {
            System.exit(0);
        }
        else if (event.getSource() == aboutMenuItem)
        {
            
        }
    }
}
