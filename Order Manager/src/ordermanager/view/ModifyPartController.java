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

public class ModifyPartController {

    @FXML
    private RadioButton outsourcedHandler;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private RadioButton inHouseHandler;

    @FXML
    private TextField ModPartIDSearch;

    @FXML
    private TextField ModPartNameSearch;

    @FXML
    private TextField ModPartCostSearch;

    @FXML
    private TextField ModPartMaxSearch;

    @FXML
    private TextField ModPartMinSearch;

    @FXML
    private TextField AddPartCompanySearch;

    @FXML
    private Button modPartSaveHandler;

    @FXML
    private Button modPartCancelHandler;

    @FXML
    private Text companyText;

    @FXML
    private Text machineIDText;

    @FXML
    private TextField machineIDSearch;

    @FXML
    private TextField modPartInv;

    @FXML
    void modPartCancelHandler(ActionEvent event) throws IOException {
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
    void modPartSaveHandler(ActionEvent event) throws IOException {

        Alert invLvl = new Alert(Alert.AlertType.ERROR);
        Alert maxMin = new Alert(Alert.AlertType.ERROR);
        Alert noData = new Alert(Alert.AlertType.ERROR);

        Boolean invFlag = false;
        Boolean maxMinflag = false;
        invLvl.setTitle("Error!");
        invLvl.setHeaderText("Validation Error!");
        invLvl.setContentText("Inventory level must be between the minimum and maximum amounts!");
        maxMin.setTitle("Error!");
        maxMin.setHeaderText("Validation Error!");
        noData.setTitle("Error!");
        noData.setHeaderText("Validation Error!");
        noData.setContentText("All of the fields must have data!");

        if (machineIDSearch.getText().isEmpty() || ModPartNameSearch.getText().isEmpty() || ModPartCostSearch.getText().isEmpty()
                || modPartInv.getText().isEmpty() || ModPartMaxSearch.getText().isEmpty() || ModPartMinSearch.getText().isEmpty()
                || machineIDSearch.getText().isEmpty()) {
            
            maxMinflag = true;
            noData.show();

        } else {

            if (inHouseHandler.isSelected() == true) {
                MainApp.getInstance().partData.remove(MainApp.getInstance().selectedPart);
                Part p = new InhousePart(
                        Integer.parseInt(machineIDSearch.getText()),
                        ModPartNameSearch.getText(),
                        Double.parseDouble(ModPartCostSearch.getText()),
                        Integer.parseInt(modPartInv.getText()),
                        Integer.parseInt(ModPartMaxSearch.getText()),
                        Integer.parseInt(ModPartMinSearch.getText()),
                        Integer.parseInt(machineIDSearch.getText()));

                if (Integer.parseInt(ModPartMaxSearch.getText()) < Integer.parseInt(ModPartMinSearch.getText())) {

                    maxMin.showAndWait();
                    maxMinflag = true;

                } else if (Integer.parseInt(modPartInv.getText()) > Integer.parseInt(ModPartMaxSearch.getText())
                        || Integer.parseInt(modPartInv.getText()) < Integer.parseInt(ModPartMinSearch.getText())) {

                    invLvl.showAndWait();
                    invFlag = true;

                } else {

                    MainApp.getInstance().addPartData(p);
                }

            } else {
                MainApp.getInstance().partData.remove(MainApp.getInstance().selectedPart);
                Part p = new OutsourcedPart(
                        AddPartCompanySearch.getText(),
                        ModPartNameSearch.getText(),
                        Double.parseDouble(ModPartCostSearch.getText()),
                        Integer.parseInt(ModPartIDSearch.getText()),
                        Integer.parseInt(modPartInv.getText()),
                        Integer.parseInt(ModPartMinSearch.getText()),
                        Integer.parseInt(ModPartMaxSearch.getText()));

                if (Integer.parseInt(ModPartMaxSearch.getText()) < Integer.parseInt(ModPartMinSearch.getText())) {

                    maxMin.showAndWait();
                    maxMinflag = true;

                } else if (Integer.parseInt(modPartInv.getText()) > Integer.parseInt(ModPartMaxSearch.getText())
                        || Integer.parseInt(modPartInv.getText()) < Integer.parseInt(ModPartMinSearch.getText())) {

                    invLvl.showAndWait();
                    invFlag = true;

                } else {

                    MainApp.getInstance().addPartData(p);
                }

            }
            if (maxMinflag == false && invFlag == false) {
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

    public void showPartDetails(Part part) {
        if (part instanceof InhousePart) {
            InhousePart newPart = (InhousePart) part;
            inHouseHandler.setSelected(true);
            showInHousePartDetails(newPart);
        } else {
            OutsourcedPart newPart = (OutsourcedPart) part;
            inHouseHandler.setSelected(false);
            outsourcedHandler.setSelected(true);
            AddPartCompanySearch.setVisible(true);
            companyText.setVisible(true);
            machineIDSearch.setVisible(false);
            machineIDText.setVisible(false);
            showOutSourcedPartDetails(newPart);
        }

    }

    private void showInHousePartDetails(InhousePart part) {
        if (inHouseHandler.isSelected() == true) {
            ModPartIDSearch.setText(Integer.toString(part.getpartID()));
            ModPartNameSearch.setText(part.getName());
            ModPartCostSearch.setText(Double.toString(part.getPrice()));
            ModPartMaxSearch.setText(Integer.toString(part.getMax()));
            ModPartMinSearch.setText(Integer.toString(part.getMin()));
            machineIDSearch.setText(Integer.toString(part.getmachineID()));
            modPartInv.setText(Integer.toString(part.getInStock()));

        }
    }

    private void showOutSourcedPartDetails(OutsourcedPart part) {
        if (outsourcedHandler.isSelected() == true) {
            ModPartIDSearch.setText(Integer.toString(part.getpartID()));
            ModPartNameSearch.setText(part.getName());
            ModPartCostSearch.setText(Double.toString(part.getPrice()));
            ModPartMaxSearch.setText(Integer.toString(part.getMax()));
            ModPartMinSearch.setText(Integer.toString(part.getMin()));
            AddPartCompanySearch.setText(part.getcompanyName());
            modPartInv.setText(Integer.toString(part.getInStock()));

        }
    }

    @FXML
    private void initialize() {
        showPartDetails(MainApp.getInstance().selectedPart);
    }

}
