import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws InterruptedException
	{
		int x,y;
		UserInterface ui = new UserInterface();
        ui.display();
        Thread UserInterfaceThread = new Thread(ui);
        UserInterfaceThread.start();
        //UserInterfaceThread.join();
        System.out.println("am iesit "+ui.gameMode);
        AI ai = new AI();
        int albe;
        int negre;
        
        
        
      //(1-pvp+internet, 2-pvp+samepc, 3-pvc+Alb, 4-pvc+Negru)
        while(ui.gameMode == 0 || ui.totulAles == false)
        {
        	System.out.println("inca nu s-a ales modul de joc");
        }
        
        
        
        System.out.println("S-a ales modul: " + ui.gameMode);
        if(ui.gameMode == 2)
        {
        	System.out.println("Game initializing...");
        	System.out.println("White - Player1");
        	System.out.println("Black  - Player2");
        	ai.samePC();
        	System.out.println("Game ended");
    	}
        else if(ui.gameMode==1)
        {
                String move;
        	albe = 0;
                negre = 0;
                if(ui.SV.ss!=null){
                    albe = ai.playerFaceMiscare(0);
                    ui.SV.sendMove(ai.istoriaMiscarilor.get(ai.istoriaMiscarilor.size()-1));
                }else{
                    
                }
        	while(albe == 0 && negre == 0){
        		if(ui.SV.ss != null){
        			System.out.println("server");
                                move = ui.SV.getMove();
                                AI.doMiscare(move,UserInterface.TablaUI);
                                x = Character.getNumericValue(move.charAt(2));
        			y = Character.getNumericValue(move.charAt(3));
        			ui.TablaUI[x][y].imagePosition.setLocation(x*ui.sc+20, y*ui.sc+20);
                                ui.jf.repaint();
        			albe = ai.playerFaceMiscare(0);
        			ui.SV.sendMove(ai.istoriaMiscarilor.get(ai.istoriaMiscarilor.size()-1));
        		}else {
                                System.out.println("socket");
                                move = ui.SV.getMove();
        			AI.doMiscare(move,UserInterface.TablaUI);
                                x = Character.getNumericValue(move.charAt(2));
        			y = Character.getNumericValue(move.charAt(3));
        			ui.TablaUI[x][y].imagePosition.setLocation(x*ui.sc+20, y*ui.sc+20);
                                ui.jf.repaint();
        			negre = ai.playerFaceMiscare(0);
                                System.out.println("cica s-a facut negrele");
        			ui.SV.sendMove(ai.istoriaMiscarilor.get(ai.istoriaMiscarilor.size()-1));
                                x = Character.getNumericValue(move.charAt(2));
        			y = Character.getNumericValue(move.charAt(3));
        			ui.TablaUI[x][y].imagePosition.setLocation(x*ui.sc+20, y*ui.sc+20);
                                ui.jf.repaint();
        		}        		
        	}
    	}
        else if(ui.gameMode == 3)
        {
        	//JUCAM CONTRA CALCULATORULUI CU PIESE ALBE
            ai.whiteAgainstComputer(ui);
            
            
    	}
        else if(ui.gameMode == 4 && ui.totulAles == true)
        {
        	System.out.println("incepem joaca");
        	//JUCAM CONTRA CALCULATORULUI CU PIESE NEGRE
        	ai.blackAgainstComputer(ui);
        }
	}
}
