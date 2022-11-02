package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmadjihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;

public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski {

    public static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public FakultetRacunarstva(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti, Ispit[] ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

    //DONE
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, Integer pismeni, Integer diplomski, Student student) {

        Ispit[] ispitiStudenta = filtrirajIspitePoStudentu(ispiti, student);
        BigDecimal prosjekOcjenaNaIspitima = null;

        try{

            prosjekOcjenaNaIspitima = odrediProsjekOcjenaNaIspitima(ispitiStudenta);

        }catch(NemoguceOdreditiProsjekStudentaException e){
            System.out.println(e.getMessage());
            logger.error(String.valueOf(e.getCause()));
            return null;
        }


        return (prosjekOcjenaNaIspitima.multiply(BigDecimal.valueOf(3)).add(BigDecimal.valueOf(diplomski).add(BigDecimal.valueOf(pismeni)))).divide(BigDecimal.valueOf(5));

    }

    //DONE
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {

        Integer[] brojIzvrsnihOcjena = new Integer[getStudenti().length];
        for(int i = 0;i< getIspiti().length;i++) brojIzvrsnihOcjena[i] = 0;


        for(Ispit ispit : getIspiti()){
            for(int i = 0;i< getStudenti().length;i++){
                if(ispit.getStudent() == getStudenti()[i] && ispit.getOcjena() == 5){
                    brojIzvrsnihOcjena[i]++;
                }
            }
        }

        int lastIndex = 0;
        for(int i = brojIzvrsnihOcjena.length-1;i>-1;i--){
            if(brojIzvrsnihOcjena[i] == 5){
                lastIndex = i;
            }
        }

        return getStudenti()[lastIndex];
    }

    //DONE
    @Override
    public Student odrediStudentaZaRektorovuNagradu() throws PostojiViseNajmadjihStudenataException {

        BigDecimal[] prosjeci = new BigDecimal[getStudenti().length];


        for(int i = 0;i< getStudenti().length;i++){
            try{
                prosjeci[i] = odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(getIspiti(), getStudenti()[i]));
            }catch(NemoguceOdreditiProsjekStudentaException e){
                System.out.println(e.getMessage());

            }
        }

        Integer najbolji = 0;
        for(int i = 1;i< getStudenti().length;i++){
            if(prosjeci[i].compareTo(prosjeci[najbolji]) > 0){
                najbolji = i;
            }else if(prosjeci[i].compareTo(prosjeci[najbolji]) == 0){
                if(getStudenti()[i].getDatumRodjenja().isAfter(getStudenti()[i].getDatumRodjenja())){
                    najbolji = i;
                }
            }
        }

        Integer brojIstihStudenata = 0;
        Student[] studentiIstogProsjekaIRodjendana = new Student[brojIstihStudenata];

        for(int i = 0;i<getStudenti().length;i++){
            if(prosjeci[i].equals(prosjeci[najbolji]) && getStudenti()[i].getDatumRodjenja().isEqual(getStudenti()[i].getDatumRodjenja())){
                brojIstihStudenata++;
                studentiIstogProsjekaIRodjendana = Arrays.copyOf(studentiIstogProsjekaIRodjendana, brojIstihStudenata);
                studentiIstogProsjekaIRodjendana[brojIstihStudenata - 1] = getStudenti()[i];
            }
        }

        if(brojIstihStudenata > 0){
            String studentiZaIspisati = "";
            for(int i = 0;i<brojIstihStudenata;i++){
                if(i == brojIstihStudenata - 1){
                    studentiZaIspisati += " i ";
                }
                /*Error*/studentiZaIspisati += studentiIstogProsjekaIRodjendana[i].getIme() + " " + studentiIstogProsjekaIRodjendana[i].getPrezime() + " ";

            }
            throw new PostojiViseNajmadjihStudenataException("Pronadeno je vise najmladih studenata s istim datumom rodenja, a to su " + studentiZaIspisati);

        }
        return getStudenti()[najbolji];

    }
}
