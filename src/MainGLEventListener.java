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
    String[] textureNames = {"back.jpg", "hammer.png", "hole.png", "rabbit1.png", "rabbit2.png", "rabbit3.png","lose.jpg","win.jpg", "hammer.png"};

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
        gl.glOrtho(-250.0, 250, -250.0, 250.0, -1, 1);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for(int i = 0; i < textureNames.length; i++){
            try {
                texture[i] = TextureReader.readTexture("Asset"+ "//" + textureNames[i] , true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Image data
                );
            } catch(IOException e) {
                System.out.println(e);
            }
        }
    }


    double hammerX = 0;
    double hammerY = 0;

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        // Displaying background.
        DrawBackground(gl);
        // draw cursor
        DrawSprite(gl,hammerX,hammerY,1,-1);

    }

    public void DrawBackground(GL gl){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);

        gl.glPushMatrix();

        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3d(-250f, -250, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3d(250.0f, -250.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3d(250.0f, 250.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3d(-250.0f, 250.0f, -1.0f);
        gl.glEnd();

        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawSprite(GL gl,double x, double y, int index, double scale){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);

        gl.glPushMatrix();

        gl.glTranslated( x , y, 0);
        gl.glScaled(0.2*scale, 0.2*scale, 1);

        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2d(-250, -250);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2d(-250, 250);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2d(250,250);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2d(250,-250);
        gl.glEnd();

        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {}

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {}

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
    public void mouseClicked(MouseEvent e){}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        hammerX = convertX(e.getX(), e.getComponent().getWidth());
        hammerY = convertY(e.getY(), e.getComponent().getHeight());
    }

    private double convertX(double x, double width) {
        return (x / width) * 500-250;
    }

    private double convertY(double y, double height) {
        return (1 - y / height) * 500-250;
    }
}
