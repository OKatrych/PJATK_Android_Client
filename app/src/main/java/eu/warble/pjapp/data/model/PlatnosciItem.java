package eu.warble.pjapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlatnosciItem{

	@SerializedName("Wplacajacy")
	@Expose
	private String wplacajacy;

	@SerializedName("Kwota")
	@Expose
	private double kwota;

	@SerializedName("TytulWplaty")
	@Expose
	private String tytulWplaty;

	@SerializedName("DataWplaty")
	@Expose
	private String dataWplaty;

	public String getWplacajacy(){
		return wplacajacy;
	}

	public double getKwota(){
		return kwota;
	}

	public String getTytulWplaty(){
		return tytulWplaty;
	}

	public String getDataWplaty(){
		return dataWplaty;
	}

	@Override
 	public String toString(){
		return 
			"PlatnosciItem{" + 
			"wplacajacy = '" + wplacajacy + '\'' + 
			",kwota = '" + kwota + '\'' + 
			",tytulWplaty = '" + tytulWplaty + '\'' + 
			",dataWplaty = '" + dataWplaty + '\'' + 
			"}";
		}
}