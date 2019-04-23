package milkman.ui.main;

import javax.inject.Singleton;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import milkman.ui.commands.UiCommand;
import milkman.ui.commands.UiCommand.NewRequest;
import milkman.ui.commands.UiCommand.RenameActiveRequest;
import milkman.ui.commands.UiCommand.RenameRequest;
import milkman.ui.commands.UiCommand.SubmitActiveRequest;
import milkman.utils.Event;

@Singleton
public class HotkeyManager {

	public final Event<UiCommand> onCommand = new Event<UiCommand>();
	
	public void registerGlobalHotkeys(Scene scene) {
		scene.getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN), 
				() -> onCommand.invoke(new SubmitActiveRequest()));
		
		scene.getAccelerators().put(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN), 
				() -> onCommand.invoke(new NewRequest()));
		
		scene.getAccelerators().put(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN), 
				() -> onCommand.invoke(new RenameActiveRequest()));
	}
	
	
	
}
