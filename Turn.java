import javax.swing.ImageIcon;

public class Turn extends Piesa{

	Turn(int cul, int x, int y)
	{
		if(cul > 1)
		{
			cul = 1;
		}
		culoare = cul;
		cod = 5; 
		stare = 1;
		if(cul == 1)
		{
			img = new ImageIcon(getClass().getClassLoader().getResource("bturn.png"));
		}
		else
		{
			img = new ImageIcon(getClass().getClassLoader().getResource("aturn.png"));
		}
		imagePosition.setLocation(x, y);
	}
}
