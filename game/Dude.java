package game;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import utils.*;

public class Dude {
    private String[] AnimateDefault = new String[] { "kill", "killing", "walk", "walking", "dead", "dying",
            "beingEndGameback", "endGameback" };
    private Image still, defa, defaB, defaD, hit, king, kingB, kingD;
    private USound usSword, usHit, usMyPosition, usBack, usGameOver, usFinish, usBlackmail;

    public static final int SIZE = 200;
    public static int xAxis;
    public static int yAxis;
    public static int deltaX;
    public static int deltaY;
    public int number;

    public static int progress;
    public static String type;
    public static String status;
    public static String kingStatus;

    public static boolean BGbreak;
    public static int frame;

    private static int loop;
    public static int fightTime;
    public static int saveTime;

    public Dude() {
        this.number = 1;
        setDefault();
        setStatus("walk");
    }

    public Dude(int number) {
        this.number = number;
        setDefault();
        setStatus("walk");
    }

    // | -=-=-=-=-=-=- SET DATA -=-=-=-=-=-=- | \\
    private void setDefault() {
        loop = 0;
        fightTime = 100;

        xAxis = 275;
        yAxis = 325;
        deltaX = 0;
        deltaY = 0;

        progress = 0;
        type = "dude";
        status = "state";
        kingStatus = "state";
        BGbreak = false;

        setSound();
        setPicture();
        setStill(defa);
        usMyPosition.start();
    }

    private void setStatus(String status) {
        Dude.status = status;
    }

    public void setKingStatus(String kingStatus) {
        Dude.kingStatus = kingStatus;
    }

    private void setStill(Image still) {
        this.still = still;
    }

    private void setBGbreak(boolean BGbreak) {
        Dude.BGbreak = BGbreak;
    }

    private void setSound() {
        usSword = new USound("sword");
        usHit = new USound("hit");
        usMyPosition = new USound("ตำเเหน่งนั้นมันต้องเป็นของฉัน");
        usBack = new USound("back");
        usGameOver = new USound("gameOver");
        usFinish = new USound("ในที่สุดชั้นก็ทำสำเร็จ");
        usBlackmail = new USound("ไอ้คนทรยศ");
    }

    private void setPicture() {
        defa = new UPicture("dude", String.valueOf(number), "default").get().getImage();
        defaD = new UPicture("dude", String.valueOf(number), "defaultD").get().getImage();
        defaB = new UPicture("dude", String.valueOf(number), "defaultB").get().getImage();
        hit = new UPicture("dude", String.valueOf(number), "hit").get().getImage();
        king = new UPicture("dude", String.valueOf(number), "king").get().getImage();
        kingD = new UPicture("dude", String.valueOf(number), "kingD").get().getImage();
        kingB = new UPicture("dude", String.valueOf(number), "kingB").get().getImage();
    }

    private void setType(String type) {
        Dude.type = type;
    }

    // | -=-=-=-=-=-=- GET DATA -=-=-=-=-=-=- | \\
    public Image getImage() {
        return still;
    }

    // | -=-=-=-=-=-=- EVENT -=-=-=-=-=-=- | \\
    public void event() {
        if (type == "dude") {
            if (kingStatus == "beingBack" && status == "willKill" && status != "endGameback") {
                setStatus("endGameback");
            }

        } else {
            if (kingStatus == "killing" && status != "endGameKill") {
                saveTime = fightTime;
                setStatus("endGameKill");
                yAxis = 400;
                setStill(defaD);
            }
            if (status == "endGameKill" && saveTime - fightTime == 3) {
                yAxis = 325;
                usGameOver.start();
                setStatus("defeated");
            }

            loop += 1;
            if (loop >= 150) {
                loop = 0;
                fightTime -= 1;
            }

            if (fightTime <= 0) {
                usFinish.start();
                setStatus("winner");
            }

        }
    }

    // | -=-=-=-=-=-=- ANIMATION -=-=-=-=-=-=- | \\
    public void animate() {
        event();

        switch (status) {
            case "state":
                break;

            case "walk":
                frame = 750;
                deltaX = 750;
                setBGbreak(true);
                setStatus("walking");
                break;
            case "walking":
                walking();
                break;

            case "kill":
                frame = 400;
                setStill(defa);
                setBGbreak(true);
                setStatus("killing");
                usBlackmail.start();
                break;
            case "killing":
                killing();
                break;

            case "dying":
                frame = 450;
                setBGbreak(true);
                setStatus("dead");
                return;
            case "dead":
                dead();
                return;

            case "endGameback":
                frame = 300;
                setBGbreak(true);
                setStatus("beingEndGameback");
                return;
            case "beingEndGameback":
                beingEndGameback();
                return;
        }
    }

    // | -=-=-=-=-=-=- ACTION -=-=-=-=-=-=- | \\
    public void beingEndGameback() {
        if (frame > 0) {
            frame -= 1;
            yAxis = 400;
            setStill(defaD);
        } else {
            yAxis = 325;
            usGameOver.start();
            setStatus("defeated");
        }
    }

    public void dead() {
        if (frame > 0) {
            frame -= 1;
            if (frame > 200 && frame < 250) {
                setStill(kingD);
                yAxis = 400;
                setStatus("dead");
            } else if (frame < 200) {
                yAxis = 325;
                setStill(null);
            }
        } else {
            setType("dude");
            number = (number % 8) + 1;
            setPicture();
            setStill(defa);
            setStatus("walk");
        }
    }

    public void killing() {
        if (frame > 0) {
            frame -= 1;
            if (frame > 250) {
                deltaX -= 1;
            } else if (frame > 200) {
                setStill(hit);
                if (frame == 225) {
                    usHit.start();
                }
            } else if (frame > 150) {
                setStill(defa);
            } else if (frame > 100) {
                setStill(king);
            } else if (frame > 0) {
                deltaX -= 1;
            }
        } else {
            setStatus("state");
            setBGbreak(false);
        }
    }

    public void walking() {
        if (frame > 0) {
            frame -= 1;
            deltaX -= 1;
        } else {
            setStatus("state");
            setBGbreak(false);
        }
    }

    // | -=-=-=-=-=-=- KEY EVENT -=-=-=-=-=-=- | \\
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!Arrays.stream(AnimateDefault).anyMatch(status::equals)) {
            if (key == KeyEvent.VK_ESCAPE) {
                setStatus("newgame");
            }

            if (key == KeyEvent.VK_LEFT) {
                if (type == "dude") {
                    setStill(defaB);
                    usBack.start();
                } else {
                    setStill(kingB);
                    usBack.start();
                }
            }

            if (key == KeyEvent.VK_RIGHT) {
                if (type == "dude") {
                    setStill(defa);
                    usBack.start();
                } else {
                    setStill(king);
                    usBack.start();
                }
            }

            if (key == KeyEvent.VK_SPACE) {
                if (type == "dude") {
                    setStill(hit);
                    if (status != "willKill") {
                        usSword.start();
                    }
                    if (progress < 200) {
                        setStatus("willKill");
                        progress += 2;
                    } else {
                        progress = 0;
                        if (status != "kill") {
                            setStatus("kill");
                            setType("king");
                            fightTime = 100;
                            progress = 200;
                        }
                    }
                } else {
                    if (progress > 1) {
                        if (status == "state") {
                            progress -= 10;
                            usBack.start();
                            setStatus("back");
                            setStill(kingB);
                            return;
                        }

                        if (status == "back") {
                            progress -= 10;
                        }
                    } else {
                        usHit.start();
                        setStatus("dying");
                    }
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (!Arrays.stream(AnimateDefault).anyMatch(status::equals)) {
            if (key == KeyEvent.VK_SPACE) {
                if (type == "dude") {
                    progress = 0;
                    setStill(defa);
                    setStatus("state");
                } else {
                    setStill(king);
                    setStatus("state");
                }
            }
        }
    }
}