package ordermanager;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ordermanager.model.Part;
import ordermanager.model.Product;

public class MainApp extends Application {

    public ObservableList<Part> partData = FXCollections.observableArrayList();
    public ObservableList<Part> selectedPartData = FXCollections.observableArrayList();
    public ObservableList<Product> productData = FXCollections.observableArrayList();
    public Part selectedPart;
    public Product selectedProduct;
    public int partID = 1;

    private static MainApp instance;

    public MainApp() {
        instance = this;
    }

    public static MainApp getInstance() {
        return instance;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/MainScreen.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void addPartData(Part part) {
        partData.add(part);
    }

    public ObservableList<Part> getPartData() {
        return partData;
    }

    public void addSelectedPartData(Part part) {
        selectedPartData.add(part);
    }

    public ObservableList<Part> getSelectedPartData() {
        return selectedPartData;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void addProductData(Product product) {
        productData.add(product);
    }

    public ObservableList<Product> getProductData() {
        return productData;
    }

}
