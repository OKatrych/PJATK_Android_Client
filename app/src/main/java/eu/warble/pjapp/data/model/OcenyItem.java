package eu.warble.pjapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OcenyItem{

	@SerializedName("NazwaPrzedmiotuAng")
	@Expose
	private String nazwaPrzedmiotuAng;

	@SerializedName("Ocena")
	@Expose
	private String ocena;

	@SerializedName("Zaliczenie")
	@Expose
	private String zaliczenie;

	@SerializedName("NazwaPrzedmiotu")
	@Expose
	private String nazwaPrzedmiotu;

	@SerializedName("LiczbaGodzin")
	@Expose
	private int liczbaGodzin;

	@SerializedName("ZaliczenieAng")
	@Expose
	private String zaliczenieAng;

	@SerializedName("Semestr")
	@Expose
	private String semestr;

	@SerializedName("Data")
	@Expose
	private String data;

	@SerializedName("KodPrzedmiotu")
	@Expose
	private String kodPrzedmiotu;

	@SerializedName("Prowadzacy")
	@Expose
	private String prowadzacy;

	public String getNazwaPrzedmiotuAng(){
		return nazwaPrzedmiotuAng;
	}

	public String getOcena(){
		return ocena;
	}

	public String getZaliczenie(){
		return zaliczenie;
	}

	public String getNazwaPrzedmiotu(){
		return nazwaPrzedmiotu;
	}

	public int getLiczbaGodzin(){
		return liczbaGodzin;
	}

	public String getZaliczenieAng(){
		return zaliczenieAng;
	}

	public String getSemestr(){
		return semestr;
	}

	public String getData(){
		return data;
	}

	public String getKodPrzedmiotu(){
		return kodPrzedmiotu;
	}

	public String getProwadzacy(){
		return prowadzacy;
	}

	@Override
 	public String toString(){
		return 
			"OcenyItem{" + 
			"nazwaPrzedmiotuAng = '" + nazwaPrzedmiotuAng + '\'' + 
			",ocena = '" + ocena + '\'' + 
			",zaliczenie = '" + zaliczenie + '\'' + 
			",nazwaPrzedmiotu = '" + nazwaPrzedmiotu + '\'' + 
			",liczbaGodzin = '" + liczbaGodzin + '\'' + 
			",zaliczenieAng = '" + zaliczenieAng + '\'' + 
			",semestr = '" + semestr + '\'' + 
			",data = '" + data + '\'' + 
			",kodPrzedmiotu = '" + kodPrzedmiotu + '\'' + 
			",prowadzacy = '" + prowadzacy + '\'' + 
			"}";
		}
}