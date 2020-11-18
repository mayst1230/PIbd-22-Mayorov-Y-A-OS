package com.company;

import javax.swing.*;
import java.awt.*;

public class DrawMemory extends JPanel {
    private CellsClass[] cells;

    public DrawMemory(Memory physMemory) {
        cells = physMemory.getCells();
    }

    public void paint(Graphics g) {
        int sizeX = 30;
        int x = 0;
        int y = 0;
        for (int i = 0; i < cells.length; i++) {
            if (x + sizeX >= 900 - sizeX) {
                x = 0;
                y += sizeX;
            }
            if (cells[i].getStatus() == 2) {
                g.setColor(Color.red);
            } else if (cells[i].getStatus() == 0) {
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