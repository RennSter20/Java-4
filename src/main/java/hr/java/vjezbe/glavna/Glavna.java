package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.PostojiViseNajmadjihStudenataException;
import hr.java.vjezbe.sortiranje.StudentSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Glavna {

    public static final int BROJ_PROFESORA = 2;
    public static final int BROJ_PREDMETA = 2;
    public static final int BROJ_STUDENTA = 2;
    public static final int BROJ_ISPITA = 2;

    public static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    static Student unosStudent(Scanner unos, Integer redniBroj){

        System.out.println("Unesite " + (redniBroj + 1) + ". studenta: ");
        System.out.print("Unesite ime studenta: ");
        String tempIme = unos.nextLine();

        System.out.print("Unesite prezime studenta: ");
        String tempPrezime = unos.nextLine();

        System.out.print("Unesite datum rodenja studenta " + tempIme + " " + tempPrezime + " u formatu (dd.MM.yyyy.): ");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        LocalDate tempDatum = LocalDate.parse(unos.nextLine(), dateFormat);

        System.out.print("Unesite JMBAG studenta: "+ tempIme + " " + tempPrezime + ": ");
        String tempJMBAG = unos.nextLine();

        return new Student(tempIme, tempPrezime, tempJMBAG, tempDatum);


    }
    static Profesor unosProfesor(Scanner unos, Integer redniBroj){

        System.out.println("Unesite " + (redniBroj+1) + ". profesora: ");

        System.out.print("Unesite šifru profesora: ");
        String tempSifra = unos.nextLine();

        System.out.print("Unesite ime profesora: ");
        String tempIme = unos.nextLine();

        System.out.print("Unesite prezime profesora: ");
        String tempPrezime = unos.nextLine();

        System.out.print("Unesite tituli profesora: ");
        String tempTitula = unos.nextLine();

        return new Profesor.Builder().withIme(tempIme).withPrezime(tempPrezime).withSifra(tempSifra).withTitula(tempTitula).build();
    }
    static List<Predmet> unosPredmet(Scanner unos, List<Profesor> profesori){

        List<String> tempSifra = new ArrayList<>();
        List<String> tempNaziv = new ArrayList<>();
        List<Integer> tempECTS = new ArrayList<>();
        List<Integer> tempOdabirProfesora = new ArrayList();

        boolean nastaviPetlju = false;

        for(int i = 0;i<BROJ_PREDMETA;i++){
            System.out.println("Unesite "+ (i+1) + ". predmet: ");
            System.out.print("Unesite sifru predmeta: ");
            tempSifra.add(i, unos.nextLine());

            System.out.print("Unesite naziv predmeta: ");
            tempNaziv.add(i, unos.nextLine());

            do{

                try{
                    System.out.print("Unesite broj ECTS bodova za predmet '" + tempNaziv.get(i) + "': ");
                    tempECTS.add(i, unos.nextInt());
                    if(tempECTS.get(i) < 1){
                        nastaviPetlju = true;
                        System.out.println("Unesen je neispravan broj ECTS bodova za predmet!");
                    }else{
                        nastaviPetlju = false;
                    }
                    unos.nextLine();
                }catch (InputMismatchException e){
                    System.out.println("Neispravan unos!");
                    logger.error(String.valueOf(e), e.fillInStackTrace());
                    nastaviPetlju = true;
                    unos.nextLine();
                }

            }while(nastaviPetlju);


            nastaviPetlju = false;
            do{
                try{

                    System.out.println("Odaberite profesora: ");
                    for(int j = 0;j<BROJ_PROFESORA;j++){
                        System.out.println((j+1) + ". " + profesori.get(j).getIme() + " " + profesori.get(j).getPrezime());
                    }

                    System.out.print("Odabir >> ");
                    tempOdabirProfesora.add(i, unos.nextInt());
                    if(tempOdabirProfesora.get(i) < 1 || tempOdabirProfesora.get(i) > BROJ_PROFESORA){
                        System.out.println("Unesen je broj koji nije dodijeljen niti jednom profesoru!");
                        nastaviPetlju = true;
                    }else{
                        nastaviPetlju = false;
                    }
                    unos.nextLine();
                }catch(InputMismatchException e){
                    System.out.println("Neispravan unos!");
                    logger.error(String.valueOf(e), e.fillInStackTrace());
                    nastaviPetlju = true;
                    unos.nextLine();
                }
            }while(nastaviPetlju);



        }

        List<Predmet> predmeti = new ArrayList<>();

        for(int i = 0;i<BROJ_PREDMETA;i++){
            predmeti.add(i, new Predmet.PredmetBuilder().setSifra(tempSifra.get(i)).setNaziv(tempNaziv.get(i)).setBrojEctsBodova(tempECTS.get(i)).setNositelj(profesori.get(tempOdabirProfesora.get(i) - 1)).setStudenti(new ArrayList<>()).createPredmet());
        }

        return predmeti;
    }

    static Ispit unosIspit(Scanner unos, Integer redniBroj, List<Predmet> predmeti, List<Student> studenti){

        System.out.println("Unesite " + (redniBroj+1) + ". ispitni rok: ");
        System.out.println("Odaberite predmet: ");
        for(int i = 0;i<BROJ_PREDMETA;i++){
            System.out.println((i+1) + " " + predmeti.get(i).getNaziv());
        }

        boolean nastaviPetlju = false;
        Integer tempOdabirPredmet = null;
        do{
            try{
                System.out.print("Odabir >> ");
                tempOdabirPredmet = unos.nextInt();
                if(tempOdabirPredmet < 0 || tempOdabirPredmet > BROJ_PREDMETA){
                    System.out.println("Unesen je broj koji nije dodijeljen niti jednom predmetu!");
                    nastaviPetlju = true;
                }else{
                    nastaviPetlju = false;
                }
            }catch (InputMismatchException e){
                System.out.println("Neispravan unos!");
                logger.error(String.valueOf(e), e.fillInStackTrace());
                nastaviPetlju = true;
                unos.nextLine();
            }
        }while(nastaviPetlju);

        unos.nextLine();

        System.out.println("Unesite naziv dvorane: ");
        String tempDvorana = unos.nextLine();

        System.out.println("Unesite zgradu dvorane: ");
        String tempZgrada = unos.nextLine();

        Ispit tempI = new Ispit();
        tempI.unosSoftwarea(unos);

        System.out.println("Odaberite studenta: ");
        for(int i = 0;i<BROJ_STUDENTA;i++){
            System.out.println((i+1) + " " + studenti.get(i).getIme() + " "+ studenti.get(i).getPrezime());
        }

        Integer tempOdabirStudenta = null;
        nastaviPetlju = false;
        do{
            try{
                System.out.print("Odabir >> ");
                tempOdabirStudenta = unos.nextInt();
                unos.nextLine();
                if(tempOdabirStudenta < 1 || tempOdabirStudenta > BROJ_STUDENTA){
                    System.out.println("Unesen je broj koji nije dodijeljen niti jednom studentu!");
                    nastaviPetlju = true;
                }else{
                    nastaviPetlju = false;
                }
            }catch (InputMismatchException e){
                System.out.println("Neispravan unos!");
                logger.error(String.valueOf(e), e.fillInStackTrace());
                nastaviPetlju = true;
                unos.nextLine();
            }
        }while(nastaviPetlju);

        Integer tempOcjena = null;
        nastaviPetlju = false;
        do{
            try{
                System.out.print("Unesite ocjenu na ispitu (1-5): ");
                tempOcjena = unos.nextInt();
                unos.nextLine();
                if(tempOcjena < 1 ||tempOcjena > 5){
                    System.out.println("Niste unijeli ocjenu izedju 1 i 5!");
                    nastaviPetlju = true;
                }else{
                    nastaviPetlju = false;
                }
            }catch (InputMismatchException e){
                System.out.println("Neispravan unos!");
                logger.error(String.valueOf(e), e.fillInStackTrace());
                nastaviPetlju = true;
                unos.nextLine();
            }
        }while(nastaviPetlju);


        System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
        LocalDateTime tempDatum = LocalDate.parse(unos.nextLine(), dateFormat).atStartOfDay();

        predmeti.get(tempOdabirPredmet - 1).getStudenti().add(studenti.get(tempOdabirStudenta - 1));


        return new Ispit(predmeti.get(tempOdabirPredmet - 1), studenti.get(tempOdabirStudenta - 1), tempOcjena, tempDatum, new Dvorana(tempDvorana, tempZgrada));
    }
    public static void ispisStudenataPoKolegijima(List<Predmet> predmeti){

        StudentSorter sorter = new StudentSorter();

        for(Predmet predmet : predmeti){
            if(predmet.getStudenti().size() == 0){
                System.out.println("Nema studenata upisanih na predmet '" + predmet.getNaziv() + "'.");
            }else{
                System.out.println("Studenti upisani na predmet '" + predmet.getNaziv() + "'.");
                Collections.sort(predmet.getStudenti(), sorter);
                for(Student student : predmet.getStudenti()){
                    System.out.println(student.getPrezime() + " " + student.getIme());
                }
            }
        }

    }
    static ObrazovnaUstanova unosUstanove(Scanner unos){

        List<Profesor> profesori = new ArrayList<>();
        List<Predmet> predmeti = new ArrayList<>();
        List<Student> studenti = new ArrayList<>();
        List<Ispit> ispiti = new ArrayList<>();


        for(int i = 0;i<BROJ_PROFESORA;i++){
            profesori.add(i, unosProfesor(unos, i));
        }

        predmeti = unosPredmet(unos, profesori);

        for(int i = 0;i<BROJ_STUDENTA;i++){
            studenti.add(i, unosStudent(unos, i));
        }

        for(int i = 0;i<BROJ_ISPITA;i++){
            ispiti.add(i, unosIspit(unos, i, predmeti, studenti));
        }


        List<Student> izvrsniStudenti = new ArrayList<>();

        Integer brojIzvrsnihStudenata = 0;
        for(int i = 0;i<BROJ_ISPITA;i++){
            if(ispiti.get(i).getOcjena().equals(5)){
                brojIzvrsnihStudenata++;
                izvrsniStudenti.add(brojIzvrsnihStudenata - 1, ispiti.get(i).getStudent());
                System.out.println("Student " + izvrsniStudenti.get(brojIzvrsnihStudenata - 1).getIme() + " " + izvrsniStudenti.get(brojIzvrsnihStudenata - 1).getPrezime() + " je ostvario ocjenu 'izvrstan' na predmetu '" + ispiti.get(i).getPredmet().getNaziv() + "'");
            }
        }


        ispisStudenataPoKolegijima(predmeti);

        Integer faks = null;
        boolean nastaviPetlju = false;
        do{
            try{
                System.out.println("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti (1 - Veleuciliste Jave, 2 - Fakultet racunarstva): ");
                faks = unos.nextInt();
                if(faks < 1 || faks > 2){
                    System.out.println("Niste unijeli broj 1 ili 2!");
                    nastaviPetlju = true;
                }else{
                    nastaviPetlju = false;
                }
            }catch (InputMismatchException e){
                System.out.println("Neispravan unos!");
                logger.error(String.valueOf(e), e.fillInStackTrace());
                nastaviPetlju = true;
                unos.nextLine();
            }
        }while(nastaviPetlju);
        unos.nextLine();

        System.out.println("Unesite naziv obrazovne ustanove: ");
        String naziv = unos.nextLine();


        if(faks == 1){
            return new VeleucilisteJave(naziv, predmeti, profesori, studenti, ispiti);
        }else{
            return new FakultetRacunarstva(naziv, predmeti, profesori, studenti, ispiti);
        }

    }

    static void odabirStudenataZaNagrade(Scanner unos, ObrazovnaUstanova ustanova){

        List<Integer> zavrsni = new ArrayList<>();
        List<Integer> obrana = new ArrayList<>();
        List<BigDecimal> konacneOcjene = new ArrayList<>();

        for(int i = 0;i<ustanova.getStudenti().size();i++){
            boolean nastaviPetlju = false;

            for(Ispit ispit : ustanova.getIspiti()){
                if(ispit.getStudent() == ustanova.getStudenti().get(i) && ispit.getOcjena() == 1){
                    System.out.println("Student " + ustanova.getStudenti().get(i).getIme() + " " + ustanova.getStudenti().get(i).getPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek nedovoljan(1)");
                    return;
                }
            }

            do{
                try{
                    System.out.println("Unesite ocjenu zavrsnog rada za studenta: " + ustanova.getStudenti().get(i).getIme() + " " + ustanova.getStudenti().get(i).getPrezime());
                    zavrsni.add(i, unos.nextInt());
                    unos.nextLine();
                }catch (InputMismatchException e){
                    System.out.println("Neispravan unos!");
                    logger.error(String.valueOf(e), e.fillInStackTrace());
                    nastaviPetlju = true;
                    unos.nextLine();
                }
            }while(nastaviPetlju);



            nastaviPetlju = false;
            do{
                try{
                    System.out.println("Unesite ocjenu obrane zavrsng rada za studenta: " + ustanova.getStudenti().get(i).getIme() + " " + ustanova.getStudenti().get(i).getPrezime());
                    obrana.add(i, unos.nextInt());
                    unos.nextLine();
                }catch (InputMismatchException e){
                    System.out.println("Neispravan unos!");
                    logger.error(String.valueOf(e), e.fillInStackTrace());
                    nastaviPetlju = true;
                    unos.nextLine();
                }
            }while(nastaviPetlju);

            konacneOcjene.add(i, ((Visokoskolska) ustanova).izracunajKonacnuOcjenuStudijaZaStudenta(ustanova.getIspiti(), zavrsni.get(i), obrana.get(i), ustanova.getStudenti().get(i)));
            if(konacneOcjene.get(i) == null){
                i = ustanova.getStudenti().size();
            }

            System.out.println("Konacna ocjena studija studenta "+ ustanova.getStudenti().get(i).getIme() + " " + ustanova.getStudenti().get(i).getPrezime() + " je " + konacneOcjene.get(i));
        }


        Student najuspjesniji = ustanova.odrediNajuspjesnijegStudentaNaGodini(2022);

        System.out.println("Najbolji student 2022. godine je " + najuspjesniji.getIme() + " " + najuspjesniji.getPrezime() + " JMBAG: " + najuspjesniji.getJmbag());

        if(ustanova instanceof FakultetRacunarstva){
            try{
                System.out.println("Student koji je osvojio rektorovu nagradu je: " + ((FakultetRacunarstva) ustanova).odrediStudentaZaRektorovuNagradu().getIme() + " " + ((FakultetRacunarstva) ustanova).odrediStudentaZaRektorovuNagradu().getPrezime());
            }catch (PostojiViseNajmadjihStudenataException e){
                logger.error(String.valueOf(e.fillInStackTrace()));
                System.out.println(e.getMessage());
            }
        }
    }



    public static void main(String[] args) {

        Scanner unos = new Scanner(System.in);
        boolean nastaviPetlju = false;

        Integer brojUstanova = null;
        do {
            try{
                System.out.print("Unesite broj obrazovnih ustanova: ");
                brojUstanova = unos.nextInt();
                if(brojUstanova < 1){
                    System.out.println("Unijeli ste broj manji od 1!");
                    nastaviPetlju = true;
                }else{
                    nastaviPetlju = false;
                }

            }catch (InputMismatchException e){
                System.out.println("Neispravan unos!");
                logger.error(String.valueOf(e), e.fillInStackTrace());
                nastaviPetlju = true;
                unos.nextLine();
            }
        }while(nastaviPetlju);
        unos.nextLine();

        List<ObrazovnaUstanova> ustanove = new ArrayList<>();

        for(int i = 0;i<brojUstanova;i++){
            System.out.println("Unesite podatke za " + (i+1) + ". obrazovnu ustanovu: ");
            ustanove.add(i, unosUstanove(unos));
            odabirStudenataZaNagrade(unos, ustanove.get(i));
        }
    }
}