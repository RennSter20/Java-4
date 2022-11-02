package hr.java.vjezbe.sortiranje;

import hr.java.vjezbe.entitet.Student;

import java.util.Comparator;

public class StudentSorter implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        if(o1.getPrezime().compareToIgnoreCase(o2.getPrezime()) < 0){
            return 1;
        }else if(o1.getPrezime().compareToIgnoreCase(o2.getPrezime()) > 0){
            return -1;
        }else{
            if(o1.getIme().compareToIgnoreCase(o2.getIme()) < 0){
                return 1;
            }else if(o1.getIme().compareToIgnoreCase(o2.getIme()) > 0){
                return -1;
            }else{
                return 0;
            }
        }
    }
}
