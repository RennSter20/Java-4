package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.PostojiViseNajmadjihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

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
    static Predmet[] unosPredmet(Scanner unos, Profesor[] profesori, Student[] studenti){

        String[] tempSifra = new String[BROJ_PREDMETA];
        String[] tempNaziv = new String[BROJ_PREDMETA];
        Integer[] tempECTS = new Integer[BROJ_PREDMETA];
        Integer[] tempOdabirProfesora = new Integer[BROJ_PREDMETA];
        Integer[] tempBrojStudenata = new Integer[BROJ_PREDMETA];

        boolean nastaviPetlju = false;

        for(int i = 0;i<BROJ_PREDMETA;i++){
            System.out.println("Unesite "+ (i+1) + ". predmet: ");
            System.out.print("Unesite sifru predmeta: ");
            tempSifra[i] = unos.nextLine();

            System.out.print("Unesite naziv predmeta: ");
            tempNaziv[i] = unos.nextLine();

            do{

                try{
                    System.out.print("Unesite broj ECTS bodova za predmet '" + tempNaziv[i] + "': ");
                    tempECTS[i] = unos.nextInt();
                    if(tempECTS[i]  < 1){
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
                        System.out.println((j+1) + ". " + profesori[j].getIme() + " " + profesori[j].getPrezime());
                    }

                    System.out.print("Odabir >> ");
                    tempOdabirProfesora[i] = unos.nextInt();
                    if(tempOdabirProfesora[i] < 1 || tempOdabirProfesora[i] > BROJ_PROFESORA){
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


            nastaviPetlju = false;
            do{
                try{
                    System.out.print("Unesite broj studenata za predmet '" + tempNaziv[i] + "': ");
                    tempBrojStudenata[i] = unos.nextInt();
                    if(tempBrojStudenata[i] < 1){
                        System.out.println("Unesen je broj manji od 1");
                        nastaviPetlju = true;
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
        }

        for(int i = 0;i<BROJ_STUDENTA;i++){
            studenti[i] = unosStudent(unos, i);
        }

        Predmet[] predmeti = new Predmet[BROJ_PREDMETA];
        for(int i = 0;i<BROJ_PREDMETA;i++){
            predmeti[i] = new Predmet(tempSifra[i], tempNaziv[i], tempECTS[i], profesori[tempOdabirProfesora[i]-1], studenti);
        }

        return predmeti;
    }

    static Ispit unosIspit(Scanner unos, Integer redniBroj, Predmet[] predmeti, Student[] studenti){

        System.out.println("Unesite " + (redniBroj+1) + ". ispitni rok: ");
        System.out.println("Odaberite predmet: ");
        for(int i = 0;i<BROJ_PREDMETA;i++){
            System.out.println((i+1) + " " + predmeti[i].getNaziv());
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
            System.out.println((i+1) + " " + studenti[i].getIme() + " "+ studenti[i].getPrezime());
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

        return new Ispit(predmeti[tempOdabirPredmet-1], studenti[tempOdabirStudenta-1], tempOcjena, tempDatum, new Dvorana(tempDvorana, tempZgrada));
    }

    static ObrazovnaUstanova unosUstanove(Scanner unos){
        Profesor[] profesori = new Profesor[BROJ_PROFESORA];
        Predmet[] predmeti = new Predmet[BROJ_PREDMETA];
        Student[] studenti = new Student[BROJ_STUDENTA];
        Ispit[] ispiti = new Ispit[BROJ_ISPITA];


        for(int i = 0;i<BROJ_PROFESORA;i++){
            profesori[i] = unosProfesor(unos, i);
        }

        predmeti = unosPredmet(unos, profesori, studenti);

        for(int i = 0;i<BROJ_ISPITA;i++){
            ispiti[i] = unosIspit(unos, i, predmeti, studenti);
        }

        Student[] izvrsniStudenti;

        Integer brojIzvrsnihStudenata = 0;
        for(int i = 0;i<BROJ_ISPITA;i++){
            if(ispiti[i].getOcjena().equals(5)){
                brojIzvrsnihStudenata++;
                izvrsniStudenti = new Student[brojIzvrsnihStudenata];
                izvrsniStudenti[brojIzvrsnihStudenata - 1] = ispiti[i].getStudent();
                System.out.println("Student " + izvrsniStudenti[brojIzvrsnihStudenata-1].getIme() + " " + izvrsniStudenti[brojIzvrsnihStudenata-1].getPrezime() + " je ostvario ocjenu 'izvrstan' na predmetu '" + ispiti[i].getPredmet().getNaziv() + "'");
            }
        }


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

        Integer[] zavrsvni = new Integer[ustanova.getStudenti().length];
        Integer[] obrana = new Integer[ustanova.getStudenti().length];
        BigDecimal[] konacneOcjene = new BigDecimal[ustanova.getStudenti().length];

        for(int i = 0;i<ustanova.getStudenti().length;i++){
            boolean nastaviPetlju = false;

            for(Ispit ispit : ustanova.getIspiti()){
                if(ispit.getStudent() == ustanova.getStudenti()[i] && ispit.getOcjena() == 1){
                    System.out.println("Student " + ustanova.getStudenti()[i].getIme() + " " + ustanova.getStudenti()[i].getPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek nedovoljan(1)");
                    return;
                }
            }

            do{
                try{
                    System.out.println("Unesite ocjenu zavrsnog rada za studenta: " + ustanova.getStudenti()[i].getIme() + " " + ustanova.getStudenti()[i].getPrezime());
                    zavrsvni[i] = unos.nextInt();
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
                    System.out.println("Unesite ocjenu obrane zavrsng rada za studenta: " + ustanova.getStudenti()[i].getIme() + " " + ustanova.getStudenti()[i].getPrezime());
                    obrana[i] = unos.nextInt();
                    unos.nextLine();
                }catch (InputMismatchException e){
                    System.out.println("Neispravan unos!");
                    logger.error(String.valueOf(e), e.fillInStackTrace());
                    nastaviPetlju = true;
                    unos.nextLine();
                }
            }while(nastaviPetlju);

            konacneOcjene[i] = ((Visokoskolska) ustanova).izracunajKonacnuOcjenuStudijaZaStudenta(ustanova.getIspiti(), zavrsvni[i], obrana[i], ustanova.getStudenti()[i]);
            if(konacneOcjene[i] == null){
                i = ustanova.getStudenti().length;
            }

            System.out.println("Konacna ocjena studija studenta "+ ustanova.getStudenti()[i].getIme() + " " + ustanova.getStudenti()[i].getPrezime() + " je " + konacneOcjene[i]);
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

        ObrazovnaUstanova[] ustanove = new ObrazovnaUstanova[brojUstanova];

        for(int i = 0;i<brojUstanova;i++){
            System.out.println("Unesite podatke za " + (i+1) + ". obrazovnu ustanovu: ");
            ustanove[i] = unosUstanove(unos);
            odabirStudenataZaNagrade(unos, ustanove[i]);
        }
    }
}