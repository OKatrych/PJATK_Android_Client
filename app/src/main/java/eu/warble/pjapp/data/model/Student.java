package eu.warble.pjapp.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("Imie")
    @Expose
    private String imie;

    @SerializedName("Kwota_wplat")
    @Expose
    private double kwotaWplat;

    @SerializedName("Kwota_wyplat")
    @Expose
    private double kwotaWyplat;

    @SerializedName("Zajecia")
    @Expose
    private List<ZajeciaItem> zajecia;

    @SerializedName("Login")
    @Expose
    private String login;

    @SerializedName("Grupy")
    @Expose
    private List<GrupyItem> grupy;

    @SerializedName("Konto_wyplat")
    @Expose
    private String kontoWyplat;

    @SerializedName("Kwota_naleznosci")
    @Expose
    private double kwotaNaleznosci;

    @SerializedName("Oplaty")
    @Expose
    private List<OplatyItem> oplaty;

    @SerializedName("Studia")
    @Expose
    private List<StudiaItem> studia;

    @SerializedName("Konto_wplat")
    @Expose
    private String kontoWplat;

    @SerializedName("Saldo")
    @Expose
    private double saldo;

    @SerializedName("Nazwisko")
    @Expose
    private String nazwisko;

    @SerializedName("Oceny")
    @Expose
    private List<OcenyItem> oceny;

    @SerializedName("Platnosci")
    @Expose
    private List<PlatnosciItem> platnosci;

    @SerializedName("Wyplaty")
    @Expose
    private List<Object> wyplaty;

    public String getStatus(){
        return status;
    }

    public String getImie(){
        return imie;
    }

    public double getKwotaWplat(){
        return kwotaWplat;
    }

    public double getKwotaWyplat(){
        return kwotaWyplat;
    }

    public List<ZajeciaItem> getZajecia(){
        return zajecia;
    }

    public String getLogin(){
        return login;
    }

    public List<GrupyItem> getGrupy(){
        return grupy;
    }

    public String getKontoWyplat(){
        return kontoWyplat;
    }

    public double getKwotaNaleznosci(){
        return kwotaNaleznosci;
    }

    public List<OplatyItem> getOplaty(){
        return oplaty;
    }

    public List<StudiaItem> getStudia(){
        return studia;
    }

    public String getKontoWplat(){
        return kontoWplat;
    }

    public double getSaldo(){
        return saldo;
    }

    public String getNazwisko(){
        return nazwisko;
    }

    public List<OcenyItem> getOceny(){
        return oceny;
    }

    public List<PlatnosciItem> getPlatnosci(){
        return platnosci;
    }

    public List<Object> getWyplaty(){
        return wyplaty;
    }

    @Override
    public String toString(){
        return
                "Student{" +
                        "status = '" + status + '\'' +
                        ",imie = '" + imie + '\'' +
                        ",kwota_wplat = '" + kwotaWplat + '\'' +
                        ",kwota_wyplat = '" + kwotaWyplat + '\'' +
                        ",zajecia = '" + zajecia + '\'' +
                        ",login = '" + login + '\'' +
                        ",grupy = '" + grupy + '\'' +
                        ",konto_wyplat = '" + kontoWyplat + '\'' +
                        ",kwota_naleznosci = '" + kwotaNaleznosci + '\'' +
                        ",oplaty = '" + oplaty + '\'' +
                        ",studia = '" + studia + '\'' +
                        ",konto_wplat = '" + kontoWplat + '\'' +
                        ",saldo = '" + saldo + '\'' +
                        ",nazwisko = '" + nazwisko + '\'' +
                        ",oceny = '" + oceny + '\'' +
                        ",platnosci = '" + platnosci + '\'' +
                        ",wyplaty = '" + wyplaty + '\'' +
                        "}";
    }
}
