package application;

public class Banner {
	int version;
	int length;
	int pid;
	int realWidth;
	int realHeight;
	int virtualWidth;
	int virtualHeight;
	int orientation;
	int quirks;
	
	public Banner(){
		version = 0; 
		length = 0;
		pid = 0;
		realWidth = 0;
		realHeight = 0;
		virtualWidth = 0;
		virtualHeight = 0;
		orientation = 0;
		quirks = 0;
		
	}
	public int getVersion(){
		return version;
	}
	
	public void setVersion(int version){
		this.version = version;
	}
	
	public int getLength(){
		return length;
	}
	
	public void setLength(int length){
		this.length = length;
	}
	
	public int getPid(){
		return length;
	}
	
	public void setPid(int pid){
		this.pid = pid;
	}
	
	public int getRealWidth(){
		return realWidth;
	}
	
	public void setRealWidth(int realWidth){
		this.realWidth = realWidth;
	}
	
	public int getRealHeight(){
		return realHeight;
	}
	
	public void setRealHeight(int realHeight){
		this.realHeight = realHeight;
	}
	
	public int getVirtualWidth(){
		return virtualWidth;
	}
	
	public void setVirtualWidth(int virtualWidth){
		this.virtualWidth = virtualWidth;
	}
	
	public int getVirtualHeight(){
		return virtualHeight;
	}
	
	public void setVirtualHeight(int virtualHeight){
		this.virtualHeight = virtualHeight;
	}
	public int getOrientation(){
		return orientation;
	}
	
	public void setOrientation(int orientation){
		this.orientation = orientation;
	}
	
	public int getQuirks(){
		return quirks;
	}
	
	public void setQuirks(int quirks){
		this.quirks = quirks;
	}
	
	public String toString(){
		return "Banner [ version = " + version + ", length = " + length + ", pid = " + pid + ", realWidth = " + realWidth + ", realHeight = " + realHeight + ", virtualWidth = " + virtualWidth + ", virtualHeight = " + virtualHeight + ", orientation = " + orientation + ", quirks = " + quirks + "]";
	}
	
//	int version;
//	int length;
//	int pid;
//	int realWidth;
//	int realHeight;
//	int virtualWidth;
//	int virtualHeight;
//	int orientation;
//	int quirks;
	
	
}
