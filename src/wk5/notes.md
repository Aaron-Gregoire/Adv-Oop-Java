UML structure for UI
+----------------------------------+
|           Student                |
+----------------------------------+
| - name: String                   |
| - course: String                 |
+----------------------------------+
| + getName()                      |
| + setName()                      |
| + getCourse()                    |
| + setCourse()                    |
+----------------------------------+
               ▲
               │ stored in
               │
+----------------------------------+
| ObservableList<Student>          |
| (MASTER DATA)                    |  
+----------------------------------+
               ▲
               │ wrapped by
               │
+----------------------------------+
| FilteredList<Student>            |
| (LIVE FILTERED VIEW)             |
+----------------------------------+
               │
   ┌───────────┼────────────┬────────────┐
   │           │            │            │
   ▼           ▼            ▼            ▼
TableView   ListView     ComboBox     TreeView
(editable)  (text)       (courses)    (course list)

==============================================================
UI layout

┌──────────────────────────────────────┐
│  Search student by name:             │
│  [__________________________]        │
├──────────────────────────────────────┤
│  TABLEVIEW (editable student grid)   │
│  Name        | Course                │
│  John        | Java                  │
│  Emma        | Python                │
├──────────────────────────────────────┤
│  LISTVIEW (text view)                │
│  John - Java                         │
│  Emma - Python                       │
├──────────────────────────────────────┤
│  TREEVIEW (courses)                  │
│  Courses(Select One)                 │
│   ├── Java                           │
│   ├── Python                         │
│   └── Web Dev                        │
├──────────────────────────────────────┤
│  [ Enter new course ]  [Add Course]  │
├──────────────────────────────────────┤
│  [ Enter student name ]              │
│  [ ComboBox ▼ course selector ]      │
│  [ Add Student ] [ Delete Student ]  │
├──────────────────────────────────────┤
│  MESSAGE LABEL                       │
│  "Student added" / errors            │ 
└──────────────────────────────────────┘

==============================================================

Flow

USER TYPES IN SEARCH FIELD
        │
        ▼
searchField.textProperty()
        │
        ▼
updateFilter()
        │
        ▼
FilteredList<Student>
        │
        ▼
TableView + ListView refresh

──────────────────────────────

USER SELECTS TREEVIEW COURSE
        │
        ▼
updateFilter()
        │
        ▼
FilteredList updates TableView

──────────────────────────────

USER EDITS TABLE CELL
        │
        ▼
setOnEditCommit()
        │
        ▼
Student object updated
        │
        ▼
ObservableList notifies UI
        │
        ▼
ListView updates

==============================================================

TableView = primary editing tool
TextFields + ComboBox = add new data or assist editing
ListView = display only
TreeView = filter only (not editing data)

==============================================================
DATA LAYER:
   Student → ObservableList

FILTER LAYER:
   FilteredList (search + TreeView)

VIEW LAYER:
   TableView (edit)
   ListView (display)
   TreeView (filter control)
   ComboBox (input helper)