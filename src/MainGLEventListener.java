import Texture.TextureReader;

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

    // Textures
    String[] textureNames = {"game.jpg", "hammer.png", "hole.png", "rabbit1.png", "rabbit2.png", "rabbit3.png",
            "lose.jpg", "win.jpg", "home.jpg", "back.png", "easy.png", "exit.png", "exitGame.png",
            "hard.png", "levels.jpg", "medium.png", "pause.jpg", "pauseBTN.png", "play.png", "restart.png", "resume.png",
            "rules.png", "rulesBack.jpg", "replay_btn.jpg"};

    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];
    public BitSet keyBits = new BitSet(256);
    private final String[] backgrounds = {"home", "play", "rules", "pause","game", "win", "lose"};
    private int currentBackground = 0;

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

                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Image data
                );
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    double hammerX = 0;
    double hammerY = 0;
    boolean home = false;
    boolean play = false;
    boolean rules = false;
    boolean pause = false;
    boolean win = false;
    boolean lose = true;
    boolean game = false;
    boolean easy = false;
    boolean medium = false ;
    boolean hard = false;
    int score =0;

    private final Point2D[] pointsForLevelEasy = new Point[3];
    int holesIndex = 0;
    int rabbitIndex = 3;
    int health = 3 ;
    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

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
        }else if (game) {
            currentBackground = 4;
            //draw game for level easy
            if (easy) {
                DrawBackground(gl, 0);
                DrawSprite(gl, 390, 300, 17, 0.6);
                DrawSprite(gl, -100, -15, 2, 1);
                pointsForLevelEasy[0] = (new Point(-100, 15));
                DrawSprite(gl, 100, -65, 2, 1);
                pointsForLevelEasy[1] = (new Point(100, -35));
                DrawSprite(gl, 0, -245, 2, 1);
                pointsForLevelEasy[2] = (new Point(0, -215));
                if (Math.random() < 0.075) {
                    rabbitIndex = (int) (Math.random() * 3 + 3);
                    holesIndex = (int) (Math.random() * 3);
                }
                DrawSprite(gl, pointsForLevelEasy[holesIndex].getX(), pointsForLevelEasy[holesIndex].getY(), rabbitIndex, 1);
                handelClick();
            } else if (medium) {
                DrawBackground(gl, 0);
                DrawSprite(gl, 390, 300, 17, 0.6);
                DrawSprite(gl, 180, -45, 2, 0.8);
                DrawSprite(gl, 10, -10, 2, 0.8);
                DrawSprite(gl, -145, 15, 2, 0.8);
                DrawSprite(gl, 50, -230, 2, 0.8);
                DrawSprite(gl, -90, -140, 2, 0.8);
                DrawSprite(gl, -250, -240, 2, 0.8);
                handelClick();
            } else if (hard) {
                DrawBackground(gl, 0);
                DrawSprite(gl, 390, 300, 17, 0.6);
                DrawSprite(gl, 200, -30, 2, 0.8);
                DrawSprite(gl, 10, 20, 2, 0.8);
                DrawSprite(gl, -150, 20, 2, 0.8);
                DrawSprite(gl, 130, -150, 2, 0.8);
                DrawSprite(gl, -10, -100, 2, 0.8);
                DrawSprite(gl, -150, -130, 2, 0.8);
                DrawSprite(gl, 90, -270, 2, 0.8);
                DrawSprite(gl, -100, -260, 2, 0.8);
                DrawSprite(gl, -300, -270, 2, 0.8);
                handelClick();
            }
        }
        else if (win) {
            DrawBackground(gl,7);
            DrawSprite(gl,390,300,9,0.6);
            //  win  page by (Mora)
        } else if (lose) {
            DrawBackground(gl,6);
            DrawSprite(gl,390,300,9,0.6);
            DrawSprite(gl,0,-220,23,0.6);
            //  lose  page by (Mora)
        }



        // draw cursor
        DrawSprite(gl, hammerX, hammerY, 1, 1);

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

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.set(keyCode);

        if (keyCode == KeyEvent.VK_ESCAPE) {
            if (currentBackground == 1) {
                play = false;
                pause = true;
                currentBackground = 2;
                keyBits.clear(keyCode);
            } else if (currentBackground == 2) {
                pause = false;
                play = true;
                currentBackground = 1;
                keyBits.clear(keyCode);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.clear(keyCode);
    }

    double mouseX;
    double mouseY;

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = convertX(e.getX(), e.getComponent().getWidth());
        mouseY = convertY(e.getY(), e.getComponent().getHeight());
    }

    public void handelClick() {
        if (currentBackground == 0) {
            if (mouseX > -283 && mouseX < 15 && mouseY < 185 && mouseY > 131) {
                home = false;
                play = true;
                currentBackground = 1;
            }
            if (mouseX > -283 && mouseX < 15 && mouseY < 79 && mouseY > 16) {
                home = false;
                rules = true;
                currentBackground = 2;
            }
            if (mouseX > -283 && mouseX < 15 && mouseY < -10 && mouseY > -79) {
                System.exit(0);
            }
        }
        if (currentBackground == 1) {
            if (mouseX > 380 && mouseX < 430 && mouseY < 300 && mouseY > 250) {
                home = true;
                play = false;
                currentBackground = 0;
            }
            //easy, hard, and medium by (Sara)
        }
        if (currentBackground == 2) {
            if (mouseX > 380 && mouseX < 430 && mouseY < 300 && mouseY > 250) {
                home = true;
                rules = false;
                currentBackground = 0;
            }
        }
        if (currentBackground == 3) {
            //resume, restart, exit for pause page by (Nada)
        }
        if (currentBackground == 4) {
            if (mouseX > 370 && mouseX < 420 && mouseY < 300 && mouseY > 250) {
                pause = true;
                game = false;
                currentBackground = 3;
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
