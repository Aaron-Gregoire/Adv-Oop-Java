/*
Student Management System
A user can:
Add students
Delete students
Add new courses
Edit student information directly in a table
Search students by name
Filter students by course
Display the same data in multiple GUI controls that stay synchronized automatically
 */
package wk5;
import javafx.application.Application;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

//https://openjfx.io/javadoc/?utm_source=chatgpt.com
/*
Quick study summary:
ObservableList → add(), remove() 
ListView → setItems(), getSelectionModel().getSelectedItem() 
TreeView → TreeItem, setRoot() 
TableView → TableColumn, setItems() 
ComboBox → getItems().add(), getValue(), setOnAction() 
TextField → getText(), setText(), setOnAction() 
 */
public class Week5GUI extends Application {

    // ===== MODEL CLASS =====
    // Represents a student with a name and course
    public static class Student {

        private String name;
        private String course;

        public Student(String name, String course) {
            this.name = name;
            this.course = course;
        }

        public String getName() {
            return name;
        }           // Getter for name

        public void setName(String name) {
            this.name = name;
        } // Setter for name

        public String getCourse() {
            return course;
        }       // Getter for course

        public void setCourse(String course) {
            this.course = course;
        } // Setter for course
    }

    @Override
    public void start(Stage stage) {

        // ===== DATA =====
        // ObservableList holds all students and notifies UI components automatically
        // can detect:additions, removals, replacements, permutations, updates
        ObservableList<Student> data = FXCollections.observableArrayList(
                new Student("John", "Java"),
                new Student("Emma", "Python")
        );

        // ===== FILTERED LIST =====
        // Wraps the ObservableList and allows dynamic filtering (the source list changes or the filter condition changes)without modifying original data
        FilteredList<Student> filteredData = new FilteredList<>(data, s -> true); // Initially show all

        // ===== LISTVIEW =====
        // Will display a simple list of students and courses as strings
        ListView<String> listView = new ListView<>();
        updateListView(listView, filteredData); // Populate ListView initially

        // ===== COMBOBOX =====
        // Dropdown to select a course for a student
        ComboBox<String> courseBox = new ComboBox<>();

        // ===== TABLEVIEW =====
        // Table showing students in rows and columns
        TableView<Student> table = new TableView<>();
        table.setEditable(true); // Allow editing directly in table cells

        // ===== NAME COLUMN =====
        TableColumn<Student, String> nameCol = new TableColumn<>("Name"); // Column header
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name")); // Bind to Student.getName(); WHAT data shows
        //HOW the cell looks
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());   // Make editable as text field; the user can double-click a cell, type new text, press Enter, update the value
        nameCol.setOnEditCommit(e -> {                                 // Handle edits
            e.getRowValue().setName(e.getNewValue());                  // Update model
            updateListView(listView, filteredData);                   // Update ListView to match
        });

        // ===== COURSE COLUMN =====
        TableColumn<Student, String> courseCol = new TableColumn<>("Course");
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course")); // Bind to Student.getCourse()
        // Make editable with ComboBox using items from courseBox
        courseCol.setCellFactory(ComboBoxTableCell.forTableColumn(courseBox.getItems()));
        courseCol.setOnEditCommit(e -> {
            e.getRowValue().setCourse(e.getNewValue());               // Update model
            updateListView(listView, filteredData);                  // Update ListView to match
        });

        // Add columns to TableView
        table.getColumns().addAll(nameCol, courseCol);

        // ===== BIND TABLEVIEW TO FILTERED DATA =====
        table.setItems(filteredData); // TableView will display the filtered students

        // ===== TREEVIEW =====
        // Displays courses in a tree structure
        TreeItem<String> rootItem = new TreeItem<>("Courses(Select One)"); // Root node
        rootItem.getChildren().addAll(
                new TreeItem<>("Java"),
                new TreeItem<>("Python"),
                new TreeItem<>("Web Dev")
        );
        rootItem.setExpanded(true); // Show all children initially
        TreeView<String> treeView = new TreeView<>(rootItem);

        // Populate ComboBox with courses from TreeView
        populateComboBoxFromTree(rootItem, courseBox);
        // Auto-update ComboBox if courses are added to TreeView
        rootItem.getChildren().addListener((ListChangeListener<TreeItem<String>>) change
                -> populateComboBoxFromTree(rootItem, courseBox));

        // ===== SEARCH AREA =====
        Label searchLabel = new Label("Search student by name:");
        TextField searchField = new TextField();

        // ===== INPUT FIELDS =====
        TextField nameField = new TextField();
        nameField.setPromptText("Enter student name"); // For adding/editing student

        TextField newCourseField = new TextField();
        newCourseField.setPromptText("Enter new course"); // For adding a new course

        Label message = new Label(); // Feedback for user actions

        // ===== BUTTONS =====
        Button addBtn = new Button("Add Student");   // Add new student
        Button deleteBtn = new Button("Delete Student"); // Delete selected student
        Button resetBtn = new Button("Reset");
        Button addCourseBtn = new Button("Add Course"); // Add new course

        // ===== ADD STUDENT =====
        addBtn.setOnAction(e -> {
            String name = nameField.getText().trim();  // Get student name
            String course = courseBox.getValue();      // Get selected course

            if (name.isEmpty() || course == null) {
                message.setText("Please enter student name and select course."); // Validation
                return;
            }

            //Todo: Add the student/course if not duplicate
            // Check if this student already exists, do not add, return
            boolean exists = data.stream()
                    .anyMatch(s -> s.getName().equalsIgnoreCase(name) && s.getCourse().equals(course));

            if (exists) {
                message.setText("This student already exists!");
                return;
            }

            data.add(new Student(name, course));       // Add student to ObservableList
            nameField.clear();                          // Clear input field
            courseBox.setValue(null);                   // Clear selection
            message.setText("Student added.");          // Feedback
            updateListView(listView, filteredData);    // Update ListView
        });

        // ===== DELETE STUDENT =====
        deleteBtn.setOnAction(e -> {
            Student selected = table.getSelectionModel().getSelectedItem(); // Get selected row
            if (selected != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to delete " + selected.getName() + "?",
                        ButtonType.YES, ButtonType.NO);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        data.remove(selected);             // Remove student
                        //Todo: clear the selection; re-enable add button and clear the name and course fields

                        table.getSelectionModel().clearSelection(); // Clear selection
                        addBtn.setDisable(false);                   // Re-enable Add
                        nameField.clear();
                        courseBox.setValue(null);

                        updateListView(listView, filteredData); // Update ListView
                        message.setText("Student deleted.");   // Feedback
                    }
                });
            }
        });
        // ===== CLEAR / RESET ENTIRE APPLICATION =====
        resetBtn.setOnAction(e -> {
            // 1. Clear the table selection (which resets the bottom form automatically)
            table.getSelectionModel().clearSelection();

            // 2. Clear the search bar text
            searchField.clear();

            // 3. Clear the TreeView course filter selection
            treeView.getSelectionModel().clearSelection();

            // 4. Clear the new course input field just in case they typed something there
            newCourseField.clear();

            // 5. Reset the feedback message
            message.setText("Application reset to starting state.");
        });

        // ===== ADD COURSE =====
        addCourseBtn.setOnAction(e -> {
            String courseName = newCourseField.getText().trim(); // Get new course
            if (!courseName.isEmpty()) {
                // Check if course already exists
                //ToDo: set the boolean variable exists to false if the course does not already
                //exist in the list rootItem; ignore case 
                boolean exists = rootItem.getChildren().stream()
                        .anyMatch(c -> c.getValue().equalsIgnoreCase(courseName));
                if (!exists) {
                    rootItem.getChildren().add(new TreeItem<>(courseName)); // Add to TreeView
                    courseBox.setValue(courseName);                          // Auto-select in ComboBox
                    newCourseField.clear();                                   // Clear input
                    message.setText("Course added.");                         // Feedback
                } else {
                    message.setText("Course already exists.");               // Feedback
                }
            }
        });

        // ===== TABLE SELECTION → FILL FORM =====
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // 1. Copy the selected student's data into the fields
                nameField.setText(newVal.getName());
                courseBox.setValue(newVal.getCourse());

                // 2. Lock the fields to prevent accidental desynchronized typing
                nameField.setDisable(true);
                courseBox.setDisable(true);
                addBtn.setDisable(true);  // Keep add button disabled during a selection
            } else {
                // 3. Clear the fields when selection is lost
                nameField.clear();
                courseBox.setValue(null);

                // 4. Unlock the fields so the user can add a new student again
                nameField.setDisable(false);
                courseBox.setDisable(false);
                addBtn.setDisable(false);
            }
        });

        // ===== FILTERING =====
        // Filter students based on TreeView selection and search text
        treeView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.getParent() == null) {
                treeView.getSelectionModel().clearSelection(); // remove highlight from root
                updateFilter(filteredData, treeView, searchField);
                updateListView(listView, filteredData);
                return;
            }
            updateFilter(filteredData, treeView, searchField);
            updateListView(listView, filteredData);
        });
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            updateFilter(filteredData, treeView, searchField);
            updateListView(listView, filteredData);
        });

        // ===== LISTEN FOR DATA CHANGES =====
        // Update ListView automatically when students are added, deleted, or edited
        data.addListener((ListChangeListener<Student>) c -> updateListView(listView, filteredData));

        // ===== LAYOUT =====
        VBox root = new VBox(10); // Vertical layout with 10px spacing
        root.getChildren().addAll(
                searchLabel,
                searchField,
                table,
                listView,
                treeView,
                newCourseField,
                addCourseBtn,
                nameField,
                courseBox,
                new HBox(10, addBtn, deleteBtn, resetBtn), // Buttons side by side
                message
        );

        Scene scene = new Scene(root, 650, 600); // Set window size
        stage.setTitle("Advanced Student Manager");
        stage.setScene(scene);
        stage.show();
    }

    // ===== HELPER METHODS =====
    // Populate ComboBox with courses from the TreeView
    private void populateComboBoxFromTree(TreeItem<String> root, ComboBox<String> comboBox) {
        comboBox.getItems().clear();               // Clear old items
        for (TreeItem<String> child : root.getChildren()) {
            comboBox.getItems().add(child.getValue()); // Add each course
        }
    }

    // Update ListView to show students in "Name - Course" format
    private void updateListView(ListView<String> listView, ObservableList<Student> data) {
        listView.getItems().clear();               // Clear old items
        for (Student s : data) {
            listView.getItems().add(s.getName() + " - " + s.getCourse());
        }
    }

    // Update filteredData based on TreeView selection and search text
    private void updateFilter(FilteredList<Student> filteredData,
            TreeView<String> treeView,
            TextField searchField) {

        String selectedCourse
                = (treeView.getSelectionModel().getSelectedItem() == null
                || treeView.getSelectionModel().getSelectedItem().getParent() == null)
                ? null
                : treeView.getSelectionModel().getSelectedItem().getValue();

        String searchText
                = searchField.getText() == null
                ? ""
                : searchField.getText().trim().toLowerCase();

        filteredData.setPredicate(student -> {

            boolean matchesCourse
                    = selectedCourse == null
                    || student.getCourse().equals(selectedCourse);

            boolean matchesSearch
                    = student.getName().toLowerCase().contains(searchText);

            return matchesCourse && matchesSearch;
        });
    }

    public static void main(String[] args) {
        launch(); // Launch JavaFX application
    }
}
