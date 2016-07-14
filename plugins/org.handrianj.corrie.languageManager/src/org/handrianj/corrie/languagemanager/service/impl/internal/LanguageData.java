package org.handrianj.corrie.languagemanager.service.impl.internal;

import org.handrianj.corrie.languagemanager.service.ILang;

public class LanguageData implements ILang {

	private int id;

	private String name;

	public LanguageData(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LanguageData other = (LanguageData) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

}
