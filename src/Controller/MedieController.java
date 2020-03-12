package Controller;

import Service.ServiceNota;
import Service.ServiceStudent;
import domain.Nota;
import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MedieController {
    @FXML
    private TextField medie_field;

    @FXML
    private ComboBox<Student> student_box;

    private ServiceNota serviceNota;
    private ServiceStudent serviceStudent;
    Stage stage;

    ObservableList<Student> model = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        student_box.setItems(model);
    }

    @FXML
    private void handleCalcul(ActionEvent e) {
        setFields();
    }

    private void initModel() {
        Iterable<Student> students = serviceStudent.getAll();
        List<Student> studentList = StreamSupport.stream(students.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(studentList);
    }

    public void setService(ServiceNota serviceNota, Stage stage, ServiceStudent serviceStudent) {
        this.serviceNota = serviceNota;

        this.stage = stage;
        this.serviceStudent = serviceStudent;


        medie_field.setEditable(false);
        initModel();

    }

    private void setFields() {
        float medie;
        int numarator = 0;
        int numitor = 0;
        Student selcted = student_box.getValue();


        for (Nota nt : serviceNota.getAll()) {

            if (nt.getStudent().getId() == selcted.getId()) {

                int interval = nt.getTema().getDeadlineWeek() - nt.getTema().getStartWeek();
                numarator = numarator + interval * nt.getNotaFinala();
                numitor = numitor + interval;

            }
        }


            if(numarator==0||numitor==0)medie=0;
            else
                medie = (float) numarator / numitor;
            medie_field.setText(String.valueOf(medie));






    }
}