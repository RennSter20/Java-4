package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface Visokoskolska {

    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta (List<Ispit> ispiti, Integer pismeni, Integer obrana, Student student);

    //DONE
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

    //DONE
    default BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispiti) throws NemoguceOdreditiProsjekStudentaException {

        Integer suma = 0;
        Integer broj = 0;

            for(int i = 0;i<ispiti.size();i++){
                if(ispiti.get(i).getOcjena() > 1){
                    suma+= ispiti.get(i).getOcjena();
                    broj++;
                }else{
                    throw new NemoguceOdreditiProsjekStudentaException("Student " + ispiti.get(i).getStudent().getIme() + " " + ispiti.get(i).getStudent().getPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek nedovoljan (1)!");
                }
            }

        return BigDecimal.valueOf(suma/broj);
    }

    //DONE
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
