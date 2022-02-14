package game;

import javax.swing.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import utils.*;

public class Display extends JFrame implements ActionListener {
    private Dimension size = new Dimension(1000, 600);
    private Game game;
    private Component menu;
    private Timer time;
    private USound usWelcome;

    public Display() {
        newMenu("newgame");
        setting();

        // |set sound welcome
        usWelcome = new USound("สวัสดีผู้เล่นทุกๆท่าน");
        usWelcome.start();
    }

    private void setting() {
        this.setTitle("Murder Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(size);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void removeGame() {
        this.remove(game);
        game = null;
        this.getContentPane().repaint();
    }

    private void removeMenu() {
        this.remove(menu);
        menu = null;
        this.getContentPane().repaint();
    }

    public void endGame(String status) {
        removeGame();
        newMenu(status);
    }

    public void newGame() {
        game = new Game();
        this.getContentPane().add(game);
        game.requestFocus();
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void newMenu(String status) {
        menu = new Menu(status, this);
        this.add(menu);
        menu.requestFocus();

        time = new Timer(10, this);
        time.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Menu.progressNewGame > 50) {
            Menu.progressNewGame = 0;
            removeMenu();
            newGame();
            time.stop();
        }
        if (Menu.progressNewGame > 10) {
            usWelcome.stop(true);
        }
    }
}
