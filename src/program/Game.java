package program;
import java.io.*;

//Birim oyunun tum bilesenlerini kapsayan oyun sinifi
public class Game implements Serializable{
	private static final long serialVersionUID = 1L;
	private final int gameID; 
	private String status;
	private String targetEq;
	private String[][] values;
	private int lineCount;
	private int timeElapsed;
	
	public Game(int ID,String status, String targetEq, String[][] values, int lineCount,int time) {
		super();
		this.gameID = ID;
		this.status = status;
		this.targetEq = targetEq;
		this.values = values;
		this.lineCount = lineCount;
		this.timeElapsed = time;
	}
	
	public int getID() {
		return gameID;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[][] getValues() {
		return values;
	}

	public void setValues(String[][] values) {
		this.values = values;
	}

	public int getLineCount() {
		return lineCount;
	}

	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}

	public String getTargetEq() {
		return targetEq;
	}
	
	public int getTime() {
		return timeElapsed;
	}
	
	public void setTime(int elapsedTime) {
		this.timeElapsed = elapsedTime;
	}
	
	public String toString() {
		return "ID:"+this.gameID+", Equation:"+this.targetEq+" ,LineCount:"+this.lineCount+", Status:"+this.status+" ,Time:"+this.timeElapsed;
	}
}
