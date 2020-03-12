package Controller;

import Service.ServiceTema;
import Utils.entity.TemaChangeEvent;
import Utils.observer.Observer;
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

public class ControllerTema implements Observer<TemaChangeEvent> {
    ServiceTema serv;
    ObservableList<Tema> model= FXCollections.observableArrayList();

    @FXML
    TableView<Tema> tableView;

    @FXML
    TableColumn<Tema,Integer> column_temaId;

    @FXML
    TableColumn<Tema,String>column_temaStartWeek;

    @FXML
    TableColumn<Tema,String>column_temaDeadlineWeek;

    @FXML
    TableColumn<Tema,Integer>column_temaDescriere;


    public void setTemaService(ServiceTema serv) {
        this.serv = serv;
        serv.addObserver(this);
        initModel();
    }

    private void initModel(){
        Iterable<Tema>teme=serv.getAll();
        List<Tema> temaList= StreamSupport.stream(teme.spliterator(),false)
                .collect(Collectors.toList());
        model.setAll(temaList);
    }

    @FXML
    public void initialize(){
        column_temaId.setCellValueFactory(new PropertyValueFactory<Tema,Integer>("Id"));
        column_temaStartWeek.setCellValueFactory(new PropertyValueFactory<Tema,String>("StartWeek"));
        column_temaDeadlineWeek.setCellValueFactory(new PropertyValueFactory<Tema,String>("DeadlineWeek"));
        column_temaDescriere.setCellValueFactory(new PropertyValueFactory<Tema,Integer>("Descriere"));

        tableView.setItems(model);
    }

    @Override
    public void update(TemaChangeEvent temaChangeEvent){
        initModel();
    }





    public void showTemaEditDialog(Tema st){
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/editTemaView.fxml"));
            AnchorPane root=(AnchorPane)loader.load();
            Stage dialogStage=new Stage();
            dialogStage.setTitle("Edit Mesaj");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene=new Scene(root);
            dialogStage.setScene(scene);

            TemaEditController editController=loader.getController();
            editController.setServ(serv,dialogStage,st);

            dialogStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }





    @FXML
    public void handleAddT(javafx.event.ActionEvent actionEvent) {
        showTemaEditDialog(null);
    }

    @FXML
    public void handleDeleteT(javafx.event.ActionEvent actionEvent) {
        Tema selected=(Tema) tableView.getSelectionModel().getSelectedItem();
        if(selected!=null){
            Tema deleted=serv.deleteTemaGui(selected);
            if(null!=deleted)
                MessageAlert.showMesaj(null, Alert.AlertType.INFORMATION,"DELETE","Tema a fost stearsa cu succes!");


        }else MessageAlert.showEroare(null,"Nu ati selectat nicio tema!");
    }

    @FXML
    public void handleUpdateT(ActionEvent actionEvent){
        Tema selected=tableView.getSelectionModel().getSelectedItem();
        if(selected!=null){
            showTemaEditDialog(selected);
        }else
            MessageAlert.showEroare(null,"Nu ati selectat niciun student");
    }

}



