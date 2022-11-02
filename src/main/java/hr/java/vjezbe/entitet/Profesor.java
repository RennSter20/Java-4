package hr.java.vjezbe.entitet;

/**
 * Klasa profesor predstavlja profesora u obrazovnoj ustanovi sa svojom sifrom, imenom, prezimenom i stečenom titulom.
 */
public class Profesor extends Osoba {

    private String sifra, ime, prezime, titula;

    public Profesor(String sifra, String ime, String prezime, String titula) {
        super(ime, prezime);
        this.sifra = sifra;
        this.ime = ime;
        this.prezime = prezime;
        this.titula = titula;
    }

    public static class Builder {

        private String sifra, ime, prezime, titula;

        public Profesor build(){
            Profesor prof = new Profesor();
            prof.sifra = this.sifra;
            prof.ime = this.ime;
            prof.prezime = this.prezime;
            prof.titula = this.titula;

            return prof;
        }


        public Builder withSifra(String sifra){
            this.sifra = sifra;
            return this;
        }

        public Builder withIme(String ime){
            this.ime = ime;
            return this;
        }

        public Builder withPrezime(String prezime){
            this.prezime = prezime;
            return this;
        }

        public Builder withTitula(String titula){
            this.titula = titula;
            return this;
        }
    }

    private Profesor(){}

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }
}