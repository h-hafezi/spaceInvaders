package PreGame;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class BackgroundMusic {
    public static BackgroundMusic kill = new BackgroundMusic("src/resources/Space Invaders with sound!-[AudioTrimmer.com].mp3");
    public static BackgroundMusic lose = new BackgroundMusic("src/resources/lose.mp3");

    private String filename;
    private static Player player;

    Thread playMusic;

    public BackgroundMusic(String filename) {
        this.filename = filename;
    }

    public void play() {
        try {
            FileInputStream fis = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        } catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            e.printStackTrace();
        }
    }

    public void start() {
        play();
        playMusic = new Thread(new PlayMusic());
        playMusic.start();

    }

    static class PlayMusic implements Runnable {

        public void run() {
            try {
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
