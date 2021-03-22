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

    @FXML private TextField SIDField;
    @FXML private TextField assignmentField;
    @FXML private TextField midtermField;
    @FXML private TextField finalExamField;

    @FXML private Button addBtn;
    private String currentFileName = "";
    private ObservableList<StudentRecord> data = DataSource.getAllMarks();

    /**
     * Loads a csv file and the parse the data while input the data into the student record arraylist
     */
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

    /**
     * saves contents of the current window
     * -> if the current file name exists then save to that file
     * --> otherwise prompt user to make a new file via FileChooser
     * @throws IOException
     */
    public void saveFileContent() throws IOException {
        Writer writer = null;
        try {
            if (this.currentFileName != null) {
                File file = new File(this.currentFileName);
                writer = new BufferedWriter(new FileWriter(file));
                for (StudentRecord student: data) {
                    String text = student.getStudentID() + "," + student.getAssignment() + "," + student.getMidterm() + "," + student.getFinalExam() + "\n";
                    writer.write(text);
                }
            } else {
                saveFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }

    /**
     *  Interacts with data interaction on the user interface (specific for this controller)
     */
    @FXML
    public void initialize() {
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        midtermColumn.setCellValueFactory(new PropertyValueFactory<>("midterm"));
        assignmentColumn.setCellValueFactory(new PropertyValueFactory<>("assignment"));
        finalExamColumn.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        finalMarkColumn.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
        letterGradeColumn.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

        // set event listener when this menu item is clicked
        newFileItem.setOnAction(actionEvent -> {
            newFile();
        });

        openFileItem.setOnAction(actionEvent -> {
            openFile(actionEvent);
        });

        saveFileItem.setOnAction(actionEvent -> {
            saveFile();
        });

        saveFileAsItem.setOnAction(actionEvent -> {
            saveFileAs(actionEvent);
        });

        exitItem.setOnAction(actionEvent -> {
            exit(actionEvent);
        });

        // handle add button event, to add new student data to the tableview
        addBtn.setOnAction(actionEvent -> {
            String sid = "";
            float assignment = 0.0f;
            float midterm = 0.0f;
            float finalExam = 0.0f;
            if (SIDField.getText().length() > 0) {
                sid = SIDField.getText();
                //System.out.println(SIDField.getText().trim());
            }
            if (assignmentField.getText().length() > 0) {
                assignment = Float.parseFloat(assignmentField.getText().trim());
                //System.out.println(assignmentField.getText());
            }
            if (midtermField.getText().length() > 0) {
                midterm = Float.parseFloat(midtermField.getText().trim());
                //System.out.println(midtermField.getText());
            }
            if (finalExamField.getText().length() > 0) {
                finalExam = Float.parseFloat(finalExamField.getText().trim());
                //System.out.println(finalExamField.getText());
            }
            data.add(new StudentRecord(sid, assignment, midterm, finalExam));
            resetTextFieldState(SIDField);
            resetTextFieldState(assignmentField);
            resetTextFieldState(midtermField);
            resetTextFieldState(finalExamField);
        });
        // set the table view items to student record data
        tableView.setItems(data);
    }

    /**
     * Resets a textfield to empty
     * @param textfield
     */
    public void resetTextFieldState(TextField textfield) {
        textfield.setText("");
    }

    /**
     * Resets an Observable list for StudentRecord
     * @param newData
     * @return
     */
    private ObservableList<StudentRecord> resetData(ObservableList<StudentRecord> newData) {
        newData = FXCollections.observableArrayList();
        this.data = newData;
        return newData;
    }

    /**
     * Opens a new file with empty tableview
     */
    public void newFile() {
        // reset data to empty cell values
        resetData(data);
        // set table value items to empty data
        tableView.setItems(data);
    }

    /**
     * Opens a file (csv format) to load data in file into the tableview
     * @param e
     */
    public void openFile(ActionEvent e) {
        FileChooser chooseFile = new FileChooser();
        File selectedFile = chooseFile.showOpenDialog(Main.getPrimaryStage());
//        currentFileName = selectedFile.getName();
        setCurrentFileName(selectedFile.getName());
        loadFile();
    }

    /**
     * Sets the current file name
     * @param currentFile
     */
    public void setCurrentFileName(String currentFile) {
        this.currentFileName = currentFile;
    }

    /**
     * Saves the contents of a file into current file
     */
    public void saveFile() {
        try {
            saveFileContent();
        } catch(IOException ev) {
            ev.printStackTrace();
        }
    }

    /**
     * Saves a file as a custom name into a custom directory
     * @param e
     */
    public void saveFileAs(ActionEvent e) {
        try {
            FileChooser filechooser = new FileChooser();
            setCurrentFileName(filechooser.showSaveDialog(Main.getPrimaryStage()).getName());
            saveFileContent();
        } catch (IOException ev) {
            ev.printStackTrace();
        }
    }

    /**
     * Exits the window
     * @param e
     */
    public void exit(ActionEvent e) {
        Stage currentStage = Main.getPrimaryStage();
        currentStage.close();
    }
}