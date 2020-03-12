package Controller;

import Service.ServiceTema;
import Validation.DataValidator;
import Validation.ValidationException;
import domain.StructuraAn;
import domain.Tema;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TemaEditController {
    @FXML
    private TextField tema_Id;
    @FXML
    private TextField startWeek_tema;

    @FXML
    private TextField deadlineWeek_tema;

    @FXML
    private TextField descriere_tema;


    private ServiceTema serv;
    Stage dialogStage;
    Tema st;

    @FXML
    private void initialize(){

    }
    public void setServ(ServiceTema serv,Stage stage,Tema s){
        this.dialogStage=stage;
        this.serv=serv;
        this.st=s;
        if(null!=s){
            setFields(s);
            tema_Id.setEditable(false);
            startWeek_tema.setEditable(false);
        }
    }

    private void setFields(Tema s){
        tema_Id.setText(s.getId().toString());
        startWeek_tema.setText(s.getStartWeek().toString());
        deadlineWeek_tema.setText(s.getDeadlineWeek().toString());
        descriere_tema.setText(s.getDescriere());
    }

    @FXML
    public void handleSave(){
        try{
            Integer id=Integer.parseInt(tema_Id.getText());
            String descriere=descriere_tema.getText();
            Integer deadlineWeek=Integer.parseInt(deadlineWeek_tema.getText());


            Tema st2=new Tema(id,descriere,deadlineWeek);
            st2.setStartWeek(StructuraAn.getInstance().getSaptamanaCurenta());
            DataValidator.getInstance().validareTema(st2);
            if(this.st!=null)

                updateT(st2);
            else
            saveTema(st2);
        }catch (ValidationException e){
            MessageAlert.showEroare(null,e.getMesaj());
        }catch (NumberFormatException e){
            MessageAlert.showEroare(null,"ID/DeadlineWeek GOL,INTRODUCETI DATE");
        }

    }



    public void saveTema(Tema s){
        Tema r = this.serv.addTemaGui(s);
        MessageAlert.showMesaj(null, Alert.AlertType.INFORMATION, "SALVARE STUDENT", "Studentul a fost salvat!");


    }

    public void updateT(Tema tema){
        try {
            Tema rez = this.serv.updateTemaGui(this.st, tema);
            DataValidator.getInstance().validareTema(rez);

            if (rez != null) {

                MessageAlert.showMesaj(null, Alert.AlertType.INFORMATION, "Modificare tema", "Tema a fost modificata cu succes.");
            }dialogStage.close();

        }catch (ValidationException e){
            MessageAlert.showEroare(null,e.getMesaj());
        }
        dialogStage.close();
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }



}


