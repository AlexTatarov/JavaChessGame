import javax.swing.ImageIcon;

public class Rege extends Piesa{
	
	Rege(int cul, int x, int y)
	{
		if(cul > 1)
		{
			cul = 1;
		}
		culoare = cul;
		cod = 9;
		stare = 1;
		if(cul == 1)
		{
			img = new ImageIcon(getClass().getClassLoader().getResource("brege.png"));
		}
		else
		{
			img = new ImageIcon(getClass().getClassLoader().getResource("arege.png"));
		}
		imagePosition.setLocation(x, y);
	}	
}