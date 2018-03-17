package eu.wiessenberg.mol.timer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    private FXMLLoader fxmlLoader;

    public Main() {
        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("countdown.fxml"));
    }

    private void printUsage() {
        System.out.println(
                "------------------ Usage: ----------------\n" +
                "Start/stop\t: <SPACE>\n" +
                "Reset\t\t: <ENTER>\n" +
                "Intro\t\t: <I>\n" +
                "Minder tijd\t: <LEFT>\n" +
                "Meer tijd\t: <RIGHT>\n" +
                "------------------------------------------");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Wie Is De Mol");
        Scene scene = new Scene(root, 800, 600);
        scene.setOnKeyPressed(event -> {
            Controller controller = fxmlLoader.getController();

            switch (event.getCode()) {
                case SPACE: {
                    if (controller.isStarted()) {
                        controller.stop();
                    } else {
                        controller.start();
                    }
                    break;
                }
                case I:
                    System.out.println("Intro");
                    controller.intro();
                    break;
                case ENTER:
                    System.out.println("reset");
                    controller.reset();
                    break;
                case RIGHT:
                    System.out.println("plus!");
                    controller.plusMinute();
                    break;
                case LEFT:
                    System.out.println("minus!");
                    controller.minusMinute();
                    break;
                default:
                    printUsage();
            }
        });
        primaryStage.setScene(scene);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        primaryStage.show();
        printUsage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
