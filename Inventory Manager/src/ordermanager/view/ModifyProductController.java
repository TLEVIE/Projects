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

public class ModifyProductController {

    @FXML
    private TableView<Part> ModifyProductPartSearch;

    @FXML
    private TableColumn<Part, Number> modsearchPartIDColumn;

    @FXML
    private TableColumn<Part, String> modsearchPartNameColumn;

    @FXML
    private TableColumn<Part, Number> modsearchPartInvLevelColumn;

    @FXML
    private TableColumn<Part, Double> modsearchPartMoneyColumn;

    @FXML
    private TableView<Part> modProductAddedPart;

    @FXML
    private TableColumn<Part, Number> modaddedPartIDColumn1;

    @FXML
    private TableColumn<Part, String> modaddedPartNameColumn1;

    @FXML
    private TableColumn<Part, Number> modaddedPartInvLevelColumn1;

    @FXML
    private TableColumn<Part, Double> modaddedPartMoneyColumn1;

    @FXML
    private TextField modProdIDSearch;

    @FXML
    private TextField modProdNameSearch;

    @FXML
    private TextField modProdInvSearch;

    @FXML
    private TextField modProdMaxSearch;

    @FXML
    private TextField modProdMinSearch;

    @FXML
    private TextField modProdPriceSearch;

    @FXML
    private Button modProdSearchHandler;

    @FXML
    private TextField modProdSearch;

    @FXML
    private Button addProdAddHandler;

    @FXML
    private Button modProdDeleteHandler;

    @FXML
    private Button modProdCancelHandler;

    @FXML
    private Button modProdSaveHandler;

    @FXML
    void addProdAddHandler(ActionEvent event) {

        Part part = ModifyProductPartSearch.getSelectionModel().getSelectedItem();
        MainApp.getInstance().selectedProduct.addAssociatedPart(part);
        MainApp.getInstance().addSelectedPartData(part);
        modProductAddedPart.setItems(MainApp.getInstance().getSelectedPartData());

    }

    @FXML
    void modProdCancelHandler(ActionEvent event) throws IOException {

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
        } else {
        }

    }

    @FXML
    void modProdDeleteHandler(ActionEvent event) {

        Alert invLvl = new Alert(Alert.AlertType.CONFIRMATION);
        invLvl.setTitle("Confirm!");
        invLvl.setHeaderText("Confirm!");
        invLvl.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = invLvl.showAndWait();
        if (result.get() == ButtonType.OK) {

            int selectedIndex = modProductAddedPart.getSelectionModel().getSelectedIndex();
            modProductAddedPart.getItems().remove(selectedIndex);
        }
    }

    @FXML
    void modProdSaveHandler(ActionEvent event) throws IOException {

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

        double price = Double.parseDouble(modProdPriceSearch.getText());
        double partPrices = 0;

        if (!modProductAddedPart.getItems().isEmpty() == true) {

            for (Part part : modProductAddedPart.getItems()) {
                MainApp.getInstance().selectedProduct.addAssociatedPart(part);
                partPrices = partPrices + MainApp.getInstance().selectedProduct.getPrice();
            }
            if (partPrices > price) {

                prices.showAndWait();

            } else {

                if (modProdNameSearch.getText().isEmpty() || modProdPriceSearch.getText().isEmpty() || modProdInvSearch.getText().isEmpty()) {

                    dataVal.show();

                } else {

                    MainApp.getInstance().selectedProduct.setProductID(Integer.parseInt(modProdIDSearch.getText()));
                    MainApp.getInstance().selectedProduct.setName(modProdNameSearch.getText());
                    MainApp.getInstance().selectedProduct.setPrice(Double.parseDouble(modProdPriceSearch.getText()));
                    MainApp.getInstance().selectedProduct.setInStock(Integer.parseInt(modProdInvSearch.getText()));
                    MainApp.getInstance().selectedProduct.setMin(Integer.parseInt(modProdMinSearch.getText()));
                    MainApp.getInstance().selectedProduct.setMax(Integer.parseInt(modProdMinSearch.getText()));

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
    void modProdSearchHandler(ActionEvent event
    ) {

        String searchText = modProdSearch.getText();
        FilteredList<Part> searchAnimalResults = searchAnimals(searchText);
        SortedList<Part> sortedData = new SortedList<>(searchAnimalResults);
        sortedData.comparatorProperty().bind(ModifyProductPartSearch.comparatorProperty());
        ModifyProductPartSearch.setItems(sortedData);
    }

    public FilteredList<Part> searchAnimals(String s) {
        return MainApp.getInstance().getPartData().filtered(p -> p.getName().toLowerCase().contains(s.toLowerCase()));
    }

    private void showProductDetails(Product product) {
        modProdIDSearch.setText(Integer.toString(product.getProductID()));
        modProdNameSearch.setText(product.getName());
        modProdPriceSearch.setText(Double.toString(product.getPrice()));
        modProdMaxSearch.setText(Integer.toString(product.getMax()));
        modProdMinSearch.setText(Integer.toString(product.getMin()));
        modProdInvSearch.setText(Integer.toString(product.getInStock()));

    }

    @FXML
    private void initialize() {
        modsearchPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        modsearchPartIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty());
        modsearchPartInvLevelColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty());
        modsearchPartMoneyColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        ModifyProductPartSearch.setItems(MainApp.getInstance().getPartData());
        showProductDetails(MainApp.getInstance().selectedProduct);
        modProductAddedPart.setItems(MainApp.getInstance().selectedProduct.associatedParts);
        modProductAddedPart.setItems(MainApp.getInstance().getSelectedPartData());

    }

}
