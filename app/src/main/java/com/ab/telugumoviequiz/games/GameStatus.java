package com.ab.telugumoviequiz.games;

import java.util.Map;

public class GameStatus {
	long gameId;
	int currentCount;
	int gameStatus;
	Map <Long, Boolean> userAccountRevertStatus;
 
	public long getGameId() {
		return gameId;
	}
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
	public int getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	public int getGameStatus() {
		return gameStatus;
	}
	public void setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
	}
	public Map<Long, Boolean> getUserAccountRevertStatus() {
		return userAccountRevertStatus;
	}
	public void setUserAccountRevertStatus(Map<Long, Boolean> userAccountRevertStatus) {
		this.userAccountRevertStatus = userAccountRevertStatus;
	}

	public String toString() {
		return gameId + " : " + getCurrentCount();
	}
}
