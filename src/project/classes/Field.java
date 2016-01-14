package project.classes;

import project.interfaces.IField;

public class Field implements IField {

	public String fieldName;
	public String fieldType;
	public String access;
	
	public Field(){
		this.fieldName = "";
		this.fieldType = "";
		this.access = "";
	}
	@Override
	public void setName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public void setType(String fieldType) {
		this.fieldType = fieldType;
	}

	@Override
	public void setAccess(String access) {
		this.access = access;
	}

	@Override
	public String getName() {
		return this.fieldName;
	}

	@Override
	public String getType() {
		return this.fieldType;
	}

	@Override
	public String getAccess() {
		return this.access;
	}

}
