package eu.warble.pjapp.util;


import eu.warble.pjapp.data.model.Student;

public class Tools {

    public static boolean checkApiResponseForErrors(Student student) {
        return !(student.getImie() == null || student.getNazwisko() == null) &&
                !(student.getStudia() == null || student.getZajecia() == null);
    }

}
