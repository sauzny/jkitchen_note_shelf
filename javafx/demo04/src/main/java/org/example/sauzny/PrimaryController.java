package org.example.sauzny;

import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import org.example.sauzny.service.ReportService;

import java.util.stream.IntStream;

public class PrimaryController {

    public ChoiceBox<Integer> year;
    public ChoiceBox<Integer> month;
    public TextArea textArea001;
    public Button submit001;

    public void tab001SelectChanged(Event event) {
        year.getItems().add(2021);
        IntStream.range(1, 13).forEach(i -> {
            month.getItems().add(i);
        });
    }

    public void submit001Clicked(MouseEvent mouseEvent) {
        int y = year.getValue();
        int m = month.getValue();
        ReportService reportService = new ReportService();
        textArea001.setText(reportService.getReport(y, m));
    }
}
