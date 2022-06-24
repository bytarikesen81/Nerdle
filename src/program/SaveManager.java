package program;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import javax.swing.JOptionPane;

//Dosya,okuma yazma ve kayit islemlerinin yapildigi sinif
public class SaveManager {
	private static LinkedList<Game> games = new LinkedList<Game>();

	private static File savegames;
	private static File stats;
	
	public static File getSavesFile() {
		return savegames;
	}
	
	public static File getStatsFile() {
		return stats;
	}
	
	public static void setSavegames(File savegames) {
		SaveManager.savegames = savegames;
	}

	public static void setStats(File stats) {
		SaveManager.stats = stats;
	}
	
	
	public static LinkedList<Game> getGames(){
		return SaveManager.games;
	}
	
	//Oyun nesnesini kayit veritabanina yazarak oyunu kaydeden metod
	public static void SaveGame(Game save) throws IOException {
		ObjectOutputStream writer;
		try {
			writer = new ObjectOutputStream(new FileOutputStream(savegames.getPath()));
			writer.writeObject(save);
			writer.close();
		} catch (FileNotFoundException f) {
			JOptionPane.showMessageDialog(null,"Kayit Veritabani Bulunamadi","Hata",JOptionPane.ERROR_MESSAGE);
			f.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Kaydedilmis bir oyunu kayit veritabanindan cekerek program ici oyun nesnesine aktaran metod
	public static Game LoadGame() throws IOException{
		Game save;
		ObjectInputStream reader =  new ObjectInputStream(new FileInputStream(savegames.getPath()));
		try {
			save = (Game)reader.readObject();
			reader.close();
			return save;
		}catch (FileNotFoundException f) {
			JOptionPane.showMessageDialog(null,"Kayit Veritabani Bulunamadi","Hata",JOptionPane.ERROR_MESSAGE);
			f.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Oyun Kaydi Sirasinda Beklenmeyen Bir Hata Olustu","Hata",JOptionPane.ERROR_MESSAGE);
			return null;
		}finally {
			reader.close();
		}
	}
	
	//Kayit veritabanini temizleyerek varolan kayitli oyunu silen metod
	public static void DeleteGame() throws IOException {
		ObjectOutputStream writer;
		try {
			writer = new ObjectOutputStream(new FileOutputStream(savegames.getPath()));
			writer.reset();
			writer.close();
		} catch (FileNotFoundException f) {
			JOptionPane.showMessageDialog(null,"Kayit Veritabani Bulunamadi","Hata",JOptionPane.ERROR_MESSAGE);
			f.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Gecmisteki tum oyunlari istatistikleri kaydetmek icin yazan metod
	public static void WriteStats(LinkedList<Game> games) throws IOException {
		ObjectOutputStream writer;
		try {
			writer = new ObjectOutputStream(new FileOutputStream(stats.getPath()));
			writer.writeObject(games);
			writer.close();
		} catch (FileNotFoundException f) {
			JOptionPane.showMessageDialog(null,"Istatistik Veritabani Bulunamadi","Hata",JOptionPane.ERROR_MESSAGE);
			f.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Gecmisteki tum oyunlari istatistiklerini elde edebilmek icin veritabanindan okuyan metod
	@SuppressWarnings("unchecked")
	public static LinkedList<Game> ReadStats() throws IOException{
		ObjectInputStream reader = new ObjectInputStream(new FileInputStream(stats.getPath()));
		try {
			games = (LinkedList<Game>)reader.readObject();
			reader.close();
			return games;
		}catch (FileNotFoundException f) {
			JOptionPane.showMessageDialog(null,"Istatistik Veritabani Bulunamadi","Hata",JOptionPane.ERROR_MESSAGE);
			f.printStackTrace();
			return null;
		}catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null,"Istatistik Yuklenmesi Sirasinda Beklenmeyen Bir Hata Olustu","Hata",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}finally {
			reader.close();
		}
	}
	
	//Mevcut oyunu istatistik veritabanina ekleyen metod
	public static void addGameToStats(Game game) {
		int i = 0;
		while(i<games.size() && games.get(i).getID() != game.getID()) ++i;
		if(i == games.size()) 
			games.add(game);
		else
			games.set(i, game);
		try {
			WriteStats(games);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
