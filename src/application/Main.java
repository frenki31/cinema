package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;


public class Main extends Application {
	double x,y = 0;
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);	
			stage.initStyle(StageStyle.TRANSPARENT);
			
			root.setOnMousePressed(mEvent -> {
				x = mEvent.getSceneX();
				y = mEvent.getSceneY();
			});
			
			root.setOnMouseDragged(mEvent -> {
				stage.setX(mEvent.getSceneX() - x);
				stage.setY(mEvent.getSceneY() - y);
			});
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
