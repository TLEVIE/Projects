package ordermanager.view;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ordermanager.MainApp;
import ordermanager.model.InhousePart;
import ordermanager.model.OutsourcedPart;
import ordermanager.model.Part;

public class AddPartController {

    @FXML
    private RadioButton outsourcedHandler;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private RadioButton inHouseHandler;

    @FXML
    private TextField AddPartIDSearch;

    @FXML
    private TextField AddPartNameSearch;

    @FXML
    private TextField AddPartCostSearch;

    @FXML
    private TextField AddPartMaxSearch;

    @FXML
    private TextField AddPartMinSearch;

    @FXML
    private TextField AddPartCompanySearch;

    @FXML
    private TextField machineIDSearch;

    @FXML
    private Button addPartSaveHandler;

    @FXML
    private Button addPartCancelHandler;

    @FXML
    private Text companyText;

    @FXML
    private Text machineIDText;

    @FXML
    private TextField addPartInvSearch;

    public AddPartController() {
    }

    @FXML
    void addPartCancelHandler(ActionEvent event) throws IOException {

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
    void addPartSaveHandler(ActionEvent event) throws IOException {

        Alert invLvl = new Alert(AlertType.ERROR);
        Alert maxMin = new Alert(AlertType.ERROR);
        Alert noData = new Alert(AlertType.ERROR);
        Boolean invFlag = false;
        Boolean maxMinflag = false;
        invLvl.setTitle("Error!");
        invLvl.setHeaderText("Validation Error!");
        invLvl.setContentText("Inventory level must be between the minimum and maximum amounts!");
        maxMin.setTitle("Error!");
        maxMin.setHeaderText("Validation Error!");
        maxMin.setContentText("Max cannot be greater than minumum and vice versa!");
        noData.setTitle("Error!");
        noData.setHeaderText("Validation Error!");
        noData.setContentText("All of the fields must have data!");

        if (AddPartCostSearch.getText().isEmpty() || AddPartNameSearch.getText().isEmpty() || AddPartCostSearch.getText().isEmpty()
                || AddPartMinSearch.getText().isEmpty() || AddPartMaxSearch.getText().isEmpty() || machineIDSearch.getText().isEmpty()
                || addPartInvSearch.getText().isEmpty()) {
            maxMinflag = true;

            noData.show();

        } else {

            if (inHouseHandler.isSelected() == true) {

                String name = AddPartNameSearch.getText();
                double price = Double.parseDouble(AddPartCostSearch.getText());
                int min = Integer.parseInt(AddPartMinSearch.getText());
                int max = Integer.parseInt(AddPartMaxSearch.getText());
                int machineID = Integer.parseInt(machineIDSearch.getText());
                int inv = Integer.parseInt(addPartInvSearch.getText());

                if (max < min) {

                    maxMin.showAndWait();
                    maxMinflag = true;

                } else if (inv > max || inv < min) {

                    invLvl.showAndWait();
                    invFlag = true;

                } else {

                    Part test = new InhousePart(machineID, name, price, MainApp.getInstance().partID, inv, min, max);
                    MainApp.getInstance().partID++;
                    MainApp.getInstance().addPartData(test);

                }

            } else {
                int prodID = Integer.parseInt(AddPartIDSearch.getText());
                String name = AddPartNameSearch.getText();
                double price = Double.parseDouble(AddPartCostSearch.getText());
                int min = Integer.parseInt(AddPartMinSearch.getText());
                int max = Integer.parseInt(AddPartMaxSearch.getText());
                int inv = Integer.parseInt(addPartInvSearch.getText());
                String companyName = AddPartCompanySearch.getText();

                if (max < min) {

                    maxMin.showAndWait();
                    maxMinflag = true;

                } else if (inv > max || inv < min) {

                    invLvl.showAndWait();
                    invFlag = true;

                } else {

                    Part test = new OutsourcedPart(companyName, name, price, prodID, inv, min, max);
                    MainApp.getInstance().partID++;
                    MainApp.getInstance().addPartData(test);

                }
            }
            if (maxMinflag == false && invFlag
                    == false) {
                Parent modifyPart = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene modifyPartScreen = new Scene(modifyPart);
                Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                mainStage.setScene(modifyPartScreen);
                mainStage.show();
            }
        }
    }

    @FXML
    void inHouseHandler(ActionEvent event
    ) {

        if (inHouseHandler.isSelected() == true) {
            AddPartCompanySearch.setVisible(false);
            companyText.setVisible(false);
            machineIDSearch.setVisible(true);
            machineIDText.setVisible(true);
        }

    }

    @FXML
    void outsourcedHandler(ActionEvent event
    ) {

        if (outsourcedHandler.isSelected() == true) {
            AddPartCompanySearch.setVisible(true);
            companyText.setVisible(true);
            machineIDSearch.setVisible(false);
            machineIDText.setVisible(false);
        }
    }

    @FXML
    private void initialize() {

        AddPartIDSearch.setText(Integer.toString(MainApp.getInstance().partID));
    }

}
