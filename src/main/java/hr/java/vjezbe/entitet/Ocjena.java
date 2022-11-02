package hr.java.vjezbe.entitet;

public enum Ocjena {



    NEDOVOLJAN(1, "nedovoljan"),
    DOVOLJAN(2, "dovoljan"),
    DOBAR(3, "dobar"),
    VRLODOBAR(4, "vrlo dobar"),
    IZVRSTAN(5, "izvrstan");


    Integer ocjena;
    String opis;
    Ocjena(int i, String s) {
        ocjena = i;
        opis = s;
    }

    public Integer getInteger(){
        return ocjena;
    }
    public String getOpis(){
        return opis;
    }


}
