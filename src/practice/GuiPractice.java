package practice;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

// NOTE: requires the JavaFX module path VM args, same as your Week5GUI launch.json setup
public class GuiPractice extends Application {

    private final ObservableList<Student> masterData = FXCollections.observableArrayList(
            new Student("John", "Java", 85),
            new Student("Emma", "Python", 92),
            new Student("Liam", "Java", 78),
            new Student("Sophia", "Web Dev", 88),
            new Student("Noah", "Python", 65),
            new Student("Ava", "Java", 91)
    );

    @Override
    public void start(Stage stage) {

        // 3.1 - ListView bound to masterData, wrapped in a FilteredList so 3.2/3.3 can reuse it
        FilteredList<Student> filteredData = new FilteredList<>(masterData, s -> true);
        ListView<Student> listView = new ListView<>(filteredData);

        // 3.2 - search field, filters live as the user types
        TextField searchField = new TextField();
        searchField.setPromptText("Search by name...");

        // 3.3 - course combo box, populated with distinct course names
        List<String> courses = masterData.stream()
                .map(Student::getCourse)
                .distinct()
                .toList();
        ComboBox<String> courseBox = new ComboBox<>(FXCollections.observableArrayList(courses));
        courseBox.setPromptText("Filter by course...");

        // Combined predicate - both search text AND selected course must match
        Runnable updateFilter = () -> {
            String searchText = searchField.getText() == null ? "" : searchField.getText().toLowerCase();
            String selectedCourse = courseBox.getValue();

            filteredData.setPredicate(student -> {
                boolean matchesName = searchText.isEmpty()
                        || student.getName().toLowerCase().contains(searchText);
                boolean matchesCourse = selectedCourse == null
                        || student.getCourse().equals(selectedCourse);
                return matchesName && matchesCourse;
            });
        };

        searchField.textProperty().addListener((obs, oldVal, newVal) -> updateFilter.run());
        courseBox.valueProperty().addListener((obs, oldVal, newVal) -> updateFilter.run());

        // 3.4 - adds directly to masterData; ListView updates automatically since it's
        // bound (through filteredData) to the same ObservableList - no refresh code needed
        Button addButton = new Button("Add Random Student");
        addButton.setOnAction(e -> {
            long tag = System.currentTimeMillis() % 1000;
            masterData.add(new Student("New" + tag, "Java", 75));
        });

        Button clearFilterButton = new Button("Clear Filters");
        clearFilterButton.setOnAction(e -> {
            searchField.clear();
            courseBox.setValue(null);
        });

        VBox root = new VBox(10, searchField, courseBox, listView, addButton, clearFilterButton);
        root.setStyle("-fx-padding: 15;");

        stage.setScene(new Scene(root, 500, 450));
        stage.setTitle("GUI Practice");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
