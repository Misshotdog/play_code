package org.mi.core.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuView {
	private long id;
	private String text;
	private String iconCls;
	private Map<String, String> attributes = new HashMap<String, String>();
	private List<MenuView> children = new ArrayList<MenuView>();

	
	
	public MenuView() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public MenuView(long id, String text, String iconCls) {
		super();
		this.id = id;
		this.text = text;
		this.iconCls = iconCls;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public List<MenuView> getChildren() {
		return children;
	}

	public void setChildren(List<MenuView> children) {
		this.children = children;
	}

}
