package Controller;

import Service.ServiceStudent;
import Service.ServiceTema;
import Service.ServiceNota;
import Utils.entity.StudentChangeEvent;
import Utils.observer.Observer;
import domain.Nota;
import domain.Student;
import domain.Tema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ControllerStudent implements Observer<StudentChangeEvent> {
    ServiceStudent serv;
    ServiceTema sv;
    ServiceNota note;
    ObservableList<Student> model= FXCollections.observableArrayList();

    @FXML
    TableView<Student>tableView;

    @FXML
    TableColumn<Student,Integer>column_studentId;

    @FXML
    TableColumn<Student,String>column_studentNume;

    @FXML
    TableColumn<Student,String>column_prenumeStudent;

    @FXML
    TableColumn<Student,Integer>column_grupaStudent;

    @FXML
    TableColumn<Student,String>column_emailStudent;

    @FXML
    TableColumn<Student,String>column_profesorInd;

    public void setStudentService(ServiceStudent serv,ServiceTema sv,ServiceNota note) {
        this.serv = serv;
        this.sv=sv;
        this.note=note;
        serv.addObserver(this);
        initModel();
    }

    private void initModel(){
        Iterable<Student>students=serv.getAll();
        List<Student>studentList= StreamSupport.stream(students.spliterator(),false)
                .collect(Collectors.toList());
        model.setAll(studentList);
    }

    @FXML
    public void initialize(){
        column_studentId.setCellValueFactory(new PropertyValueFactory<Student,Integer>("Id"));
        column_studentNume.setCellValueFactory(new PropertyValueFactory<Student,String>("Nume"));
        column_prenumeStudent.setCellValueFactory(new PropertyValueFactory<Student,String>("Prenume"));
        column_grupaStudent.setCellValueFactory(new PropertyValueFactory<Student,Integer>("Grupa"));
        column_emailStudent.setCellValueFactory(new PropertyValueFactory<Student,String>("Email"));
        column_profesorInd.setCellValueFactory(new PropertyValueFactory<Student,String>("cadruDidacticIndrumatorLab"));
        tableView.setItems(model);
    }

    @Override
    public void update(StudentChangeEvent studentChangeEvent){
        initModel();
    }





    public void showStudentEditDialog(Student st){
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/editStudentView.fxml"));
            AnchorPane root=(AnchorPane)loader.load();
            Stage dialogStage=new Stage();
            dialogStage.setTitle("Edit Mesaj");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene=new Scene(root);
            dialogStage.setScene(scene);

            StudentEditController editController=loader.getController();
            editController.setServ(serv,dialogStage,st);

            dialogStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void showTemaDialog(Tema t){
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/TemaView.fxml"));
            AnchorPane root=(AnchorPane)loader.load();
            Stage dialogStage=new Stage();
            dialogStage.setTitle("Tema");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene=new Scene(root);
            dialogStage.setScene(scene);

            ControllerTema editController=loader.getController();
            editController.setTemaService(sv);


            dialogStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void showNotaDialog(Nota n){
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/NotaView.fxml"));
            AnchorPane root=(AnchorPane)loader.load();
            Stage dialogStage=new Stage();
            dialogStage.setTitle("Nota");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene=new Scene(root);
            dialogStage.setScene(scene);

            ControllerNota controllerNota=loader.getController();
            controllerNota.setNotaService(note,sv,serv);
            dialogStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddTema(ActionEvent e){
        showTemaDialog(null );
    }

    @FXML
    public void handleAddNota(ActionEvent e){
        showNotaDialog(null);
    }

    @FXML
    public void handleAdd(javafx.event.ActionEvent actionEvent) {
        showStudentEditDialog(null);
    }

    @FXML
    public void handleDelete(javafx.event.ActionEvent actionEvent) {
        Student selected=(Student)tableView.getSelectionModel().getSelectedItem();
        if(selected!=null){
            Student deleted=serv.deleteStudentGui(selected);
            if(null!=deleted)
                MessageAlert.showMesaj(null, Alert.AlertType.INFORMATION,"DELETE","Studentul a fost sters cu succes!");


        }else MessageAlert.showEroare(null,"Nu ati selectat niciun student!");
    }

    @FXML
    public void handleUpdate(ActionEvent actionEvent){
        Student selected=tableView.getSelectionModel().getSelectedItem();
        if(selected!=null){
            showStudentEditDialog(selected);
        }else
            MessageAlert.showEroare(null,"Nu ati selectat niciun student");
    }

}

