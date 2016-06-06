import org.junit.*;
import static org.junit.Assert.*;

public class JavaTest{
	static Piesa[][] Tabla = new Piesa[8][8];
	int xRegeAlb,yRegeAlb,xRegeNegru,yRegeNegru;
	int nivelDificultate = 6;
	
	@Test 
	public void T1(){
		boolean expected = true;
		AI ai = new AI();
		ai.adaugaRege(1,1,1,Tabla);
		boolean result = AI.regeNeAtacat(1,1,Tabla);
		assertTrue(expected == result);
	}
	
	@Test
	public void T2(){
		boolean expected = false;
		AI ai = new AI();
		ai.adaugaRege(1, 1, 1, Tabla);
		ai.adaugaRegina(1, 5, 0, Tabla);
		boolean result = AI.regeNeAtacat(1, 1, Tabla);
		assertTrue(expected == result);
	}
	@Test
	public void T3(){
		AI.xRegeAlb = 0;
		AI.yRegeAlb = 0;
		AI.xRegeNegru = 7;
		AI.yRegeNegru = 4;
		
		String expected = "3303 ";
		AI ai = new AI();
		ai.adaugaRege(0, 0, 0, Tabla);
		ai.adaugaRegina(3, 3, 1,Tabla);
		ai.adaugaPion(1,1,0,Tabla);
		ai.adaugaCal(1, 0, 0, Tabla);
		ai.adaugaRege(7, 4, 1, Tabla);
		AI.alegeMiscareAI(Tabla,AI.nivelDificultate,Integer.MIN_VALUE,Integer.MAX_VALUE,1);
		String result = AI.miscareAleasa;
		System.out.println("Miscarea aleasa este: "+result);
		assertTrue(expected == result);
	}
	
	
	
	
}
