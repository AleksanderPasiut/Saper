package saper;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.Timer;

public class TimerDisplay extends JLabel implements ActionListener
{
    Timer timer;
    int value;
    
    final void setValue(int value)
    {
        this.value = value;
        super.setText(Integer.toString(value/60)+":"+String.format("%02d", value%60));
    }
    
    public TimerDisplay()
    {
        setValue(0);
        super.setHorizontalAlignment(JLabel.CENTER);
        super.setFont(new Font("Lucida Console", Font.PLAIN, 18));
        super.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                
        timer = new Timer(1000, this);
    }
    
    public void start() { timer.start(); }
    public void pause() { timer.stop(); }
    public void stop() { timer.stop(); setValue(0); }
    
    @Override public void actionPerformed(ActionEvent event)
    {
        setValue(++value);
        timer.restart();
    }
}
