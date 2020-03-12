package Controller;

import Service.ServiceStudent;
import Validation.DataValidator;
import Validation.ValidationException;
import domain.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentEditController {
    @FXML
    private TextField student_id;

    @FXML
    private TextField nume_student;

    @FXML
    private TextField prenume_student;

    @FXML
    private TextField grupa_student;

    @FXML
    private TextField email_student;
    @FXML
    private TextField profesor_student;

    private ServiceStudent serv;
    Stage dialogStage;
    Student st;

    @FXML
    private void initialize(){

    }
    public void setServ(ServiceStudent serv,Stage stage,Student s){
        this.dialogStage=stage;
        this.serv=serv;
        this.st=s;
        if(null!=s){
            setFields(s);
            student_id.setEditable(false);
        }
    }

    private void setFields(Student s){
        student_id.setText(s.getId().toString());
        nume_student.setText(s.getNume());
        prenume_student.setText(s.getPrenume());
        grupa_student.setText(s.getGrupa().toString());
        email_student.setText(s.getEmail());
        profesor_student.setText(s.getCadruDidacticIndrumatorLab());
    }

    @FXML
    public void handleSave(){
        try {
            Integer id = Integer.parseInt(student_id.getText());
            String nume = nume_student.getText();
            String prenume = prenume_student.getText();
            Integer grupa = Integer.parseInt(grupa_student.getText());
            String email = email_student.getText();
            String profesor = profesor_student.getText();


            Student st1 = new Student(id, nume, prenume, grupa, email, profesor);
            DataValidator.getInstance().validareStudent(st1);
            if (this.st != null)
                updateS(st1);
             else {



                saveStudent(st1);
            }
        }catch(ValidationException e){
                MessageAlert.showEroare(null, e.getMesaj());
        }catch(NumberFormatException e){
                MessageAlert.showEroare(null, "ID/GRUPA GOALA,INTRODUCETI DATE");
            }


    }



    public void saveStudent(Student s){
            Student r = this.serv.addStudentGui(s);
            MessageAlert.showMesaj(null, Alert.AlertType.INFORMATION, "SALVARE STUDENT", "Studentul a fost salvat!");


    }

    public  void updateS(Student student){
        try {
            Student rez = this.serv.updateStudentGui(this.st,student);
            DataValidator.getInstance().validareStudent(rez);
            if (rez!= null) {


                MessageAlert.showMesaj(null, Alert.AlertType.INFORMATION, "Modificare student", "Studentul a fost modificat!");
            }dialogStage.close();
        }catch (Exception e){
            MessageAlert.showEroare(null,"Student invalid");
        }
        dialogStage.close();
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }



}
