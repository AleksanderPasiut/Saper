package saper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSettings implements ActionListener
{
    GameSettingsDialog gameSettingsDialog;
    
    int width;
    int height;
    int bombsAmount;
    
    public GameSettings()
    {
        width = 30;
        height = 16;
        bombsAmount = 99;
    }
    
    public int fieldsAmount() { return width*height; }
    
    public void dialog()
    {
        gameSettingsDialog = new GameSettingsDialog(this);
        gameSettingsDialog.setVisible(true);
    }
    
    @Override public void actionPerformed(ActionEvent event)
    {
        String cmd = event.getActionCommand();
        
        switch (cmd)
        {
            case "button_OK":
                width = Integer.parseInt(gameSettingsDialog.widthInput.getText());
                height = Integer.parseInt(gameSettingsDialog.heightInput.getText());
                bombsAmount = Integer.parseInt(gameSettingsDialog.bombsAmountInput.getText());
                gameSettingsDialog.dispose();
                break;
            default:
                break;
        }
    }
}
