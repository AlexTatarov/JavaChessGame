import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Point;
import java.io.IOException;

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
			img = new ImageIcon(getClass().getClassLoader().getResource("bpion.png"));
		}
		else
		{
			img = new ImageIcon(getClass().getClassLoader().getResource("apion.png"));
		}
		imagePosition.setLocation(x, y);
	}
	static void ana(){
		System.out.println("ana");
	}
}
