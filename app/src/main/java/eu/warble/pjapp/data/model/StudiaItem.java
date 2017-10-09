package eu.warble.pjapp.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudiaItem{

	@SerializedName("RokStudiow")
	@Expose
	private int rokStudiow;

	@SerializedName("Kierunek")
	@Expose
	private Kierunek kierunek;

	@SerializedName("Nazwa")
	@Expose
	private String nazwa;

	@SerializedName("SredniaSem")
	@Expose
	private double sredniaSem;

	@SerializedName("Oceny")
	@Expose
	private List<OcenyItem> oceny;

	@SerializedName("Specjalizacja")
	@Expose
	private String specjalizacja;

	@SerializedName("SredniaStudia")
	@Expose
	private double sredniaStudia;

	@SerializedName("IdWpisNaSemestr")
	@Expose
	private int idWpisNaSemestr;

	@SerializedName("SemestrStudiow")
	@Expose
	private int semestrStudiow;

	@SerializedName("SredniaRok")
	@Expose
	private double sredniaRok;

	public int getRokStudiow(){
		return rokStudiow;
	}

	public Kierunek getKierunek(){
		return kierunek;
	}

	public String getNazwa(){
		return nazwa;
	}

	public double getSredniaSem(){
		return sredniaSem;
	}

	public List<OcenyItem> getOceny(){
		return oceny;
	}

	public String getSpecjalizacja(){
		return specjalizacja;
	}

	public double getSredniaStudia(){
		return sredniaStudia;
	}

	public int getIdWpisNaSemestr(){
		return idWpisNaSemestr;
	}

	public int getSemestrStudiow(){
		return semestrStudiow;
	}

	public double getSredniaRok(){
		return sredniaRok;
	}

	@Override
 	public String toString(){
		return 
			"StudiaItem{" + 
			"rokStudiow = '" + rokStudiow + '\'' + 
			",kierunek = '" + kierunek + '\'' + 
			",nazwa = '" + nazwa + '\'' + 
			",sredniaSem = '" + sredniaSem + '\'' + 
			",oceny = '" + oceny + '\'' + 
			",specjalizacja = '" + specjalizacja + '\'' + 
			",sredniaStudia = '" + sredniaStudia + '\'' + 
			",idWpisNaSemestr = '" + idWpisNaSemestr + '\'' + 
			",semestrStudiow = '" + semestrStudiow + '\'' + 
			",sredniaRok = '" + sredniaRok + '\'' + 
			"}";
		}
}