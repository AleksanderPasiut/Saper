package saper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Field extends JButton implements MouseListener
{   
    static Font font;
    static ImageIcon bombIcon;
    static ImageIcon flagIcon;
    static ImageIcon falseIcon;
    static FieldsPanel fieldsPanel;
    
    static Color crCovered = Color.getHSBColor(0.0f, 0.0f, 0.8f);
    static Color crUncovered = Color.getHSBColor(0.0f, 0.0f, 0.9f);
    static Color crRed = Color.getHSBColor(0.0f, 1.0f, 1.0f);
        
    static Color crt[] = { Color.getHSBColor(0.0f, 0.0f, 0.0f),  // black
                           Color.getHSBColor(0.67f, 1.0f, 0.8f), // 1
                           Color.getHSBColor(0.33f, 1.0f, 0.8f), // 2
                           Color.getHSBColor(0.0f, 1.0f, 0.8f),  // 3
                           Color.getHSBColor(0.67f, 1.0f, 0.4f), // 4
                           Color.getHSBColor(0.0f, 1.0f, 0.4f),  // 5
                           Color.getHSBColor(0.44f, 1.0f, 0.8f), // 6
                           Color.getHSBColor(0.67f, 1.0f, 0.3f), // 7
                           Color.getHSBColor(0.0f, 0.0f, 0.0f)}; // 8
    
    
    public enum State { COVERED, UNCOVERED, MARKED, BOMB_SHOWN, FALSE_MARKED };
    Boolean bomb = false;
    State state = State.COVERED;
    int x;
    int y;
    int neighbours = 0;
    
    final void updateAppearance()
    {
        switch(state)
        {
            case COVERED:
            {
                setBackground(crCovered);
                setIcon(null);
                setText(null);
                break;
            }
            case UNCOVERED:
            {
                if (bomb)
                {
                    setBackground(crRed);
                    setIcon(bombIcon);
                    setText(null);
                }
                else
                {
                    setBackground(crUncovered);
                    setForeground(crt[neighbours]);
                    setText(neighbours != 0 ? Integer.toString(neighbours) : null);
                }       
                break;
            }
            case MARKED:
            {
                setBackground(crCovered);
                setIcon(flagIcon);
                setText(null);
                break;
            }
            case BOMB_SHOWN:
            {
                setBackground(crUncovered);
                setIcon(bombIcon);
                setText(null);
                break;
            }
            case FALSE_MARKED:
            {
                setBackground(crUncovered);
                setIcon(falseIcon);
                setText(null);
                break;
            }
        }
    }
    final void updateGraphics()
    {
        super.setFont(font);
        updateAppearance();
    }
    
    public Field(Boolean bomb, int x, int y)
    {
        this.bomb = bomb;
        this.x = x;
        this.y = y;
        super.addMouseListener(this);
        super.setMargin(new Insets(0, 0, 0, 0));
        super.setFocusPainted(false);
        updateGraphics();
    }    
    public void swapBombs(Field another)
    {
        Boolean tmp = this.bomb;
        this.bomb = another.bomb;
        another.bomb = tmp;
    }
    public void setState(State state)
    {
        this.state = state;
        updateAppearance();
    }
    public void setNeighboursAmount(int amount) { neighbours = amount; }
    public Boolean bomb() { return bomb; }
    
    @Override public void mouseClicked(MouseEvent event) {}
    @Override public void mouseEntered(MouseEvent event) {}
    @Override public void mouseExited(MouseEvent event) {}
    @Override public void mouseReleased(MouseEvent event) {}
    @Override public void mousePressed(MouseEvent event)
    {
        switch(event.getButton())
        {
            case MouseEvent.BUTTON1: fieldsPanel.leftClick(x, y); break;
            case MouseEvent.BUTTON3: fieldsPanel.rightClick(x, y); break;
        }
    }
    
}
