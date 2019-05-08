package com.maistasinamus.kebabai;

public class NewEntry {
    private int id;
    private String pavadinimas;
    private String padazas;
    private String dydis;
    private String mesa;
    private double kaina;


    //kurian yrasa pries issaugant i db
    public NewEntry(String pavadinimas,String padazas,String dydis,String mesa,double kaina) {
        this.pavadinimas=pavadinimas;
        this.padazas=padazas;
        this.dydis=dydis;
        this.mesa=mesa;
        this.kaina=kaina;
    }

    //darbui su duomenu bazes yrasu
    public NewEntry(int id, String pavadinimas, String padazas, String dydis, String mesa, double kaina) {
        this.id = id;
        this.pavadinimas = pavadinimas;
        this.padazas = padazas;
        this.dydis = dydis;
        this.mesa = mesa;
        this.kaina = kaina;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public String getPadazas() {
        return padazas;
    }

    public void setPadazas(String padazas) {
        this.padazas = padazas;
    }

    public String getDydis() {
        return dydis;
    }

    public void setDydis(String dydis) {
        this.dydis = dydis;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public Double getKaina() {
        return kaina;
    }

    public void setKaina(Double kaina) {
        this.kaina = kaina;
    }







}
