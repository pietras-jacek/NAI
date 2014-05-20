package liczby;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Klasa z metoda main. wyswietla interfejs uzytkownika
 * @author Jacek Pietras
 */
public class mainGUI extends java.applet.Applet implements ActionListener {
 
	
      private JButton b = new JButton("   Wyczysc    ");
	  private JButton c = new JButton("   Sprawdz     ");
	  private JButton d = new JButton("   Rysuj wzorzec     ");
	  static String[] nu={"0","1","2","3","4","5","6","7","8","9"};
	  private static JComboBox n=new JComboBox(nu);
	  public int[] in=new int[35];
	  public Number num=new Number();
	  public JLabel lblEnter = new JLabel("Rysuj myszką po płótnie");
	  private JProgressBar[] numbers=new JProgressBar[10] ;
	  private JLabel[] lblnumbers=new JLabel[10];
	  private JPanel panelProg =  new JPanel(new GridLayout(10,1,4,8));
	  private JPanel panelProgLbl = new JPanel(new GridLayout(10,1,4,13));
	  private JPanel panelButton = new JPanel(new GridLayout(3,2,5,5));
	  private static final long serialVersionUID = 1L;
      DrawingArea draw;
      Image cursorImage;
 
 public void init(){
	 setBackground(Color.DARK_GRAY);
	 setSize(550,400);
 }
      
 public void inita(){
	 
     add(panelProgLbl);
	 add(panelProg);
	 add(panelButton);
	 panelProg.setBackground(Color.WHITE);
	 for(int i=0;i<10;i++){
		 lblnumbers[i]=new JLabel(i+"");
	   	 numbers[i]=new JProgressBar(0,100);
	     numbers[i].setStringPainted(true);
	     panelProgLbl.add(lblnumbers[i]);
	     panelProg.add(numbers[i]);
	    }
	   panelButton.add(d);
	   panelButton.add(n);
	   panelButton.add(c);
	   panelButton.add(b);
	   panelButton.add(lblEnter);
	    d.addActionListener(this);
	    c.addActionListener(this);
	    b.addActionListener(this);
	 setSize(550,380);
 }
   
  public static void main(String[] args) {
    JFrame.setDefaultLookAndFeelDecorated(true);
	  
    JFrame f = new JFrame("Rozpoznawanie liczb za pomocą sieci Neuronowych ");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add("Center",new mainGUI());
    f.setSize(550,420);
    f.setVisible(true);
  }

  public mainGUI() {
	
	
    draw = new DrawingArea();
    draw.setBackground(Color.WHITE);
    
    add(draw);
    inita();
  }

  

public void actionPerformed(ActionEvent e) {
	if(e.getSource()==b){
		draw.repaint();
     for(int i=0;i<35;i++)
    	   in[i]=-1;
     for(int i=0;i<10;i++){
        numbers[i].setValue(0);
     }
	}
	if(e.getSource()==c){
		 
		double[] A=new double[11];
        double iloczyn=0;
	 
	for(int j=0;j<Number.n;j++){ 
	      for(int i=0;i<35;i++){
		 
		iloczyn=iloczyn+in[i]*num.weight[j][i]; 
		 
	 }

	if(iloczyn>0) A[j%10]=A[j%10]+iloczyn;
	iloczyn=0;
	}	 

    double max=A[0];	
		for(int i=1;i<11;i++)	
			if(max<A[i]) {
		      max=A[i];
	       }

        for(int i=0;i<10;i++){
           numbers[i].setValue((int)(A[i]/max*100));
        }
	   	
	}
    if(e.getSource()==d){
    	int i=n.getSelectedIndex();
    	int x=0,y=0;
      for(int j=0;j<35;j++){
    	  if(Number.numbers[i][j]==1){
    		 in[j]=1;
    		 
    	      y=j/5;
    	      x=j%5;
    	      draw.getGraphics().fillOval(40*x+10,40*y+10,20,20);	  
    	  }
      }
    }
}



class DrawingArea extends Canvas {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
int x=0;
  int y=0;
  
//private int[] in=new int[35];

  public Dimension preferredSize(){
    return new Dimension(200,280);
    }

  public void paint(Graphics g){
    g.drawRect(0,0,this.getSize().width-1,this.getSize().height-1);
    for (int i=0;i<=5*40;i+=40)
        g.drawLine(i,0,i,7*40);  
   for (int i=0;i<=7*40;i+=40)
        g.drawLine (0,i,5*40,i);
    }
  
  public boolean mouseDown(Event e,int x, int y){
    this.x = x;
    this.y = y;
    return true;
  }
  public boolean mouseMove(Event e,int x,int y) {
	  
	  lblEnter.setText( x+ ", "+y);
	  return true;
  }
  public boolean mouseExit(Event e,int x,int y) {
	    lblEnter.setText("Rysuj myszką po płótnie");
	    return false;
	  }

  public boolean mouseDrag(Event e,int x, int y){
	    //getGraphics().drawLine(this.x,this.y,x,y);
	    this.x = x;
	    this.y = y;
	    int i = 0,j=0;
	    lblEnter.setText(" Rysuj "+ x+ ", "+y);
	    
	      if(x>=0 && x<=200 && y>=0 && y<=280) { 
	      i=y/40;
	      j=x/40;
	      
	      }
	      //System.out.println(5*i+j);
	      if(5*i+j<35)
	        in[5*i+j]=1;  
	    if(x>0 && y>0 && x<5*40-10 && y<7*40-10)    	 
	        getGraphics().fillOval(x,y,10,10);
	    
	 
	      
		return true;
    }
  }
  
}  