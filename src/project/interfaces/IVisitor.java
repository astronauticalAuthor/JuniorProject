package project.interfaces;

@SuppressWarnings("rawtypes")
public interface IVisitor {
	public void preVisit(ITraverser t);
	public void visit(ITraverser t);
	public void postVisit(ITraverser t);
	public void addVisit(VisitType visitType, Class clazz, ICommand c);
	public void removeVisit(VisitType visitType, Class clazz);
}
