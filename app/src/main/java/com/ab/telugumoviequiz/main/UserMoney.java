package com.ab.telugumoviequiz.main;

public class UserMoney {
	private long id;
	private long userProfileId;
	private long loadedAmount;
	private long winningAmount;
	private long referalAmount;
	private long loadedAmtLocked;
	private long winningAmtLocked;
	private long referalAmtLocked;
	
	public long getLoadedAmtLocked() {
		return loadedAmtLocked;
	}
	public void setLoadedAmtLocked(long loadedAmtLocked) {
		this.loadedAmtLocked = loadedAmtLocked;
	}
	public long getWinningAmtLocked() {
		return winningAmtLocked;
	}
	public void setWinningAmtLocked(long winningAmtLocked) {
		this.winningAmtLocked = winningAmtLocked;
	}
	public long getReferalAmtLocked() {
		return referalAmtLocked;
	}
	public void setReferalAmtLocked(long referalAmtLocked) {
		this.referalAmtLocked = referalAmtLocked;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserProfileId() {
		return userProfileId;
	}
	public void setUserId(long userProfileId) {
		this.userProfileId = userProfileId;
	}
	public long getLoadedAmount() {
		return loadedAmount;
	}
	public void setLoadedAmount(long loadedAmount) {
		this.loadedAmount = loadedAmount;
	}
	public long getWinningAmount() {
		return winningAmount;
	}
	public void setWinningAmount(long winningAmount) {
		this.winningAmount = winningAmount;
	}
	public long getReferalAmount() {
		return referalAmount;
	}
	public void setReferalAmount(long referalAmount) {
		this.referalAmount = referalAmount;
	}
	
	@Override
	public String toString() {
		return "UserMoney [id=" + id + ", userProfileId=" + userProfileId + ", loadedAmount=" + loadedAmount
				+ ", winningAmount=" + winningAmount + ", referalAmount=" + referalAmount + "]";
	}

}
