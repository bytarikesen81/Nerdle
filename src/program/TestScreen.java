package program;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Toolkit;

//Buton vasitasiyla duzenli olarak generateEquation metodunu calistirarak urettigi denklemi yazdiran islemin yapildigi arayuz
public class TestScreen {

	private JFrame frame;

	public JFrame getFrame() {
		return this.frame;
	}
	
	//Arayuzu calistir
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestScreen window = new TestScreen();
					window.frame.setLocationRelativeTo(null); 
					window.frame.setResizable(false);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Arayuzu Olustur
	public TestScreen() {
		initialize();
	}
	
	//Arayuz iceriklerini hazirla ve arayuz yuklenirken iceriklerle baglantili olan on-islemleri yerine getir
	private void initialize() {
		
		//Ekran cercevesi
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(TestScreen.class.getResource("/program/cubelogo.png")));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 297);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblHeader = new JLabel("Oyun Denklemi:");
		lblHeader.setBounds(127, 10, 185, 20);
		lblHeader.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblHeader);
		
		JLabel lblEquation = new JLabel("");
		lblEquation.setBounds(127, 105, 185, 20);
		lblEquation.setHorizontalAlignment(SwingConstants.CENTER);
		lblEquation.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.getContentPane().add(lblEquation);
		lblEquation.setText(GameOps.generateEquation());
		
		JButton btnNewButton = new JButton("YENIDEN URET");
		btnNewButton.setBackground(SystemColor.inactiveCaptionBorder);
		btnNewButton.setBounds(0, 184, 217, 49);
		frame.getContentPane().add(btnNewButton);
		
		//Basildiginda ana ekrana geri donduren buton
		JButton btnGeri = new JButton("GERI");
		btnGeri.setBackground(SystemColor.inactiveCaptionBorder);
		btnGeri.setBounds(217, 184, 217, 49);
		btnGeri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu anaMenu = new MainMenu();
				anaMenu.getFrame().setLocationRelativeTo(null); 
				anaMenu.getFrame().setResizable(false);
				anaMenu.getFrame().setVisible(true);
				frame.dispose();
			}
		});
		frame.getContentPane().add(btnGeri);
		
		JLabel signFooter = new JLabel("Gelistirici: 20011071 - Tar\u0131k Esen");
		signFooter.setBounds(0, 244, 217, 14);
		frame.getContentPane().add(signFooter);
		
		//Her yeniden uret butonuna basildiginda denklemi uret ve yazdir
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblEquation.setText(GameOps.generateEquation());
			}
		});
	}
}
