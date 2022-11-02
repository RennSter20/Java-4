package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska{

    public static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public VeleucilisteJave(String naziv, List<Predmet> predmeti, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

    //DONE
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti, Integer pismeni, Integer obrana, Student student) {


            BigDecimal prosjekOcjenaNaIspitima = null;

            try{
                prosjekOcjenaNaIspitima = odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(ispiti, student));

            }catch (NemoguceOdreditiProsjekStudentaException e){
                System.out.println(e.getMessage());
                logger.error(String.valueOf(e.getCause()));
                return null;
            }

           return (prosjekOcjenaNaIspitima.multiply(BigDecimal.valueOf(2)).add(BigDecimal.valueOf(obrana).add(BigDecimal.valueOf(pismeni)))).divide(BigDecimal.valueOf(4));
    }

    //DONE
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {

        Integer indexStudenta = 0;
        BigDecimal najboljiProsjek = BigDecimal.valueOf(0);

        for(int i = 0;i< getIspiti().size();i++){

            BigDecimal temp = null;

            try{
                temp = odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(getIspiti(), getStudenti().get(i)));
            }catch(NemoguceOdreditiProsjekStudentaException e){
                System.out.println(e.getMessage());
            }
            if(temp.compareTo(najboljiProsjek) >= 0){
                indexStudenta = i;
                najboljiProsjek = temp;
            }
        }

        return getStudenti().get(indexStudenta);

    }
}
