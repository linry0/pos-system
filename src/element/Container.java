package element;

import gui.Constants;
import javafx.scene.layout.Pane;

public abstract class Container<T extends Pane> {
	protected final T container;
	
	public Container(T container) {
		this.container = container;
		container.setPrefSize(Constants.VBOX_LIST_WIDTH, Constants.VBOX_LIST_HEIGHT);
	}
	
	public Pane getContainer() {
		return container;
	}
}
