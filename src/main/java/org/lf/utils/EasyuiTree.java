package org.lf.utils;

import java.util.ArrayList;
import java.util.List;

public class EasyuiTree {

	private String id;
	private String text;
	private List<EasyuiTree> children = new ArrayList<>();
	private String state;
	private String iconCls;
	private Boolean checked;

	public Boolean isChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
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

	public List<EasyuiTree> getChildren() {
		return children;
	}

	public void setChildren(List<EasyuiTree> children) {
		this.children = children;
	}

	public EasyuiTree(String id, String text, List<EasyuiTree> children,
			String state, Boolean checked) {
		super();
		this.id = id;
		this.text = text;
		this.children = children;
		this.state = state;
		this.checked = checked;
	}

	public EasyuiTree(String id, String text, List<EasyuiTree> children,
			Boolean checked) {
		super();
		this.id = id;
		this.text = text;
		this.children = children;
		this.checked = checked;
	}

	public EasyuiTree(String id, String text, List<EasyuiTree> children,
			String state, String iconCls, Boolean checked) {
		super();
		this.id = id;
		this.text = text;
		this.children = children;
		this.state = state;
		this.iconCls = iconCls;
		this.checked = checked;
	}

	public EasyuiTree(String text, List<EasyuiTree> children,
			String state, String iconCls, Boolean checked) {
		super();
		this.text = text;
		this.children = children;
		this.state = state;
		this.iconCls = iconCls;
		this.checked = checked;
	}
	
	public EasyuiTree() {
	}

}
