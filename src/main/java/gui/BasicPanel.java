package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BasicPanel extends JPanel {
    public BasicPanel() {
        setLayout(new GridBagLayout());
        setBorder(new LineBorder(Color.GRAY, 0, true));
        setBackground(Color.WHITE);
    }

    public void setNullBorder() {
        setBorder(new LineBorder(Color.BLACK, 0, true));
    }
}
