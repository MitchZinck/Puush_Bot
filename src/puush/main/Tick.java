package puush.main;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Tick implements Runnable {
	private static Image imageFinal = null;
	private static String hostname;
	private static String puush = "http://puu.sh/6";
	
	public static Image checkHeader(String randUrl) {
		Image image = null;
		try {
            URL url = new URL(randUrl);
            image = ImageIO.read(url);
        } catch (IOException e) {
        	e.printStackTrace();
        }
		return image;
	}
	
	public static char randomUrl() {
		Random r = new Random();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		char a = alphabet.charAt(r.nextInt(26));
		return a;		
	}
	
	public void run() {
	    hostname = System.getProperty("user.name");
		getImage();
		outputImage();
	}
	
	public static void getImage() {
		String c = "http://puu.sh/6";
		for(int i = 0; i < 4; i++) {
			c += randomUrl();
		}
		if(checkHeader(c) != null) {
			imageFinal = checkHeader(c);
		} else {
			imageFinal = null;
		}
		puush = c;
	}

	private static void outputImage() {
		final JFrame frame = new JFrame();
        JPanel buttonPanel = new JPanel();
        while(imageFinal == null) {
			   getImage();
		}
//        if(imageFinal == null) {
////        	JOptionPane.showMessageDialog(frame,
////					    "Could not find random puush, operation aborted.",
////					    "Error",
////					    JOptionPane.ERROR_MESSAGE);
//        }
        final JLabel label = new JLabel(new ImageIcon(imageFinal));
        JButton keep = new JButton("Keep");
        JButton skip = new JButton("skip");
        final JTextField tb = new JTextField(puush);
        //frame.setSize(imageFinal.getWidth(null), imageFinal.getHeight(null) + 100);     
        frame.setSize(1000, 800);
		keep.addActionListener(new ActionListener() {
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
                buttonListener(evt, true, imageFinal);
                java.awt.EventQueue.invokeLater(new Runnable() { 
             	   public void run() { 
             		  while(imageFinal == null) {
           			   buttonListener(evt, false, imageFinal);
           		   }
//           			   JOptionPane.showMessageDialog(frame,
//           					    "Could not find random puush.",
//           					    "Error",
//           					    JOptionPane.ERROR_MESSAGE);
           		   tb.setText(puush);
                      label.setIcon(new ImageIcon(imageFinal));
                      frame.revalidate();
                      frame.repaint();
             	   } 
             	}); 
			}
		});
		
		skip.addActionListener(new ActionListener() {
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
                buttonListener(evt, false, imageFinal);
                java.awt.EventQueue.invokeLater(new Runnable() { 
                	   public void run() { 
                		   while(imageFinal == null) {
                			   buttonListener(evt, false, imageFinal);
                		   }
//                			   JOptionPane.showMessageDialog(frame,
//                					    "Could not find random puush.",
//                					    "Error",
//                					    JOptionPane.ERROR_MESSAGE);
                		   tb.setText(puush);
                           label.setIcon(new ImageIcon(imageFinal));
                           frame.revalidate();
                           frame.repaint();
                		}
                	   
                	}); 
            }
		});
		
        frame.setLayout(new BorderLayout());
        frame.add(label);
        buttonPanel.add(keep);
        buttonPanel.add(tb);
        buttonPanel.add(skip);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);		
	}
	
	public static void buttonListener(ActionEvent e, Boolean keep, Image image) {
		if(keep) { 	 
			BufferedImage i = (BufferedImage) image;
			Random r = new Random();
			String s = "";
			for(int j = 0; j < 5; j++) {
				int z = r.nextInt(10);
				s += z;
			}
			File outputfile = new File("C:/Users/" + hostname + "/Downloads/" + s + ".png");
			try {
				ImageIO.write(i, "png", outputfile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			getImage();
		} else {
			getImage();
		}
	}

}
