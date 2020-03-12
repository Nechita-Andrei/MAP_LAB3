package Controller;

import Service.ServiceNota;
import Service.ServiceStudent;
import Service.ServiceTema;
import Utils.entity.NotaChangeEvent;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ControllerNota implements Observer<NotaChangeEvent> {
    ServiceNota serviceNota;
    ServiceTema serviceTema;
    ServiceStudent serviceStudent;
    ObservableList<Nota> model = FXCollections.observableArrayList();
    ObservableList<Student> model1 = FXCollections.observableArrayList();
    ObservableList<Student> model2 = FXCollections.observableArrayList();


    @FXML
    private TableView<Nota> tableView;

    @FXML
    private TableColumn<Nota, Integer> column_nota;

    @FXML
    private TableColumn<Nota, Student> column_NumeStudent;

    @FXML
    private TableColumn<Nota, Tema> column_tema;

    @FXML
    private TableColumn<Nota, String> column_feedback;
    @FXML
    private TextField temaGrea_field;

    @FXML
    private TextField tema_fieldM;
    @FXML
    private ComboBox<Student> examen_box;

    @FXML
    private ComboBox<Student> timp_studenti;
    private Object Map;



    public void setNotaService(ServiceNota serv, ServiceTema tema, ServiceStudent serviceStudent) {
        this.serviceNota = serv;
        this.serviceTema = tema;
        this.serviceStudent = serviceStudent;
        serv.addObserver(this);
        initModel();

    }

    private void initModel() {
        Iterable<Nota> note = serviceNota.getAll();
        List<Nota> temaList = StreamSupport.stream(note.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(temaList);


    }

    @Override
    public void update(NotaChangeEvent notaChangeEvent) {
        initModel();
    }

    @FXML
    public void initialize() {
        column_nota.setCellValueFactory(new PropertyValueFactory<Nota, Integer>("NotaFinala"));
        column_feedback.setCellValueFactory(new PropertyValueFactory<Nota, String>("feedback"));
        column_NumeStudent.setCellValueFactory(new PropertyValueFactory<Nota, Student>("student"));
        column_tema.setCellValueFactory(new PropertyValueFactory<Nota, Tema>("tema"));
        tableView.setItems(model);

    }

    public void showNotaEditDialog(Nota nota) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/editNotaView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage dialog = new Stage();
            dialog.setTitle("Adauga Nota");
            dialog.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root);
            dialog.setScene(scene);

            NotaEditController editController = loader.getController();
            editController.setService(serviceNota, serviceTema, dialog, nota, serviceStudent);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddN(ActionEvent e) {
        showNotaEditDialog(null);
    }

    @FXML
    public void handleFinal(ActionEvent e) {
        showFinalDialog();
 
    }

    public void showFinalDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/medieView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage dialog = new Stage();
            dialog.setTitle("Media fiecarui student");
            dialog.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root);
            dialog.setScene(scene);
            MedieController medieController = loader.getController();
            medieController.setService(serviceNota, dialog, serviceStudent);
            dialog.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void tema_grea(ActionEvent e) {
        float minim = 11;
        for (Tema t : serviceTema.getAll()) {
            float medie = 0f;
            float suma = 0f;
            float numar = 0f;
            for (Nota nt : serviceNota.getAll()) {
                if (nt.getTema().getId() == t.getId()) {
                    numar = numar + 1;
                    suma = suma + nt.getNotaFinala();

                }
            }
            if (suma != 0)
                medie = suma / numar;
            else
                medie = 11;
            if (minim > medie) {
                minim = medie;
                tema_fieldM.setText(t.toString());

            }

            temaGrea_field.setText(String.valueOf(minim));

        }


    }

    public void examen_studenti(ActionEvent e) {
        Iterable<Student> lista = serviceStudent.getAll();
        List<Student> rezultat = new ArrayList<>();
        for (Student st : lista) {
            float medie = 0f;
            float suma = 0f;
            float numar = 0f;
            for (Nota nt : serviceNota.getAll()) {
                if (nt.getStudent().getId() == st.getId()) {
                    numar = numar + 1;
                    suma = suma + nt.getNotaFinala();
                }

            }
            if (suma != 0)
                medie = suma / numar;
            else
                medie = 0;

            if (medie >= 4)
                rezultat.add(st);

        }
        model1.setAll(rezultat);
        examen_box.setItems(model1);


    }




    public void Handlepredat() {
        List<Student> rezultat = new ArrayList<>();
        for (Student st : serviceStudent.getAll()) {
            int ok = 0;
            int gasit = 0;
            for (Nota nt : serviceNota.getAll()) {
                if (nt.getStudent().getId() == st.getId()) {
                    gasit = 1;
                    if (nt.getDataP() > nt.getTema().getDeadlineWeek())
                        ok = 1;
                }

            }
            if (ok == 0 && gasit == 1)
                rezultat.add(st);
        }
        model2.setAll(rezultat);
        timp_studenti.setItems(model2);
    }




}

