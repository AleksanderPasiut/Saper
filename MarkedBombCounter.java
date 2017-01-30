package saper;

import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class MarkedBombCounter extends JLabel
{
    int value;
    
    public final void setValue(int value)
    {
        this.value = value;
        setText("Remaining bombs: "+Integer.toString(value));
    }
    public final void decValue()
    {
        setValue(value-1);
    }
    public final void incValue()
    {
        setValue(value+1);
    }
    
    public MarkedBombCounter(int value)
    {
        setValue(value);
        super.setFont(new Font("Lucida Console", Font.PLAIN, 18));
        super.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
}
