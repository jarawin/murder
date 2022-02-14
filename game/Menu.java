package game;

import java.awt.Color;
import java.awt.event.*;
import java.awt.*;

import utils.*;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Menu extends JPanel implements ActionListener {
    private String status = "newgame";
    static int progressNewGame = 0;
    public Display display;
    public Timer time;

    public Menu() {
    }

    public Menu(String status, Display display) {
        this.display = display;
        this.status = status;
        setDefault();
    }

    public void setDefault() {
        this.setBounds(0, 0, 1000, 600);
        this.setFocusable(true);
        this.setLayout(null);

        addKeyListener(new Action());
        time = new Timer(10, this);
        time.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        // |set drawing
        g2d.setColor(Color.white); // white text
        g2d.setFont(new UFont(50).get()); // font of text

        // |set background
        if (status == "newgame") {
            this.setBackground(new Color(61, 174, 0)); // bg green

            // |draw text
            g2d.drawString("Welcome to Murder Game", 185, 275);

        } else if (status == "winner") {
            this.setBackground(new Color(61, 174, 0)); // bg green

            // |draw text
            g2d.drawString("You are Winner!!", 285, 275);

        } else {
            this.setBackground(new Color(241, 98, 69)); // bg orange

            // |draw text
            g2d.drawString("You are Defeated!!", 260, 275);
        }

        // |set bg progress
        g2d.setColor(new Color(221, 221, 221)); // สีของแถบสี
        g2d.fillRect(250, 350, 500, 60); // เส้นข้างใน

        // |set progress
        g2d.setColor(new Color(0, 188, 230)); // สีของแถบสี
        g2d.fillRect(250, 350, progressNewGame * 10, 60); // เส้นข้างใน

        // |set text
        g2d.setColor(Color.white); // white text
        g2d.setFont(new UFont(40).get()); // font of text
        g2d.drawString("press the spacebar", 305, 393);

        // |set frame
        g2d.setColor(Color.white); // สีของกรอบนอก
        g2d.setStroke(new BasicStroke(8.0f, 1, 1)); // ขนาดข้างนอก
        g2d.drawRect(250, 350, 500, 60);

    }

    private class Action extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == 32) {
                if (progressNewGame > 50) {
                    progressNewGame = 0;
                    time.stop();
                } else {
                    progressNewGame += 1;
                }
            }
        }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == 32) {
                progressNewGame = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}