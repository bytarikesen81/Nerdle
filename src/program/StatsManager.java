package program;

import java.util.*;

//Oyun istatistiklerini hesaplayan islemlerin yapildigi sinif
public class StatsManager {
	
	//Toplam kazanilan oyunu hesaplar
	public static int totalGamesWon() {
		int count = 0;
		LinkedList<Game> games = SaveManager.getGames();
		for(Game game : games) 
			if(game.getStatus().equals("won")) ++count;
		return count;
	}
	
	//Toplam kaybedilen oyunu hesaplar
	public static int totalGamesLost() {
		int count = 0;
		LinkedList<Game> games = SaveManager.getGames();
		for(Game game : games) 
			if(game.getStatus().equals("lost")) ++count;
		return count;
	}
	
	//Toplam yarida birakilmis oyunu hesaplar
	public static int totalGamesAborted() {
		int count = 0;
		LinkedList<Game> games = SaveManager.getGames();
		for(Game game : games) 
			if(game.getStatus().equals("in prog")) ++count;
		return count;
	}
	
	//Toplam kazanc basina kac satir harcandigini/tahmin yapildigini hesaplar
	public static double guessPerWin() {
		double gamesWon = 0,totalRow = 0;
		LinkedList<Game> games = SaveManager.getGames();
		for(Game game : games) {
			if(game.getStatus().equals("won")) {
				++gamesWon;
				totalRow += (game.getLineCount()+1);
			}
		}
		if(gamesWon != 0)
			return (totalRow/gamesWon);
		else return 0;
	}
	
	//Toplam kazanc basina ne kadar sure harcandigini hesaplar
	public static double completeTimePerWin() {
		double gamesWon = 0,totalTime = 0;
		LinkedList<Game> games = SaveManager.getGames();
		for(Game game : games) {
			if(game.getStatus().equals("won")) {
				++gamesWon;
				totalTime += game.getTime();
			}
		}
		if(gamesWon != 0)
			return (totalTime/gamesWon);
		else return 0;
	}
}
