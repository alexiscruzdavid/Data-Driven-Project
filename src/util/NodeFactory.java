package ooga.util;

import java.lang.reflect.Method;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ResourceBundle;
import java.util.function.Consumer;

/** Class used to create node for use in the view. */
public class NodeFactory {

  private ResourceBundle myResources;
  private ResourceBundle myButtonResources;

  /** node factory constructor */
  public NodeFactory(ResourceBundle resources, ResourceBundle buttonResources) {
    myResources = resources;
    myButtonResources = buttonResources;
  }

  /**
   * Creates button using reflection
   *
   * @param property Key to use in properties file
   * @return new Button
   */
  public Button makeButton(String property, Object obj) {
    Button result = new Button();
    result.setText(myResources.getString(property));
    result.setOnAction(value -> {
          try {
            Method m = obj.getClass().getDeclaredMethod(myButtonResources.getString(property));
            m.invoke(obj);
          } catch (Exception e) {
            System.out.println(property);
            throw new RuntimeException(e);
          }
        }
    );
    result.setId(property);
    return result;
  }

  public ComboBox<String> makeDropDown(String category, String[] options, String id) {
    ComboBox<String> newComboBox = new ComboBox<>();
    newComboBox.setPromptText(myResources.getString("Select") + myResources.getString(category));
    for (String option : options) {
      newComboBox.getItems().add(option);
    }
    newComboBox.setEditable(false);
    newComboBox.setMinWidth(150);
    newComboBox.setId(id);
    return newComboBox;
  }

  public Label makeLabel(String title, int fontSize, String id) {
    Label lbl = new Label(title);
    lbl.setFont(new Font("Arial", fontSize));
    lbl.setId(id);
    return lbl;
  }

  public TextField makeTextField(String id) {
    TextField field = new TextField();
    field.setId(id);
    return field;
  }

  public void setTextField(TextField textField, boolean editable, String text) {
    textField.setEditable(editable);
    textField.setText(text);
  }

  public ColorPicker makeColorPicker(String id, Color defaultChoice, Consumer<Color> response) {
    ColorPicker picker = new ColorPicker(defaultChoice);
    picker.getStyleClass().add("color-picker");
    picker.setOnAction(e -> {
      response.accept(picker.getValue());
    });
    picker.setId(id);
    return picker;
  }
}
