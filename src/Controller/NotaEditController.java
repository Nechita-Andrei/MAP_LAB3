package Controller;

import Service.ServiceNota;
import Service.ServiceStudent;
import Service.ServiceTema;
import domain.Nota;
import domain.StructuraAn;
import domain.Student;
import domain.Tema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class NotaEditController {
    @FXML
    private ComboBox<Tema> temaComboBox;

    @FXML
    private ComboBox<Student>comboBoxStudent;
    @FXML
    private TextArea feedback_Nota;

    @FXML
    private RadioButton intarziere_D;

    @FXML
    private RadioButton intarzie_N;

    @FXML
    private RadioButton motivare_D;

    @FXML
    private RadioButton motivare_N;

    @FXML
    private TextField optional_weeks;

    @FXML
    private TextField field_nota;



    ServiceTema serviceTema;
    ServiceNota serviceNota;
    ServiceStudent serviceStudent;
    Stage dialogStage;
    Nota n;
    ObservableList<Tema>model= FXCollections.observableArrayList();
    ObservableList<Student>model1=FXCollections.observableArrayList();




    private void initModel(){
        Iterable<Tema>teme=serviceTema.getAll();
        List<Tema> temaList= StreamSupport.stream(teme.spliterator(),false)
                .collect(Collectors.toList());
        model.setAll(temaList);


        Iterable<Student>students=serviceStudent.getAll();
        List<Student>studentList=StreamSupport.stream(students.spliterator(),false)
                .collect(Collectors.toList());
        model1.setAll(studentList);

    }
    @FXML
    private void initialize(){
        temaComboBox.setItems(model);
        comboBoxStudent.setItems(model1);
        optional_weeks.setText("0");


    }

    public void setService(ServiceNota serviceNota,ServiceTema tema, Stage stage,Nota nota,ServiceStudent serviceStudent){
        this.dialogStage=stage;
        this.serviceNota=serviceNota;
        this.serviceTema=tema;
        this.serviceStudent=serviceStudent;
        this.n=nota;



        initModel();


    }
    @FXML
    public void handleSaveNota(){
        try{
            Tema selectedT=temaComboBox.getValue();
            Student selectedS=comboBoxStudent.getValue();
            Nota n=new Nota(serviceNota.sizeLista()+1,selectedS,selectedT,feedback_Nota.getText(),Integer.parseInt(field_nota.getText()));
            Integer intarziere=0;
            if(intarziere_D.isSelected())
                    intarziere= StructuraAn.getInstance().getSaptamanaCurenta()-selectedT.getDeadlineWeek();

            if(motivare_D.isSelected())
                if(Integer.parseInt(optional_weeks.getText())>intarziere)
                    intarziere=Integer.parseInt(optional_weeks.getText());
            n.setIntarziere(intarziere);
            n.setDataP(StructuraAn.getInstance().getSaptamanaCurenta());
            n.setNotaFinala(n.CalculateFinalGrade());
            serviceNota.store(n);
            MessageAlert.showMesaj(null, Alert.AlertType.INFORMATION,"Adaugare nota","S a adaugat lejer");
            dialogStage.close();
        }catch (Exception e){
            MessageAlert.showEroare(null,e.getMessage());
        }
    }

    public void handleCancel(){
        dialogStage.close();
    }

}

