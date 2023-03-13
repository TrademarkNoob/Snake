package org.example;

import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame() {

        this.add(new GamePanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Snake");
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
