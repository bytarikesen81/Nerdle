package program;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;

//Ana Menu Ekrani
public class MainMenu {
	private JFrame frame;
	private static boolean hasSave;
	private static Game gameToLoad;
	public JFrame getFrame() {
		return this.frame;
	}
	
	//Arayuzu calistir
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
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
	public MainMenu() {
		//Gecmis oyun kayitlarini ve istatistiklerini hazirla
		
		//Gerekli dosyalar yoksa uygun konumlarda olustur, varsa hazir olanlardan devam et
		File stats = new File("playedgames.ser");
		File saves = new File("savegame.ser");
		if(!stats.exists()) {
			try {
				stats.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!saves.exists()) {
			try {
				saves.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Dosyalar uzerinden program kapsaminda kullanilacak dosya objelerini olustur
		SaveManager.setSavegames(saves);
		SaveManager.setStats(stats);
		
		//Istatistikleri Oku
		try {
			SaveManager.ReadStats();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Kayit durumunu hazirla ardindan kayitta oyun olup olmadigini kontrol et ve varsa kayitli oyunu yukle 
		try {
			gameToLoad = SaveManager.LoadGame();
			MainMenu.hasSave = true;
		}
		catch (IOException e1) {
			MainMenu.hasSave = false;
			e1.printStackTrace();
		}
		initialize();
	}

	//Arayuz iceriklerini hazirla ve arayuz yuklenirken iceriklerle baglantili olan on-islemleri yerine getir
	private void initialize() {
		
		//Ekran cercevesi
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainMenu.class.getResource("/program/cubelogo.png")));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 476, 538);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Oyun basligi
		JLabel lblHeader = new JLabel("NERDLE ");
		lblHeader.setIcon(new ImageIcon(MainMenu.class.getResource("/program/cubelogo.png")));
		lblHeader.setBounds(101, 11, 250, 42);
		lblHeader.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblHeader);
		
		//Yeni oyun olusturan buton
		JButton btnNewButton = new JButton("YENI OYUN");
		btnNewButton.setBackground(new Color(0, 204, 51));
		btnNewButton.setBounds(10, 311, 440, 42);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tarEqu = GameOps.generateEquation(); //Yeni oyun icin denklem olustur
				//Denklem uzerinden oyun nesnesi olustur 
				Game newGame = new Game(SaveManager.getGames().size()+1,"in prog",tarEqu,new String[6][tarEqu.length()],0,0);
				//Olusturulan yeni oyun nesnesi parametresi uzerinden yeni oyun arayuzune gecis yap
				GameScreen yeniOyunEkrani = new GameScreen(newGame);
				yeniOyunEkrani.getFrame().setLocationRelativeTo(null); 
				yeniOyunEkrani.getFrame().setResizable(false);
				yeniOyunEkrani.getFrame().setVisible(true);
				frame.dispose();//Ana ekran arayuzunu kapat
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		//Test arayuzunu calistiran buton
		JButton btnTest = new JButton("TEST");
		btnTest.setBackground(new Color(255, 215, 0));
		btnTest.setBounds(10, 359, 440, 42);
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Test arayuzune gecis yap
				TestScreen testEkrani = new TestScreen();
				testEkrani.getFrame().setLocationRelativeTo(null); 
				testEkrani.getFrame().setResizable(false);
				testEkrani.getFrame().setVisible(true);
				frame.dispose();//Ana ekran arayuzunu kapat
			}
		});
		frame.getContentPane().add(btnTest);
		
		//Kayitta var olan bir oyunu devam ettiren buton
		JButton btnDevamEt = new JButton("DEVAM ET");
		btnDevamEt.setBackground(new Color(0, 204, 255));
		btnDevamEt.setBounds(10, 407, 440, 42);
		btnDevamEt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Kayittaki oyun parametresi uzerinden oyun arayuzune gecis yap
				GameScreen yeniOyunEkrani = new GameScreen(gameToLoad);
				yeniOyunEkrani.getFrame().setLocationRelativeTo(null); 
				yeniOyunEkrani.getFrame().setResizable(false);
				yeniOyunEkrani.getFrame().setVisible(true);
				frame.dispose();//Ana ekran arayuzunu kapat
			}
		});
		frame.getContentPane().add(btnDevamEt);
		
		//Istatistik Paneli
		JPanel panelStats = new JPanel();
		panelStats.setBackground(new Color(255, 153, 0));
		panelStats.setBounds(10, 75, 440, 225);
		frame.getContentPane().add(panelStats);
		panelStats.setLayout(null);
		
		//Panel Basligi
		JLabel lblNewLabel_1 = new JLabel("ISTATISTIKLER"); 
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(161, 11, 120, 25);
		panelStats.add(lblNewLabel_1);
		
		//Yarida Birakilan Oyun Sayisini Gosteren Baslik
		JLabel lblNewLabel = new JLabel("Yarida Birakilan Oyun: "+StatsManager.totalGamesAborted()); 
		lblNewLabel.setBounds(10, 47, 420, 20);
		panelStats.add(lblNewLabel);
		
		//Kaybedilen Oyun Sayisini Gosteren Baslik
		JLabel lblKaybedilenOyun = new JLabel("Kaybedilen Oyun: "+StatsManager.totalGamesLost()); 
		lblKaybedilenOyun.setBounds(10, 78, 420, 20);
		panelStats.add(lblKaybedilenOyun);
		
		//Kazanilan Oyun Sayisini Gosteren Baslik
		JLabel lblKazanlanOyun = new JLabel("Kazanilan Oyun: "+StatsManager.totalGamesWon()); 
		lblKazanlanOyun.setBounds(10, 109, 420, 20);
		panelStats.add(lblKazanlanOyun);
		
		//Basariyla Tamamlanan Oyunlarin Ortalama Kac Satirda Tamamlandigini Gosteren Baslik
		JLabel lblKazanlanOyun_1 = new JLabel("Kazanilan Oyun Basina Yapilan Tahmin(Satir) Sayisi: "+StatsManager.guessPerWin());  
		lblKazanlanOyun_1.setBounds(10, 140, 420, 20);
		panelStats.add(lblKazanlanOyun_1);
		
		//Basariyla Tamamlanan Oyunlarin Ortalama Ne Kadar Surede Tamamlandigini Gosteren Baslik
		JLabel lblKazanlanOyun_1_1 = new JLabel("Kazanilan Oyun Basina Ortalama Gecirilen Sure(s): "+StatsManager.completeTimePerWin()); 
		lblKazanlanOyun_1_1.setBounds(10, 171, 420, 20);
		panelStats.add(lblKazanlanOyun_1_1);
		
		//Imza Footer'i
		JLabel signFooter = new JLabel("Gelistirici: 20011071 - Tarik Esen");
		signFooter.setBounds(10, 474, 208, 14);
		frame.getContentPane().add(signFooter);
		
		JButton btnSettings = new JButton("Ayarlar");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnSettings.setBounds(361, 11, 89, 23);
		frame.getContentPane().add(btnSettings);
		
		/*Ana menuyu hazirla*/	
		
		//Kayitta oyun olup olmamasina gore devam et butonunu goster/gizle 
		btnDevamEt.setVisible(hasSave);
	}
}
