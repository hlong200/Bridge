// 
// Decompiled by Procyon v0.5.30
// 

package derp;

import java.util.ArrayList;

import org.newdawn.slick.geom.Circle;

public class Node extends Circle
{
    private int type;
    private ArrayList<Member> connectedMembers = new ArrayList<Member>();
    
    public Node (float x, float y, float r) {
        super(x, y, r);
        this.type = 0;
    }
    
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    public boolean isConnected(Member m) {
    	return connectedMembers.contains(m);
    	
    }
    
    public ArrayList<Member> getConnectedMembers() {
    	return connectedMembers;
    	
    }
    
    public void connectMember(Member m) {
    	connectedMembers.add(m);
    	
    }
}
