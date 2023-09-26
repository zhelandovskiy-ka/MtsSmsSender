package gui;

import javax.swing.*;
import java.awt.*;

/**
 * шаблон для создания окна с круглыми краями
 */
public class RoundArea extends JTextArea {
    public RoundArea(String text) {
        super(text);

        Dimension size = getPreferredSize();
        size.width = size.height = Math.max(size.width, size.height);
        setPreferredSize(size);

//        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 13, 13);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 13, 13);
    }
}
