package bankManagementServer;

import java.io.Serializable;

import Bank.PersonalData;

public class AccountInformation implements Serializable {
	PersonalData data;
	Bank.accountType type;
	String accountID;
	int balance = 100;
	float rateDecrease = 0;

	public PersonalData getData() {
		return data;
	}

	public int getBalance() {
		return balance;
	}

	public Bank.accountType getType() {
		return type;
	}

	public String getAccountID() {
		return accountID;
	}

	public void changeBalance(int amount){
		balance += amount;
	}
	
	public void setRateDecrease(float rateDecrease){
		this.rateDecrease = rateDecrease;
	}
	public float getRateDecrease(){
		return this.rateDecrease;
	}
	
	public AccountInformation(PersonalData data, Bank.accountType type,
			String accountID) {
		// TODO Auto-generated constructor stub
		this.data = data;
		this.type = type;
		this.accountID = accountID;
	}
	
}
