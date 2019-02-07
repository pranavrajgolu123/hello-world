package com.fmlb.forsaos.client.application.common;

import gwt.material.design.client.ui.MaterialLink;

public class NavigationLinkModel {

	private MaterialLink parentLink;

	private MaterialLink link;

	public NavigationLinkModel(MaterialLink parentLink, MaterialLink link) {
		super();
		this.parentLink = parentLink;
		this.link = link;
	}

	public MaterialLink getParentLink() {
		return parentLink;
	}

	public void setParentLink(MaterialLink parentLink) {
		this.parentLink = parentLink;
	}

	public MaterialLink getLink() {
		return link;
	}

	public void setLink(MaterialLink link) {
		this.link = link;
	}

}
