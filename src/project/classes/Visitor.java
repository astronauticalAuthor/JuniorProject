package project.classes;

import java.util.HashMap;
import java.util.Map;

import project.interfaces.*;

@SuppressWarnings("rawtypes")
public class Visitor implements IVisitor {

	Map<LookupKey,ICommand> keyToMethodMap = new HashMap<LookupKey,ICommand>();
	
	class LookupKey{
		VisitType type;
		Class clazz;
		public LookupKey(VisitType type, Class whatWereVisiting){
			this.type = type;
			this.clazz = whatWereVisiting;
		}
		
		public int hashCode(){
			return type.hashCode() + clazz.hashCode();
		}
		
		public boolean equals(Object o){
			if(!(o instanceof LookupKey)){
				return false;
			}
			
			LookupKey key = (LookupKey) o;
			
			return this.type == key.type && this.clazz.equals(key.clazz);
		}
	}

	@Override
	public void preVisit(ITraverser t) {
		LookupKey key = new LookupKey(VisitType.PREVISIT, t.getClass());
		ICommand c = this.keyToMethodMap.get(key);
		if(c != null) {
			c.execute(t);
		}
	}

	@Override
	public void visit(ITraverser t) {
		LookupKey key = new LookupKey(VisitType.VISIT, t.getClass());
		ICommand c = this.keyToMethodMap.get(key);
		if(c != null) {
			c.execute(t);
		}
	}

	@Override
	public void postVisit(ITraverser t) {
		LookupKey key = new LookupKey(VisitType.POSTVISIT, t.getClass());
		ICommand c = this.keyToMethodMap.get(key);
		if(c != null) {
			c.execute(t);
		}
	}

	@Override
	public void addVisit(VisitType visitType, Class clazz, ICommand c) {
		LookupKey key = new LookupKey(visitType,clazz);
		this.keyToMethodMap.put(key, c);
	}

	@Override
	public void removeVisit(VisitType visitType, Class clazz) {
		LookupKey key = new LookupKey(visitType,clazz);
		this.keyToMethodMap.remove(key);
	}
}
