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

public class AddProductController {

    @FXML
    private TableView<Part> addProductPartSearch;

    @FXML
    private TableColumn<Part, Number> searchPartIDColumn;

    @FXML
    private TableColumn<Part, String> searchPartNameColumn;

    @FXML
    private TableColumn<Part, Number> searchPartInvLevelColumn;

    @FXML
    private TableColumn<Part, Double> searchPartMoneyColumn;

    @FXML
    private TextField AddProdIDSearch;

    @FXML
    private TextField AddProdNameSearch;

    @FXML
    private TextField AddProdInvSearch;

    @FXML
    private TextField AddProdMaxSearch;

    @FXML
    private TextField AddProdMinSearch;

    @FXML
    private TextField AddProdPriceSearch;

    @FXML
    private Button addProdSearchHandler;

    @FXML
    private TextField AddProdSearch;

    @FXML
    private Button addProdAddHandler;

    @FXML
    private Button addProdDeleteHandler;

    @FXML
    private Button addProdCancelHandler;

    @FXML
    private Button addProdSaveHandler;

    @FXML
    private TableView<Part> addProductAddedPart;

    @FXML
    private TableColumn<Part, Number> addedPartIDColumn1;

    @FXML
    private TableColumn<Part, String> addedPartNameColumn1;

    @FXML
    private TableColumn<Part, Number> addedPartInvLevelColumn1;

    @FXML
    private TableColumn<Part, Double> addedPartMoneyColumn1;

    @FXML
    void addProdAddHandler(ActionEvent event) {

        Part part = addProductPartSearch.getSelectionModel().getSelectedItem();
        MainApp.getInstance().addSelectedPartData(part);
        addProductAddedPart.setItems(MainApp.getInstance().getSelectedPartData());

    }

    @FXML
    void addProdCancelHandler(ActionEvent event) throws IOException {

        Alert invLvl = new Alert(Alert.AlertType.CONFIRMATION);
        invLvl.setTitle("Confirm!");
        invLvl.setHeaderText("Confirm!");
        invLvl.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = invLvl.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent modifyPart = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene modifyPartScreen = new Scene(modifyPart);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(modifyPartScreen);
            mainStage.show();

        }

    }

    @FXML
    void addProdDeleteHandler(ActionEvent event) {

        Alert invLvl = new Alert(Alert.AlertType.CONFIRMATION);
        invLvl.setTitle("Confirm!");
        invLvl.setHeaderText("Confirm!");
        invLvl.setContentText("Are you sure you want to delete?");

        Optional<ButtonType> result = invLvl.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = addProductAddedPart.getSelectionModel().getSelectedIndex();
            addProductAddedPart.getItems().remove(selectedIndex);

        }
    }

    @FXML
    void addProdSaveHandler(ActionEvent event) throws IOException {

        Alert invLvl = new Alert(Alert.AlertType.ERROR);
        invLvl.setTitle("Error!");
        invLvl.setHeaderText("Validation Error!");
        invLvl.setContentText("You must add at least one part!");

        Alert prices = new Alert(Alert.AlertType.ERROR);
        prices.setTitle("Error!");
        prices.setContentText("The prices of your parts costs more than the product!");

        Alert dataVal = new Alert(Alert.AlertType.ERROR);
        dataVal.setTitle("Error!");
        dataVal.setHeaderText("Validation Error!");
        dataVal.setContentText("You must have a name, price, and inventory level entered!");

        if (!addProductAddedPart.getItems().isEmpty() == true) {

            double partPrices = 0;
            if (AddProdNameSearch.getText().isEmpty() || AddProdPriceSearch.getText().isEmpty() || AddProdInvSearch.getText().isEmpty()) {

                dataVal.show();

            } else {
                String name = AddProdNameSearch.getText();
                double price = Double.parseDouble(AddProdPriceSearch.getText());
                int inv = Integer.parseInt(AddProdInvSearch.getText());
                int prodID = Integer.parseInt(AddProdIDSearch.getText());
                int min = Integer.parseInt(AddProdMinSearch.getText());
                int max = Integer.parseInt(AddProdMaxSearch.getText());

                Product test = new Product(name, price, prodID, 1, min, max);

                for (Part part : addProductAddedPart.getItems()) {
                    test.addAssociatedPart(part);
                    partPrices = partPrices + part.getPrice();
                }

                if (partPrices > price) {

                    prices.showAndWait();

                } else {

                    MainApp.getInstance().addProductData(test);
                    Parent modifyPart = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                    Scene modifyPartScreen = new Scene(modifyPart);
                    Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    mainStage.setScene(modifyPartScreen);
                    mainStage.show();
                }
            }

        } else {

            invLvl.show();

        }

    }

    @FXML
    void addProdSearchHandler(ActionEvent event
    ) {

        String searchText = AddProdSearch.getText();
        FilteredList<Part> searchAnimalResults = searchAnimals(searchText);
        SortedList<Part> sortedData = new SortedList<>(searchAnimalResults);
        sortedData.comparatorProperty().bind(addProductPartSearch.comparatorProperty());
        addProductPartSearch.setItems(sortedData);
    }

    public FilteredList<Part> searchAnimals(String s) {
        return MainApp.getInstance().getPartData().filtered(p -> p.getName().toLowerCase().contains(s.toLowerCase()));
    }

    @FXML
    private void initialize() {
        searchPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        searchPartIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty());
        searchPartInvLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        searchPartMoneyColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        addProductPartSearch.setItems(MainApp.getInstance().getPartData());

    }

}
