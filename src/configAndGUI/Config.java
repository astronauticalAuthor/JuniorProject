package configAndGUI;

import java.util.List;

public class Config {
	public String INPUTDIR;
	public List<String> CLASSES;
	public String OUTDIR;
	public String DOTPATH;
	public List<String> PHASES;
	public int adapterMethodDelegation;
	
	public String getInputDir() {
		return INPUTDIR;
	}
	public void setInputDir(String iNPUTDIR) {
		INPUTDIR = iNPUTDIR;
	}
	public List<String> getClasses() {
		return CLASSES;
	}
	public void setClasses(List<String> cLASSES) {
		CLASSES = cLASSES;
	}
	public String getOutDir() {
		return OUTDIR;
	}
	public void setOutDir(String oUTDIR) {
		OUTDIR = oUTDIR;
	}
	public String getDotPath() {
		return DOTPATH;
	}
	public void setDotPath(String dOTPATH) {
		DOTPATH = dOTPATH;
	}
	public List<String> getPhases() {
		return PHASES;
	}
	public void setPhases(List<String> pHASES) {
		PHASES = pHASES;
	}
	public int getAdapterMethodDelegation() {
		return adapterMethodDelegation;
	}
	public void setAdapterMethodDelegation(int adapterMethodDelegation) {
		this.adapterMethodDelegation = adapterMethodDelegation;
	}
	
	
	
}
