module org.example.sauzny {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.sauzny to javafx.fxml;
    exports org.example.sauzny;
}
