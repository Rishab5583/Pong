package Pong;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Pong.pong.repaint(g);
    }
}