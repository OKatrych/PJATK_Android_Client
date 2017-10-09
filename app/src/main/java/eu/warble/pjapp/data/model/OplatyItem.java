package eu.warble.pjapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OplatyItem{

	@SerializedName("TerminPlatnosci")
	@Expose
	private String terminPlatnosci;

	@SerializedName("LiczbaRat")
	@Expose
	private int liczbaRat;

	@SerializedName("Kwota")
	@Expose
	private double kwota;

	@SerializedName("Nazwa")
	@Expose
	private String nazwa;

	@SerializedName("NrRaty")
	@Expose
	private int nrRaty;

	public String getTerminPlatnosci(){
		return terminPlatnosci;
	}

	public int getLiczbaRat(){
		return liczbaRat;
	}

	public double getKwota(){
		return kwota;
	}

	public String getNazwa(){
		return nazwa;
	}

	public int getNrRaty(){
		return nrRaty;
	}

	@Override
 	public String toString(){
		return 
			"OplatyItem{" + 
			"terminPlatnosci = '" + terminPlatnosci + '\'' + 
			",liczbaRat = '" + liczbaRat + '\'' + 
			",kwota = '" + kwota + '\'' + 
			",nazwa = '" + nazwa + '\'' + 
			",nrRaty = '" + nrRaty + '\'' + 
			"}";
		}
}