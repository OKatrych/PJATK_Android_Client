package eu.warble.pjapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZajeciaItem{

	@SerializedName("Budynek")
	@Expose
	private String budynek;

	@SerializedName("Kod")
	@Expose
	private String kod;

	@SerializedName("Nazwa_sali")
	@Expose
	private String nazwaSali;

	@SerializedName("TypZajec")
	@Expose
	private String typZajec;

	@SerializedName("Nazwa")
	@Expose
	private String nazwa;

	@SerializedName("Data_roz")
	@Expose
	private String dataRoz;

	@SerializedName("idRealizacja_zajec")
	@Expose
	private int idRealizacjaZajec;

	@SerializedName("Data_zak")
	@Expose
	private String dataZak;

	public String getBudynek(){
		return budynek;
	}

	public String getKod(){
		return kod;
	}

	public String getNazwaSali(){
		return nazwaSali;
	}

	public String getTypZajec(){
		return typZajec;
	}

	public String getNazwa(){
		return nazwa;
	}

	public String getDataRoz(){
		return dataRoz;
	}

	public int getIdRealizacjaZajec(){
		return idRealizacjaZajec;
	}

	public String getDataZak(){
		return dataZak;
	}

	@Override
 	public String toString(){
		return 
			"ZajeciaItem{" + 
			"budynek = '" + budynek + '\'' + 
			",kod = '" + kod + '\'' + 
			",nazwa_sali = '" + nazwaSali + '\'' + 
			",typZajec = '" + typZajec + '\'' + 
			",nazwa = '" + nazwa + '\'' + 
			",data_roz = '" + dataRoz + '\'' + 
			",idRealizacja_zajec = '" + idRealizacjaZajec + '\'' + 
			",data_zak = '" + dataZak + '\'' + 
			"}";
		}
}