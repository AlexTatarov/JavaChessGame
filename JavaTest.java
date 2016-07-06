import org.junit.*;
import static org.junit.Assert.*;

public class JavaTest{
	static Piesa[][] Tabla = new Piesa[8][8];
	int xRegeAlb = 7,yRegeAlb = 7 ,xRegeNegru,yRegeNegru;
	String miscareAleasa;
	int nivelDificultate = 3;
	int sc = UserInterface.sc;
	@Test 
	
	public void T1(){
		boolean expected = true;
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				Tabla[i][j] = null;
			}
		}
		xRegeNegru = 1;
		yRegeNegru = 1;
		Tabla[xRegeNegru][yRegeNegru] = new Rege(1,sc*xRegeNegru+20,sc*yRegeNegru+20);
		Tabla[2][2] = new Turn(0,sc*2+20,sc*2+20);
		Tabla[3][4] = new Regina(0,sc*3+20,sc*4+20);
		Tabla[1][1] = new Cal(0,sc*1+20,sc*1+20);
		boolean result = AI.regeNeAtacat(xRegeNegru,yRegeNegru,Tabla);
		assertTrue(expected == result);
	}
	
	@Test
	public void T2(){
		boolean expected = false;
		
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				Tabla[i][j] = null;
			}
		}
		xRegeNegru = 0;
		yRegeNegru = 0;
		Tabla[xRegeNegru][yRegeNegru] = new Rege(1,sc*xRegeNegru+20,sc*yRegeNegru+20);
		Tabla[1][0] = new Pion(1,sc*1+20,sc*0+20);
		Tabla[1][1] = new Pion(1,sc*1+20,sc*1+20);
		Tabla[xRegeAlb][yRegeAlb] = new Rege(0,xRegeAlb*sc+20,yRegeAlb*sc+20);
		Tabla[0][4] = new Regina(0,4*sc+20,0*sc+20);
		boolean result = AI.regeNeAtacat(xRegeNegru, yRegeNegru, Tabla);
		assertTrue(expected == result);
	}
	
	@Test
	public void T3(){
		
		AI.xRegeAlb = 0;
		AI.yRegeAlb = 0;
		AI.xRegeNegru = 7;
		AI.yRegeNegru = 4;
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				Tabla[i][j] = null;
			}
		}
		Tabla[5][4] = new Regina(0,0,0);
		String miscare = "5452  ";
		int expected = Tabla[5][4].cod;
		AI.doMiscare(miscare,Tabla);
		AI.undoMiscare(miscare,Tabla);
		assertTrue(expected == Tabla[5][4].cod);
	}
	@Test
	public void T4(){
		AI.xRegeAlb = 0;
		AI.yRegeAlb = 0;
		AI.xRegeNegru = 7;
		AI.yRegeNegru = 7;
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				Tabla[i][j] = null;
			}
		}
		Tabla[5][3] = new Regina(0,0,0);
		String miscare = "5333  ";
		AI.doMiscare(miscare,Tabla);
		Piesa expected = null;
		assertTrue(expected == Tabla[5][3] );
	}
	
	
	
	
	
}
