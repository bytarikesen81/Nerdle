package program;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.util.*;
import java.lang.*;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;

import java.awt.Canvas;
import java.awt.Button;
import java.awt.ScrollPane;
import java.awt.Panel;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.JEditorPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.Toolkit;
import javax.swing.SwingConstants;

//Oyun ekrani
public class GameScreen {
	private static Game game;
	private static Border normal =	BorderFactory.createEmptyBorder(5, 5, 5, 15);
	private static Border selected = new LineBorder(Color.black,3,true);
	private static ArrayList<PanelRound> curLine;
	private static Integer elapsedSec;
	private static Timer timer;
	private static String values[][];
	
	private JFrame frame;
	public JFrame getFrame() {
		return this.frame;
	}
	
	//Arayuzu calistir
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameScreen window = new GameScreen(game);
					window.frame.setLocationRelativeTo(null); 
					window.frame.setResizable(false);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Arayuzu olustur
	public GameScreen(Game game) {
		GameScreen.game = game; //Ana ekran nesnesinden alinan oyun parametresi
		GameScreen.curLine = new ArrayList<PanelRound>();
		GameScreen.elapsedSec = game.getTime();
		GameScreen.values = new String[6][GameScreen.game.getTargetEq().length()];
		this.initialize();
	}
	
	//Arayuz iceriklerini hazirla ve arayuz yuklenirken iceriklerle baglantili olan on-islemleri yerine getir
	private void initialize()  {
		String targetEq = GameScreen.game.getTargetEq();
		int L = targetEq.length();
		
		//Create Tile Components
		PanelRound[][] tiles = new PanelRound[6][L];
		JLabel[][] tileValues = new JLabel[6][L];
		
		//Ekran cercevesi
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(GameScreen.class.getResource("/program/cubelogo.png")));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.setBounds(100, 100, 754, 653);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel btnsPanel = new JPanel();
		btnsPanel.setBackground(Color.WHITE);
		btnsPanel.setBounds(0, 481, 715, 110);
		frame.getContentPane().add(btnsPanel);
		btnsPanel.setLayout(null);
		
		JButton btn1 = new JButton("1");
		btn1.setBackground(SystemColor.inactiveCaptionBorder);
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumBtnOperation(btn1,tiles,tileValues,L);
			}
		});
		btn1.setBounds(35, 5, 60, 40);
		btnsPanel.add(btn1);
		
		JButton btn2 = new JButton("2");
		btn2.setBackground(SystemColor.inactiveCaptionBorder);
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumBtnOperation(btn2,tiles,tileValues,L);
			}
		});
		btn2.setBounds(100, 5, 60, 40);
		btnsPanel.add(btn2);
		
		JButton btn3 = new JButton("3");
		btn3.setBackground(SystemColor.inactiveCaptionBorder);
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumBtnOperation(btn3,tiles,tileValues,L);
			}
		});
		btn3.setBounds(165, 5, 60, 40);
		btnsPanel.add(btn3);
		
		JButton btn4 = new JButton("4");
		btn4.setBackground(SystemColor.inactiveCaptionBorder);
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumBtnOperation(btn4,tiles,tileValues,L);
			}
		});
		btn4.setBounds(230, 5, 60, 40);
		btnsPanel.add(btn4);
		
		JButton btn5 = new JButton("5");
		btn5.setBackground(SystemColor.inactiveCaptionBorder);
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumBtnOperation(btn5,tiles,tileValues,L);
			}
		});
		btn5.setBounds(295, 5, 60, 40);
		btnsPanel.add(btn5);
		
		JButton btn6 = new JButton("6");
		btn6.setBackground(SystemColor.inactiveCaptionBorder);
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumBtnOperation(btn6,tiles,tileValues,L);
			}
		});
		btn6.setBounds(360, 5, 60, 40);
		btnsPanel.add(btn6);
		
		JButton btn7 = new JButton("7");
		btn7.setBackground(SystemColor.inactiveCaptionBorder);
		btn7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumBtnOperation(btn7,tiles,tileValues,L);
			}
		});
		btn7.setBounds(425, 5, 60, 40);
		btnsPanel.add(btn7);
		
		JButton btn8 = new JButton("8");
		btn8.setBackground(SystemColor.inactiveCaptionBorder);
		btn8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumBtnOperation(btn8,tiles,tileValues,L);
			}
		});
		btn8.setBounds(490, 5, 60, 40);
		btnsPanel.add(btn8);
		
		JButton btn9 = new JButton("9");
		btn9.setBackground(SystemColor.inactiveCaptionBorder);
		btn9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumBtnOperation(btn9,tiles,tileValues,L);
			}
		});
		btn9.setBounds(555, 5, 60, 40);
		btnsPanel.add(btn9);
		
		JButton btn0 = new JButton("0");
		btn0.setBackground(SystemColor.inactiveCaptionBorder);
		btn0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumBtnOperation(btn0,tiles,tileValues,L);
			}
		});
		btn0.setBounds(622, 5, 60, 40);
		btnsPanel.add(btn0);
		
		JButton btnAdd = new JButton("+");
		btnAdd.setBackground(SystemColor.inactiveCaptionBorder);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumBtnOperation(btnAdd,tiles,tileValues,L);
			}
		});
		btnAdd.setBounds(35, 56, 60, 40);
		btnsPanel.add(btnAdd);
		
		JButton btnAbort = new JButton("Sonra Bitir");
		btnAbort.setBackground(new Color(0,204,255));
		btnAbort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Oyunun verilerini al
				timer.cancel();
				GameScreen.game.setTime(elapsedSec);
				setTileValues(tiles,tileValues,L,game.getLineCount());
				resetTiles(tiles,L);
				
				//Oyun verisini kayit haznesine yaz
				try {
					SaveManager.SaveGame(GameScreen.game);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//Oyun verisini istatistiklere kaydet
				SaveManager.addGameToStats(GameScreen.game);
				
				//Ana Menuye Don
				MainMenu anaMenu = new MainMenu();
				anaMenu.getFrame().setLocationRelativeTo(null); 
				anaMenu.getFrame().setResizable(false);
				anaMenu.getFrame().setVisible(true);
				getFrame().dispose();
			}
		});
		btnAbort.setBounds(10, 11, 104, 35);
		frame.getContentPane().add(btnAbort);
		
		JButton btnEnter = new JButton("Tahmin Et");
		btnEnter.setBackground(new Color(124, 252, 0));
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Satir ici kutulardaki haneleri ifade haline getir
				try {
					String curEq = "",digs[];
					for(int i=0; i<L; i++) {
						curEq += tileValues[game.getLineCount()][i].getText();
					}
					digs = curEq.split(" ");
					curEq = "";
					for(int i=0; i<=L; i++) {
						curEq += digs[i];
					}
					
					//Ifadenin matematiksel kontrolunu yap. Gecerliyse cevabi onayla degilse islemi iptal et.
					
					//Ifade gecerli ve dogruysa
					if(GameOps.checkEquation(curEq)) {
						adjustTileColors(tiles,L,curEq,game.getLineCount());
						
						//Ifade hedef denkleme esitse kazanc olarak bitir
						if(curEq.compareTo(GameScreen.game.getTargetEq()) == 0) {
							timer.cancel(); //Sure akisini dondur
							GameScreen.game.setTime(elapsedSec);
							GameScreen.game.setStatus("won");
							setTileValues(tiles,tileValues,L,game.getLineCount());
							resetTiles(tiles,L);
							JOptionPane.showMessageDialog(null,"Tebrikler, dogru cevap!"+"\nTamamlama Suresi: "+game.getTime()+" saniye","Kazandiniz.",JOptionPane.INFORMATION_MESSAGE);
							for(int i=game.getLineCount(); i<6; i++) {
								for(int j=0; j<L; j++) {
									deactiveTile(tiles[i][j]);
								}
							}
							btnEnter.setEnabled(false);
							SaveManager.addGameToStats(game);
							try {
								SaveManager.DeleteGame();
							} catch (IOException e1) {
								JOptionPane.showMessageDialog(null,"Oyun silme basarisiz","Test",JOptionPane.INFORMATION_MESSAGE);
								e1.printStackTrace();
							}
							btnAbort.setEnabled(false);
						}
						//Esit degilse ve tahmin hakki kalmissa sonraki satira gec
						else if(nextLine(curLine,game.getLineCount(),L,tiles))
							GameScreen.game.setLineCount(game.getLineCount()+1);
						//Tahmin hakki kalmamissa oyunu kayip olarak bitir
						else {
							timer.cancel();
							GameScreen.game.setTime(elapsedSec);
							GameScreen.game.setStatus("lost");
							setTileValues(tiles,tileValues,L,game.getLineCount());
							resetTiles(tiles,L);
							JOptionPane.showMessageDialog(null,"Tahmin hakkiniz doldu. Bir dahaki sefere iyi sanslar!"
									+ "\nGecen Sure: "+game.getTime()+" saniye"+"\nDogru Cevap: "+game.getTargetEq(),"Kaybettiniz.",JOptionPane.INFORMATION_MESSAGE);
							btnEnter.setEnabled(false);
							SaveManager.addGameToStats(game);
							try {
								SaveManager.DeleteGame();
							} catch (IOException e1) {
								JOptionPane.showMessageDialog(null,"Oyun silme basarisiz","Test",JOptionPane.INFORMATION_MESSAGE);
								e1.printStackTrace();
							}
							btnAbort.setEnabled(false);
						}
					}
					
					//Ifade gecersiz veya yanlissa
					else {
						JOptionPane.showMessageDialog(null,"Gecersiz Islem","Test",JOptionPane.INFORMATION_MESSAGE);
					}
				//Ifade eksik girilmisse
				}catch(ArrayIndexOutOfBoundsException I) {
					JOptionPane.showMessageDialog(null,"Eksik tahmin\nIsleminizi tamamlamak icin lutfen tum haneleri doldurun","Test",JOptionPane.INFORMATION_MESSAGE);
					I.printStackTrace();
				}
			}
		});
		btnEnter.setBounds(555, 56, 127, 40);
		btnsPanel.add(btnEnter);
		
		JButton btnSub = new JButton("-");
		btnSub.setBackground(SystemColor.inactiveCaptionBorder);
		btnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumBtnOperation(btnSub,tiles,tileValues,L);
			}
		});
		btnSub.setBounds(100, 56, 60, 40);
		btnsPanel.add(btnSub);
		
		JButton btnMul = new JButton("*");
		btnMul.setBackground(SystemColor.inactiveCaptionBorder);
		btnMul.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumBtnOperation(btnMul,tiles,tileValues,L);
			}
		});
		btnMul.setBounds(165, 56, 60, 40);
		btnsPanel.add(btnMul);
		
		JButton btnDiv = new JButton("/");
		btnDiv.setBackground(SystemColor.inactiveCaptionBorder);
		btnDiv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumBtnOperation(btnDiv,tiles,tileValues,L);
			}
		});
		btnDiv.setBounds(230, 56, 60, 40);
		btnsPanel.add(btnDiv);
		
		JButton btnDel = new JButton("Sil");
		btnDel.setBackground(SystemColor.inactiveCaptionBorder);
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delBtnOperation(btnDel,tiles,tileValues,L);
			}
		});
		btnDel.setBounds(425, 56, 125, 40);
		btnsPanel.add(btnDel);
		
		JButton btnEqualizer = new JButton("=");
		btnEqualizer.setBackground(SystemColor.inactiveCaptionBorder);
		btnEqualizer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumBtnOperation(btnEqualizer,tiles,tileValues,L);
			}
		});
		btnEqualizer.setBounds(295, 56, 125, 40);
		btnsPanel.add(btnEqualizer);
		
		
		JPanel gamePanel = new JPanel();
		gamePanel.setBackground(Color.WHITE);
		gamePanel.setBounds(10, 70, 780, 400);
		frame.getContentPane().add(gamePanel);
		gamePanel.setLayout(null);
		
		JLabel lblTimer = new JLabel("Sure: "+game.getTime()+"s");
		lblTimer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTimer.setBounds(499, 21, 149, 25);
		frame.getContentPane().add(lblTimer);
		
		JLabel signFooter = new JLabel("Gelistirici: 20011071 - Tar\u0131k Esen");
		signFooter.setBounds(10, 589, 208, 14);
		frame.getContentPane().add(signFooter);
		
		JButton btnAnaSayfa = new JButton("Ana Sayfa");
		btnAnaSayfa.setBackground(SystemColor.inactiveCaptionBorder);
		btnAnaSayfa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Ana Menuye Don
				if(game.getStatus().equals("in prog")) {
					int dialogResult = JOptionPane.showConfirmDialog(null, "Oynadiginiz oyunu kaydetmeden ana ekrana donmek istediginizden emin misiniz?\nSu an oynamakta oldugunuz oyun silinecek.","Uyari",JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION){
						timer.cancel();
						try {
							SaveManager.DeleteGame();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null,"Oyun silme basarisiz","Test",JOptionPane.INFORMATION_MESSAGE);
							e1.printStackTrace();
						}
						SaveManager.addGameToStats(game);
						MainMenu anaMenu = new MainMenu();
						anaMenu.getFrame().setLocationRelativeTo(null); 
						anaMenu.getFrame().setResizable(false);
						anaMenu.getFrame().setVisible(true);
						getFrame().dispose();
					}
				}
				else {
					MainMenu anaMenu = new MainMenu();
					anaMenu.getFrame().setLocationRelativeTo(null); 
					anaMenu.getFrame().setResizable(false);
					anaMenu.getFrame().setVisible(true);
					getFrame().dispose();
				}
			}
		});
		btnAnaSayfa.setBounds(124, 11, 104, 35);
		frame.getContentPane().add(btnAnaSayfa);
		
		GameScreen.timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				elapsedSec++;
				lblTimer.setText("Sure: "+elapsedSec.toString()+"s");
			}
		};
		if(game.getStatus().equals("in prog"))
			timer.scheduleAtFixedRate(task, 1000, 1000);
		
		/* Oyunu hazirla/yukle */
		
		//Oyun tablosunu olustur
		double xj;
		int xi = 0;
		switch(L) {
			case 7:
				xi = 25;
				break;
			case 8:
				xi = 18;
				break;
			case 9:
				xi = 15;
				break;
			default:break;
		}
		xj = (780-(L*70+2*xi))/(L-1);
		for(int i=0; i<6; i++) {
			for(int j=0; j<L; j++) {
				tiles[i][j] = new PanelRound();
				///tiles[i][j].setEditable(false);
				tiles[i][j].setRounds(5, 5, 5, 5);
				tiles[i][j].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				tiles[i][j].setBackground(new Color(98, 94, 84));
				tiles[i][j].setForeground(Color.WHITE);
				tiles[i][j].setBounds((int)Math.round(xi+j*(60+xj)),5+(i*66),66, 60);
				
				tileValues[i][j] = new JLabel();
				tileValues[i][j].setFont(new Font("Tahoma", Font.PLAIN, 26));
				tileValues[i][j].setForeground(Color.white);
				tiles[i][j].add(tileValues[i][j]);
				
				gamePanel.add(tiles[i][j]);
			}
		}
		
		//Kutularin aktiflik durumlarini hazirla
		if(game.getStatus().equals("in prog")) {
			String[][] values = game.getValues();
			String curEq = "",digs[];
		
			//Satirlarin degerlerini yukle
			for(int i=0; i<=game.getLineCount(); i++) 
				for(int j=0; j<L; j++) 
					tileValues[i][j].setText(values[i][j]);
			
			//Her bir satirin yuklenen degerini esitlige cevir, buna gore renklerini yukle ve guncel satira kadar satir ilerlet
			for(int i=0; i<game.getLineCount(); i++) {
				//Satir icin denklem olustur
				for(int j=0; j<L; j++) {
					curEq += tileValues[i][j].getText();
				}
				digs = curEq.split(" ");
				curEq = "";
				for(int k=0; k<=L; k++) {
					curEq += digs[k];
				}
				//Denklem sonucuna gore satir icin renk ayarla
				adjustTileColors(tiles,L,curEq,i);
				//Denklemi sifirla ve iterasyonu surdur
				curEq = "";
			}
			
			//Simdiki satira gelince satiri aktiflestir
			for(int i=0; i<L; i++) {
				activeTile(tiles,L,tiles[game.getLineCount()][i]);
				selectTile(tiles,L,tiles[game.getLineCount()][0]);
			}
		}
		else {
			String[][] values = game.getValues();
			String curEq = "",digs[];
		
			//Satirlarin degerlerini yukle
			for(int i=0; i<=game.getLineCount(); i++) 
				for(int j=0; j<L; j++) 
					tileValues[i][j].setText(values[i][j]);
			
			//Her bir satirin yuklenen degerini esitlige cevir, buna gore renklerini yukle ve guncel satira kadar satir ilerlet
			for(int i=0; i<=game.getLineCount(); i++) {
				//Satir icin denklem olustur
				for(int j=0; j<L; j++) {
					curEq += tileValues[i][j].getText();
				}
				digs = curEq.split(" ");
				curEq = "";
				for(int k=0; k<=L; k++) {
					curEq += digs[k];
				}
				
				//Denklem sonucuna gore satir icin renk ayarla
				adjustTileColors(tiles,L,curEq,i);
				
				//Denklemi sifirla ve iterasyonu surdur
				curEq = "";
			}
			timer.cancel();//Sure akisini durdur
			btnEnter.setEnabled(false);
		}
		
	}
	
	private void resetTiles(JPanel[][] tiles,int L) {
		int i,j;
		for(i=0; i<6; i++) {
			for(j=0; j<L; j++) {
				tiles[i][j].setBorder(normal);
			}
		}
	}
	
	private void selectTile(JPanel[][] tiles,int L,JPanel tileToSelect) {
		resetTiles(tiles,L);
		tileToSelect.setBorder(selected);
	}
	
	private void activeTile(JPanel[][] tiles,int L,JPanel tile) {
		if(tile.isEnabled()) {
			if(tile.getMouseListeners().length == 0) {
				MouseListener clickFunc = new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
							resetTiles(tiles,L);
							selectTile(tiles,L,tile);
					}
				};
				tile.addMouseListener(clickFunc);
			}
		}
	}
	
	private void deactiveTile(JPanel tile) {
		for(MouseListener ml : tile.getMouseListeners()) {
			tile.removeMouseListener(ml);
		}
	}
	
	
	private boolean nextLine(ArrayList<PanelRound> curTiles,int lineNum,int L,PanelRound[][] tiles) {
		resetTiles(tiles,L);
		curTiles.clear();
		if(lineNum < 5) {
			for(int i=0; i<L; i++) {
				deactiveTile(tiles[lineNum][i]);
				activeTile(tiles,L,tiles[lineNum+1][i]);
				curTiles.add(tiles[lineNum+1][i]);
				selectTile(tiles,L,tiles[lineNum+1][0]);
			}
			return true;
		}
		else{
			for(int i=0; i<L; i++) {
				deactiveTile(tiles[lineNum][i]);
			}
			return false;
		}
	}
	
	private void NumBtnOperation(JButton btn,JPanel[][] tiles,JLabel[][] tileValues,int L) {
		int i = 0;
		while(i < L && tiles[game.getLineCount()][i].getBorder().equals(normal)) ++i;
		if(i != L) {
			tileValues[game.getLineCount()][i].setText(" "+btn.getText());
			if(i != L-1)
				selectTile(tiles,L,tiles[game.getLineCount()][i+1]);
		}
	}
	private void delBtnOperation(JButton btn,JPanel[][] tiles,JLabel[][] tileValues,int L) {
		int i = 0;
		while(i < L && tiles[game.getLineCount()][i].getBorder().equals(normal)) ++i;
		if(i != L) {
			if(i > 0) {
				if(tileValues[game.getLineCount()][i].getText().compareTo("") != 0) {
					tileValues[game.getLineCount()][i].setText("");
				}
				else {
					selectTile(tiles,L,tiles[game.getLineCount()][i-1]);
					tileValues[game.getLineCount()][i-1].setText("");
				}
			}
			else {
				tileValues[game.getLineCount()][i].setText("");	
			}
		}
	}
	
	private void setTileValues(JPanel tiles[][],JLabel tileValues[][],int L,int line) {
		String target[][] = new String[6][game.getTargetEq().length()];
		for(int i=0; i<=line; i++) {
			for(int j=0; j<L; j++) {
				target[i][j] = tileValues[i][j].getText();
			}
		}
		GameScreen.game.setValues(target);
	}
	
	private void adjustTileColors(JPanel[][] tiles,int L,String curEq,int line) {
		Integer foundInd;
		Character c;
		char tempEq[] = GameScreen.game.getTargetEq().toCharArray();
		
		//Tam Dogruluk Kontrolu
		for(int i=0; i<curEq.length(); i++) {
			c = curEq.charAt(i);
			if(c == tempEq[i]) {
				tiles[line][i].setBackground(new Color(0, 204, 51));
				tempEq[i] = 'x';
			}
		}
		
		//Yari Dogruluk Kontrolu
		for(int i=0; i<curEq.length(); i++) {
			c = curEq.charAt(i);
			if((foundInd = GameOps.arrayContains(tempEq, L, c)) != -1 && !tiles[line][i].getBackground().equals(new Color(0, 204, 51))) {
				tiles[line][i].setBackground(new Color(255, 215, 0));
				tempEq[foundInd] = 'x';
			}
		}
		
		//Yanlis Isaretleme
		for(int i=0; i<curEq.length(); i++) {
			if(!tiles[line][i].getBackground().equals(new Color(255, 215, 0)) && !tiles[line][i].getBackground().equals(new Color(0, 204, 51)))
				tiles[line][i].setBackground(new Color(255, 69, 0));
		}
	}
	
	public static class RoundedBorder implements Border {
        
        private int radius;
        
        RoundedBorder(int radius) {
            this.radius = radius;
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x,y,width-1,height-1,radius,radius);
        }
    }
}
