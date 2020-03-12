package Validation;

import domain.Nota;
import domain.Student;
import domain.Tema;

public abstract class AbstractValidator implements Validator {
    public AbstractValidator(){};

    public void validareTema(Tema tema)throws ValidationException{
        if(tema.getStartWeek()<1||tema.getStartWeek()>14)
            throw new ValidationException("Saptamana invalida");
        if(tema.getStartWeek()>tema.getDeadlineWeek())
            throw new ValidationException("Deadline ul nu poate fi mai mic ca startweek ul");

    }

    public void validareStudent(Student student)throws ValidationException{

        if(!student.getNume().matches("(^[A-Z])((?![.,'-]$)[a-z.,'-]){0,24}$")){
            throw new ValidationException("Numele trebuie sa contina doar litere.\nTrebuie sa inceapa cu litera mare.");
        }
        if(!student.getPrenume().matches("(^[A-Z])((?![.,'-]$)[a-z.,'-]){0,24}$")){
            throw new ValidationException("Prenumele trebuie sa contina doar litere.\nTrebuie sa inceapa cu litera mare.");
        }

        if(!student.getEmail().matches("^(.+)@(.+)")){
            throw new ValidationException("Adresa de email trebuie sa fie valida");
        }
        if (student.getId()<1||student.getId()>1000||student.getId()==null)
            throw new ValidationException("Id ul trebuie sa fie intre 0 si 1000!");
        if(student.getGrupa()>227||student.getGrupa()<221)
            throw new ValidationException(" Sa fie un numar intre 227 si 221!");
        if(!student.getCadruDidacticIndrumatorLab().matches("(^[A-Z])((?![.,'-]$)[a-z.,'-]){0,24}$")){
            throw new ValidationException("Profesorul trebuie sa inceapa cu litera mare.\nTrebuie sa contina numai litere.");
        }
    }
    public void validareNota(Nota nota)throws ValidationException{
        if(nota.getStudent()==null) {
            throw new ValidationException("Studentul trebuie precizat!");
        }
        if(nota.getTema()==null){
            throw new ValidationException("Tema trebuie sa fie precizata!");
        }


    }
}
