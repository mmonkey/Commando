package com.gmail.mmonkey.Commando;

public class Action {

	private ActionType type;
	private String value;
	
	public ActionType getType() {
		return this.type;
	}
	
	public void setType(ActionType type) {
		this.type = type;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public Action(ActionType type, String value) {
		this.type = type;
		this.value = value;
	}
	
}
