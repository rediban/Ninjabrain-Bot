package ninjabrainbot.gui.components;

import ninjabrainbot.gui.style.SizePreference;
import ninjabrainbot.gui.style.StyleManager;

@SuppressWarnings("serial")
public class SmallThemedLabel extends ThemedLabel {

	public SmallThemedLabel(StyleManager styleManager) {
		super(styleManager);
	}

	public SmallThemedLabel(StyleManager styleManager, String text) {
		super(styleManager, text);
	}

	@Override
	public int getTextSize(SizePreference p) {
		return p.TEXT_SIZE_SMALL;
	}

}
