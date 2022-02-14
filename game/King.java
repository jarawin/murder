package game;

import java.awt.Image;
import utils.*;

public class King {
    private int[] ran = new int[] { 5, 6, 7, 8, 9, 12, 15, 17, 18, 20 };
    private Image still, defa, defaB, defaD, hit, king, kingB, kingD;
    private static int difficulty = 6;
    private USound usAEIOU, usSword, usBack, usBlackmail;

    public static final int SIZE = 200;
    public static int xAxis;
    public static int yAxis;
    public static int deltaX;
    public static int deltaY;
    public int number;

    public static int progress;
    public static boolean BHVbreak;

    public static String type;
    public static String status;
    public static String dudeStatus;
    public static String text;

    public static boolean BGbreak;
    public int frame;
    public int loop;
    public int wait;

    // | -=-=-=-=-=-=- CONSTRUCTURE -=-=-=-=-=-=- | \\
    public King() {
        this.number = 15;
        setDefault();
        setStatus("walk");
    }

    public King(int number) {
        this.number = number;
        setDefault();
        setStatus("walk");
    }

    // | -=-=-=-=-=-=- SET DATA -=-=-=-=-=-=- | \\
    private void setDefault() {
        frame = 0;
        loop = 0;
        wait = 10;

        type = "king";
        xAxis = 525;
        yAxis = 325;
        deltaX = 0;
        deltaY = 0;

        progress = 0;

        BHVbreak = false;
        BGbreak = false;

        type = "king";
        status = "state";
        dudeStatus = "state";

        setSound();
        setPicture();
        setStill(king);
    }

    private void setStatus(String status) {
        King.status = status;
    }

    public void setDudeStatus(String dudeStatus) {
        King.dudeStatus = dudeStatus;
    }

    private void setStill(Image still) {
        this.still = still;
    }

    private void setBGbreak(boolean BGbreak) {
        King.BGbreak = BGbreak;
    }

    private void setBHVbreak(boolean BHVbreak) {
        King.BHVbreak = BHVbreak;
    }

    private void setSound() {
        usAEIOU = new USound("AEIOU3");
        usSword = new USound("sword");
        usBack = new USound("back");
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
        King.type = type;
    }

    // | -=-=-=-=-=-=- GET DATA -=-=-=-=-=-=- | \\
    public Image getImage() {
        return still;
    }

    // | -=-=-=-=-=-=- BEHAVIOR -=-=-=-=-=-=- | \\
    public void behavior() {
        if (dudeStatus == "back" && status == "willKill") {
            setStatus("dying");
        }

        if (!BHVbreak) {
            loop += 1;
        } else {
            return;
        }

        text = "loop : " + loop + " | wait : " + wait + " | number : " + number;

        if (wait != 0) {
            if (loop >= 150) {
                loop = 0;
                wait -= 1;
                if (type == "king") {
                    if (wait == 3) {
                        usAEIOU.start();
                    }
                } else {
                    if (wait == 0) {
                        usSword.start();
                    }
                }
            }
            return;
        } else {
            wait = ran[new URandom().get()];
        }

        if (type == "dude") {
            setStatus("kill");
        } else {
            setStatus("back");
        }
    }

    // | -=-=-=-=-=-=- ANIMATION -=-=-=-=-=-=- | \\
    public void animate() {
        behavior();

        switch (status) {
            case "state":
                break;

            case "kill":
                frame = 400 * new URandom(6, 1).get();
                setStatus("willKill");
                return;
            case "willKill":
                willKill();
                return;
            case "Killing":
                killing();
                setBGbreak(true);
                return;

            case "back":
                frame = 100 * new URandom(5, 1).get();
                setStatus("beingBack");
                usBack.start();
                return;
            case "beingBack":
                beingBack();
                return;

            case "walk":
                frame = 725;
                deltaX = 725;
                setBGbreak(true);
                setStatus("walking");
                return;
            case "walking":
                walking();
                return;

            case "dying":
                frame = 450;
                setBHVbreak(true);
                setBGbreak(true);
                setStatus("dead");
                return;
            case "dead":
                dead();
                return;

            case "dude":
                frame = 475;
                deltaX = 725;
                setBGbreak(true);
                number = (number % 8) + 1;
                setPicture();
                setStill(defa);
                setStatus("duding");
                return;
            case "duding":
                duding();
                return;

            case "king":
                frame = 450;
                setBGbreak(true);
                setStatus("beingKing");
                return;

            case "beingKing":
                beingKing();
                return;
        }

        switch (dudeStatus) {
            case "state":
                setBHVbreak(false);
                return;
            case "willKill":
                setBHVbreak(false);
                return;

            case "kill":
                usAEIOU.stop(true);
                setBHVbreak(true);
                return;
            case "killing":
                setBHVbreak(true);
                if (status != "dying" || status != "dead") {
                    setStatus("dying");
                }
                return;
            case "dying":
                setBHVbreak(true);
                return;
            case "dead":
                setBHVbreak(true);
                if (status != "king" || status != "beingKing") {
                    setStatus("king");
                }
                return;

            case "walk":
                setBHVbreak(true);
                return;
            case "walking":
                setBHVbreak(true);
                return;

            case "endGameback":
                setBHVbreak(true);
                return;
            case "beingEndGameback":
                setBHVbreak(true);
                return;

        }
    }

    // | -=-=-=-=-=-=- ACTION -=-=-=-=-=-=- | \\
    private void killing() {
        text += " | frame :" + frame;
        if (frame > 0) {
            frame -= 1;
        } else {
            setStill(defa);
            setStatus("state");
            setBGbreak(false);
        }
    }

    private void willKill() {
        text += " | frame :" + frame;
        if (frame > 0) {
            frame -= 1;
            if (progress < 200 * new URandom(8, 1).get() * difficulty) {
                text += " | progress :" + progress;
                progress += 2;
                setStill(hit);
            } else {
                setStatus("killing");
            }
        } else {
            text += " | progress :" + progress;
            progress = 0;
            setStill(defa);
            setStatus("state");
        }
    }

    private void beingBack() {
        text += " | frame :" + frame;
        if (frame > 0) {
            frame -= 1;
            setStill(kingB);
        } else {
            setStill(king);
            setStatus("state");
            usBack.start();
        }
    }

    private void beingKing() {
        if (frame > 0) {
            frame -= 1;
            if (frame <= 250) {
                deltaX -= 1;
            }
        } else {
            setStill(king);
            setStatus("state");
            setBGbreak(false);
        }
    }

    public void duding() {
        if (frame > 0) {
            frame -= 1;
            deltaX -= 1;
        } else {
            setStatus("state");
            setBGbreak(false);
        }
    }

    private void dead() {
        if (frame > 0) {
            frame -= 1;
            if (frame > 200 && frame < 250) {
                setStill(defaD);
                yAxis = 400;
                setStatus("dead");
            } else if (frame < 200) {
                yAxis = 325;
                setStill(null);
            }
        } else {
            setType("dude");
            setStatus("dude");
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
}