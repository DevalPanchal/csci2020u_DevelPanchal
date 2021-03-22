package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

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
    private String fileName;

    private ObservableList<StudentRecord> data = DataSource.getAllMarks();

    public void loadFile() {
        String line = "";
        try {
            resetData(data);
            BufferedReader reader = new BufferedReader(new FileReader(this.fileName));
            while((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                data.add(new StudentRecord(columns[0].toString(), Float.parseFloat(columns[1]), Float.parseFloat(columns[2]), Float.parseFloat(columns[3])));
            }
            tableView.setItems(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        openFileItem.setOnAction(actionEvent -> {
            openFile(actionEvent);
        });

        exitItem.setOnAction(actionEvent -> {
            exit(actionEvent);
        });

        tableView.setItems(data);
    }

    private ObservableList<StudentRecord> resetData(ObservableList<StudentRecord> newData) {
        newData = FXCollections.observableArrayList();
        this.data = newData;
        return newData;
    }

    // open new file with empty table
    public void newFile(ActionEvent e) {
        // reset data to empty cell values
        resetData(data);
        // set table value items to empty data
        tableView.setItems(data);
    }

    public void openFile(ActionEvent e) {

        FileChooser chooseFile = new FileChooser();
        File selectedFile = chooseFile.showOpenDialog(Main.getPrimaryStage());
        currentFileName = selectedFile.getName();
        loadFile();
    }
    public void saveFile(ActionEvent e) { }
    public void saveFileAs(ActionEvent e) { }
    public void exit(ActionEvent e) {
        Stage currentStage = Main.getPrimaryStage();
        currentStage.close();
    }
}