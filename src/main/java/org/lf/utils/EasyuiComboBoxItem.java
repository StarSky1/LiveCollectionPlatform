package org.lf.utils;

public class EasyuiComboBoxItem {
	private String id;
	private String text;
	private boolean selected;
	
	
	public EasyuiComboBoxItem(String id, String text) {
		this.id = id;
		this.text = text;
	}
	
	

	public EasyuiComboBoxItem(String id, String text, boolean selected) {
		super();
		this.id = id;
		this.text = text;
		this.selected = selected;
	}



	public EasyuiComboBoxItem() {
		super();
	}



	public boolean isSelected() {
		return selected;
	}



	public void setSelected(boolean selected) {
		this.selected = selected;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
