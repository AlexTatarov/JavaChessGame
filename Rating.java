import java.util.ArrayList;

public class Rating {
	//TABLA POZITIONARII PIONILOR
	static int pozitiePion [][] = {
			{ 0,  0,  0,  0,  0,  0,  0,  0},
	        {50, 50, 50, 50, 50, 50, 50, 50},
	        {10, 10, 20, 30, 30, 20, 10, 10},
	        { 5,  5, 10, 25, 25, 10,  5,  5},
	        { 0,  0,  0, 20, 20,  0,  0,  0},
	        { 5, -5,-10,  0,  0,-10, -5,  5},
	        { 5, 10, 10,-20,-20, 10, 10,  5},
	        { 0,  0,  0,  0,  0,  0,  0,  0}};	
	static int pozitiePionNegru [][] = {
			{ 0,  0,  0,  0,  0,  0,  0,  0},
			{ 5, 10, 10,-20,-20, 10, 10,  5},
			{ 5, -5,-10,  0,  0,-10, -5,  5},
			{ 0,  0,  0, 20, 20,  0,  0,  0},
			{ 5,  5, 10, 25, 25, 10,  5,  5},
			{10, 10, 20, 30, 30, 20, 10, 10},
			{50, 50, 50, 50, 50, 50, 50, 50},
			{ 0,  0,  0,  0,  0,  0,  0,  0}};
	static int pozitieTurn [][] = {
			{ 0,  0,  0,  0,  0,  0,  0,  0},
	        { 5, 10, 10, 10, 10, 10, 10,  5},
	        {-5,  0,  0,  0,  0,  0,  0, -5},
	        {-5,  0,  0,  0,  0,  0,  0, -5},
	        {-5,  0,  0,  0,  0,  0,  0, -5},
	        {-5,  0,  0,  0,  0,  0,  0, -5},
	        {-5,  0,  0,  0,  0,  0,  0, -5},
	        { 0,  0,  0,  5,  5,  0,  0,  0}};
	static int pozitieTurnNegru [][] = {
			{ 0,  0,  0,  5,  5,  0,  0,  0},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{ 5, 10, 10, 10, 10, 10, 10,  5},
			{ 0,  0,  0,  0,  0,  0,  0,  0}};
	static int pozitieNebun [][] = {
			{-20,-10,-10,-10,-10,-10,-10,-20},
	        {-10,  0,  0,  0,  0,  0,  0,-10},
	        {-10,  0,  5, 10, 10,  5,  0,-10},
	        {-10,  5,  5, 10, 10,  5,  5,-10},
	        {-10,  0, 10, 10, 10, 10,  0,-10},
	        {-10, 10, 10, 10, 10, 10, 10,-10},
	        {-10,  5,  0,  0,  0,  0,  5,-10},
	        {-20,-10,-10,-10,-10,-10,-10,-20}};
	static int pozitieNebunNegru [][] = {
			{-20,-10,-10,-10,-10,-10,-10,-20},
			{-10,  5,  0,  0,  0,  0,  5,-10},
			{-10, 10, 10, 10, 10, 10, 10,-10},
			{-10,  0, 10, 10, 10, 10,  0,-10},
			{-10,  5,  5, 10, 10,  5,  5,-10},
			{-10,  0,  5, 10, 10,  5,  0,-10},
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-20,-10,-10,-10,-10,-10,-10,-20}};
	static int pozitieCal [][] = {
			{-50,-40,-30,-30,-30,-30,-40,-50},
	        {-40,-20,  0,  0,  0,  0,-20,-40},
	        {-30,  0, 10, 15, 15, 10,  0,-30},
	        {-30,  5, 15, 20, 20, 15,  5,-30},
	        {-30,  0, 15, 20, 20, 15,  0,-30},
	        {-30,  5, 10, 15, 15, 10,  5,-30},
	        {-40,-20,  0,  5,  5,  0,-20,-40},
	        {-50,-40,-30,-30,-30,-30,-40,-50}};
	static int pozitieRegina [][] = {
			{-20,-10,-10, -5, -5,-10,-10,-20},
	        {-10,  0,  0,  0,  0,  0,  0,-10},
	        {-10,  0,  5,  5,  5,  5,  0,-10},
	        { -5,  0,  5,  5,  5,  5,  0, -5},
	        {  0,  0,  5,  5,  5,  5,  0, -5},
	        {-10,  5,  5,  5,  5,  5,  0,-10},
	        {-10,  0,  5,  0,  0,  0,  0,-10},
	        {-20,-10,-10, -5, -5,-10,-10,-20}};
	static int pozitieRegeInceput [][] = {
			{-30,-40,-40,-50,-50,-40,-40,-30},
	        {-30,-40,-40,-50,-50,-40,-40,-30},
	        {-30,-40,-40,-50,-50,-40,-40,-30},
	        {-30,-40,-40,-50,-50,-40,-40,-30},
	        {-20,-30,-30,-40,-40,-30,-30,-20},
	        {-10,-20,-20,-20,-20,-20,-20,-10},
	        { 20, 20,  0,  0,  0,  0, 20, 20},
	        { 20, 30, 10,  0,  0, 10, 30, 20}};
	static int pozitieRegeSfarsit [][] = {
			{-50,-40,-30,-20,-20,-30,-40,-50},
	        {-30,-20,-10,  0,  0,-10,-20,-30},
	        {-30,-10, 20, 30, 30, 20,-10,-30},
	        {-30,-10, 30, 40, 40, 30,-10,-30},
	        {-30,-10, 30, 40, 40, 30,-10,-30},
	        {-30,-10, 20, 30, 30, 20,-10,-30},
	        {-30,-30,  0,  0,  0,  0,-30,-30},
	        {-50,-30,-30,-30,-30,-30,-30,-50}
	};
	
	static int evaluare(Piesa[][] Tabla,int cul,ArrayList<String> miscariPosibile,ArrayList<String> miscariPosibileAdversar,int adancime)
	{
		int scor = 0;
		int material;
		scor += evaluareMaterial(Tabla,cul);
		material = scor;
		scor += evaluareMiscari(Tabla,cul,miscariPosibile,miscariPosibileAdversar);
		scor += evaluarePozitie(Tabla,cul,material);
		scor += evaluareAtac(Tabla,cul,miscariPosibileAdversar,adancime);
		return scor;
	}
	static int evaluareMaterial(Piesa[][] Tabla, int cul)
	{
		int scor = 0;
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				if(Tabla[i][j] != null && Tabla[i][j].culoare == cul)
				{
					switch(Tabla[i][j].cod)
					{
					case 1 : //AVEM UN PION
						scor+=100;
						break;
					case 2 : //AVEM UN NEBUN
						scor+=310;
						break;
					case 3 : //AVEM UN CAL
						scor+=300;
						break; 
					case 5 : //AVEM UN TURN
						scor+=500;
						break;
					case 8 : //AVEM O REGINA
						scor+=900;
						break;
					case 9 : //AVEM UN REGE
						scor+=10000;
						break;
					default : 
						break;
					}
				}
				 
			}
		}
		//System.out.println("MATERIALUL S-A EVALUAT LA VALOAREA "+scor);
		return scor;
	}
	
	static int evaluareMiscari(Piesa[][] Tabla,int culoare,ArrayList<String> miscariPosibile,ArrayList<String> miscariPosibileAdversar)
	{
		int scor = 0;
			scor += miscariPosibile.size()*5;
			scor -= miscariPosibileAdversar.size()*5;
		//System.out.println("MISCARILE S-AU EVALUAT LA VALOAREA "+scor);
		return scor;
	}
	
	static int evaluarePozitie(Piesa[][] Tabla,int cul,int material)
	{
		int scor = 0;
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				if(Tabla[i][j] != null && Tabla[i][j].culoare == cul)
				{
					switch(Tabla[i][j].cod)
					{
					case 1 : 
						if(cul == 1)
						{
							scor += pozitiePion[7-i][7-j];
						}
						else
						{
							scor += pozitiePion[i][j];
						}
						break;
					case 2 : 
						if(cul == 1)
						{
							scor += pozitieNebun[7-i][7-j];
						}
						else
						{
							scor += pozitieNebun[i][j];
						}
						break;
					case 3 :
						if(cul == 1)
						{
							scor += pozitieCal[7-i][7-j];
						}
						else
						{
							scor += pozitieCal[i][j];
						}
						break;
					case 5 :
						if(cul == 1)
						{
							scor += pozitieTurn[7-i][7-j];
						}
						else
						{
							scor += pozitieTurn[i][j];
						}
						break;
					case 9 :
						if(cul == 1)
						{
							scor += pozitieRegina[7-i][7-j];
						}
						else
						{
							scor += pozitieRegina[i][j];
						}
						break;
					case 8 :
						
						if(material > 2000)
						{
							if(cul == 1)
							{
								scor += pozitieRegeInceput[7-i][7-j];
							}
							else
							{
								scor += pozitieRegeInceput[i][j];
							}
							
						}
						else
						{
							if(cul == 1)
							{
								scor += pozitieRegeSfarsit[7-i][7-j];
							}
							else
							{
								scor += pozitieRegeSfarsit[i][j];
							}
						}
						break;
					default :
						break;
					}
				}
			}
		}
		//System.out.println("POZITIA S-A EVALUAT LA VALOAREA "+scor);
		return scor;
	}
	
	static int evaluareAtac(Piesa[][] Tabla,int cul,ArrayList<String> miscariPosibileAdversar,int adancime)
	{
		int scor = 0;
		boolean regeAdversAtacat = false;
		if(cul == 0 )
		{
			if(!AI.regeNeAtacat(AI.xRegeNegru,AI.yRegeNegru,Tabla))
			{
				regeAdversAtacat = true;
			}
		}
		else
		{
			if(!AI.regeNeAtacat(AI.xRegeAlb,AI.yRegeAlb,Tabla))
			{
				regeAdversAtacat = true;
			}
		}
		if(regeAdversAtacat && miscariPosibileAdversar.size() == 0)
		{
			scor = 20000;
		}
		else if(regeAdversAtacat && miscariPosibileAdversar.size() != 0)
		{
			scor = 500;
		}
		//System.out.println("ATACUL S-A EVALUAT LA VALOAREA "+scor);
		return scor;
	}
	
	
}
