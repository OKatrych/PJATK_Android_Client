package eu.warble.pjapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kierunek{

	@SerializedName("Studia")
	@Expose
	private Object studia;

	@SerializedName("NazwaWydzial")
	@Expose
	private String nazwaWydzial;

	@SerializedName("IdWydzial")
	@Expose
	private int idWydzial;

	@SerializedName("Specjalizacja")
	@Expose
	private Object specjalizacja;

	@SerializedName("IdKierunek")
	@Expose
	private int idKierunek;

	@SerializedName("NazwaKierunek")
	@Expose
	private String nazwaKierunek;

	@SerializedName("NazwaKierunekAng")
	@Expose
	private String nazwaKierunekAng;

	@SerializedName("NazwaWydzialAng")
	@Expose
	private String nazwaWydzialAng;

	public Object getStudia(){
		return studia;
	}

	public String getNazwaWydzial(){
		return nazwaWydzial;
	}

	public int getIdWydzial(){
		return idWydzial;
	}

	public Object getSpecjalizacja(){
		return specjalizacja;
	}

	public int getIdKierunek(){
		return idKierunek;
	}

	public String getNazwaKierunek(){
		return nazwaKierunek;
	}

	public String getNazwaKierunekAng(){
		return nazwaKierunekAng;
	}

	public String getNazwaWydzialAng(){
		return nazwaWydzialAng;
	}

	@Override
 	public String toString(){
		return 
			"Kierunek{" + 
			"studia = '" + studia + '\'' + 
			",nazwaWydzial = '" + nazwaWydzial + '\'' + 
			",idWydzial = '" + idWydzial + '\'' + 
			",specjalizacja = '" + specjalizacja + '\'' + 
			",idKierunek = '" + idKierunek + '\'' + 
			",nazwaKierunek = '" + nazwaKierunek + '\'' + 
			",nazwaKierunekAng = '" + nazwaKierunekAng + '\'' + 
			",nazwaWydzialAng = '" + nazwaWydzialAng + '\'' + 
			"}";
		}
}