package Controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MessageAlert {
    static void showMesaj(Stage owner, Alert.AlertType type,String header,String text){
        Alert mesaj=new Alert(type);
        mesaj.setHeaderText(header);
        mesaj.setContentText(text);
        mesaj.initOwner(owner);
        mesaj.showAndWait();
    }

    static void showEroare(Stage owner,String text){
        Alert mesaj=new Alert(Alert.AlertType.ERROR);
        mesaj.initOwner(owner);
        mesaj.setTitle("Mesaj de eroare");
        mesaj.setContentText(text);
        mesaj.showAndWait();
    }
}
