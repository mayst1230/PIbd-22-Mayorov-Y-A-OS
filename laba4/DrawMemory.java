package com.company;

import javax.swing.*;
import java.awt.*;

public class DrawMemory extends JPanel {
    private Memory memory;

    public DrawMemory(Memory physMemory) {
       this.memory = physMemory;
    }

    public void paint(Graphics g) {
        int sizeX = 30;
        int x = 0;
        int y = 0;
        for (int i = 0; i < memory.getCellsSize(); i++) {
            if (x + sizeX >= 900 - sizeX) {
                x = 0;
                y += sizeX;
            }
            if (memory.getCellIndex(i).getStatus() == 2) {
                g.setColor(Color.red);
            } else if (memory.getCellIndex(i).getStatus() == 0) {
                g.setColor(Color.gray);
            } else {
                g.setColor(Color.blue);
            }
            g.fillRect(x, y, sizeX, sizeX);
            g.setColor(Color.black);
            g.drawRect(x, y, sizeX, sizeX);
            x += sizeX;
        }
    }
}