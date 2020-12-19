package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL location = getClass().getResource("sample.fxml");
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("基于磁头引臂调度算法的磁盘I/O访问的调度及其时间参数计算的模拟实现");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        Controller controller = fxmlLoader.getController();
        controller.firstrandom();
    }


    public static void main(String[] args) {
        launch(args);
    }
}




