import javax.swing.ImageIcon;

public class Regina extends Piesa{

	Regina(int cul, int x, int y)
	{ 
		if(cul > 1)
		{
			cul = 1;
		}
		culoare = cul;
		cod = 8;
		if(cul == 1)
		{
			img = new ImageIcon(getClass().getClassLoader().getResource("bregina.png"));
		}
		else
		{
			img = new ImageIcon(getClass().getClassLoader().getResource("aregina.png"));
		}
		imagePosition.setLocation(x, y);
	}
}
