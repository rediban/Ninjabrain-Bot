package ninjabrainbot.gui.panels.information;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;

import ninjabrainbot.data.information.InformationMessage;
import ninjabrainbot.data.information.InformationMessageList;
import ninjabrainbot.gui.components.ThemedComponent;
import ninjabrainbot.gui.panels.ResizablePanel;
import ninjabrainbot.gui.style.StyleManager;
import ninjabrainbot.io.preferences.NinjabrainBotPreferences;

@SuppressWarnings("serial")
public class InformationListPanel extends ResizablePanel implements ThemedComponent {

	StyleManager styleManager;

	public InformationListPanel(StyleManager styleManager, NinjabrainBotPreferences preferences, InformationMessageList informationMessageList) {
		styleManager.registerThemedComponent(this);
		setOpaque(true);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(0);
		this.styleManager = styleManager;

		SwingUtilities.invokeLater(() -> synchronizeInformationMessages(informationMessageList.get()));

		informationMessageList.subscribeEDT(informationMessages -> synchronizeInformationMessages(informationMessages));
	}

	private void synchronizeInformationMessages(List<InformationMessage> informationMessages) {
		removeAll();
		for (InformationMessage informationMessage : informationMessages) {
			InformationTextPanel informationTextPanel = new InformationTextPanel(styleManager, informationMessage);
			add(informationTextPanel);
		}
		revalidate();
		whenSizeModified.notifySubscribers(this);
	}

	@Override
	public void updateSize(StyleManager styleManager) {

	}

	@Override
	public void updateColors() {

	}

}
