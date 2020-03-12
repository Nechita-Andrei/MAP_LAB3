import Controller.ControllerStudent;
import Repository.RepositoryNota;
import Repository.RepositoryStudents;
import Repository.RepositoryTema;
import Service.ServiceNota;
import Service.ServiceStudent;
import Service.ServiceTema;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    RepositoryStudents repositoryStudents;
    ServiceStudent serviceStudent;
    RepositoryTema repositoryTema;
    ServiceTema serviceTema;
    RepositoryNota repositoryNota;
    ServiceNota serviceNota;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)throws IOException{
        repositoryStudents=new RepositoryStudents("C:\\Users\\bogne\\Desktop\\An2 sem1\\MAP_LAB\\MAP_LAB3\\src\\Resources\\Studenti.xml");
        serviceStudent=new ServiceStudent(repositoryStudents);
        repositoryTema=new RepositoryTema("C:\\Users\\bogne\\Desktop\\An2 sem1\\MAP_LAB\\MAP_LAB3\\src\\Resources\\Teme.xml");
        serviceTema=new ServiceTema(repositoryTema);
        repositoryNota=new RepositoryNota("C:\\Users\\bogne\\Desktop\\An2 sem1\\MAP_LAB\\MAP_LAB3\\src\\Resources\\Note.xml");
        serviceNota=new ServiceNota(repositoryNota);
        init1(primaryStage);
        primaryStage.setWidth(800);
        primaryStage.show();
    }

    private void init1(Stage primaryStage)throws IOException{
        FXMLLoader studentLoader=new FXMLLoader();
        studentLoader.setLocation(getClass().getResource("/view/studentView.fxml"));
        AnchorPane studentLayout=studentLoader.load();
        primaryStage.setScene(new Scene(studentLayout));


        ControllerStudent controllerStudent=studentLoader.getController();
        controllerStudent.setStudentService(serviceStudent,serviceTema,serviceNota);
    }

}
