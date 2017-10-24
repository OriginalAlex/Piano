package io.github.originalalex.util;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class NodeUtils {

    public static void makeNodeAlwaysGrowVBox(Node... nodes) {
        for (Node n : nodes) {
            VBox.setVgrow(n, Priority.ALWAYS);
        }
    }

    public static void makeNodeAlwaysGrowHBox(Node... nodes) {
        for (Node n : nodes) {
            HBox.setHgrow(n, Priority.ALWAYS);
        }
    }

}
