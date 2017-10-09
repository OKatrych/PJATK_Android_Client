package eu.warble.pjapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrupyItem{

	@SerializedName("Nazwa")
	@Expose
	private String nazwa;

	public String getNazwa(){
		return nazwa;
	}

	@Override
 	public String toString(){
		return nazwa;
		}
}