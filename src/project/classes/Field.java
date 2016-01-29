package project.classes;

import project.interfaces.IField;
import project.interfaces.IVisitor;

public class Field implements IField {

	private String fieldName;
	private String fieldType;
	private String access;
	private String sign;
	private int moreAccess;
	
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
		this.fieldType = fieldType.substring(fieldType.lastIndexOf(".")+1);
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
	@Override
	public void setSignature(String sign) {
		this.sign = sign;
		
	}
	@Override
	public String getSignature() {
		return this.sign;
	}
	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		v.postVisit(this);
	}
	@Override
	public void setAdditionalAccess(int access) {
		this.moreAccess = access;
	}
	@Override
	public int getAdditionalAccess() {
		return this.moreAccess;
	}
	
	

}
