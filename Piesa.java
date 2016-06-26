import java.awt.Point;

import javax.swing.ImageIcon;

public class Piesa {

	int cod = -1;	// 1-pion, 2-nebun, 3-cal, 5-turn, 8-regina, 9-rege;
	int culoare = -1; // 1-negru,  0-alb;
	int stare = 0;
	boolean apar = false; // f-nu e aparat, t-e aparat;
	ImageIcon img = null;
	Point imagePosition = new Point();

}
