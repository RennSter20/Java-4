package hr.java.vjezbe.entitet;

import java.util.List;

/**
 * Klasa predmet sadrži sve informacije o predmetu, od šifre, naziva, nositelja te svih studenata koji su upisani na taj predmet. Ti studenti se određuju tako da se pridodaju predmetu jedino ako
 * su pisali jedan ispit iz tog predmeta.
 */
public class Predmet {

    private String sifra, naziv;
    private Integer brojEctsBodova;
    private Profesor nositelj;
    private List<Student> studenti;

    public Predmet(String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj, List<Student> studenti) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.studenti = studenti;
    }

    public static class PredmetBuilder {
        private String sifra;
        private String naziv;
        private Integer brojEctsBodova;
        private Profesor nositelj;
        private List<Student> studenti;

        public PredmetBuilder setSifra(String sifra) {
            this.sifra = sifra;
            return this;
        }

        public PredmetBuilder setNaziv(String naziv) {
            this.naziv = naziv;
            return this;
        }

        public PredmetBuilder setBrojEctsBodova(Integer brojEctsBodova) {
            this.brojEctsBodova = brojEctsBodova;
            return this;
        }

        public PredmetBuilder setNositelj(Profesor nositelj) {
            this.nositelj = nositelj;
            return this;
        }

        public PredmetBuilder setStudenti(List<Student> studenti) {
            this.studenti = studenti;
            return this;
        }

        public Predmet createPredmet() {
            return new Predmet(sifra, naziv, brojEctsBodova, nositelj, studenti);
        }
    }


    public String getSifra() {
        return sifra;
    }
    public void setSifra(String sifra) {
        this.sifra = sifra;
    }
    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public Integer getBrojEctsBodova() {
        return brojEctsBodova;
    }
    public void setBrojEctsBodova(Integer brojEctsBodova) {
        this.brojEctsBodova = brojEctsBodova;
    }
    public Profesor getNositelj() {
        return nositelj;
    }
    public void setNositelj(Profesor nositelj) {
        this.nositelj = nositelj;
    }
    public List<Student> getStudenti() {
        return studenti;
    }
    public void setStudenti(List<Student> studenti) {
        this.studenti = studenti;
    }
}