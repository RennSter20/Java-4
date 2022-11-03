package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Sučelje s metodama koje se implementiraju u klasama FakultetRacunarstva i VeleucilisteJave.
 */
public interface Visokoskolska {

    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta (List<Ispit> ispiti, Integer pismeni, Integer obrana, Student student);

    /**
     * Metoda služi za izdvajanje polozenih ispita.
     * @param ispiti Lista ispita prema kojima se izdvajaju samo polozeni ispiti.
     * @return Lista polozenih ispita.
     */
    private List<Ispit> filtrirajPolozeneIspite(List<Ispit> ispiti){

        Integer broj = 0;
        List<Ispit> tempIspiti = new ArrayList<>();

        for(int i = 0;i< ispiti.size();i++){
            if(ispiti.get(i).getOcjena() > 1){
                broj++;
                tempIspiti.add(broj - 1, ispiti.get(i));
            }
        }
        return tempIspiti;
    }

    /**
     * Metoda određuje prosjek ocjena za sve ispite koji su priloženi u parametrima. U slučaju da je jedan od ispita ocjenjen s nedovoljan, prekida se obrada prosjeka ocjena.
     * @param ispiti
     * @return Prosjek ocjena na ispitima.
     * @throws NemoguceOdreditiProsjekStudentaException
     */
    default BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispiti) throws NemoguceOdreditiProsjekStudentaException {

        Integer suma = 0;
        Integer broj = 0;

        Ocjena o = Ocjena.NEDOVOLJAN;
            for(int i = 0;i<ispiti.size();i++){
                if(ispiti.get(i).getOcjena() > o.ocjena){
                    suma+= ispiti.get(i).getOcjena();
                    broj++;
                }else{
                    throw new NemoguceOdreditiProsjekStudentaException("Student " + ispiti.get(i).getStudent().getIme() + " " + ispiti.get(i).getStudent().getPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek nedovoljan (1)!");
                }
            }

        return BigDecimal.valueOf(suma/broj);
    }

    /**
     * Metoda nalazi sve ispite koje je student priložen u parametru pisao.
     * @param ispiti Ispiti po kojima se gleda da li je student pisao ispit.
     * @param student Student prema kojem se trebaju filtrirati ispiti.
     * @return Lista ispita.
     */
    default  List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispiti, Student student) {

        int brojIspita = 0;

        List<Ispit> tempIspiti = new ArrayList<>();

        for(int i = 0;i<ispiti.size();i++){
            if(ispiti.get(i).getStudent() == student){
                brojIspita++;
                tempIspiti.add(brojIspita - 1, ispiti.get(i));
            }
        }
        return tempIspiti;
    }


}
