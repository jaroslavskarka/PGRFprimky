import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Renderer {
    private BufferedImage img;
    private Canvas canvas;
    private static final int FPS = 1000 / 30;
    private int x1, y1;
    public ArrayList<Bod> body = new ArrayList<>();
    public ArrayList<Bod> uhelniky = new ArrayList<>();

    public Renderer(BufferedImage img, Canvas canvas) {
        this.img = img;
        this.canvas = canvas;
        setLoop();
    }

    private void setLoop() {
        // časovač, který 30 krát za vteřinu obnoví obsah plátna aktuálním img
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // říct plátnu, aby zobrazil aktuální img
                canvas.getGraphics().drawImage(img, 0, 0, null);
                // pro zájemce - co dělá observer - https://stackoverflow.com/a/1684476
            }
        }, 0, FPS);
    }

    public void clear() {
        // https://stackoverflow.com/a/5843470
        Graphics g = img.getGraphics();
        g.setColor(Color.BLACK);
        g.clearRect(0, 0, 800, 600);
        drawExisting();
        drawUhelnik();
    }
    private void drawUhelnik() {
        if (this.uhelniky.size() > 1) {
            Bod pomocna, pomocna1;
            int size;

            for (int i = 0; i <= this.uhelniky.size() - 1; i = i + 2) {
                pomocna = uhelniky.get(i);
                if (i + 1 < uhelniky.size()) {
                    pomocna1 = uhelniky.get(i + 1);
                    drawDDA(pomocna.getX1(), pomocna.getY1(), pomocna1.getX1(), pomocna1.getY1(), 0xff0000);
                }
            }
           /* pomocna = this.uhelniky.get(1);
            size = this.uhelniky.size();
            pomocna1 = this.uhelniky.get(size);
            drawDDA(pomocna.getX1(), pomocna.getY1(), pomocna1.getX1(), pomocna1.getY1(), 0xff0000);
        */
        }

    }

    private void drawExisting() {
        if (this.body.size() > 1) {
            Bod pomocna, pomocna1;

            for (int i = 0; i <= this.body.size() - 1; i = i + 2) {
                pomocna = body.get(i);
                if (i + 1 < body.size()) {
                    pomocna1 = body.get(i + 1);
                    drawDDA(pomocna.getX1(), pomocna.getY1(), pomocna1.getX1(), pomocna1.getY1(), 0x00ffff);
                }
            }
        }

    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }



    public void drawPixel(int x, int y, int color) {
        // nastavit pixel do img
        img.setRGB(x, y, color);
    }

    public void drawDDA(int x1, int y1, int x2, int y2, int color) {
        int pomocna1,pomocna2;
        int dx = x2 - x1;
        int dy = y2 - y1;
        float x, y, k, h = 0, g = 0,b;

        k = dy / (float) dx;
        if(x2<x1) {
            pomocna1 = x1;
            x1 = x2;
            x2 = pomocna1;
            pomocna2 = y1;
            y1 = y2;
            y2 = pomocna2;

            if (Math.abs(k) < 1) {

                // řídící osa X
                g = 1;
                h = k;

            } else {
                if (x2 < x1) {
                    pomocna1 = x1;
                    x1 = x2;
                    x2 = pomocna1;
                    pomocna2 = y1;
                    y1 = y2;
                    y2 = pomocna2;
                } else {
                    if (y2 < y1) {
                        pomocna1 = x1;
                        x1 = x2;
                        x2 = pomocna1;
                        pomocna2 = y1;
                        y1 = y2;
                        y2 = pomocna2;
                    }
                    // řídící osa Y
                    g = 1 / k;
                    h = 1;
                }
            }

        } else {if (Math.abs(k) < 1) {

            // řídící osa X
            g = 1;
            h = k;

        } else {
            if (x2 < x1) {
                pomocna1 = x1;
                x1 = x2;
                x2 = pomocna1;
                pomocna2 = y1;
                y1 = y2;
                y2 = pomocna2;
            } else {
                if (y2 < y1) {
                    pomocna1 = x1;
                    x1 = x2;
                    x2 = pomocna1;
                    pomocna2 = y1;
                    y1 = y2;
                    y2 = pomocna2;
                }
                // řídící osa Y
                g = 1 / k;
                h = 1;
            }
        }

        }
        x = x1;
        y = y1;
        for (int i = 0; i <= Math.max(Math.abs(dx), Math.abs(dy)); i++) {
            drawPixel(Math.round(x), Math.round(y), color);
            x += g;
            y += h;

        }

    }
}
