import javax.swing.ImageIcon;
import java.awt.Point;

public class Pion extends Piesa{

	Pion(int cul, int x, int y)
	{	
		if(cul > 1)
		{
			cul = 1;
		}
		culoare = cul;
		stare = 1;
		cod = 1; 
		if(cul == 1)
		{
			img = new ImageIcon("bpion.png");
		}
		else
		{
			img = new ImageIcon("apion.png");
		}
		imagePosition.setLocation(x, y);
	}
	static void ana(){
		System.out.println("ana");
	}
}