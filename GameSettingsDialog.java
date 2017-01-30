package saper;

import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameSettingsDialog extends JDialog
{
    GameSettings gameSettings;
    
    JTextField widthInput;
    JTextField heightInput;
    JTextField bombsAmountInput;
    JButton buttonOK;
    
    final void initWidthPanel()
    {
        JPanel widthPanel = new JPanel();
        widthPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        super.add(widthPanel);

        JTextField widthInfo = new JTextField("Board width: ");
        widthInfo.setEditable(false);
        widthInfo.setOpaque(false);
        widthPanel.add(widthInfo);

        widthInput = new JTextField("", 6);
        widthInput.setMargin(new Insets(2, 2, 2, 2));
        widthInput.setText(Integer.toString(gameSettings.width));
        widthPanel.add(widthInput);
    }
    final void initHeightPanel()
    {
        JPanel heightPanel = new JPanel();
        heightPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        super.add(heightPanel);

        JTextField heightInfo = new JTextField("Board height: ");
        heightInfo.setBorder(BorderFactory.createEmptyBorder());
        heightInfo.setEditable(false);
        heightInfo.setOpaque(false);
        heightPanel.add(heightInfo);

        heightInput = new JTextField("", 6);
        heightInput.setMargin(new Insets(2, 2, 2, 2));
        heightInput.setText(Integer.toString(gameSettings.height));
        heightPanel.add(heightInput);
    }
    final void initBombsAmountPanel()
    {
        JPanel bombsAmountPanel = new JPanel();
        bombsAmountPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        super.add(bombsAmountPanel);

        JTextField bombsAmountInfo = new JTextField("Bombs amount: ");
        bombsAmountInfo.setBorder(BorderFactory.createEmptyBorder());
        bombsAmountInfo.setEditable(false);
        bombsAmountInfo.setOpaque(false);
        bombsAmountPanel.add(bombsAmountInfo);

        bombsAmountInput = new JTextField("", 6);
        bombsAmountInput.setMargin(new Insets(2, 2, 2, 2));
        bombsAmountInput.setText(Integer.toString(gameSettings.bombsAmount));
        bombsAmountPanel.add(bombsAmountInput);
    }
    final void initButtonOK()
    {
        JPanel buttonOKPanel = new JPanel();
        buttonOKPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        super.add(buttonOKPanel);
        
        buttonOK = new JButton("OK");
        buttonOK.setActionCommand("button_OK");
        buttonOK.addActionListener(gameSettings);
        buttonOK.setFocusPainted(false);
        buttonOKPanel.add(buttonOK);
    }
    
    GameSettingsDialog(GameSettings gameSettings)
    {
        super(Saper.mainFrame, "Settings", true);
        
        this.gameSettings = gameSettings;
        super.setLayout(new GridLayout(4, 1));
        
        initWidthPanel();
        initHeightPanel();
        initBombsAmountPanel();
        initButtonOK();
        
        super.pack();
        super.setLocationRelativeTo(null);
        super.setResizable(false);
    }
}
