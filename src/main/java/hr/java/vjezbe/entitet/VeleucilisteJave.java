package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 *  * Klasa VeleucilisteJave nasljeđuje ObrazovnaUstanova i implementira sučelje Visokoskolska.
 *  * Unutar klase implementirane su metode koje se koriste tijekom izvođenja programa u svrhu
 *  * određivanja konačnih ocjena i najuspješnijeg studenta.
 */
public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska{

    public static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public VeleucilisteJave(String naziv, List<Predmet> predmeti, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }


    /**
     * Metoda određuje konačnu ocjenu za studenta prema filtriranim ispitima po studentu, ocjeni iz pismenog dijela te obrani rada. Konačna ocjena se izračunava
     * uz formulu: konačna ocjena = (2 * prosjek ocjena studenta + ocjena završnog rada + ocjena obrane završnog rada) / 4.
     * @param ispiti
     * @param pismeni
     * @param obrana
     * @param student
     * @return
     */
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

    /**
     * Metoda određuje najuspješnijeg studenta na godini na način da se odabere student s najboljim prosjekom.
     * Ako više studenata ima isti najveći prosjek, izabire se student koji je posljednji po redu u listi.
     * @param godina Parametar metode koji određiva za koju godinu će se odrediti najuspjesniji student.
     * @return Najuspješniji student.
     */
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
