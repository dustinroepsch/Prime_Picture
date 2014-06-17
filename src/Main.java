import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Created by dustin on 6/16/14.
 */
public class Main extends JFrame{
    private JButton button;
    private File file;
    public Main(){
        super("Prime Picture Maker");
        setSize(300,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(0,1));
        button = new JButton("Start");
        final JFileChooser filechooser = new JFileChooser();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int accepted = filechooser.showSaveDialog(getComponent(0));
                if (accepted == JFileChooser.APPROVE_OPTION) {

                    file = filechooser.getSelectedFile();

                    try {
                        String input = JOptionPane.showInputDialog("Enter Size");
                        BufferedImage image = imageWithPrimes(Integer.valueOf(input));
                        JOptionPane.showMessageDialog(getComponent(0),"Creating and saving!");
                        ImageIO.write(image, "png", file);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        add(button);
    }

    private BufferedImage imageWithPrimes(Integer size) {
        boolean[] primes = createSiv(size*size);
        BufferedImage image = new BufferedImage(size,size,BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                image.setRGB(j,i,(primes[(i*(size)+j)])?0:16777215);
            }
        }
        return image;
    }

    public static void main(String[] args){
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    Main main = new Main();
                    main.setVisible(true);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static boolean[] createSiv(int size) {
        boolean[] siv = new boolean[size];
        Arrays.fill(siv,true);
        siv[0] = false;
        siv[1] = false;
        for (int prime = 2; prime * 2 < siv.length; prime++){
            if (siv[prime]){
                for (int i = 2; i * prime < siv.length; i++){
                    siv[i*prime] = false;
                }
            }
        }
        return siv;
    }
}
