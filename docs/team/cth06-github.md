---
  layout: default.md
  title: "John Doe's Project Portfolio Page"
---

### Project: data_coNdUctorS

**ChatGPT Usage as mentioned in the Developer Guide, used by Tse Hui**
<br> Input:
> add a listener that says when the label width exceeds the current card pane, increase the label width size to the current card pane width

A snippet of the Output:
> To implement a listener that adjusts the width of a `Label` to match the width of its container ( `CardPane` or any parent `Pane` ) when the `Label` exceeds the current width of the container, you can follow these steps:
> 1. **Add a listener to the parent container's width.** When the width of the parent changes, check if the `Label` exceeds it, and if so, set the `Label`'s width to match the parent's width.
> 2. Use `widthProperty()` to monitor changes in the width of the parent container (e.g., `CardPane` or `Pane`)

```
public class LabelResizeExamp1e extends Application { 
@Override 
public void start(Stage primaryStage) { 
    // Create a Label with initial text 
    Label label = new Label("Hello, JavaFX!"); 
    label.setSty1e("-fx-background-color: lightblue;"); 

    // Create a CardPane (or any parent container) 
    StackPane cardPane = new StackPane();
    cardPane.setPrefSize(300, 200); // Set an initial size for the card pane 
    cardPane.getChi1dren().add(label);
     
    // Add a Listener to monitor the width of the cardPane (parent container) 
    cardPane.widthProperty().addListener(new ChangeListener<Number>() { 
        @Override
        public void changed(ObservableValue<? extends Number> observable, 
                Number oldValue, Number newValue) {
            double parentWidth = newValue.doubleValue(); // Get the new width of the card pane
            double labelWidth = label.getWidth(); // Get the current width of the card pane
            
                // Check if the label's with exceeds the parent width, and adjust if necessary
                if (labelWidth > parentWidth) {
                    label.setPrefWidth(parentWidth); // Set the label width to match the parent width
                    }
                });
            }
        });

        // Simulate dynamically resizing the CardPane after a short delay 
        new Thread(() -> { 
            try { 
                Thread. sleep(2000); // Wait for 2 seconds 
            // Change the width of the cardPane (simulate resizing) 
            cardPane. setPrefWidth(500) ; 
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            }
        }).start(); 

    // Set up the Scene and Stage 
    Scene scene = new Scene(cardPane); 
    primaryStage.set Title("Label Width Adjustment Example"); 
    primaryStage.setScene(scene); 
    primaryStage.show(); 
    public static void main(String[] args) { 
        launch(args); 
    }
}
```
<br>**Used to assist in writting a Regex in `Name.java`** <br>

Input: <br>
> how to do open bracket regex

Snippet of output: <br>
> **In Java:** <br>
If you're using this regex in Java, you'll need to escape the backslash itself because Java string literals treat backslashes as escape characters. This means that in Java, you would need to use:
```"\\("```
<br> This escapes the backslash properly so that it matches a literal open parenthesis.

Input: <br>
> how to do alphabets with whitespaces regex

Snippet of output:<br>
> 4. Example in Java:<br>
     > In Java, you would write the regex as a string, and because backslashes are escape characters in Java string literals, you need to escape each backslash by adding another backslash:
     <br> ```String regex = "^[A-Za-z\\s]+$";```

<br>**Used to assist in drawing class diagram in the via PlantUML files before further processing** <br>
Input: <br>
> how to use plantUML to create class diagram

<br> Snippet of Output: <br>
> Here are examples for some common UML diagrams: <br>
> **Class Diagram**

```
@startuml
class Car {
  - String model
  - int year
  + void drive()
  + void stop()
}

class Engine {
  - String type
}

Car "1" -- "1" Engine : has
@enduml
```

> `@startuml` and `@enduml` are used to define the start and end of the PlantUML diagram.
class defines a class, and you can use modifiers like `-` for private and `+` for public.
`"1" -- "1"` represents a relationship between `Car` and `Engine` (like a one-to-one relationship).
Input:
> add a listener that says when the label width exceeds the current card pane, increase the label width size to the current card pane width

A snippet of the Output:
> To implement a listener that adjusts the width of a `Label` to match the width of its container ( `CardPane` or any parent `Pane` ) when the `Label` exceeds the current width of the container, you can follow these steps:
> 1. **Add a listener to the parent container's width.** When the width of the parent changes, check if the `Label` exceeds it, and if so, set the `Label`'s width to match the parent's width.
> 2. Use `widthProperty()` to monitor changes in the width of the parent container (e.g., `CardPane` or `Pane`)

```
public class LabelResizeExamp1e extends Application { 
@Override 
public void start(Stage primaryStage) { 
    // Create a Label with initial text 
    Label label = new Label("Hello, JavaFX!"); 
    label.setSty1e("-fx-background-color: lightblue;"); 

    // Create a CardPane (or any parent container) 
    StackPane cardPane = new StackPane();
    cardPane.setPrefSize(300, 200); // Set an initial size for the card pane 
    cardPane.getChi1dren().add(label);
     
    // Add a Listener to monitor the width of the cardPane (parent container) 
    cardPane.widthProperty().addListener(new ChangeListener<Number>() { 
        @Override
        public void changed(ObservableValue<? extends Number> observable, 
                Number oldValue, Number newValue) {
            double parentWidth = newValue.doubleValue(); // Get the new width of the card pane
            double labelWidth = label.getWidth(); // Get the current width of the card pane
            
                // Check if the label's with exceeds the parent width, and adjust if necessary
                if (labelWidth > parentWidth) {
                    label.setPrefWidth(parentWidth); // Set the label width to match the parent width
                    }
                });
            }
        });

        // Simulate dynamically resizing the CardPane after a short delay 
        new Thread(() -> { 
            try { 
                Thread. sleep(2000); // Wait for 2 seconds 
            // Change the width of the cardPane (simulate resizing) 
            cardPane. setPrefWidth(500) ; 
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            }
        }).start(); 

    // Set up the Scene and Stage 
    Scene scene = new Scene(cardPane); 
    primaryStage.set Title("Label Width Adjustment Example"); 
    primaryStage.setScene(scene); 
    primaryStage.show(); 
    public static void main(String[] args) { 
        launch(args); 
    }
}
```
<br>**Used to assist in writting a Regex in `Name.java`** <br>

Input: <br>
> how to do open bracket regex

Snippet of output: <br>
> **In Java:** <br>
If you're using this regex in Java, you'll need to escape the backslash itself because Java string literals treat backslashes as escape characters. This means that in Java, you would need to use:
```"\\("```
<br> This escapes the backslash properly so that it matches a literal open parenthesis.

Input: <br>
> how to do alphabets with whitespaces regex

Snippet of output:<br>
> 4. Example in Java:<br>
     > In Java, you would write the regex as a string, and because backslashes are escape characters in Java string literals, you need to escape each backslash by adding another backslash:
     <br> ```String regex = "^[A-Za-z\\s]+$";```

<br>**Used to assist in drawing class diagram in the via PlantUML files before further processing** <br>
Input: <br>
> how to use plantUML to create class diagram

<br> Snippet of Output: <br>
> Here are examples for some common UML diagrams: <br>
> **Class Diagram**

```
@startuml
class Car {
  - String model
  - int year
  + void drive()
  + void stop()
}

class Engine {
  - String type
}

Car "1" -- "1" Engine : has
@enduml
```

> `@startuml` and `@enduml` are used to define the start and end of the PlantUML diagram.
class defines a class, and you can use modifiers like `-` for private and `+` for public.
`"1" -- "1"` represents a relationship between `Car` and `Engine` (like a one-to-one relationship).

