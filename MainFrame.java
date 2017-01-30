package saper;

import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class MainFrame extends JFrame
{    
    GameSettings gameSettings;
    FieldsPanel fieldsPanel;
    MarkedBombCounter markedBombCounter;
    TimerDisplay timer;
    MainFrameMenu mainFrameMenu;
        
    public MainFrame()
    {
        super.setTitle("Saper");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int size = Integer.min(screenSize.height, screenSize.width)*4/5;
        super.setSize(size, size);
        
        super.setLayout(new BorderLayout());
        
        gameSettings = new GameSettings();
        
        JPanel pageEndMenu = new JPanel(new GridLayout(1, 2));
        markedBombCounter = new MarkedBombCounter(gameSettings.bombsAmount);
        pageEndMenu.add(markedBombCounter);
        timer = new TimerDisplay();
        pageEndMenu.add(timer);
        super.add(pageEndMenu, BorderLayout.PAGE_END);
        
        JPanel overFieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel = new FieldsPanel(this, gameSettings, markedBombCounter, timer);
        fieldsPanel.overPanel = overFieldsPanel;
        overFieldsPanel.add(fieldsPanel);
        super.add(overFieldsPanel, BorderLayout.CENTER);  
        
        mainFrameMenu = new MainFrameMenu(gameSettings, fieldsPanel);
        super.setJMenuBar(mainFrameMenu);
        
        super.setLocationRelativeTo(null);
        super.setVisible(true);
    }
}
