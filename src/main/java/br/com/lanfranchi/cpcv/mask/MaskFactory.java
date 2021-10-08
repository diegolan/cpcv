package br.com.lanfranchi.cpcv.mask;

import javax.swing.JFrame;
import javax.swing.text.MaskFormatter;

public class MaskFactory extends JFrame {

	private static final long serialVersionUID = 1L;

	public MaskFormatter getMask(String formato) {
		MaskFormatter mask = new MaskFormatter();
		try {
			mask.setMask(formato);
			mask.setPlaceholderCharacter('_');
		} catch (Exception e) {
			e.getMessage();
		}
		return mask;
	}
}
