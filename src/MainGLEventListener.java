import Texture.TextureReader;
import com.sun.opengl.util.GLUT;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.BitSet;

public class MainGLEventListener implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {
    private final String[] backgrounds = {"home", "play", "rules", "pause", "game", "win", "lose"};
    private final Point2D[] pointsForLevelEasy = new Point[3];
    private final Point2D[] pointsForLevelMedium = new Point[6];
    private final Point2D[] pointsForLevelHard = new Point[9];
    private final Timer timer = new Timer();
    public BitSet keyBits = new BitSet(256);
    String userName;
    // Textures
    String[] textureNames = {"game.jpg", "hammer.png", "hole.png", "rabbit1.png", "rabbit2.png", "rabbit3.png", "lose.jpg", "win.jpg", "home.jpg", "back.png", "easy.png", "exit.png", "exitGame.png", "hard.png", "levels.jpg", "medium.png", "pause.jpg", "pauseBTN.png", "play.png", "restart.png", "resume.png", "rules.png", "rulesBack.jpg", "replay_btn.png", "carrot.png","hammer5.png"};
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];
    double hammerX = 0;
    double hammerY = 0;
    boolean home = true;
    boolean play = false;
    boolean rules = false;
    boolean pause = false;
    boolean win = false;
    boolean lose = false;
    boolean game = false;
    boolean easy = false;
    boolean medium = false;
    boolean hard = false;
    int score = 1;
    int highscore = 0;
    boolean mouseClick = false;
    int holesIndex = 0;
    int rabbitIndex = 3;
    int health = 4;
    double mouseX;
    double mouseY;
    private int currentBackground = 0;
    boolean mClick = false;
    public static void drawGLUT(GL gl, double x, double y, String text) {
        GLUT glut = new GLUT();
        gl.glRasterPos2d(x, y);
        int font = GLUT.BITMAP_HELVETICA_18;
        gl.glScalef(0.1f, 0.1f, 0.1f);
        for (char c : text.toCharArray()) {
            glut.glutBitmapCharacter(font, c);
        }
        gl.glScalef((float) (1.0f / 0.1), (float) (1.0f / 0.1), 1.0f);
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glViewport(0, 0, 500, 300);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-450.0, 450, -350.0, 350.0, -1, 1);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture("Asset" + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

                new GLU().gluBuild2DMipmaps(GL.GL_TEXTURE_2D, GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(), GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE, texture[i].getPixels() // Image data
                );
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        userName = login.userName();

        // Home game state and buttons
        if (home) {
            currentBackground = 0;
            DrawBackground(gl, 8);
            DrawSprite(gl, -150, 190, 18, 2);
            DrawSprite(gl, -150, 85, 21, 2);
            DrawSprite(gl, -150, -20, 12, 2);
            handelClick();
        }
        // Play state and pause button
        else if (play) {
            currentBackground = 1;
            DrawBackground(gl, 14);
            DrawSprite(gl, -15, 70, 10, 2);
            DrawSprite(gl, -15, -50, 15, 2);
            DrawSprite(gl, -15, -170, 13, 2);
            DrawSprite(gl, 400, 300, 9, 0.6);
            handelClick();
        }
        // Rules state and back button.
        else if (rules) {
            currentBackground = 2;
            DrawBackground(gl, 22);
            DrawSprite(gl, 400, 300, 9, 0.6);
            handelClick();
        }
        // Pause state and buttons.
        else if (pause) {
            currentBackground = 3;
            DrawBackground(gl, 16);
            DrawSprite(gl, 0, 70, 20, 2);
            DrawSprite(gl, 0, -50, 19, 2);
            DrawSprite(gl, 0, -170, 11, 2);
            handelClick();
        } else if (game) {
            currentBackground = 4;
            timer.updateTimer();
            //draw game for level easy
            if (easy) {
                DrawBackground(gl, 0);
                for (int i = 0; i < health; i++) {
                    DrawSprite(gl, 70 + (i * 50), 300, 24, 0.6);
                }
                DrawSprite(gl, 390, 300, 17, 0.6);
                DrawSprite(gl, -100, -15, 2, 1);
                pointsForLevelEasy[0] = (new Point(-100, 15));
                DrawSprite(gl, 100, -65, 2, 1);
                pointsForLevelEasy[1] = (new Point(100, -35));
                DrawSprite(gl, 0, -245, 2, 1);
                pointsForLevelEasy[2] = (new Point(0, -215));
                if (Math.random() < 0.05) {
                    rabbitIndex = (int) (Math.random() * 3 + 3);
                    holesIndex = (int) (Math.random() * 3);
                }
                DrawSprite(gl, pointsForLevelEasy[holesIndex].getX(), pointsForLevelEasy[holesIndex].getY(), rabbitIndex, 1);
                hitRabbit();
                handelClick();
            } else if (medium) {
                DrawBackground(gl, 0);
                for (int i = 0; i < health; i++) {
                    DrawSprite(gl, 70 + (i * 50), 300, 24, 0.6);
                }
                DrawSprite(gl, 390, 300, 17, 0.6);
                DrawSprite(gl, 180, -45, 2, 0.8);
                pointsForLevelMedium[0] = (new Point(180, -15));
                DrawSprite(gl, 10, -10, 2, 0.8);
                pointsForLevelMedium[1] = (new Point(10, 20));
                DrawSprite(gl, -145, 15, 2, 0.8);
                pointsForLevelMedium[2] = (new Point(-145, 45));
                DrawSprite(gl, 50, -230, 2, 0.8);
                pointsForLevelMedium[3] = (new Point(50, -200));
                DrawSprite(gl, -90, -140, 2, 0.8);
                pointsForLevelMedium[4] = (new Point(-90, -110));
                DrawSprite(gl, -250, -240, 2, 0.8);
                pointsForLevelMedium[5] = (new Point(-250, -210));
                if (Math.random() < 0.08) {
                    rabbitIndex = (int) (Math.random() * 3 + 3);
                    holesIndex = (int) (Math.random() * 6);
                }
                DrawSprite(gl, pointsForLevelMedium[holesIndex].getX(), pointsForLevelMedium[holesIndex].getY(), rabbitIndex, 0.8);
                hitRabbit2();
                handelClick();
            } else if (hard) {
                DrawBackground(gl, 0);
                for (int i = 0; i < health; i++) {
                    DrawSprite(gl, 70 + (i * 50), 300, 24, 0.6);
                }
                DrawSprite(gl, 390, 300, 17, 0.6);

                DrawSprite(gl, 200, -30, 2, 0.8);
                pointsForLevelHard[0] = (new Point(200, 0));
                DrawSprite(gl, 10, 20, 2, 0.8);
                pointsForLevelHard[1] = (new Point(10, 50));
                DrawSprite(gl, -150, 20, 2, 0.8);
                pointsForLevelHard[2] = (new Point(-150, 50));
                DrawSprite(gl, 130, -150, 2, 0.8);
                pointsForLevelHard[3] = (new Point(130, -120));
                DrawSprite(gl, -10, -100, 2, 0.8);
                pointsForLevelHard[4] = (new Point(-10, -70));
                DrawSprite(gl, -150, -130, 2, 0.8);
                pointsForLevelHard[5] = (new Point(-150, -100));
                DrawSprite(gl, 90, -270, 2, 0.8);
                pointsForLevelHard[6] = (new Point(90, -240));
                DrawSprite(gl, -100, -260, 2, 0.8);
                pointsForLevelHard[7] = (new Point(-100, -230));
                DrawSprite(gl, -300, -270, 2, 0.8);
                pointsForLevelHard[8] = (new Point(-300, -240));

                if (Math.random() < 0.09) {
                    rabbitIndex = (int) (Math.random() * 3 + 3);
                    holesIndex = (int) (Math.random() * 9);
                }
                DrawSprite(gl, pointsForLevelHard[holesIndex].getX(), pointsForLevelHard[holesIndex].getY(), rabbitIndex, 0.8);
                hitRabbit3();
                handelClick();
            }
            drawGLUT(gl, -130, 320, "Username : " + userName);
            drawGLUT(gl, -130, 300, "High Score : " + highscore);
            drawGLUT(gl, -130, 280, "Score : " + score);
            drawGLUT(gl, -130, 260, "Timer : " + timer.getTimeRemaining());
        } else if (win) {
            currentBackground = 5;
            DrawBackground(gl, 7);
            DrawSprite(gl, 390, 300, 9, 0.6);
            handelClick();
        } else if (lose) {
            currentBackground = 6;
            DrawBackground(gl, 6);
            DrawSprite(gl, 390, 300, 9, 0.6);
            DrawSprite(gl, 0, -220, 23, 0.6);
            handelClick();
        }

        // draw cursor
        if(true) {
            if (mClick) {
                DrawSprite(gl, hammerX, hammerY, textureNames.length - 1, 1);
                mClick =false;
            } else
                DrawSprite(gl, hammerX, hammerY, 1, 1);
        }
    }

    public void DrawBackground(GL gl, int n) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[n]);

        gl.glPushMatrix();

        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3d(-450f, -350, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3d(450.0f, -350.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3d(450.0f, 350.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3d(-450.0f, 350.0f, -1.0f);
        gl.glEnd();

        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawSprite(GL gl, double x, double y, int index, double scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);

        gl.glPushMatrix();

        gl.glTranslated(x, y, 0);
        gl.glScaled(0.2 * scale, 0.2 * scale, 1);

        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2d(-450, -350);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2d(450, -350);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2d(450, 350);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2d(-450, 350);
        gl.glEnd();

        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    private void hitRabbit() {
        if (mouseClick) {
            double x = pointsForLevelEasy[holesIndex].getX();
            double y = pointsForLevelEasy[holesIndex].getY();
            if ((x - 50 < mouseX && x + 50 > mouseX) && (y - 80 < mouseY && y + 90 > mouseY)) {
                score++;
                mouseClick = false;
                Game.hit.playMusic();
                if (holesIndex == 2) holesIndex--;
                else holesIndex++;
            } else {
                mouseClick = false;
                health--;
            }
        }
        if (health == 0) {
            game = false;
            lose = true;
            health = 4;
            score = 1;
        }
        if (timer.getTimeRemaining() == 0 && score <= 5) {
            game = false;
            lose = true;
            health = 4;
            score = 1;
            timer.resetTimer();
        } else if (timer.getTimeRemaining() == 0 && score > 5) {
            game = false;
            win = true;
            if (score > highscore) {
                highscore = score;
            }
            health = 4;
            timer.resetTimer();
            score = 1;
        }
    }

    private void hitRabbit2() {
        if (mouseClick) {
            double x = pointsForLevelMedium[holesIndex].getX();
            double y = pointsForLevelMedium[holesIndex].getY();
            if ((x - 50 < mouseX && x + 50 > mouseX) && (y - 65 < mouseY && y + 75 > mouseY)) {
                score++;
                mouseClick = false;
                Game.hit.playMusic();
                if (holesIndex > 2 && holesIndex < 5) holesIndex++;
                else holesIndex--;
            } else {
                mouseClick = false;
                health--;
            }
        }
        if (health < 1) {
            game = false;
            lose = true;
            health = 4;
            score = 1;
        }
        if (timer.getTimeRemaining() == 0 && score <= 5) {
            game = false;
            lose = true;
            health = 4;
            score = 1;
            timer.resetTimer();
        } else if (timer.getTimeRemaining() == 0 && score > 5) {
            game = false;
            win = true;
            if (score > highscore) {
                highscore = score;
            }
            health = 4;
            timer.resetTimer();
            score = 1;
        }
    }

    private void hitRabbit3() {
        if (mouseClick) {
            double x = pointsForLevelHard[holesIndex].getX();
            double y = pointsForLevelHard[holesIndex].getY();
            if ((x - 50 < mouseX && x + 50 > mouseX) && (y - 65 < mouseY && y + 75 > mouseY)) {
                score++;
                mouseClick = false;
                Game.hit.playMusic();
                if (holesIndex > 2 && holesIndex < 7) holesIndex++;
                else holesIndex--;
            } else {
                mouseClick = false;
                health--;
            }
        }
        if (health < 1) {
            game = false;
            lose = true;
            health = 4;
            score = 1;
        }
        if (timer.getTimeRemaining() == 0 && score <= 5) {
            game = false;
            lose = true;
            health = 4;
            timer.resetTimer();
            score = 1;
        } else if (timer.getTimeRemaining() == 0 && score > 5) {
            game = false;
            win = true;
            if (score > highscore) {
                highscore = score;
            }
            health = 4;
            timer.resetTimer();
            score = 1;
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.set(keyCode);

        if (keyCode == KeyEvent.VK_ESCAPE) {
            if (currentBackground == 4) {
                game = false;
                pause = true;
                currentBackground = 3;
                keyBits.clear(keyCode);
            } else if (currentBackground == 3) {
                pause = false;
                game = true;
                currentBackground = 4;
                keyBits.clear(keyCode);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = convertX(e.getX(), e.getComponent().getWidth());
        mouseY = convertY(e.getY(), e.getComponent().getHeight());
        mouseClick = true;
        mClick =true;
    }

    public void handelClick() {
        if (currentBackground == 0) {
            // Play btn
            if (mouseX > -283 && mouseX < 15 && mouseY < 185 && mouseY > 131) {
                home = false;
                play = true;
                currentBackground = 1;
            }
            // Rules btn
            if (mouseX > -283 && mouseX < 15 && mouseY < 79 && mouseY > 16) {
                home = false;
                rules = true;
                currentBackground = 2;
            }
            // Exit out of the game
            if (mouseX > -283 && mouseX < 15 && mouseY < -10 && mouseY > -79) {
                System.exit(0);
            }
        }
        if (currentBackground == 1) {
            // Back btn
            if (mouseX > 380 && mouseX < 430 && mouseY < 300 && mouseY > 250) {
                home = true;
                play = false;
                currentBackground = 0;
            }
            // Easy btn
            if (mouseX > -75 && mouseX < 90 && mouseY < 77 && mouseY > 1) {
                play = false;
                game = true;
                easy = true;
                medium = false;
                hard = false;
                timer.startTimer();
                currentBackground = 4;
            }
            // Medium btn
            if (mouseX > -75 && mouseX < 90 && mouseY < -42 && mouseY > -115) {
                play = false;
                game = true;
                easy = false;
                medium = true;
                hard = false;
                timer.startTimer();
                currentBackground = 4;
            }
            // Hard btn
            if (mouseX > -80 && mouseX < 90 && mouseY < -161 && mouseY > -236) {
                play = false;
                game = true;
                easy = false;
                medium = false;
                hard = true;
                timer.startTimer();
                currentBackground = 4;
            }
        }
        // Exit in game btn
        if (currentBackground == 2) {
            if (mouseX > 380 && mouseX < 430 && mouseY < 300 && mouseY > 250) {
                home = true;
                rules = false;
                easy = false;
                medium = false;
                hard = false;
                currentBackground = 0;
            }
        }
        if (currentBackground == 3) {
            //
            if (mouseX > -64 && mouseX < 116 && mouseY < 88 && mouseY > -5) {
                game = true;
                pause = false;
                currentBackground = 4;
            }
            if (mouseX > -68 && mouseX < 120 && mouseY < -34 && mouseY > -125) {
                game = true;
                pause = false;
                score = 0;
                health = 3;
                timer.startTimer();
                currentBackground = 4;
            }
            if (mouseX > -67 && mouseX < 106 && mouseY < -160 && mouseY > -241) {
                score = 0;
                timer.resetTimer();
                health = 3;
                pause = false;
                home = true;
                easy = false;
                medium = false;
                hard = false;
                currentBackground = 0;
            }
        }
        if (currentBackground == 4) {
            if (mouseX > 370 && mouseX < 420 && mouseY < 300 && mouseY > 250) {
                pause = true;
                game = false;
                currentBackground = 3;
            }
        }
        if (currentBackground == 5) {
            if (mouseX > 380 && mouseX < 430 && mouseY < 300 && mouseY > 250) {
                home = true;
                win = false;
                currentBackground = 0;
            }
        }
        if (currentBackground == 6) {
            if (mouseX > 380 && mouseX < 430 && mouseY < 300 && mouseY > 250) {
                home = true;
                lose = false;
                currentBackground = 0;
            }
            if (mouseX > -16 && mouseX < 66 && mouseY < -223 && mouseY > -285) {
                game = true;
                lose = false;
                timer.startTimer();
                currentBackground = 4;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        hammerX = convertX(e.getX(), e.getComponent().getWidth());
        hammerY = convertY(e.getY(), e.getComponent().getHeight());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        hammerX = convertX(e.getX(), e.getComponent().getWidth());
        hammerY = convertY(e.getY(), e.getComponent().getHeight());
    }

    private double convertX(double x, double width) {
        return (x / width) * 900 - 450;
    }

    private double convertY(double y, double height) {
        return (1 - y / height) * 700 - 350;
    }
}
