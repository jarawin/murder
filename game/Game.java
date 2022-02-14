package game;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import utils.*;

public class Game extends JPanel implements ActionListener {
    // |set necsecsary game
    private Dude dude;
    private King king;
    private USound usBG;
    private Image bgImg;
    private Timer time;

    // |set position background
    private int deltaX = 0;
    private int deltaY = 0;
    private int xAxis = 0;
    private int yAxis = 0;

    // |set default game
    private static Display display;
    private boolean BGslide = true;
    private int gameSpeed = 6;

    // | -=-=-=-=-=-=- MAIN -=-=-=-=-=-=- | \\
    public static void main(String[] args) {
        display = new Display();
    }

    // | -=-=-=-=-=-=- CONSTRUCTURE-=-=-=-=-=-=- | \\
    public Game() {
        addKeyListener(new Action());
        setFocusable(true);
        setNewGame();
    }

    // | -=-=-=-=-=-=- SET DATA -=-=-=-=-=-=- | \\
    public void setNewGame() {
        // |set character
        dude = new Dude(new URandom(5, 1).get());
        king = new King(new URandom(8, 6).get());

        // |set sound background
        usBG = new USound("Happypappyend");
        usBG.loop(true);
        usBG.start();

        // |set background
        bgImg = new UPicture("background", new URandom(4, 1).get()).get().getImage();

        // |set loop of game
        time = new Timer(gameSpeed, this);
        time.start();
    }

    public void setEndGame(String status) {
        if (status == "winner" || status == "defeated") {
            usBG.loop(false);
            usBG.stop(true);
            time.stop();
            display.endGame(status);
        }
    }

    public void setBGslide(boolean BGslide) {
        this.BGslide = BGslide;
    }

    // | -=-=-=-=-=-=- LOOP GAME -=-=-=-=-=-=- | \\
    public void actionPerformed(ActionEvent e) {
        setBGslide(!Dude.BGbreak && !King.BGbreak);

        dude.setKingStatus(King.status);
        dude.animate();

        king.setDudeStatus(Dude.status);
        king.animate();

        setEndGame(Dude.status);
        setEndGame(King.status);

        repaint();
    }

    // | -=-=-=-=-=-=- KEY EVENT -=-=-=-=-=-=- | \\
    private class Action extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
            dude.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            dude.keyPressed(e);
        }
    }

    // | -=-=-=-=-=-=- GRAPHICS -=-=-=-=-=-=- | \\
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        // |draw background
        g2d.drawImage(bgImg, xAxis - deltaX, yAxis - deltaY, null);
        g2d.drawImage(bgImg, xAxis - deltaX, yAxis - deltaY, null);
        g2d.drawImage(bgImg, xAxis - deltaX + 1000, yAxis - deltaY, null);

        // |set slide background
        if (BGslide) {
            deltaX += 1;

            if (deltaX >= 1000) {
                deltaX = 0;
            }
        }

        // |draw progress
        if (Dude.progress >= 0) {
            drawProgress(g2d);
        }

        drawDevStatus(g2d);

        // |draw enemy
        g2d.drawImage(king.getImage(), King.xAxis - King.deltaX, King.yAxis - King.deltaY, King.SIZE, King.SIZE, null);

        // |draw dude
        g2d.drawImage(dude.getImage(), Dude.xAxis - Dude.deltaX, Dude.yAxis - Dude.deltaY, Dude.SIZE, Dude.SIZE, null);
    }

    private void drawProgress(Graphics2D g2d) {
        // |set drawing
        if (Dude.type == "dude") {
            g2d.drawImage(new UPicture("element", "sword").get().getImage(), 15, 20, 20, 20, null); // draw sword
            g2d.setColor(new Color(241, 98, 69)); // orange progress bar
        } else {
            g2d.drawImage(new UPicture("element", "heart").get().getImage(), 15, 20, 20, 20, null); // draw Heart
            g2d.setColor(new Color(61, 174, 0)); // green progress bar
        }

        // |draw progress bar
        g2d.fillRect(50, 20, Dude.progress, 20);

        // |set drawing
        g2d.setColor(Color.white); // color of text & frame
        g2d.setFont(new UFont(30).get()); // font of text

        // |draw lifetime text
        if (Dude.type == "king") {
            g2d.drawString("Fight Time : " + Dude.fightTime, 750, 40);
        }

        // |set drawing
        g2d.setFont(new UFont(15).get()); // font of text
        g2d.setStroke(new BasicStroke(6.0f, 2, 2)); // size of frame

        // |draw frame
        g2d.drawRect(50, 20, 200, 20);

        // |draw progress text
        g2d.drawString(String.valueOf(Dude.progress), 60, 35);
    }

    private void drawDevStatus(Graphics2D g2d) {
        Font f30 = new UFont(30).get();
        Font f15 = new UFont(15).get();

        g2d.setFont(f15);
        g2d.drawString(King.text, 400, 80);

        g2d.setFont(f30);
        if (Dude.type == "dude") {
            g2d.drawString(Dude.status + " | " + King.status, 400, 40); // ? draw status
        } else {
            g2d.drawString(King.status + " | " + Dude.status, 400, 40); // ? draw status
        }
    }
}