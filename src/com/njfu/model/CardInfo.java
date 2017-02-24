package com.njfu.model;

public class CardInfo {

	private String cardno;
	private String identity;
	private String type="储蓄卡";
	private String password="111111";
	private Double balance=1000.0;
	
	//注册时随机生成19为的卡号
		public CardInfo(){
			String str ="";
			while(true){
				str=""+System.currentTimeMillis()+(int)(Math.random()*87654321);
				if(str.length()>=19){
					break;
				}
			}
			this.cardno=str.substring(str.length()-19);
		}

		public String getCardno() {
			return cardno;
		}

		public void setCardno(String cardno) {
			this.cardno = cardno;
		}

		public String getIdentity() {
			return identity;
		}

		public void setIdentity(String identity) {
			this.identity = identity;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Double getBalance() {
			return balance;
		}

		public void setBalance(Double balance) {
			this.balance = balance;
		} 
	

}
