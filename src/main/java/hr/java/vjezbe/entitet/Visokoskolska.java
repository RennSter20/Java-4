package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.util.Arrays;

public interface Visokoskolska {

    //KORISTI FILTRIRAJ ISPITE PO STUDENTU
    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta (Ispit[] ispiti, Integer pismeni, Integer obrana, Student student);

    //DONE
    private Ispit[] filtrirajPolozeneIspite(Ispit[] ispiti){

        Integer broj = 0;
        Ispit[] tempIspiti = new Ispit[broj];

        for(int i = 0;i< ispiti.length;i++){
            if(ispiti[i].getOcjena() > 1){
                broj++;
                tempIspiti = Arrays.copyOf(tempIspiti, broj);
                tempIspiti[broj-1] = ispiti[i];
            }
        }
        return tempIspiti;
    }

    //DONE
    default BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispiti) throws NemoguceOdreditiProsjekStudentaException {

        Integer suma = 0;
        Integer broj = 0;

            for(int i = 0;i<ispiti.length;i++){
                if(ispiti[i].getOcjena() > 1){
                    suma+= ispiti[i].getOcjena();
                    broj++;
                }else{
                    throw new NemoguceOdreditiProsjekStudentaException("Student " + ispiti[i].getStudent().getIme() + " " + ispiti[i].getStudent().getPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek nedovoljan (1)!");
                }
            }

        return BigDecimal.valueOf(suma/broj);
    }

    //DONE
    default  Ispit[] filtrirajIspitePoStudentu(Ispit[] ispiti, Student student) {

        int brojIspita = 0;

        Ispit[] tempIspiti = new Ispit[brojIspita];
        for(int i = 0;i<ispiti.length;i++){
            if(ispiti[i].getStudent() == student){
                brojIspita++;
                tempIspiti = Arrays.copyOf(tempIspiti, brojIspita);
                tempIspiti[brojIspita-1] = ispiti[i];
            }
        }
        return tempIspiti;
    }


}
