package utils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

class Play extends USound implements Runnable {
    // private String baseLocate =
    // "/Users/jarawin/Desktop/Study/java/GAME/murder/src/sound/";
    private String baseLocate = "/Users/jarawin/Desktop";
    private File soundFile;
    private USound us;

    public Play(String fileName, USound us) {
        soundFile = new File(baseLocate + fileName + ".wav");
        this.us = us;
    }

    public void play() {
        int EXTERNAL_BUFFER_SIZE = 524288;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];

        AudioInputStream audioInputStream = null;
        int nBytesRead = 0;

        SourceDataLine auline = null;
        AudioFormat format;
        DataLine.Info info;

        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            format = audioInputStream.getFormat();
            info = new DataLine.Info(SourceDataLine.class, format);
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        auline.start();

        try {
            while (nBytesRead != -1 && !us.stop) {
                if (us.sleep != 0) {
                    TimeUnit.SECONDS.sleep(us.sleep);
                    us.sleep = 0;
                }

                while (us.pause) {
                    TimeUnit.SECONDS.sleep(1);
                }

                nBytesRead = audioInputStream.read(abData, 0, abData.length);

                if (nBytesRead >= 0) {
                    auline.write(abData, 0, nBytesRead);
                }

                if (us.end) {
                    nBytesRead = -1;
                    us.end = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {
            auline.drain();
            auline.close();
        }
    }

    @Override
    public void run() {
        if (us.loop) {
            while (!us.stop && !us.end) {
                play();
            }
        } else {
            play();
        }
    }
}

public class USound {
    protected boolean stop = false;
    protected boolean loop = false;
    protected boolean pause = false;
    protected boolean end = false;
    protected int sleep = 0;
    protected String fileName;
    private Thread t;

    public USound() {
    }

    public USound(String fileName) {
        this.fileName = fileName;
    }

    public void start() {
        t = new Thread(new Play(fileName, this));
        t.start();
    }

    public void fileName(String fileName) {
        this.fileName = fileName;
    }

    public void stop(boolean stop) {
        this.stop = stop;
    }

    public void loop(boolean loop) {
        this.loop = loop;
    }

    public void sleep(int sleep) {
        this.sleep = sleep;
    }

    public void pause() {
        this.pause = true;
    }

    public void play() {
        this.pause = false;
    }

    public void end() {
        this.end = true;
        t.interrupt();
    }

    public void reStart() {
        end();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        start();
    }

    public boolean getPause() {
        return pause;
    }

}