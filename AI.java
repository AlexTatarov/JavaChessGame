import java.util.ArrayList;

public class AI {

	static ArrayList<String> istoriaMiscarilor = new ArrayList<String>();
	static ArrayList<String> miscariPosibileAlbe = new ArrayList<String>();
	static ArrayList<Piesa> pieseBatuteAlbe = new ArrayList<Piesa>();
	static ArrayList<String> miscariPosibileNegre = new ArrayList<String>();
	static ArrayList<Piesa> pieseBatuteNegre = new ArrayList<Piesa>();
	static int xRegeAlb,yRegeAlb,xRegeNegru,yRegeNegru;
	static int randMiscare = 0;
	static int nrMiscare = 0;
	static String miscareAleasa;
	static Piesa TablaAI[][] = new Piesa[8][8];
	static int nivelDificultate;
	static int culoareCalculator = 0;
	static int culoareJucator = 1;
	
	
	
	//alfa = valoarea maxima
	//beta = valoarea minima
	//SE ALEGE MISCAREA PENTRU CALCULATOR, DECI CALCULATORUL VA FI MAXIMAZING PLAYER
	static int alegeMiscareAI(Piesa[][] Tabla,int adancime, int alfa, int beta, int cul)
	{
		int valoareMaxima = Integer.MIN_VALUE;
		int pozitiaValoriiMaxime;
		int valoare;
		
		ArrayList<String> miscariPosibileJucator = new ArrayList<String>();
		ArrayList<String> miscariPosibileCalculator = new ArrayList<String>();
		
		analizeazaTabla(culoareCalculator,miscariPosibileCalculator,Tabla);
		analizeazaTabla(culoareJucator,miscariPosibileJucator,Tabla);
		
		//DACA ESTE NIVEL TERMINAL SAU DACA NU MAI ARE FII
		if(adancime == 0 || 
				(cul == culoareCalculator && miscariPosibileCalculator.size() == 0) || 
				(cul == culoareJucator && miscariPosibileJucator.size() == 0))
		{
			int val = Rating.evaluare(Tabla,culoareCalculator,miscariPosibileCalculator,miscariPosibileJucator,nivelDificultate + 1 - adancime) 
					- Rating.evaluare(Tabla,culoareJucator,miscariPosibileJucator,miscariPosibileCalculator,nivelDificultate + 1 - adancime);
			val*=adancime+1;
			//System.out.println(val);
			return val;
		}
		//DACA ESTE CALCULATORUL ATUNCI SIMULAM SI UPDATE-AM alfa
		if(cul ==culoareCalculator)
		{
			valoare = Integer.MIN_VALUE;
			for(int i=0; i<miscariPosibileCalculator.size(); ++i)
			{
				
				doMiscare(miscariPosibileCalculator.get(i),Tabla);
				valoare = Math.max(valoare,alegeMiscareAI(Tabla,adancime-1,alfa,beta,culoareJucator));
				if(adancime == nivelDificultate)
				{
					
					if(valoare > valoareMaxima)
					{
						valoareMaxima = valoare;
						miscareAleasa = miscariPosibileCalculator.get(i);
						
					}
				}
				undoMiscare(miscariPosibileCalculator.get(i),Tabla);
				alfa = Math.max(alfa, valoare);
				if(beta <= alfa)
				{
					//BETA CUT-OFF
					break;
				}
				
			}
			return valoare;
		}
		else
		{
			valoare = Integer.MAX_VALUE;
			for(int i=0; i<miscariPosibileJucator.size(); i++)
			{
				doMiscare(miscariPosibileJucator.get(i),Tabla);
				valoare = Math.min(valoare, alegeMiscareAI(Tabla,adancime-1,alfa,beta,culoareCalculator));
				if(adancime == nivelDificultate)
				{
					if(valoare < valoareMaxima)
					{
						valoareMaxima = valoare;
						miscareAleasa = miscariPosibileJucator.get(i);
					}
				}
				undoMiscare(miscariPosibileJucator.get(i),Tabla);
				beta = Math.min(valoare, beta);
				if(beta <= alfa)
				{
					//ALFA CUT-OFF
					break;
				}
			}
			return valoare;
		}
	}
	
	
	//SINCRONIZEAZA TABLALE
	//TablaAI DEVINE LA FEL CA SI TablaUI
	static void sincronizareTable()
	{
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				TablaAI[i][j] = UserInterface.TablaUI[i][j];
				if(TablaAI[i][j]!=null)
				{
					if(TablaAI[i][j].cod == 9)
					{
						if(TablaAI[i][j].culoare == 0)
						{
							AI.xRegeAlb = i;
							AI.yRegeAlb = j;
						}
						else
						{
							AI.xRegeNegru = i;
							AI.yRegeNegru = j;
						}
					}
				}
			}
		}
	}
	
	//SE DETERMINA MISCARILE POSIBILE PE TABLA Tabla, PENTRU PIESELE DE CULOARE cul, SI SUNT PLASATE IN miscariPosibile
	static void analizeazaTabla(int cul,ArrayList<String> miscariPosibile,Piesa[][] Tabla)
	{
		for(int i=0;i<8; i++)
        {
        	for(int j=0; j<8; j++)
        	{
        		if(Tabla[i][j]!= null && Tabla[i][j].culoare == cul)
        		{
        			switch(Tabla[i][j].cod)
        			{
        				case 2 :
        					//System.out.println("Pe pozitia "+i+" "+j+" este un nebun");
        					verificareNebun(i,j,miscariPosibile,Tabla);
        					break;
        				case 9 : 
        					//System.out.println("Pe pozitia "+i+" "+j+" este un rege");
        					verificareRege(i,j,miscariPosibile,Tabla);
        					break;
        				case 5 :
        					//System.out.println("Pe pozitia "+i+" "+j+" este un turn");
        					verificareTurn(i,j,miscariPosibile,Tabla);
        					break;
        				case 3 : 
        					//System.out.println("Pe pozitia "+i+" "+j+" este un cal");
        					verificareCal(i,j,miscariPosibile,Tabla);
        					break;
        				case 1 :
        					//System.out.println("Pe pozitia "+i+" "+j+" este un pion");
        					verificarePion(i,j,miscariPosibile,Tabla);
        					break;
        				case 8 :
        					//System.out.println("Pe pozitia "+i+" "+j+" este un regina");
        					verificareRegina(i,j,miscariPosibile,Tabla);
        					break;
        				default :
        					//System.out.println("Pe pozitia "+i+" "+j+" este Eroare");
        					break;		
        			}
        			
        		}
        	}
        }
	}
	
	//JUCATORUL DE CULOAREA cul FACE O MISCARE
	boolean faceMiscare(int cul)
	{
		UserInterface.miscareFacuta = false;
		sincronizareTable();
		/*ultima schimbare
		if(cul == 0)
		{
			
			System.out.println("PIESELE ALBE FAC MISCARE");
			
			for(int i=0; i<miscariPosibileAlbe.size(); i++)
			{
				System.out.println(miscariPosibileAlbe.get(i)+".");
			}
		}
		else
		{
			System.out.println("PIESELE NEGRE FAC MISCARE");
			for(int i=0; i<miscariPosibileNegre.size(); i++)
			{
				System.out.println(miscariPosibileNegre.get(i)+".");
			}
		}
		*/
		if(cul == 0)
		{
			analizeazaTabla(0,miscariPosibileAlbe,TablaAI);
		}
		else
		{
			analizeazaTabla(1,miscariPosibileNegre,TablaAI);
		}
		if(cul == 0)
		{
			if(miscariPosibileAlbe.size() == 0)
			{
				//System.out.println("Pisele albe nu mai pot face nici o miscare");
				if(regeNeAtacat(xRegeAlb,yRegeAlb,TablaAI))
				{
					System.out.println("Remiza");
				}
				else
				{
					System.out.println("Sah. Mat.");
				}
				return false;
			}
			else
			{
				/*
				 * ultima schimbare
				 * System.out.println("Miscarile posibile sunt:");
				for(int i=0; i<miscariPosibileAlbe.size(); i++)
				{
					System.out.println(miscariPosibileAlbe.get(i)+".");
				}
				*/
				while(UserInterface.miscareFacuta == false)
				{
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					//System.out.println("Sunt in while");
				}
				
				//System.out.println("S-a facut miscarea alba");

			}
		}
		if(cul == 1)
		{
			if(miscariPosibileNegre.size() == 0)
			{
				//System.out.println("Pisele negre nu mai pot face miscare");
				if(regeNeAtacat(xRegeNegru,yRegeNegru,TablaAI))
				{
					System.out.println("Remiza");
					for(int i=0; i<istoriaMiscarilor.size(); i++)
					{
						System.out.println(istoriaMiscarilor.get(i)+".");
					}
				}
				else
				{
					System.out.println("Sah. Mat.");
					for(int i=0; i<istoriaMiscarilor.size(); i++)
					{
						System.out.println(istoriaMiscarilor.get(i)+".");
					}
						
				}
				return false;
			}
			else
			{
				/*ultima schimabre
				System.out.println("Miscarile posibile sunt:");
				for(int i=0; i<miscariPosibileNegre.size(); i++)
				{
					System.out.println(miscariPosibileNegre.get(i)+".");
				}
				*/
				while(UserInterface.miscareFacuta == false)
				{
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					//System.out.println("Sunt in while");
				} 
				//System.out.println("S-a facut miscarea neagra");
			}
			
			/*
			for(int i=0; i<miscariPosibileNegre.size(); i++)
			{
				System.out.println(miscariPosibileNegre.get(i));
			}
			*/
		}
		//repaint();
		miscariPosibileAlbe.clear();
		miscariPosibileNegre.clear();
		UserInterface.miscareFacuta = false;
		randMiscare = 1 - randMiscare;
		return true;
	}
	
	//SE FACE MISCAREA DETERMINATA DE miscare, PE TABLA Tabla
	static void doMiscare(String miscare,Piesa[][] Tabla)
	{
		//System.out.println("Am receptionat" +  miscare);
		int cul;
		int x_initial,x_final,y_initial,y_final;
		x_initial = Character.getNumericValue(miscare.charAt(0));
		y_initial = Character.getNumericValue(miscare.charAt(1));
		x_final = Character.getNumericValue(miscare.charAt(2));
		y_final = Character.getNumericValue(miscare.charAt(3));
		cul = Tabla[x_initial][y_initial].culoare;
		//daca este rege
		if(Tabla[x_initial][y_initial].cod == 9)
		{
			if(cul == 0)
			{
				xRegeAlb = x_final;
				yRegeAlb = y_final;
			}
			else
			{
				xRegeNegru = x_final;
				yRegeNegru = y_final;
			}
		}
		//daca este pion
		else if(Tabla[x_initial][y_initial].cod == 1)
		{
			Tabla[x_initial][y_initial].stare = 2;
		}
		if(miscare.charAt(4) != ' ')
		{
			if(cul == 0)
			{
				pieseBatuteNegre.add(Tabla[x_final][y_final]);
			}
			else
			{
				pieseBatuteAlbe.add(Tabla[x_final][y_final]);
			}
		}
		Tabla[x_final][y_final] = Tabla[x_initial][y_initial];
		Tabla[x_initial][y_initial] = null;
		
	}
	
	//SE ANULEAZA MISCAREA DETERMINATA DE miscare, PE TABLA  Tabla
	static void undoMiscare(String miscare,Piesa[][] Tabla)
	{
		Piesa PiesaTemp = new Piesa();
		
		int x_initial,y_initial,x_final,y_final;
		x_initial = Character.getNumericValue(miscare.charAt(0));
		y_initial = Character.getNumericValue(miscare.charAt(1));
		x_final = Character.getNumericValue(miscare.charAt(2));
		y_final = Character.getNumericValue(miscare.charAt(3));
		if(miscare.charAt(4) != ' ')
		{
			if(Tabla[x_final][y_final].culoare == 1)
			{
				PiesaTemp = pieseBatuteAlbe.get(pieseBatuteAlbe.size()-1);
				pieseBatuteAlbe.remove(pieseBatuteAlbe.size()-1);
			}
			else
			{
				PiesaTemp = pieseBatuteNegre.get(pieseBatuteNegre.size()-1);
				pieseBatuteNegre.remove(pieseBatuteNegre.size()-1);
			}
			Tabla[x_initial][y_initial] = Tabla[x_final][y_final];
			Tabla[x_final][y_final] = PiesaTemp;
		}
		else
		{
			Tabla[x_initial][y_initial] = Tabla[x_final][y_final];
			Tabla[x_final][y_final] = null;
		}
		//daca este rege
		if(Tabla[x_initial][y_initial].cod == 9)
		{
			if(Tabla[x_initial][y_initial].culoare == 0)
			{
				xRegeAlb = x_initial;
				yRegeAlb = y_initial;
			}
			else
			{
				xRegeNegru = x_initial;
				yRegeNegru = y_initial;
			}
		}
		else if(Tabla[x_initial][y_initial].cod == 1)
		{
			if(Tabla[x_initial][y_initial].culoare == 0 && x_initial == 6)
			{
				Tabla[x_initial][y_initial].stare = 1;
			}
			if(Tabla[x_initial][y_initial].culoare == 1 && x_initial == 1)
			{
				Tabla[x_initial][y_initial].stare = 1;
			}
		}
		
	}
	
	//SE VERIFICA DACA REGELE CU POZITIA r c NU ESTE ATACAT(TRUE == REGELE NU ESTE ATACAT, FALSE == REGELE ESTE ATACAT)
	static boolean regeNeAtacat(int r,int c,Piesa[][] Tabla)
	{
		int culoare = Tabla[r][c].culoare;
		int raux = r;
		int caux = c;
		
		//verificam daca regele nu e atacat de regina turn sau nebun
		for(int i=-1; i<2;i++)
		{
			for(int j=-1;j<2;j++)
			{
				raux = r + i;
				caux = c + j;
				while(raux >= 0 && raux < 8 && caux < 8 && caux >=0 && (i != 0 || j != 0))
				{
					if(Tabla[raux][caux] != null && (i==0 || j==0))
					{
						if(Tabla[raux][caux].culoare != Tabla[r][c].culoare && (Tabla[raux][caux].cod == 8 || Tabla[raux][caux].cod == 5))
						{
							return false;
						}
						if(Tabla[raux][caux].culoare == Tabla[r][c].culoare || (Tabla[raux][caux].cod != 8 && Tabla[raux][caux].cod != 5))
						{
							break;
						}
					}
					if(Tabla[raux][caux]!=null && i!=0 && j!=0)
					{
						if(Tabla[raux][caux].culoare != culoare && (Tabla[raux][caux].cod == 8 || Tabla[raux][caux].cod == 2))
						{
							return false;
						}
						if(Tabla[raux][caux].culoare == Tabla[r][c].culoare || (Tabla[raux][caux].cod != 8 && Tabla[raux][caux].cod != 2))
						{
							break;
						}
					}
					
					raux += i;
					caux += j;
				}
			}
		}
		
		//verificam daca regele nu e atacat de cal
		for(int i=-2; i<=2; i++)
		{
			for(int j=-2; j<=2; j++)
			{
				raux = r + i;
				caux = c + j;
				if(Math.abs(i)!=Math.abs(j) && i!=0 && j!=0 && raux > -1 && raux <8 && caux > -1 && caux <8)
				{
					if(Tabla[raux][caux] == null)
					{
						continue;
					}
					else if(Tabla[raux][caux].culoare != culoare && Tabla[raux][caux].cod == 3)
					{
						return false;
					}
				}
			}
		}
		
		
		//verificam daca este atacat de alt rege
		for(int i=-1; i<2; i++)
		{
			for(int j=-1; j<2; j++)
			{
				raux = r + i;
				caux = c + j;
				if(raux > -1 && raux < 8 && caux > -1 && caux < 8)
				{
					if(Tabla[raux][caux] != null)
					{
						if(Tabla[raux][caux].culoare != Tabla[r][c].culoare && Tabla[raux][caux].cod == 9)
						{
							return false;
						}
					}
					
				}
			}
		}
		//verificam daca este atacat de un pion
		//caz 1 
		//daca e rege negru atacat de pion alb
		if(r < 6 && c > 0)
		{
			if(Tabla[r+1][c-1] !=null)
			{
				if(Tabla[r+1][c-1].cod == 1 && Tabla[r+1][c-1].culoare == 0 && Tabla[r][c].culoare == 1)
				{
					return false;
				}
			}
		}
		if(r < 6 && c < 7)
		{
			if(Tabla[r+1][c+1]!= null)
			{
				if(Tabla[r+1][c+1].cod == 1 && Tabla[r+1][c+1].culoare == 0 && Tabla[r][c].culoare == 1)
				{
					return false;
				}
			}
		}
		//caz 2
		//daca e rege alb atacat de pion negru
		if(r > 1 && c > 0)
		{
			if(Tabla[r-1][c-1]!= null)
			{
				if(Tabla[r-1][c-1].cod == 1 && Tabla[r-1][c-1].culoare == 1 && Tabla[r][c].culoare == 0)
				{
					return false;
				}
			}
		}
		if(r > 1 && c < 7)
		{
			if(Tabla[r-1][c+1]!= null)
			{
				if(Tabla[r-1][c+1].cod == 1 && Tabla[r-1][c+1].culoare == 1 && Tabla[r][c].culoare == 0)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	//SE VERIFICA MISCARILE POSIBILE GENERATE DE REGELE DE PE POZITIA r c SI SE INTRODUC IN miscariPosibile
	static void verificareRege(int r,int c,ArrayList<String> miscariPosibile,Piesa[][] Tabla)
	{
		String miscare;
		int raux,caux;
		int cul = Tabla[r][c].culoare;
		for(int i=-1; i<2; i++)
			for(int j=-1; j<2; j++)
			{
				raux = r+i;
				caux = c+j;
				if((raux)>=0 && (caux)>=0 && (raux)<8 && (caux)<8 && (i!=0 || j!=0))
				{
					if(Tabla[raux][caux] == null)
					{
						miscare = ""+r+c+raux+caux+" ";
						doMiscare(miscare,Tabla);
						if(regeNeAtacat(raux,caux,Tabla))
						{
							miscariPosibile.add(miscare);
						}
						undoMiscare(miscare,Tabla);
					} 
					else if (Tabla[raux][caux] != null && Tabla[raux][caux].culoare != cul)
					{
						miscare = ""+r+c+raux+caux+Tabla[raux][caux].cod;
						doMiscare(miscare,Tabla);
						if(regeNeAtacat(raux,caux,Tabla))
						{
							miscariPosibile.add(miscare);
							
						}
						undoMiscare(miscare,Tabla);
					}
				}
			}
	}
	
	//SE VERIFICA MISCARILE POSIBILE GENERATE DE NEBUNUL DE PE POZITIA r c SI SE INTRODUC IN miscariPosibile
	static void verificareNebun(int r,int c,ArrayList<String> miscariPosibile,Piesa[][] Tabla)
	{
		String miscare = new String();
		int raux = r;
		int caux = c;
		int cul = Tabla[r][c].culoare;
		//verificam cele 4 directii de deplasare a nebunului
		for(int i=-1; i<=1; i+=2)
		{
			for(int j=-1; j<=1; j+=2)
			{
				raux = r;
				caux = c;
				raux+=i;
				caux+=j;
				while(raux >= 0 && raux < 8 && caux < 8 && caux >= 0)
				{
					
					if(Tabla[raux][caux]==null)
					{
						miscare = "" + r + c + raux + caux+ " ";
						doMiscare(miscare,Tabla);
						if(cul == 0)
						{
							if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
							{
								miscariPosibile.add(miscare);
							}
						}
						else
						{
							if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
							{
								miscariPosibile.add(miscare);
							}
						}
						undoMiscare(miscare,Tabla);
					}
					else if(Tabla[raux][caux].culoare != cul && Tabla[raux][caux].cod != 9)
					{
						miscare = ""+r+c+raux+caux+Tabla[raux][caux].cod;
						doMiscare(miscare,Tabla);
						if(cul == 0)
						{
							if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
							{
								miscariPosibile.add(miscare);
							}
						}
						else
						{
							if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
							{
								miscariPosibile.add(miscare);
							}
						}
						undoMiscare(miscare,Tabla);
						break;
					}
					else
					{
						break;
					}
					raux+=i;
					caux+=j;
				}
			}
		}
	}
	
	//SE VERIFICA MISCARILE POSIBILE GENERATE DE TURNUL DE PE POZITIA r c SI SE INTRODUC IN miscariPosibile
	static void verificareTurn(int r,int c,ArrayList<String> miscariPosibile,Piesa[][] Tabla)
	{
		int cul = Tabla[r][c].culoare;
		String miscare = new String();
		int raux = r;
		int caux = c;
		
		for(int i=-1; i<2; i++)
		{
			for(int j=-1; j<2; j++)
			{
				if((i+j == 1)||(i+j == -1))
				{
					raux = r + i;
					caux = c + j;
					while(raux>=0 && raux < 8 && caux >=0 && caux <8)
					{
						if(Tabla[raux][caux] == null)
						{
							miscare = ""+r+c+raux+caux+" ";
							doMiscare(miscare,Tabla);
							if(cul == 0)
							{
								if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
								{
									miscariPosibile.add(miscare);
								}
							}
							else
							{
								if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
								{
									miscariPosibile.add(miscare);
								}
							}
							undoMiscare(miscare,Tabla);
						}
						else if(Tabla[raux][caux].culoare != cul && Tabla[raux][caux].cod != 9)
						{
							miscare = ""+r+c+raux+caux+Tabla[raux][caux].cod;
							doMiscare(miscare,Tabla);
							if(cul == 0)
							{
								if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
								{
									miscariPosibile.add(miscare);
								}
							}
							else
							{
								if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
								{
									miscariPosibile.add(miscare);
								}
							}
							undoMiscare(miscare,Tabla);
							break;
						}
						else
						{
							break;
						}
						raux+=i;
						caux+=j;
					}
				}
			}
		}
	}
	
	//SE VERIFICA MISCARILE POSIBILE GENERATE DE CALUL DE PE POZITIA r c SI SE INTRODUC IN miscariPosibile
	static void verificareCal(int r,int c,ArrayList<String> miscariPosibile,Piesa[][] Tabla)
	{
		int cul = Tabla[r][c].culoare;
		boolean posibil = false;
		String miscare = new String();
		int raux;
		int caux;
		if(Tabla[r][c] == null)
		{
			System.out.println("Este null1");
		}
		//caz 1
		raux = r - 2;
		caux = c - 1;
		if(raux > -1 && raux < 8 && caux > -1 && caux < 8)
		{
			if(Tabla[raux][caux] == null)
			{
				miscare = "" + r + c + raux + caux + " ";
				posibil = true;
			}
			else if(Tabla[raux][caux] != null && Tabla[raux][caux].culoare != cul && Tabla[raux][caux].cod != 9)
			{
				miscare = "" + r + c + raux + caux + Tabla[raux][caux].cod;
				posibil = true;
			}
			if(posibil)
			{
				doMiscare(miscare,Tabla);
				
				if(cul == 0)
				{
					
					if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				else
				{
					if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				undoMiscare(miscare,TablaAI);
			}
			
		}
		//caz 2
		posibil = false;
		raux = r - 2;
		caux = c + 1;
		if(raux > -1 && raux < 8 && caux > -1 && caux < 8)
		{
			if(Tabla[raux][caux] == null)
			{
				miscare = "" + r + c + raux + caux + " ";
				posibil = true;
			}
			else if(Tabla[raux][caux] != null && Tabla[raux][caux].culoare != cul && Tabla[raux][caux].cod != 9)
			{
				miscare = "" + r + c + raux + caux + Tabla[raux][caux].cod;
				posibil = true;
			}
			if(posibil)
			{
				doMiscare(miscare,Tabla);
				if(cul == 0)
				{
					if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				else
				{
					if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				undoMiscare(miscare,Tabla);
			}
		}
		//caz 3
		raux = r - 1;
		caux = c - 2;
		posibil = false;
		if(raux > -1 && raux < 8 && caux > -1 && caux < 8)
		{
			if(Tabla[raux][caux] == null)
			{
				miscare = "" + r + c + raux + caux + " ";
				posibil = true;
			}
			else if(Tabla[raux][caux] != null && Tabla[raux][caux].culoare != cul && Tabla[raux][caux].cod != 9)
			{
				posibil = true;
				miscare = "" + r + c + raux + caux + Tabla[raux][caux].cod;
			}
			if(posibil)
			{
				doMiscare(miscare,Tabla);
				if(cul == 0)
				{
					if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				else
				{
					if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				undoMiscare(miscare,Tabla);
			}
		}
		//caz 4
		raux = r - 1;
		caux = c + 2;
		posibil = false;
		if(raux > -1 && raux < 8 && caux > -1 && caux < 8)
		{
			if(Tabla[raux][caux] == null)
			{
				miscare = "" + r + c + raux + caux + " ";
				posibil = true;
			}
			else if(Tabla[raux][caux] != null && Tabla[raux][caux].culoare != cul && Tabla[raux][caux].cod != 9)
			{
				miscare = "" + r + c + raux + caux + Tabla[raux][caux].cod;
				posibil = true;
			}
			if(posibil)
			{
				doMiscare(miscare,Tabla);
				if(cul == 0)
				{
					if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				else
				{
					if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				undoMiscare(miscare,Tabla);
			}
		}
		//caz 5
		raux = r + 1;
		caux = c - 2;
		posibil = false;
		if(raux > -1 && raux < 8 && caux > -1 && caux < 8)
		{
			if(Tabla[raux][caux] == null)
			{
				miscare = "" + r + c + raux + caux + " ";
				posibil = true;
			}
			else if(Tabla[raux][caux] != null && Tabla[raux][caux].culoare != cul && Tabla[raux][caux].cod != 9)
			{
				miscare = "" + r + c + raux + caux + Tabla[raux][caux].cod;
				posibil = true;
			}
			if(posibil)
			{
				doMiscare(miscare,Tabla);
				if(cul == 0)
				{
					if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				else
				{
					if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				undoMiscare(miscare,Tabla);
			}
		}
		//caz 6
		raux = r + 1;
		caux = c + 2;
		posibil = false;
		if(raux > -1 && raux < 8 && caux > -1 && caux < 8)
		{
			if(Tabla[raux][caux] == null)
			{
				miscare = "" + r + c + raux + caux + " ";
				posibil = true;
			}
			else if(Tabla[raux][caux] != null && Tabla[raux][caux].culoare != cul && Tabla[raux][caux].cod != 9)
			{
				miscare = "" + r + c + raux + caux + TablaAI[raux][caux].cod;
				posibil = true;
			}
			if(posibil)
			{
				doMiscare(miscare,Tabla);
				if(cul == 0)
				{
					if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				else
				{
					if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				undoMiscare(miscare,Tabla);
			}
		}
		//caz 7
		raux = r + 2;
		caux = c - 1;
		posibil = false;
		if(raux > -1 && raux < 8 && caux > -1 && caux < 8)
		{
			if(Tabla[raux][caux] == null)
			{
				miscare = "" + r + c + raux + caux + " ";
				posibil = true;
			}
			else if(Tabla[raux][caux] != null && Tabla[raux][caux].culoare != cul && Tabla[raux][caux].cod != 9)
			{
				miscare = "" + r + c + raux + caux + Tabla[raux][caux].cod;
				posibil = true;
			}
			if(posibil)
			{
				doMiscare(miscare,Tabla);
				if(cul == 0)
				{
					if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				else
				{
					if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				undoMiscare(miscare,Tabla);
			}
		}
		//caz 8
		raux = r + 2;
		caux = c + 1;
		posibil = false;
		if(raux > -1 && raux < 8 && caux > -1 && caux < 8)
		{
			if(Tabla[raux][caux] == null)
			{
				miscare = "" + r + c + raux + caux + " ";
				posibil = true;
			}
			else if(Tabla[raux][caux] != null && Tabla[raux][caux].culoare != cul && Tabla[raux][caux].cod != 9)
			{
				miscare = "" + r + c + raux + caux + Tabla[raux][caux].cod;
				posibil = true;
			}
			if(posibil)
			{
				doMiscare(miscare,Tabla);
				if(cul == 0)
				{
					if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				else
				{
					if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				undoMiscare(miscare,Tabla);
			}
		}
	}
	
	//SE VERIFICA MISCARILE POSIBILE GENERATE DE PIONUL DE PE POZITIA r c SI SE INTRODUC IN miscariPosibile
	static void verificarePion(int r,int c,ArrayList<String> miscariPosibile,Piesa[][] Tabla)
	{
		String miscare = new String();
		int directie = 0;
		int cul = Tabla[r][c].culoare;
		if(Tabla[r][c].culoare == 0)//este alb
		{
			directie = -1;
		}
		else//este negru
		{
			directie = 1;
		}
		int raux = r + directie;
		int caux = c;
		if(r+directie < 8 && r+directie > -1)
		{
			if(Tabla[r+directie][c] == null)
			{
				miscare = ""+r+c+raux+caux+" ";
				doMiscare(miscare,Tabla);
				if(cul == 0)
				{
					if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				else
				{
					if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				undoMiscare(miscare,Tabla);
			}
		}
		
		if(caux+1 < 8 && Tabla[raux][caux+1]!= null && Tabla[raux][caux+1].cod != 9)
		{
			if(Tabla[r][c].culoare != Tabla[raux][caux+1].culoare)
			{
				miscare = ""+r+c+raux+(caux+1)+Tabla[raux][caux+1].cod;
				doMiscare(miscare,Tabla);
				if(cul == 0)
				{
					if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				else
				{
					if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				undoMiscare(miscare,Tabla);
			}
		}
		if(caux-1>-1 && Tabla[raux][caux-1]!=null && Tabla[raux][caux-1].cod != 9)
		{
			if(Tabla[r][c].culoare != Tabla[raux][caux-1].culoare)
			{
				miscare = ""+r+c+raux+(caux-1)+Tabla[raux][caux-1].cod;
				doMiscare(miscare,Tabla);
				if(cul == 0)
				{
					if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				else
				{
					if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
					{
						miscariPosibile.add(miscare);
					}
				}
				undoMiscare(miscare,Tabla);
			}
		}
		if(Tabla[r][c].stare == 1 && Tabla[r+directie*2][c] == null)
		{
			miscare = ""+r+c+(r+directie*2)+c+" ";
			doMiscare(miscare,Tabla);
			if(cul == 0)
			{
				if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
				{
					miscariPosibile.add(miscare);
				}
			}
			else
			{
				if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
				{
					miscariPosibile.add(miscare);
				}
			}
			undoMiscare(miscare,Tabla);
		}
	}
	
	//SE VERIFICA MISCARILE POSIBILE GENERATE DE REGINA DE PE POZITIA r c SI SE INTRODUC IN miscariPosibile
	static void verificareRegina(int r,int c,ArrayList<String> miscariPosibile,Piesa[][] Tabla)
	{
		String miscare = new String();
		int raux = r;
		int caux = c;
		int cul = Tabla[r][c].culoare;
		for(int i=-1; i<2; i++)
		{
			for(int j=-1; j<2; j++)
			{
				if(i != 0 || j != 0)
				{
					raux = r + i;
					caux = c + j;
					while(raux >=0 && raux <8 && caux >=0 && caux <8)
					{
						
						if(Tabla[raux][caux] == null)
						{
							miscare = "" + r + c + raux + caux + " ";
							doMiscare(miscare,Tabla);
							if(cul == 0)
							{
								if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
								{
									miscariPosibile.add(miscare);
								}
							}
							else
							{
								if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
								{
									miscariPosibile.add(miscare);
								}
							}
							undoMiscare(miscare,Tabla);
							
						}
						else if(Tabla[raux][caux].culoare != Tabla[r][c].culoare && Tabla[raux][caux].cod != 9)
						{
							miscare = "" + r + c + raux + caux + Tabla[raux][caux].cod;
							doMiscare(miscare,Tabla);
							if(cul == 0)
							{
								if(regeNeAtacat(xRegeAlb,yRegeAlb,Tabla))
								{
									miscariPosibile.add(miscare);
								}
							}
							else
							{
								if(regeNeAtacat(xRegeNegru,yRegeNegru,Tabla))
								{
									miscariPosibile.add(miscare);
								}
							}
							undoMiscare(miscare,Tabla);
							break;
						}
						else
						{
							break;
						}
						raux += i;
						caux += j;
					}
				}
			}
		}
	}
	
	//se adauga un rege de culoare "culoare" pe TablaAI pe pozitiile "r" "c"
	//daca pozitia este deja ocupata de alta piesa se returneaza false
	boolean adaugaRege(int r,int c,int culoare,Piesa[][] Tabla)
		{
			if(r < 0 || r > 7 || c < 0 || c > 7)
			{
				return false;
			}
			if(Tabla[r][c] == null)
			{
				Tabla[r][c] = new Rege(culoare,0,0);
				if(culoare == 0)
				{
					xRegeAlb = r;
					yRegeAlb = c;
				}
				else
				{
					xRegeNegru = r;
					yRegeNegru = c;
				}
			}
			else
			{
				return false;
			}
			return true;
		}
		
	//se adauga o regina pe Tabla
	boolean adaugaRegina(int r,int c,int culoare,Piesa[][] Tabla)
		{
			if(r < 0 || r > 7 || c < 0 || c > 7)
			{
				return false;
			}
			if(Tabla[r][c] == null)
			{
				Tabla[r][c] = new Regina(culoare,0,0);
			}
			else
			{
				return false;
			}
			return true;
		}
		
	//se adauga un turn pe Tabla
	boolean adaugaTurn(int r,int c,int culoare,Piesa[][] Tabla)
		{
			if(r < 0 || r > 7 || c < 0 || c > 7)
			{
				return false;
			}
			if(Tabla[r][c] == null)
			{
				Tabla[r][c] = new Turn(culoare,0,0);
			}
			else
			{
				return false;
			}
			return true;
		}
		
	//se adauga un nebun pe Tabla
	boolean adaugaNebun(int r, int c,int culoare,Piesa[][] Tabla)
		{
			if(r < 0 || r > 7 || c < 0 || c > 7)
			{
				return false;
			}
			if(Tabla[r][c] == null)
			{
				Tabla[r][c] = new Nebun(culoare,0,0);
			}
			else
			{
				return false;
			}
			return true;
		}
		
	//se adauga un cal pe Tabla
	boolean adaugaCal(int r,int c,int culoare,Piesa[][] Tabla)
		{
			if(r < 0 || r > 7 || c < 0 || c > 7)
			{
				return false;
			}
			if(Tabla[r][c] == null)
			{
				Tabla[r][c] = new Cal(culoare,0,0);
			}
			else
			{
				return false;
			}
			return true;
		}
		
	//se adauga un pion pe Tabla
	boolean adaugaPion(int r,int c,int culoare,Piesa[][] Tabla)
		{
			if(r < 0 || r > 7 || c < 0 || c > 7)
			{
				return false;
			}
			if(Tabla[r][c] == null)
			{
				Tabla[r][c] = new Pion(culoare,0,0);
			}
			else
			{
				return false;
			}
			return true;
		}
	
	
}