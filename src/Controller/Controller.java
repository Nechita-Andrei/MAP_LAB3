package Controller;

import Service.ServiceNota;
import Service.ServiceStudent;
import Service.ServiceTema;
import domain.Nota;
import domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    private ServiceNota serviceNota;
    private ServiceTema serviceTema;
    private ServiceStudent serviceStudent;

    public Controller(ServiceStudent serviceStudent,ServiceTema serviceTema,ServiceNota serviceNota){
        this.serviceNota=serviceNota;
        this.serviceStudent=serviceStudent;
        this.serviceTema=serviceTema;
    }

    public ServiceNota getServiceNota(){
        return serviceNota;
    }

    public ServiceStudent getServiceStudent(){
        return  serviceStudent;
    }

    public ServiceTema getServiceTema(){
        return serviceTema;
    }

    public static <E>List<E>makeCollection(Iterable<E>iter){
        List<E>list=new ArrayList<E>();
        for(E item:iter){
            list.add(item);
        }
        return list;
    }

    public List<Student>filtreazaGrupaStudenti(Integer grupa){
        return makeCollection(serviceStudent.getAllStudents()).stream().filter(a->a.getGrupa().equals(grupa)).collect(Collectors.toList());

    }
    public List<Student>filtreazaTemaStudenti(Integer idTema){
        return makeCollection(serviceNota.getAllGrades()).stream().filter(a->a.getTema().getId().equals(idTema)).map(Nota::getStudent).collect(Collectors.toList());
    }

    public List<Student>filtreazaProfesorStudenti(String profesor){
        return  makeCollection(serviceNota.getAllGrades()).stream().filter(a->a.getStudent().getCadruDidacticIndrumatorLab().equals(profesor)).map(Nota::getStudent).collect(Collectors.toList());
    }

    public List<Nota>filtreazaTemaNote(Integer saptamana){
        return makeCollection(serviceNota.getAllGrades()).stream().filter(a->a.getDataP().equals(saptamana)).collect(Collectors.toList());
    }


}
