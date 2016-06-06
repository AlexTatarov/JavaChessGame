import javax.swing.ImageIcon;

public class Nebun extends Piesa{

	Nebun(int cul, int x, int y)
	{ 
		if(cul > 1)
		{
			cul = 1;
		}
		culoare = cul;
		cod = 2; 
		if(cul == 1)
		{
			img = new ImageIcon("bnebun.png");
		}
		else
		{
			img = new ImageIcon("anebun.png");
		}
		imagePosition.setLocation(x, y);
	}
}
