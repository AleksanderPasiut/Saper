package saper;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Random;
import javax.swing.ImageIcon;

public class FieldsPanel extends JPanel implements ComponentListener
{
    GameSettings gameSettings;
    MarkedBombCounter markedBombCounter;
    TimerDisplay timerDisplay;
    
    Field field[][];
    JPanel overPanel;
    JPanel panel;
    Boolean enable = true;
    
    final void initFields()
    {
        super.setLayout(new GridLayout(gameSettings.height, gameSettings.width));
        field = new Field[gameSettings.height][gameSettings.width];
        int k = 0;
        for (int i = 0; i < gameSettings.height; i++)
            for (int j = 0; j < gameSettings.width; j++)
            {
                field[i][j] = new Field(k++ < gameSettings.bombsAmount, i, j);
                super.add(field[i][j]);
            }
    }
    final void randomizeFields()
    {
        Random generator = new Random();
        for (int i = 0; i < 10*gameSettings.fieldsAmount(); i++)
        {
            Field f1 = field[generator.nextInt(gameSettings.height)][generator.nextInt(gameSettings.width)];
            Field f2 = field[generator.nextInt(gameSettings.height)][generator.nextInt(gameSettings.width)];
            f1.swapBombs(f2);
        }
    }
    final void setNeighbours()
    {
        for (int i = 0; i < gameSettings.height; i++)
            for (int j = 0; j < gameSettings.width; j++)
            {
                if (field[i][j].bomb())
                    continue;
                
                int neighbours = 0;
                for (int k = Integer.max(0, i-1); k < Integer.min(gameSettings.height, i+2); k++)
                    for (int m = Integer.max(0, j-1); m < Integer.min(gameSettings.width, j+2); m++)
                        if ((k != i || m != j) && field[k][m].bomb())
                            neighbours++;
                
                field[i][j].setNeighboursAmount(neighbours);
            }
        
        for (int i = 0; i < gameSettings.height; i++)
            for (int j = 0; j < gameSettings.width; j++)
                field[i][j].updateAppearance();
    }
    final void updateFieldGraphics()
    {
        Dimension dim = field[0][0].getSize();
        int z = Integer.min(dim.width, dim.height);
        
        Field.font = new Font("Lucida Console", Font.PLAIN, z*2/3);
        
        ImageIcon tmp1 = new ImageIcon("src\\saper\\bomb.gif");
        Image img1 = tmp1.getImage();
        Field.bombIcon = new ImageIcon(img1.getScaledInstance(z, z, Image.SCALE_SMOOTH));
        
        ImageIcon tmp2 = new ImageIcon("src\\saper\\flag.gif");
        Image img2 = tmp2.getImage();
        Field.flagIcon = new ImageIcon(img2.getScaledInstance(z, z, Image.SCALE_SMOOTH));
        
        ImageIcon tmp3 = new ImageIcon("src\\saper\\false.gif");
        Image img3 = tmp3.getImage();
        Field.falseIcon = new ImageIcon(img3.getScaledInstance(z, z, Image.SCALE_SMOOTH));
        
        for (int i = 0; i < gameSettings.height; i++)
            for (int j = 0; j < gameSettings.width; j++)
                field[i][j].updateGraphics();
    }
    
    @SuppressWarnings("LeakingThisInConstructor")
    public FieldsPanel(MainFrame mainFrame, GameSettings gameSettings, MarkedBombCounter markedBombCounter, TimerDisplay timerDisplay)
    {
        this.gameSettings = gameSettings;
        this.markedBombCounter = markedBombCounter;
        this.timerDisplay = timerDisplay;
        
        super.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        initFields();
        randomizeFields();
        setNeighbours();
        
        Field.fieldsPanel = this;
        super.addComponentListener(this);
    }
    
    void newGame()
    {
        removeAll();
        initFields();
        randomizeFields();
        setNeighbours();
        enable = true;
        setBackground(null);
        overPanel.setBackground(null);
        markedBombCounter.setValue(gameSettings.bombsAmount);
        timerDisplay.stop();
    }        
    void nullClick(int x, int y)
    {
        if (field[x][y].state == Field.State.COVERED && field[x][y].neighbours == 0 && field[x][y].bomb == false)
        {
            field[x][y].setState(Field.State.UNCOVERED);
            
            for (int i = Integer.max(0, x-1); i < Integer.min(gameSettings.height, x+2); i++)
                for (int j = Integer.max(0, y-1); j < Integer.min(gameSettings.width, y+2); j++)
                    if ((i != x || j != y) && field[i][j].state == Field.State.COVERED)
                    {
                        if (field[i][j].neighbours == 0)
                            nullClick(i, j);
                        else field[i][j].setState(Field.State.UNCOVERED);
                    }
        }
    }
    Boolean checkForWin()
    {
        for (int i = 0; i < gameSettings.height; i++)
            for (int j = 0; j < gameSettings.width; j++)
                if (field[i][j].bomb == false && field[i][j].state == Field.State.COVERED)
                    return false;
        
        return true;
    }
    void win()
    {
        enable = false;
        timerDisplay.pause();
        Color winColor = Color.getHSBColor(0.33f, 0.8f, 0.8f);
        setBackground(winColor);
        overPanel.setBackground(winColor);
    }
    void loss()
    {   
        enable = false;
        for (int i = 0; i < gameSettings.height; i++)
            for (int j = 0; j < gameSettings.width; j++)
                switch(field[i][j].state)
                {
                    case COVERED:
                        if (field[i][j].bomb)
                            field[i][j].setState(Field.State.BOMB_SHOWN);
                        break;
                    case MARKED:
                        if (!field[i][j].bomb)
                            field[i][j].setState(Field.State.FALSE_MARKED);
                        break;
                }
        
        timerDisplay.pause();
        Color lossColor = Color.getHSBColor(0.0f, 0.8f, 0.8f);
        setBackground(lossColor);
        overPanel.setBackground(lossColor);
    }
    void leftClick(int x, int y)
    {
        if (!enable)
            return;
        
        switch(field[x][y].state)
        {
            case COVERED:
            {
                nullClick(x, y);
                field[x][y].setState(Field.State.UNCOVERED);
                timerDisplay.start();
                
                if (field[x][y].bomb)
                    loss();
                else if (checkForWin())
                    win();
                
                break;
            }
        }
    }
    void rightClick(int x, int y)
    {   
        if (!enable)
            return;
        
        switch(field[x][y].state)
        {
            case COVERED: field[x][y].setState(Field.State.MARKED); markedBombCounter.decValue(); break;
            case MARKED: field[x][y].setState(Field.State.COVERED); markedBombCounter.incValue(); break;
        }
    }
    
    @Override public Dimension getPreferredSize()
    {
        Dimension dim = getParent().getSize();
        int z = Integer.min(dim.width / gameSettings.width, dim.height / gameSettings.height);
        return new Dimension(gameSettings.width*z, gameSettings.height*z);
    }
    @Override public void componentHidden(ComponentEvent e) {}
    @Override public void componentShown(ComponentEvent e) {}
    @Override public void componentMoved(ComponentEvent e) {}
    @Override public void componentResized(ComponentEvent e) { updateFieldGraphics(); }
}
