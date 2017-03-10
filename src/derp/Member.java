// 
// Decompiled by Procyon v0.5.30
// 

package derp;

import org.newdawn.slick.geom.Line;

public class Member extends Line
{
    public Member(final float x, final float y, final float xx, final float yy) {
        super(x, y, xx, yy);
    }
    
    public double getAngle() {
    	double distX = this.getEnd().getX() - this.getStart().getX();
    	System.out.println(distX);
    	double distY = this.getEnd().getY() - this.getStart().getY();
    	System.out.println(distY);
    	double angle;
    	
    	if(distY == 0) {
    		angle = 0.0;
    		
    	} else {
    		angle = Math.tan(distX / distY);
    		
    	}
    	
    	return angle;
    	
    }
    
}
