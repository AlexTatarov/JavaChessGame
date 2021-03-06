import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.*;

public class UserInterface extends JPanel implements Runnable,MouseListener,MouseMotionListener,ActionListener{
	
	private static final long serialVersionUID = 1L;
	static int sc=50, xmouse=3, ymouse=3;
	Image img;
	static int x,y,x2,y2;
	static String miscare;
	static int tempsc=50;
	static Piesa TablaUI[][] = new Piesa[8][8];
	static boolean miscareFacuta;
	String St = new String("ABCDEFGH12345678");
	static boolean functieTerminata = false;
	public static int gameMode = 0; //(1-pvp+internet, 2-pvp+samepc, 3-pvc+Alb, 4-pvc+Negru)
	public boolean totulAles = false;
	static JPanel 	chatpanel= new JPanel();
	static JPanel	istoricpanel = new JPanel();
	static JPanel panel = new JPanel();
	static JFrame jf = new JFrame();
	JLabel label1 = new JLabel("Un joc de Radut Daniel si Alexandru Tatarov",SwingConstants.CENTER);
	JButton pvpbut1 = new JButton("Jucator vs Jucator");
	JButton pvpbut2 = new JButton("Jucator vs Calculator");
	JButton singlepc1 = new JButton("Joc prin internet");
	JButton singlepc2 = new JButton("Joc pe acelasi calculator");
	JButton colorbut1 = new JButton("Alb");
	JButton colorbut2 = new JButton("Negru");
	JButton backbut = new JButton("Inapoi");
	JPanel panel1 = new JPanel();
	//chat
	JTextField chat_text = new JTextField();
	JButton chat_btntrimite = new JButton("Trimite");
	JScrollPane chat_scrollPane = new JScrollPane();
	JTextArea chat_textArea = new JTextArea();
	//istoricpanel
	JScrollPane istoric_scrollPane = new JScrollPane();
	JTextArea istoric_textArea = new JTextArea();
	Boolean mousePressed = false;
	Server SV;
	
	public void display(){

		label1.setPreferredSize(new Dimension(jf.getWidth(), 20));
			panel1.setPreferredSize(new Dimension(401,401));
			pvpbut1.setPreferredSize(new Dimension(300,190));
			pvpbut2.setPreferredSize(new Dimension(300,190));
			singlepc1.setPreferredSize(new Dimension(300,125));
			singlepc2.setPreferredSize(new Dimension(300,125));
			colorbut1.setPreferredSize(new Dimension(300,125));
			colorbut2.setPreferredSize(new Dimension(300,125));
			backbut.setPreferredSize(new Dimension(200,100));
		jf.setTitle("TD Chess Game v0.3");
		jf.setIconImage(new ImageIcon("rege.png").getImage());
		jf.setSize(425, 465);
		jf.setMinimumSize(new Dimension(425,465));
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        panel1.add(pvpbut1);
        panel1.add(pvpbut2);
        jf.add(panel1);
        
        
        pvpbut1.setFont(new Font("Arial", Font.PLAIN, 25));
        pvpbut2.setFont(new Font("Arial", Font.PLAIN, 25));
        colorbut1.setFont(new Font("Arial", Font.PLAIN, 30));
        colorbut2.setFont(new Font("Arial", Font.PLAIN, 30));
        singlepc1.setFont(new Font("Arial", Font.PLAIN, 20));
        singlepc2.setFont(new Font("Arial", Font.PLAIN, 20));
        backbut.setFont(new Font("Arial", Font.PLAIN, 25));
        pvpbut1.addActionListener(this);
        pvpbut2.addActionListener(this);       
        colorbut1.addActionListener(this);      	
        colorbut2.addActionListener(this); 
        singlepc1.addActionListener(this);
        singlepc2.addActionListener(this);      
        backbut.addActionListener(this);
	}
	int promovarePion(){
		JOptionPane jp = new JOptionPane();
		String mess = new String("Pionul se va transforma in:");
		CheckboxGroup grp = new CheckboxGroup();
		Checkbox c1 = new Checkbox("Regina", grp, true);
		Checkbox c2 = new Checkbox("Turn", grp, false);
		Checkbox c3 = new Checkbox("Nebun",grp, false);
		Checkbox c4 = new Checkbox("Cal",grp, false);
		Object[] ob =  {mess,c1,c2,c3,c4};
		int op = JOptionPane.showConfirmDialog(null, ob, "Evolutie",JOptionPane.OK_CANCEL_OPTION);
		if(op == JOptionPane.OK_OPTION){
			if(grp.getSelectedCheckbox() == c1)
				 return 8;
			else if(grp.getSelectedCheckbox() == c2)
				 return 5;
			else if(grp.getSelectedCheckbox() == c3)
				 return 2;
			else if(grp.getSelectedCheckbox() == c2)
				 return 3;
		}
		return 0;
	}
	
	void anuntCastigator(int castig){
		String mess = null;
		if(castig == 0)
			mess = new String("AI PIERDUT!");
		else if (castig == 1)
			mess = new String("REMIZA!");
		else if (castig == 2)
			mess = new String("AI CASTIGAT!");
		else if (castig == 3)
			mess = new String("AU CASTIGAT ALBELE!");
		else if (castig == 4)
			mess = new String("AU CASTIGAT NEGRELE!");
		JOptionPane.showMessageDialog(null, mess);
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		//Buton jucator vs jucator
		if(ev.getSource() == pvpbut1){
			panel1.removeAll();
            panel1.add(singlepc1);
            panel1.add(singlepc2);
            panel1.add(backbut);
            panel1.revalidate();
            panel1.repaint();
		}//Buton jucator vs calculator
		else if(ev.getSource() == pvpbut2){
			panel1.removeAll();
            panel1.add(colorbut1);
            panel1.add(colorbut2);
            panel1.add(backbut);
            panel1.revalidate();
            panel1.repaint();
		}
		else if(ev.getSource() == singlepc1){
                    SV = new Server();
			gameMode = 1;
			totulAles = true;
				gameinterface(gameMode);				
		}
		else if(ev.getSource() == singlepc2){
			gameMode = 2;
			totulAles = true;
			gameinterface(gameMode);
		}
		else if(ev.getSource() == colorbut1){
			gameMode = 3;
			totulAles = true;
			AI.culoareJucator = 0;
			AI.culoareCalculator = 1;
			optionpane(gameMode);
		}
		else if(ev.getSource() == colorbut2){
			gameMode = 4;
			AI.culoareJucator = 1;
			AI.culoareCalculator = 0;
			totulAles = false;
			optionpane(gameMode);
		}
		else if(ev.getSource() == backbut){
        		panel1.removeAll();
        		panel1.add(pvpbut1);
        		panel1.add(pvpbut2);
        		panel1.revalidate();
        		panel1.repaint();
		}
	}
	
	void optionpane(int gamemod){
		JOptionPane jp = new JOptionPane();
		String mess = new String("Ce nivel de dificultate alegi?");
		CheckboxGroup lvl = new CheckboxGroup();
		Checkbox lvl4 = new Checkbox("Incepator", lvl, true);
		Checkbox lvl6 = new Checkbox("Mediu",lvl, false);
		Checkbox lvl8 = new Checkbox("Avansat",lvl, false);
		Object[] ob =  {mess,lvl4,lvl6,lvl8};
		int op = JOptionPane.showConfirmDialog(null, ob, "Nivel",JOptionPane.OK_CANCEL_OPTION);
		if(op == JOptionPane.OK_OPTION){
			if(lvl.getSelectedCheckbox() == lvl4)
				AI.nivelDificultate = 1;
			else if(lvl.getSelectedCheckbox() == lvl6)
				AI.nivelDificultate = 2;
			else if(lvl.getSelectedCheckbox() == lvl8)
				AI.nivelDificultate = 4;
			totulAles = true;
			gameinterface(gamemod);
			
		}
	}
	
	void gameinterface(int gamemod){
		jf.getContentPane().removeAll();
		jf.setLayout(new BorderLayout());	
		UserInterface t = new UserInterface();
		t.setPreferredSize(new Dimension(450,500));
		t.setMinimumSize(new Dimension(400,400));	
		panel.setBounds(100, 100, 460, 435);
		//panel.setPreferredSize(new Dimension(420,415));	
		panel.setLayout(new BorderLayout());
		panel.add(t, BorderLayout.CENTER);	
		panel.setBackground(Color.LIGHT_GRAY);
		jf.setSize(456, 500);
		jf.setMinimumSize(new Dimension(456, 500));
		jf.add(panel, BorderLayout.CENTER);
        jf.add(label1, BorderLayout.SOUTH);
        if(gamemod == 1){
        	jf.setMinimumSize(new Dimension(924, 495));
        	chatpanel.setBackground(Color.ORANGE);
        	istoricpanel.setBackground(Color.BLUE);	
        	chatpanel.setPreferredSize(new Dimension((int)((jf.getWidth()-panel.getWidth())*0.6), jf.getHeight()-20));		
        	istoricpanel.setPreferredSize(new Dimension((int)((jf.getWidth()-panel.getWidth())*0.4), jf.getHeight()-20));	
        	jf.add(chatpanel, BorderLayout.EAST);
        	jf.add(istoricpanel, BorderLayout.WEST);
        	chat();
        	istoric();
        }
		jf.getContentPane().revalidate();
	}
	
	public void paintComponent(Graphics g)
	{
		sc = Math.min((int)panel.getSize().getWidth()-40, (int)panel.getSize().getHeight()-35)/8;

		super.paintComponent(g);
		this.addMouseListener(this);
        this.addMouseMotionListener(this);
		// FORMAM TABLA ALB-NEGRU
        g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, sc*8+40, sc*8+40);
		g.setColor(Color.cyan); 
		
		g.setFont(new Font("TimesNewRoman", Font.BOLD, 13));
	    g.drawString("A", 15+sc/2, 15);
	    g.drawString("B", 15+sc/2+sc, 15);
	    g.drawString("C", 15+sc/2+sc*2, 15);
	    g.drawString("D", 15+sc/2+sc*3, 15);
	    g.drawString("E", 15+sc/2+sc*4, 15);
	    g.drawString("F", 15+sc/2+sc*5, 15);
	    g.drawString("G", 15+sc/2+sc*6, 15);
	    g.drawString("H", 15+sc/2+sc*7, 15);
	    g.drawString("1", 6, 25+sc/2);
	    g.drawString("2", 6, 25+sc/2+sc);
	    g.drawString("3", 6, 25+sc/2+sc*2);
	    g.drawString("4", 6, 25+sc/2+sc*3);
	    g.drawString("5", 6, 25+sc/2+sc*4);
	    g.drawString("6", 6, 25+sc/2+sc*5);
	    g.drawString("7", 6, 25+sc/2+sc*6);
	    g.drawString("8", 6, 25+sc/2+sc*7);
		g.setColor(Color.gray);
		g.fillRect(20, 20, sc*8, sc*8);
		
		for(int i=0; i<sc*8; i+=sc*2)
			for(int j=0; j<sc*8; j+=sc*2)
				g.clearRect(i+20, j+20, sc, sc);
			
		for(int i=sc; i<sc*8; i+=sc*2)
			for(int j=sc; j<sc*8; j+=sc*2)
				g.clearRect(i+20, j+20, sc, sc);

			
		//	PUNEM PIESE LA LOC IN FUNCTIE DE MATRICEA TABLA
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				if(TablaUI[i][j] != null){
					if(sc!=tempsc){
						TablaUI[i][j].imagePosition.setLocation(i*sc+20, j*sc+20);
					}
					g.drawImage(img = TablaUI[i][j].img.getImage() ,TablaUI[i][j].imagePosition.y  ,TablaUI[i][j].imagePosition.x , sc, sc, null);
					
				}
		tempsc=sc;
		System.out.println(sc+","+ tempsc);
	}
	
	void chat(){		
		chat_textArea.setLineWrap(true);
		chat_scrollPane.setViewportView(chat_textArea);
		chatpanel.setLayout(new BorderLayout(0, 0));
		chatpanel.add(chat_scrollPane, BorderLayout.CENTER);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		chatpanel.add(panel, BorderLayout.SOUTH);
		panel.add(chat_text, BorderLayout.CENTER);
		panel.add(chat_btntrimite, BorderLayout.EAST);
		chat_btntrimite.setPreferredSize(new Dimension(120, 30));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		chat_scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}
	
	void istoric(){
		istoricpanel.setLayout(new BorderLayout(0, 0));
		istoricpanel.add(istoric_scrollPane, BorderLayout.CENTER);
		istoric_scrollPane.setViewportView(istoric_textArea);
		istoric_textArea.setLineWrap(true);
		istoric_scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}
	
	
	
	UserInterface()
	{
		int space;
		//INITIALIZAM TOTUL CU NULL
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				TablaUI[i][j]=null;
			}		
		}
		//PLASAM PIONII
		for(int i = 0; i<8; i++)
		{
			TablaUI[6][i] = new Pion(0,6*sc+20,i*sc+20);
		}
		for(int i = 0; i<8; i++)
		{
			TablaUI[1][i] = new Pion(1,1*sc+20,i*sc+20);
		}
		//PLASAM TURNURILE
		TablaUI[0][0] = new Turn(1,20,20);
		TablaUI[0][7] = new Turn(1,20,7*sc+20);
		TablaUI[7][0] = new Turn(0,7*sc+20,20);
		TablaUI[7][7] = new Turn(0,7*sc+20,7*sc+20);
		//PLASAM CAII
		TablaUI[0][6] = new Cal(1,20,6*sc+20);
		TablaUI[0][1] = new Cal(1,20,1*sc+20);
		TablaUI[7][1] = new Cal(0,7*sc+20,1*sc+20);
		TablaUI[7][6] = new Cal(0,7*sc+20,6*sc+20);
		//PLASAM NEBUNII
		TablaUI[0][5] = new Nebun(1,20,5*sc+20);
		TablaUI[0][2] = new Nebun(1,20,2*sc+20);
		TablaUI[7][5] = new Nebun(0,7*sc+20,5*sc+20);
		TablaUI[7][2] = new Nebun(0,7*sc+20,2*sc+20);
		//PLASAM REGINELE
		TablaUI[0][3] = new Regina(1,20,3*sc+20);
		TablaUI[7][3] = new Regina(0,7*sc+20,3*sc+20);
		//PLASAM REGII
		TablaUI[0][4] = new Rege(1,20,4*sc+20);
		TablaUI[7][4] = new Rege(0,7*sc+20,4*sc+20);
		//FACEM SA FIE TOTUL ORDONAT IN PATRAT
		
		
	}

	boolean peTabla(int a, int b, int c, int d)
	{
		System.out.println(a+" "+b+" "+c+" "+d);
		if(0>a||7<a)
			return false;
		
		else if(0>b||7<b)
			return false;
		
		else if(0>c||jf.getHeight()<c)
			return false;
		
		else if(0>d||jf.getWidth()<d)
			return false;
		
		else return true;
	}
	
	//FUNCTIE PENTRU THREAD
	@Override
	public void run() {
		while(gameMode == 0)
			System.out.print(gameMode);
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(mousePressed){
			TablaUI[x][y].imagePosition.setLocation(e.getY()-24,e.getX()-24);
			repaint();
		}
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed = true;
		miscare = "";
		if(TablaUI[(e.getY()-20)/sc][(e.getX()-20)/sc] != null)
		{
			xmouse = (e.getX()-20)/sc;
			ymouse = (e.getY()-20)/sc;
			x = ymouse;
			y = xmouse;
			miscare+=x+""+y;
			TablaUI[(e.getY()-20)/sc][(e.getX()-20)/sc].imagePosition.setLocation(e.getY()-24,e.getX()-24);
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
		int x1,y1;
		functieTerminata = false;
		System.out.println("release:"+(e.getY()-20)/sc+" "+(e.getX()-20)/sc);
		System.out.println(jf.getHeight()+" "+jf.getWidth());
		if(TablaUI[ymouse][xmouse] != null && peTabla(ymouse,xmouse,e.getY()-20,e.getX()-20))
		{
			System.out.println("S-a intrat in if");
			x2 = (e.getY()-20)/sc;
			y2 = (e.getX()-20)/sc;
			miscare+=x2+""+y2;
			x1 = miscare.charAt(0)-48;
			y1 = miscare.charAt(1)-48;
			if(TablaUI[x2][y2] == null)
			{
				miscare += " ";
			}
			else
			{
				miscare += TablaUI[x2][y2].cod;
			}
			
			if(TablaUI[x1][y1].cod == 1)
			{
				if(TablaUI[x1][y1].culoare == 0 && x2 == 0)
				{
					miscare += "t";
				}
				else if(TablaUI[x1][y1].culoare == 1 && x2 == 7)
				{
					miscare+= "t";
				}
				else
				{
					miscare += " ";
				}
			}
			else
			{
				miscare += " ";
			}
			System.out.println(x1 + " " + y1);
			System.out.println("S-a generat miscarea "+miscare);
			
			if(miscare.charAt(0) == miscare.charAt(2) && miscare.charAt(1) == miscare.charAt(3))
			{
				System.out.println("Miscare imposibila");
				TablaUI[ymouse][xmouse].imagePosition.setLocation(ymouse*sc+20, xmouse*sc+20);
			}
			if(AI.randMiscare == 0)
			{
				for(int i=0; i<AI.miscariPosibileAlbe.size(); i++)
				{
					if(miscare.equals(AI.miscariPosibileAlbe.get(i)))
					{
						if(miscare.charAt(5) == 't')
						{
							TablaUI[x2][y2] = null;
							TablaUI[x1][y1] = null;
							int rez = promovarePion();
							if(rez == 8)
							{
								TablaUI[x2][y2] = new Regina(0,x2*sc+20,y2*sc+20);
								AI.istoriaMiscarilor.add(miscare);
								miscareFacuta = true;
								break;
							}
							else if(rez == 5)
							{
								TablaUI[x2][y2] = new Turn(0,x2*sc+20,y2*sc+20);
								AI.istoriaMiscarilor.add(miscare);
								miscareFacuta = true;
								break;
							}
							else if(rez == 2)
							{
								TablaUI[x2][y2] = new Nebun(0,x2*sc+20,y2*sc+20);
								AI.istoriaMiscarilor.add(miscare);
								miscareFacuta = true;
								break;
							}
							else if(rez == 3)
							{
								TablaUI[x2][y2] = new Cal(0,x2*sc+20,y2*sc+20);
								AI.istoriaMiscarilor.add(miscare);
								miscareFacuta = true;
								break;
							}
							TablaUI[x1][y1] = null;
						}
						miscareFacuta = true;
						AI.doMiscare(miscare,TablaUI);
						TablaUI[x2][y2].imagePosition.setLocation(x2*sc+20, y2*sc+20);
						AI.istoriaMiscarilor.add(miscare);
						break;
					}
				}
				if(miscareFacuta == false)
				{
					System.out.println("Miscare imposibila");
					TablaUI[ymouse][xmouse].imagePosition.setLocation(ymouse*sc+20, xmouse*sc+20);
				}
			}
			else
			{
				for(int i=0; i<AI.miscariPosibileNegre.size(); i++)
				{
					if(miscare.equals(AI.miscariPosibileNegre.get(i)))
					{
						if(miscare.charAt(5) == 't')
						{
							TablaUI[x2][y2] = null;
							TablaUI[x1][y1] = null;
							int rez = promovarePion();
							if(rez == 8)
							{
								TablaUI[x2][y2] = new Regina(1,x2*sc+20,y2*sc+20);
								AI.istoriaMiscarilor.add(miscare);
								miscareFacuta = true;
								break;
							}
							else if(rez == 5)
							{
								TablaUI[x2][y2] = new Turn(1,x2*sc+20,y2*sc+20);
								AI.istoriaMiscarilor.add(miscare);
								miscareFacuta = true;
								break;
							}
							else if(rez == 2)
							{
								TablaUI[x2][y2] = new Nebun(1,x2*sc+20,y2*sc+20);
								AI.istoriaMiscarilor.add(miscare);
								miscareFacuta = true;
								break;
							}
							else if(rez == 3)
							{
								TablaUI[x2][y2] = new Cal(1,x2*sc+20,y2*sc+20);
								AI.istoriaMiscarilor.add(miscare);
								miscareFacuta = true;
								break;
							}
							TablaUI[x1][y1] = null;
						}
						miscareFacuta = true;
						AI.doMiscare(miscare,TablaUI);
						TablaUI[x2][y2].imagePosition.setLocation(x2*sc+20, y2*sc+20);
						AI.istoriaMiscarilor.add(miscare);
						break;
					}
				}
				if(miscareFacuta == false)
				{
					System.out.println("Miscare imposibila");
					TablaUI[ymouse][xmouse].imagePosition.setLocation(ymouse*sc+20, xmouse*sc+20);
				}
			}
		}
		functieTerminata = true;
		repaint();	
	}


	
	
	

	

	

	

	
	
	
	
	
	
	
	
	
	
	
	
}
