package ordermanager.view;

import java.io.IOException;
import java.util.Optional;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ordermanager.MainApp;
import ordermanager.model.Part;
import ordermanager.model.Product;

public class MainScreenController {

    @FXML
    private TableView<Part> MainPartsTable;

    @FXML
    private TableColumn<Part, Number> partIDColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Number> invLevelColumn;

    @FXML
    private TableColumn<Part, Double> partMoneyPerUnitColumn;

    @FXML
    private TableView<Product> MainProductsTable;

    @FXML
    private TableColumn<Product, Number> productIDColumn;

    @FXML
    private TableColumn<Product, String> produtNameColumn;

    @FXML
    private TableColumn<Product, Number> productInvLevelColumn;

    @FXML
    private TableColumn<Product, Double> prodMoneyForUnitColumn;

    @FXML
    private Button mainPartsSearchHandler;

    @FXML
    private Button mainProductsSearchHandler;

    @FXML
    private TextField MainPartsSearch;

    @FXML
    private TextField MainProductsSearch;

    @FXML
    private Button mainExit;

    @FXML
    private Button mainPartsAddHandler;

    @FXML
    private Button mainPartsModifyHandler;

    @FXML
    private Button mainPartsDeleteHandler;

    @FXML
    private Button mainProductsAddHandler;

    @FXML
    private Button mainProductsModifyHandler;

    @FXML
    private Button mainProductsDeleteHandler;

    @FXML
    void mainExit(ActionEvent event) {

        Alert invLvl = new Alert(Alert.AlertType.CONFIRMATION);
        invLvl.setTitle("Confirm!");
        invLvl.setHeaderText("Confirm!");
        invLvl.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = invLvl.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) mainExit.getScene().getWindow();
            stage.close();

        }
    }

    @FXML
    void mainPartsAddHandler(ActionEvent event) throws IOException {
        //Navigate to the add parts form
        Parent addPart = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene modifyPartScreen = new Scene(addPart);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(modifyPartScreen);
        mainStage.show();

    }

    @FXML
    void mainPartsDeleteHandler(ActionEvent event) {
        //Delete the selected item from the table

        Alert invLvl = new Alert(Alert.AlertType.CONFIRMATION);
        invLvl.setTitle("Confirm!");
        invLvl.setHeaderText("Confirm!");
        invLvl.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = invLvl.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = MainPartsTable.getSelectionModel().getSelectedIndex();
            MainPartsTable.getItems().remove(selectedIndex);

        }

    }

    @FXML
    void mainPartsModifyHandler(ActionEvent event) throws IOException {
        //Grab the part the user has selected
        Part part = MainPartsTable.getSelectionModel().getSelectedItem();
        //Set this part to the selectedPart variable from the mainapp
        MainApp.getInstance().selectedPart = part;
        //Navigate to the modify parts form
        Parent addPart = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
        Scene modifyPartScreen = new Scene(addPart);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(modifyPartScreen);
        mainStage.show();

    }

    @FXML
    private void mainPartsSearchHandler(ActionEvent event) {
        //Search the table using the search button and the text field next to it
        String searchText = MainPartsSearch.getText();
        FilteredList<Part> searchPartResults = searchParts(searchText);
        SortedList<Part> sortedData = new SortedList<>(searchPartResults);
        sortedData.comparatorProperty().bind(MainPartsTable.comparatorProperty());
        MainPartsTable.setItems(sortedData);

    }

    @FXML
    void mainProductsAddHandler(ActionEvent event) throws IOException {
        //Navigate to the add productrs form
        Parent modifyPart = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene modifyPartScreen = new Scene(modifyPart);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(modifyPartScreen);
        mainStage.show();

    }

    @FXML
    void mainProductsDeleteHandler(ActionEvent event) {
        //delete the selected product

        Alert invLvl = new Alert(Alert.AlertType.CONFIRMATION);
        invLvl.setTitle("Warning!");
        invLvl.setHeaderText("Warning!");
        invLvl.setContentText("This product has parts attached to it. Are you sure you want to delete this product?");

        int selectedIndex = MainProductsTable.getSelectionModel().getSelectedIndex();

        Optional<ButtonType> result = invLvl.showAndWait();
        if (result.get() == ButtonType.OK) {
            MainProductsTable.getItems().remove(selectedIndex);

        }

    }

    @FXML
    void mainProductsModifyHandler(ActionEvent event) throws IOException {
        //Grab the product from the table
        Product prod = MainProductsTable.getSelectionModel().getSelectedItem();
        //Set the grabbed product to the selectedProduct variable in the mainapp
        MainApp.getInstance().selectedProduct = prod;
        //navigate to the modfiy product form
        Parent modifyPart = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        Scene modifyPartScreen = new Scene(modifyPart);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(modifyPartScreen);
        mainStage.show();

    }

    @FXML
    void mainProductsSearchHandler(ActionEvent event) {
        //Search the table using the search button and the text field next to it
        String searchText = MainProductsSearch.getText();
        FilteredList<Product> searchProductsResults = searchProducts(searchText);
        SortedList<Product> sortedData = new SortedList<>(searchProductsResults);
        sortedData.comparatorProperty().bind(MainProductsTable.comparatorProperty());
        MainProductsTable.setItems(sortedData);

    }

    //helper method to search
    public FilteredList<Part> searchParts(String s) {
        //uses this pretty cool lambda expression to filter the table based on the parts name
        return MainApp.getInstance().getPartData().filtered(p -> p.getName().toLowerCase().contains(s.toLowerCase()));
    }

    //helper method to search
    public FilteredList<Product> searchProducts(String s) {
        //uses this pretty cool lambda expression to filter the table based on the parts name
        return MainApp.getInstance().getProductData().filtered(p -> p.getName().toLowerCase().contains(s.toLowerCase()));
    }

    @FXML
    private void initialize() {
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty());
        invLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        partMoneyPerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        MainPartsTable.setItems(MainApp.getInstance().getPartData());

        produtNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        productIDColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProperty());
        productInvLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        prodMoneyForUnitColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        MainProductsTable.setItems(MainApp.getInstance().getProductData());

    }
}
