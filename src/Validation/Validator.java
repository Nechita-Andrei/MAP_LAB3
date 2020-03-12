package Validation;


import domain.Nota;
import domain.Student;
import domain.Tema;

public interface Validator{
    void validareStudent(Student student)throws ValidationException;
    void validareTema(Tema tema)throws ValidationException;
    void validareNota(Nota nota)throws  ValidationException;
}
