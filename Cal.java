import javax.swing.ImageIcon;

public class Cal extends Piesa{

	Cal(int cul, int x, int y)
	{
		if(cul > 1)
		{
			cul = 1;
		}
		culoare = cul;
		cod = 3; 
		if(cul == 1)
		{
			img = new ImageIcon("bcal.png");
		}
		else
		{
			img = new ImageIcon("acal.png");
		}
		imagePosition.setLocation(x, y);
	}
}