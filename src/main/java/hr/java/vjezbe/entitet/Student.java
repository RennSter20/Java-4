package hr.java.vjezbe.entitet;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;

/**
 * Klasa Student predstavlja studenta u obrazovnoj ustanovi sa svojim imenom, prezimenom, JMBAG-om kao jedinstvenom oznakom te datumom rođenja.
 */
public class Student extends Osoba {

    private String ime, prezime, jmbag;
    private LocalDate datumRodjenja;

    public Student(String ime, String prezime, String jmbag, LocalDate datumRodjenja) {
        super(ime, prezime);
        this.ime = ime;
        this.prezime = prezime;
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;
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
    public String getJmbag() {
        return jmbag;
    }
    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }
    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }
    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (!ime.equals(student.ime)) return false;
        if (!prezime.equals(student.prezime)) return false;
        if (!jmbag.equals(student.jmbag)) return false;
        return datumRodjenja.equals(student.datumRodjenja);
    }

    @Override
    public int hashCode() {
        int result = ime.hashCode();
        result = 31 * result + prezime.hashCode();
        result = 31 * result + jmbag.hashCode();
        result = 31 * result + datumRodjenja.hashCode();
        return result;
    }
}