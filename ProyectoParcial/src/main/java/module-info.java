module com.example.proyectoparcial {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.scripting;


    opens com.example.proyectoparcial to javafx.fxml;
    exports com.example.proyectoparcial;
}