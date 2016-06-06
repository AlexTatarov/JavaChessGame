import javax.swing.JFrame;

public class Main {

	public static void main(String[] args)
	{
		UserInterface ui = new UserInterface();
        ui.display();
        Thread UserInterfaceThread = new Thread(ui);
        UserInterfaceThread.start();
        
        AI ai = new AI();
        boolean albe = ai.faceMiscare(0);
        boolean negre = false;
        AI.alegeMiscareAI(AI.TablaAI,4,Integer.MIN_VALUE,Integer.MAX_VALUE,1);
        System.out.println("Miscarea aleasa este "+AI.miscareAleasa);
        AI.doMiscare(AI.miscareAleasa,UserInterface.TablaUI);
        int x = Character.getNumericValue(AI.miscareAleasa.charAt(2));
        int y = Character.getNumericValue(AI.miscareAleasa.charAt(3));
        ui.TablaUI[x][y].imagePosition.setLocation(x*ui.sc+20, y*ui.sc+20);
        AI.randMiscare = 0;
        negre = true;
        ui.jf.repaint();
        ui.TablaUI[0][6].ana();
        while(albe && negre)
        {
        	albe = ai.faceMiscare(0);
        	AI.alegeMiscareAI(AI.TablaAI,4,Integer.MIN_VALUE,Integer.MAX_VALUE,1);
            AI.doMiscare(AI.miscareAleasa,UserInterface.TablaUI);
            x = Character.getNumericValue(AI.miscareAleasa.charAt(2));
            y = Character.getNumericValue(AI.miscareAleasa.charAt(3));
            ui.TablaUI[x][y].imagePosition.setLocation(x*ui.sc+20, y*ui.sc+20);
            ui.jf.repaint();
           
        }
	}
}