import Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.event.*;
import java.io.IOException;
import java.util.BitSet;

public class MainGLEventListener implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {

    // Textures
    String[] textureNames = {"game.jpg", "hammer.png", "hole.png", "rabbit1.png", "rabbit2.png", "rabbit3.png",
            "lose.jpg", "win.jpg", "home.jpg", "back.png", "easy.png", "exit.png", "exitGame.png", "rulesBack.jpg",
            "hard.png", "levels.jpg", "medium.png", "pause.jpg", "pauseBTN.png", "play.png", "restart.png", "resume.png",
            "rules.png"};

    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];
    public BitSet keyBits = new BitSet(256);

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
    boolean easy = true;

    boolean play = true;


    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        //draw holes for level easy
        if (easy) {
            DrawBackground(gl, 0);
            DrawSprite(gl, -100, 0, 2, 1);
            DrawSprite(gl, 100, -50, 2, 1);
            DrawSprite(gl, 0, -230, 2, 1);
        }

        // Home game state and buttons
//        DrawBackground(gl ,8);
//        DrawSprite(gl, -100, 130, 18, 2);
//        DrawSprite(gl, -100, 70, 21, 2);
//        DrawSprite(gl, -100, 10, 12, 2);

        // Play state
//        if (play) {
//            DrawBackground(gl ,14);
//            DrawSprite(gl, -10, 50, 10, 2);
//            DrawSprite(gl, -10, -50, 15, 2);
//            DrawSprite(gl, -10, -150, 13, 2);
//            DrawSprite(gl, 200, 200, 9, 0.6);
//        }
//        else
//
//            // Displaying background.
//            DrawBackground(gl, 0);

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
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
