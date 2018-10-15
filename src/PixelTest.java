import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PixelTest {
    private JFrame window;
    private BufferedImage img;
    private Canvas canvas;
    private Renderer renderer;


    public PixelTest(){
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);
        window.setTitle("Úsečka a n-úhelník");
        //inicializace image, nastavení rozměrů
        img = new BufferedImage(800,600, BufferedImage.TYPE_INT_RGB);

        // inicializtace plátna, do kterého budeme kreslit img
        canvas = new Canvas();


        window.add(canvas);
        window.setVisible(true);

        renderer = new Renderer(img, canvas);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                    System.out.println("Pressed");
                    renderer.body.add(new Bod(e.getX(), e.getY()));
                    renderer.drawPixel(e.getX(), e.getY(), 0x00ffff);
                }else {
                    System.out.println("Pressed");
                    renderer.uhelniky.add(new Bod(e.getX(), e.getY()));
                    renderer.drawPixel(e.getX(), e.getY(), 0xff0000);
                }

                //  renderer.setX1(e.getX());
                //  renderer.setY1(e.getY());

                //points.add(e.getX());
                //points.add(e.getY());
                //renderer.drawPolygon(points);
            }

        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                    renderer.clear();
                    int b = renderer.body.size();
                    Bod pomocnaZacatek = renderer.body.get(b - 1);
                    renderer.drawDDA(pomocnaZacatek.getX1(), pomocnaZacatek.getY1(), e.getX(), e.getY(), 0x00ffff);
                } else {
                    renderer.clear();
                    int a = 1;
                    int b = renderer.uhelniky.size();

                    Bod pomocnaZacatek = renderer.uhelniky.get(b - 1);
                    if(2 >= renderer.uhelniky.size()) {
                        renderer.drawDDA(pomocnaZacatek.getX1(), pomocnaZacatek.getY1(), e.getX(), e.getY(), 0xffd700);
                    } else {
                        Bod pomocna = renderer.uhelniky.get(a);
                        renderer.drawDDA(pomocnaZacatek.getX1(), pomocnaZacatek.getY1(), e.getX(), e.getY(), 0xffd700);
                        renderer.drawDDA(pomocna.getX1(), pomocna.getY1(), e.getX(), e.getY(), 0xffd700);
                    }
                }
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                renderer.body.add(new Bod(e.getX(),e.getY()));
                System.out.println("Release");
                } else {
                    renderer.uhelniky.add(new Bod(e.getX(),e.getY()));
                    System.out.println("Release");
                }
            }
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(PixelTest::new);
        // https://www.google.com/search?q=SwingUtilities.invokeLater
        // https://www.javamex.com/tutorials/threads/invokelater.shtml
        // https://www.google.com/search?q=java+double+colon
    }
}