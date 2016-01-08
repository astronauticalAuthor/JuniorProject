package project;

public class FieldField {
	String fieldName;
	String fieldType;
	String access;
	
	public void setName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public void setType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	public void setAccess(String access){
		this.access = access;
	}
	
	public String getName() {
		return fieldName;
	}
	
	public String getType() {
		return fieldType;
	}
	
	public String getAccess(){
		return this.access;
	}
}
