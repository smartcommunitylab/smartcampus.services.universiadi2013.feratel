package it.sayservice.services.feratel.script;

public class KeyValueItem {
	private String id;
	private String descIt;
	private String descEn;
	
	public KeyValueItem(String id, String descIt, String descEn) {
		this.id = id;
		this.descIt = descIt;
		this.descEn = descEn;
	}
	
	public KeyValueItem() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescIt() {
		return descIt;
	}

	public void setDescIt(String descIt) {
		this.descIt = descIt;
	}

	public String getDescEn() {
		return descEn;
	}

	public void setDescEn(String descEn) {
		this.descEn = descEn;
	}
}
