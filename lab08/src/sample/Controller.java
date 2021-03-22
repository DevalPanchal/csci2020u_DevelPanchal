package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {
    @FXML private MenuBar menubar;
    @FXML private TableView tableView;
    @FXML private TableColumn studentIDColumn;
    @FXML private TableColumn midtermColumn;
    @FXML private TableColumn assignmentColumn;
    @FXML private TableColumn finalExamColumn;
    @FXML private TableColumn finalMarkColumn;
    @FXML private TableColumn letterGradeColumn;

    private ObservableList<StudentRecord> data = DataSource.getAllMarks();


    public void newFile(ActionEvent e) { }
    public void openFile(ActionEvent e) { }
    public void saveFile(ActionEvent e) { }
    public void saveFileAs(ActionEvent e) { }
    public void exit(ActionEvent e) { }

    @FXML
    public void initialize() {
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        midtermColumn.setCellValueFactory(new PropertyValueFactory<>("midterm"));
        assignmentColumn.setCellValueFactory(new PropertyValueFactory<>("assignment"));
        finalExamColumn.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        finalMarkColumn.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
        letterGradeColumn.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

        tableView.setItems(DataSource.getAllMarks());
    }
}