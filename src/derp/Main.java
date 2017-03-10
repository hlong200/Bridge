// 
// Decompiled by Procyon v0.5.30
// 

package derp;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Game;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import java.util.Iterator;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.GameContainer;
import java.util.ArrayList;
import org.newdawn.slick.BasicGame;

public class Main extends BasicGame
{
    static final int WIDTH = 1360;
    static final int HEIGHT = 768;
    Tool currentTool;
    boolean toolActive;
    int screenState;
    ArrayList<Member> members;
    ArrayList<Node> nodes;
    ArrayList<GridLine> gridLines;
    Node rolling;
    Node fixed;
    GridLine xGrid;
    GridLine yGrid;
    Circle mouseCircle;
    float x;
    float y;
    float xx;
    float yy;
    
    public Main(final String title) {
        super(title);
        this.currentTool = Tool.NONE;
        this.toolActive = false;
        this.screenState = 0;
        this.members = new ArrayList<Member>();
        this.nodes = new ArrayList<Node>();
        this.gridLines = new ArrayList<GridLine>();
        this.xGrid = null;
        this.yGrid = null;
        mouseCircle = new Circle(0, 0, 5.0f);
    }
    
    @Override
    public void init(final GameContainer gc) {
        for (int i = 0; i < 1360; ++i) {
            if (i % 25 == 0) {
                this.gridLines.add(new GridLine(i, 0.0f, i, 768.0f, LineType.GRID_X));
            }
        }
        for (int i = 0; i < 768; ++i) {
            if (i % 25 == 0) {
                this.gridLines.add(new GridLine(0.0f, i, 1360.0f, i, LineType.GRID_Y));
            }
        }
    }
    
    public double getDistance(final float x1, final float y1, final float x2, final float y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0));
    }
    
    public double getDistance(final Shape s1, final Shape s2) {
        return Math.sqrt(Math.pow(s2.getCenterX() - s1.getCenterX(), 2.0) + Math.pow(s2.getCenterY() - s1.getCenterY(), 2.0));
    }
    
    public void updateTools(final GameContainer gc, final int delta) {
        final Input in = gc.getInput();
        this.xGrid = null;
        this.yGrid = null;
        if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            System.out.println("Click");
            switch (this.currentTool) {
                case MEMBER: {
                    if (this.toolActive) {
                        this.toolActive = false;
                        Member m = new Member(0, 0, 0, 0);
                        for (Node n : this.nodes) {
                            if (this.getDistance(n.getCenterX(), n.getCenterY(), in.getMouseX(), in.getMouseY()) <= 5.0) {
                                this.xx = n.getCenterX();
                                this.yy = n.getCenterY();
                                n.connectMember(m);
                            }
                        }
                        m.set(x, y, xx, yy);
                        System.out.println(m.getAngle());
                        System.out.println(Math.toDegrees(m.getAngle()));
                        this.members.add(m);
                        break;
                    }
                    for (Node n : this.nodes) {
                        if (this.getDistance(n.getCenterX(), n.getCenterY(), in.getMouseX(), in.getMouseY()) <= 5.0) {
                            this.toolActive = true;
                            this.x = n.getCenterX();
                            this.y = n.getCenterY();
                        }
                    }
                    break;
                }
                case FIXED_NODE: {
                    for (Node n : this.nodes) {
                        if (this.getDistance(n.getCenterX(), n.getCenterY(), in.getMouseX(), in.getMouseY()) <= 5.0) {
                            for (final Node nn : this.nodes) {
                                if (nn.getType() == 1) {
                                    nn.setType(0);
                                }
                            }
                            n.setType(1);
                            this.fixed = n;
                        }
                    }
                    break;
                }
                case NODE: {
                    for (GridLine gl : this.gridLines) {
                        if (gl.type == LineType.GRID_X) {
                            if (this.getDistance(gl.getCenterX(), in.getMouseY(), in.getMouseX(), in.getMouseY()) > 5.0) {
                                continue;
                            }
                            this.xGrid = gl;
                        } else {
                            if (gl.type != LineType.GRID_Y || this.getDistance(in.getMouseX(), gl.getCenterY(), in.getMouseX(), in.getMouseY()) > 5.0) {
                                continue;
                            }
                            this.yGrid = gl;
                        }
                    }
                    if (this.xGrid != null) {
                        this.x = this.xGrid.getCenterX();
                    } else {
                        this.x = in.getMouseX();
                    }
                    if (this.yGrid != null) {
                        this.y = this.yGrid.getCenterY();
                    } else {
                        this.y = in.getMouseY();
                    }
                    this.nodes.add(new Node(this.x, this.y, 3.0f));
                }
                case ROLLING_NODE: {
                    for (final Node n : this.nodes) {
                        if (this.getDistance(n.getCenterX(), n.getCenterY(), in.getMouseX(), in.getMouseY()) <= 5.0) {
                            for (final Node nn : this.nodes) {
                                if (nn.getType() == 2) {
                                    nn.setType(0);
                                }
                            }
                            n.setType(2);
                            this.rolling = n;
                        }
                    }
                    break;
                }
            }
            System.out.println(this.toolActive);
        }
        if (in.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            Node todoNode = null;
            Member todoMember = null;
            System.out.println("Mouse x: " + mouseCircle.getCenterX() + " -- y: " + mouseCircle.getCenterY());
            switch (this.currentTool) {
                case NODE:
                    for (final Node n2 : this.nodes) {
                        if (this.getDistance(n2.getCenterX(), n2.getCenterY(), in.getMouseX(), in.getMouseY()) <= 7.0) {
                            todoNode = n2;
                        }
                    }
                    break;
                case MEMBER:
                	for(Member m : members) {
                		if(mouseCircle.intersects(m)) {
                			todoMember = m;
                		}
                	}
            }
            if (todoNode != null) {
                this.nodes.remove(todoNode);
            }
            if(todoMember != null) {
            	members.remove(todoMember);
            	
            }
        }
        if (in.isKeyPressed(59)) {
            this.currentTool = Tool.NODE;
        } else if (in.isKeyPressed(60)) {
            this.currentTool = Tool.MEMBER;
            this.toolActive = false;
        } else if (in.isKeyPressed(61)) {
            this.currentTool = Tool.FIXED_NODE;
        } else if (in.isKeyPressed(62)) {
            this.currentTool = Tool.ROLLING_NODE;
        }
    }
    
    @Override
    public void update(final GameContainer gc, final int delta) {
    	mouseCircle.setCenterX(gc.getInput().getMouseX());
    	mouseCircle.setCenterY(gc.getInput().getMouseY());
        this.updateTools(gc, delta);
    }
    
    @Override
    public void render(final GameContainer gc, final Graphics g) {
        g.setBackground(Color.white);
        g.setColor(Color.black);
        g.setLineWidth(1.0f);
        for (final GridLine gl : this.gridLines) {
            g.draw(gl);
        }
        g.setColor(Color.red);
        g.drawString("Current Tool: " + this.currentTool.getValue(), 0.0f, 0.0f);
        g.drawString("Tools: \nF1: Node\nF2: Member\nF3: Fixed Node\nF4: Horizontal Rolling Node", 1110.0f, 0.0f);
        g.setColor(Color.blue);
        g.draw(mouseCircle);
        if (this.toolActive) {
            g.drawLine(this.x, this.y, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        }
        g.setLineWidth(2.0f);
        for (final Member m : this.members) {
            g.draw(m);
        }
        for (final Node n : this.nodes) {
            if (n.getType() == 0) {
                g.setColor(Color.red);
            } else if (n.getType() == 1) {
                g.setColor(Color.red);
            } else {
                g.setColor(Color.magenta);
            }
            g.draw(n);
        }
    }
    
    public static void main(final String[] args) {
        try {
            final AppGameContainer appgc = new AppGameContainer(new Main("Bridge Designer"));
            appgc.setDisplayMode(1360, 768, false);
            appgc.setTargetFrameRate(60);
            appgc.setShowFPS(false);
            appgc.start();
        }
        catch (SlickException ex) {}
    }
}
