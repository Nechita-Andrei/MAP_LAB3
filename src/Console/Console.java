/*package Console;
import Controller.Controller;
import Validation.ValidationException;
import domain.Nota;
import domain.StructuraAn;
import domain.Student;
import domain.Tema;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Console {
    private Controller controller;
    private Scanner keyboard;

    public Console(Controller controller){
        this.controller=controller;
        this.keyboard=new Scanner(System.in);
    }

    public Student citesteStudent(){
        System.out.println("Citire student: ");
        String id,nume,prenume,grupa,email,profesor;

        System.out.println("Id: ");
        id=keyboard.nextLine();

        System.out.println("Nume: ");
        nume=keyboard.nextLine();

        System.out.println("Prenume: ");
        prenume=keyboard.nextLine();

        System.out.println("Grupa: ");
        grupa=keyboard.nextLine();

        System.out.println("Email: ");
        email=keyboard.nextLine();

        System.out.println("Profesor: ");
        profesor=keyboard.nextLine();

        return new Student(Integer.parseInt(id),nume,prenume,Integer.parseInt(grupa),email,profesor);
    }

    public Tema citesteTema(){
        System.out.println("citire tema: ");
        String descriere,id,deadline;

        System.out.println("Id: ");
        id=keyboard.nextLine();

        System.out.println("Descriere: ");
        descriere=keyboard.nextLine();

        System.out.println("Deadline :");
        deadline=keyboard.nextLine();

        return new Tema(Integer.parseInt(id),descriere,Integer.parseInt(deadline));
    }

    public Nota citesteNota(){
        System.out.println("Citeste nota: ");
        String id,feedback,eProfesorT,eStrudentM,motivare;
        System.out.println("id: ");
        id=keyboard.nextLine();

        Student student=controller.getServiceStudent().getStudent(readId("Student"));
        Tema tema=controller.getServiceTema().getTema(readId("Tema"));

        System.out.println("Feedback: ");
        feedback=keyboard.nextLine();

        Nota n=new Nota(Integer.parseInt(id),student,tema,feedback);
       n.setProfesor(student.getCadruDidacticIndrumatorLab());

        Integer ProfesorT=0;

        System.out.println("A intarziat profesorul? [D/N]");
        eProfesorT=keyboard.nextLine();

        while (!eProfesorT.equals("D")&&!eProfesorT.equals("N")){
            System.out.println("Raspuns invalid");
            System.out.println("A intarziat profesorul? [D/N]");
            eProfesorT=keyboard.nextLine();
        }
        if(eProfesorT.equals("D")){
            ProfesorT= StructuraAn.getInstance().getSaptamanaCurenta()-tema.getDeadlineWeek();
        }

        System.out.println("Este studentul motivat? [D/N]");
        eStrudentM=keyboard.nextLine();

        while(!eStrudentM.equals("D")&&!eStrudentM.equals("N")){
            System.out.println("Raspuns invalid");
            System.out.println("Este studentul motivat?[D/N]");
            eStrudentM=keyboard.nextLine();
        }

        if (eStrudentM.equals("D")){
            System.out.println("Cate saptamani?");
            motivare=keyboard.nextLine();
            if(Integer.parseInt(motivare)>ProfesorT)
                ProfesorT=Integer.parseInt(motivare);
        }

        n.setIntarziere(ProfesorT);
        n.setDataP(StructuraAn.getInstance().getSaptamanaCurenta());
        return n;
    }



    public Integer readId(String e){
        System.out.println(e+" id: ");
        return Integer.parseInt(keyboard.nextLine());
    }

    public void AddUi(String entitate){
        try{
            switch(entitate){
                case "Student":{
                    Student st=citesteStudent();
                    controller.getServiceStudent().store(st);
                    break;
                }
                case "Tema":{
                    Tema t=citesteTema();
                    t.setStartWeek(StructuraAn.getInstance().getSaptamanaCurenta());
                    controller.getServiceTema().store(t);
                    break;
                }
                case "Nota":{
                    Nota n=citesteNota();
                    controller.getServiceNota().store(n);
                    break;
                }
                default:break;
            }
        }
        catch (ValidationException e){
            System.out.println(e);
        }
    }

    public void prinOne(String entitate){
        switch (entitate){
            case "Student":{
                System.out.println(controller.getServiceStudent().getStudent(readId("Student")).toString());
                break;

            }
            case "Tema":{
                System.out.println(controller.getServiceTema().getTema(readId("Tema")).toString());
                break;
            }
            case "Nota":{
                System.out.println(controller.getServiceNota().getNota(readId("Nota")).toString());
                break;
            }
            default:break;
        }
    }

    public  void printAll(String entitate){
        switch (entitate){
            case "Student":{
                for(Student s:controller.getServiceStudent().getAllStudents())
                    System.out.println(s);
                break;
            }
            case"Tema":{
                for(Tema t:controller.getServiceTema().getAllTeme())
                    System.out.println(t);
                break;

            }
            case "Nota":{
                for(Nota n:controller.getServiceNota().getAllGrades())
                    System.out.println(n);
                break;
            }
            default:break;
        }
    }

    public void UpdateUi(String entitate){
        try{
            System.out.println("Vechiul obiect "+entitate+" id ");
            switch (entitate){
                case "Student":{
                    controller.getServiceStudent().updateStudent(controller.getServiceStudent().getStudent(readId("Student")),citesteStudent());
                    break;
                }
                case "Tema":{
                    controller.getServiceTema().updateTema(controller.getServiceTema().getTema(readId("Tema")),citesteTema());
                    break;
                }
                case"Nota":{
                    controller.getServiceNota().updateNota(controller.getServiceNota().getNota(readId("Nota")),citesteNota());
                    break;
                }
                default:break;

            }
        }
        catch(ValidationException e){
            System.out.println(e);
        }
    }
    public void DeleteUi(String entitate){
        switch (entitate){
            case "Student":{
                System.out.println(controller.getServiceStudent().deleteStudent(readId("Student")));
                break;
            }
            case "Tema":{
                System.out.println(controller.getServiceTema().deleteTema(readId("Tema")));
                break;
            }
            case "Nota":{
                System.out.println(controller.getServiceNota().deleteNota(readId("Nota")));
                break;
            }
            default:break;
        }

    }
    public Integer readOption(){
        Integer option=-1;
        System.out.println("Scrie optiunea: ");
        option=Integer.parseInt(keyboard.nextLine());
        return option;
    }

    public void UiGrupaStudenti(){
        String grupa;
        System.out.println("Grupa: ");
        grupa=keyboard.nextLine();
        printIsEmptyList(controller.filtreazaGrupaStudenti(Integer.parseInt(grupa)));
    }

    public void UiProfesorStudenti(){
        String profesor;
        System.out.println("Profesor: ");
        profesor=keyboard.nextLine();
        printIsEmptyList(controller.filtreazaProfesorStudenti(profesor));
    }

    public void UiTemaNote(){
        String sapt;
        System.out.println("Saptamana: ");
        sapt=keyboard.nextLine();
        printIsEmptyList(controller.filtreazaTemaNote(Integer.parseInt(sapt)));
    }

    public void UiTemaStudenti(){
        printIsEmptyList(controller.filtreazaTemaStudenti(readId("Tema")));
    }

    public static void clearScreen(){
        try{
            if(System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        }catch (IOException|InterruptedException e){}
    }

    public void invalidOption(){
        System.out.println("Optiune invalida!");
    }

    public static <E> void printIsEmptyList(List<E> list){
        if(list.isEmpty())
            System.out.println("Nu au fost gasite rezultate!");
        else {
            for(E ent:list)
                System.out.println(ent);
        }
    }

    public void FiltrareMeniu(){
        clearScreen();
        System.out.println("Filtrari: ");
        System.out.println("1.Toti studentii unei grupe.");
        System.out.println("2.Toti studentii care au predat o anumita tema.");
        System.out.println("3.Toti studentii care au predat o anumita tema unui profesor anume.");
        System.out.println("4.Toate notele la o anumita tema, dintr-o saptamana data.");

        switch (readOption()){
            case 1: UiGrupaStudenti();break;
            case 2: UiTemaStudenti();break;
            case 3: UiTemaStudenti();break;
            case 4: UiTemaNote();break;
            default:invalidOption();break;
        }
    }


    public void SpecificMeniu(String tip)
    {
        clearScreen();
        System.out.println("["  +tip  +"]");
        System.out.println("1.Adauga "+ tip);
        System.out.println("2.Show one "+tip);
        System.out.println("3.Show all "+tip);
        System.out.println("4. Update "+tip);
        System.out.println("5.Delete  "+tip);

        switch (readOption()){
            case 1:AddUi(tip);break;
            case 2:prinOne(tip);break;
            case 3:printAll(tip);break;
            case 4:UpdateUi(tip);break;
            case 5:DeleteUi(tip);break;
            default:invalidOption();break;
        }
    }

    public void prinMenu(){
        System.out.println("1/Student");
        System.out.println("2/Teme");
        System.out.println("3/Note");
        System.out.println("4/Filtreaza");
        System.out.println("5/Exit");
    }

    public void Run(){
        Integer option=-1;
        do{
            prinMenu();
            option=readOption();
            switch(option){
                case 1:SpecificMeniu("Student");break;
                case 2:SpecificMeniu("Tema");break;
                case 3:SpecificMeniu("Nota");break;
                case 4:FiltrareMeniu();break;
                case 5:break;
                default:invalidOption();break;
            }
        }while(option!=5);
    }

}


 */


