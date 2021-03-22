package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Controller {
    @FXML private MenuBar menubar;
    @FXML private TableView tableView;
    @FXML private TableColumn studentIDColumn;
    @FXML private TableColumn midtermColumn;
    @FXML private TableColumn assignmentColumn;
    @FXML private TableColumn finalExamColumn;
    @FXML private TableColumn finalMarkColumn;
    @FXML private TableColumn letterGradeColumn;

    @FXML private MenuItem newFileItem;
    @FXML private MenuItem openFileItem;
    @FXML private MenuItem saveFileItem;
    @FXML private MenuItem saveFileAsItem;
    @FXML private MenuItem exitItem;
    private String currentFileName = "";

    private ObservableList<StudentRecord> data = DataSource.getAllMarks();

    @FXML
    public void initialize() {
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        midtermColumn.setCellValueFactory(new PropertyValueFactory<>("midterm"));
        assignmentColumn.setCellValueFactory(new PropertyValueFactory<>("assignment"));
        finalExamColumn.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        finalMarkColumn.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
        letterGradeColumn.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

        newFileItem.setOnAction(actionEvent -> {
            newFile(actionEvent);
        });




        exitItem.setOnAction(actionEvent -> {
            exit(actionEvent);
        });
        tableView.setItems(data);
    }


    // open new file with empty table
    public void newFile(ActionEvent e) {
        // reset data to empty cell values
        resetData(data);
        // set table value items to empty data
        tableView.setItems(data);
    }

    private ObservableList<StudentRecord> resetData(ObservableList<StudentRecord> newData) {
        newData = FXCollections.observableArrayList();
        this.data = newData;
        return newData;
    }

    public void openFile(ActionEvent e) { }
    public void saveFile(ActionEvent e) { }
    public void saveFileAs(ActionEvent e) { }
    public void exit(ActionEvent e) {
        Stage currentStage = Main.getPrimaryStage();
        currentStage.close();
    }
}