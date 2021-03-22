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
    private ObservableList<StudentRecord> data = DataSource.getAllMarks();

    public void loadFile() {
        String line = "";
        try {
            newFile();
            BufferedReader reader = new BufferedReader(new FileReader(this.currentFileName));
            while((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                data.add(new StudentRecord(columns[0].trim(), Float.parseFloat(columns[1].trim()), Float.parseFloat(columns[2].trim()), Float.parseFloat(columns[3].trim())));
            }
            tableView.setItems(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFileContent() throws IOException {
        Writer writer = null;
        try {
            File file = new File(this.currentFileName);
            writer = new BufferedWriter(new FileWriter(file));
            for (StudentRecord student: data) {
                String text = student.getStudentID() + "," + student.getAssignment() + "," + student.getMidterm() + "," + student.getFinalMark() + "\n";
                writer.write(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
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
            newFile();
        });

        openFileItem.setOnAction(actionEvent -> {
            openFile(actionEvent);
        });

        saveFileItem.setOnAction(actionEvent -> {
            saveFile(actionEvent);
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
    public void newFile() {
        // reset data to empty cell values
        resetData(data);
        // set table value items to empty data
        tableView.setItems(data);
    }

    public void openFile(ActionEvent e) {
        FileChooser chooseFile = new FileChooser();
        File selectedFile = chooseFile.showOpenDialog(Main.getPrimaryStage());
//        currentFileName = selectedFile.getName();
        setCurrentFileName(selectedFile.getName());
        loadFile();
    }

    public void setCurrentFileName(String currentFile) {
        this.currentFileName = currentFile;
    }

    public void saveFile(ActionEvent e) {
        try {
            FileChooser filechooser = new FileChooser();
            setCurrentFileName(filechooser.showSaveDialog(Main.getPrimaryStage()).getName());
            saveFileContent();
        } catch (IOException ev) {
            ev.printStackTrace();
        }
    }

    public void saveFileAs(ActionEvent e) { }

    public void exit(ActionEvent e) {
        Stage currentStage = Main.getPrimaryStage();
        currentStage.close();
    }
}