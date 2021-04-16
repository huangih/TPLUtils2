package swt.client.comp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.CUS;
import net.NetManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ReceiveMan {
	private static final String[] styleColor = new String[] { "chkItem_holds", "chkItem_place", "chkItem_transit",
			"chkItem_warn", "chkItem_holds", "chkItem_err" };
	private NetManager netMan;
	private Composite inComposite;
	private Composite diagComposite;
	private Composite resComposite;
	private Text diagText;
	private Text resText;
	private String previousStr = "11";
	private boolean needClear = false;
	// private Color[] colors;
	private Text text;
	private StyledText locText;

	public ReceiveMan(Composite parent, NetManager netMan) {
		this.netMan = netMan;
		Composite composite = new Composite(parent, SWT.BORDER);
		netMan.setShowPane(0, composite);
		composite.setData(this);
		composite.setLayout(new FillLayout());
		SashForm sash = new SashForm(composite, SWT.VERTICAL);
		inComposite = new Composite(sash, SWT.NONE);
		diagComposite = new Composite(sash, SWT.NONE);
		resComposite = new Composite(sash, SWT.NONE);
		createInComp();
		diagComposite.setLayout(new FillLayout());
		this.diagText = createTextComp(diagComposite, SWT.SINGLE, "Arial", 24, SWT.BOLD);
		this.diagText.setText(netMan.getSeqNum(0, ""));
		this.locText = createStyledTextComp(diagComposite, SWT.MULTI, "Arial", 18, SWT.BOLD);
		this.locText.setText("");

		resComposite.setLayout(new FillLayout());
		this.resText = createTextComp(resComposite, SWT.MULTI | SWT.WRAP, "Arial", 12, SWT.NORMAL);
		this.resText.setText("點收結果");

		sash.setWeights(new int[] { 30, 40, 40 });
		inComposite.pack();
		diagComposite.pack();
		resComposite.pack();
		composite.pack();
	}

	private Text createTextComp(Composite parent, int style, String fontName, int height, int fontStyle) {
		Text text = new Text(parent, style);
		text.setEditable(false);
		final Font newFont = new Font(parent.getDisplay(), fontName, height, fontStyle);
		text.setFont(newFont);
		text.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				newFont.dispose();
			}
		});
		return text;
	}

	private StyledText createStyledTextComp(Composite parent, int style, String fontName, int height, int fontStyle) {
		StyledText styledText = new StyledText(parent, style);
		styledText.setEditable(false);
		final Font newFont = new Font(parent.getDisplay(), fontName, height, fontStyle);
		styledText.setFont(newFont);
		styledText.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				newFont.dispose();
			}
		});
		return styledText;
	}

	private void createInComp() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		inComposite.setLayout(gridLayout);

		Label label = new Label(inComposite, SWT.NONE);
		label.setText("請輸入條碼號");
		GridData data = new GridData();
		data.grabExcessVerticalSpace = true;
		label.setLayoutData(data);

		this.text = createTextComp(inComposite, SWT.BORDER, "Arial", 16, SWT.BOLD);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		text.setLayoutData(data);

		text.setEditable(true);
		text.forceFocus();

		text.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				e.text = e.text.toUpperCase();
			}
		});
		text.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				String str = text.getText();
				needClear = true;
				if (str.equals(previousStr))
					return;
				previousStr = str;
				if (str.length() > 1)
					netMan.addTask(str);
			}
		});
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// System.out.println(e.keyCode);
				if (e.keyCode == 112 && (e.stateMask & SWT.CONTROL) != 0)
					netMan.addTask("PRINT");
				if (needClear) {
					text.setText("");
					needClear = false;
				}
				super.keyPressed(e);
			}
		});
	}

	// 外掛點收顯示
	public void setResultData(int style, Map<String, String> respsChk, String seqNum) {
		this.locText.setText("");
		String respStr = "";
		Display display = this.locText.getDisplay();
		switch (style) {
		case -1:
			// diagText.setForeground(colors[5]);
			this.diagText.setForeground(CUS.CR.get(styleColor[5]));
			this.diagText.setText(respsChk.get("err"));

			this.locText.setText(respsChk.get("MA"));

			break;
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			// diagText.setForeground(colors[style - 1]);
			this.diagText.setForeground(CUS.CR.get(styleColor[style - 1]));
			this.diagText.setText(seqNum);
			if (respsChk.containsKey("MA")) {
				String ma = respsChk.get("MA");
				if (CUS.messageMap.containsKey(ma))
					respStr += CUS.messageMap.get(ma) + "\n";
				else
					respStr += ma + "\n";
			} else
				respStr += "已點收或workFlow處理過" + "\n";

			break;

		// default:
		// diagText.setForeground(colors[5]);
		// diagText.setText(str);
		// break;
		}
		String itemType = respsChk.get("IG");
		String location = respsChk.get("IL");
		String library = respsChk.get("NS");
		List<StyleRange> srl = new ArrayList<StyleRange>();
		switch (style) {
		case 0:
			for (String key : respsChk.keySet()) {
				respStr += key + respsChk.get(key) + "^";
			}
			respStr += "\n";

		case 1:
		case 5:
			String userID = respsChk.get("UO");
			if (style == 1 && !respsChk.get("HK").equals("TITLE"))
				// locText.setForeground(colors[1]);
				srl.add(getStyleRange(this.locText, userID + "\n", SWT.COLOR_DARK_GREEN, display));

			respStr += "讀者證號 : " + userID + "    取書期限 : " + respsChk.get("GB") + "\n";

			break;

		case 2:
			respStr += "請上架至" + location + "\n";

			break;

		case 3:
			respStr += "從" + respsChk.get("nt") + "調撥至" + respsChk.get("nu") + "\n";

			break;
		case 6:
			respStr += "未調撥它館資料--" + library + "--" + respsChk.get("IG") + "\n";
			break;

		default:
			for (String key : respsChk.keySet()) {
				respStr += key + respsChk.get(key) + "^";
			}
			respStr += "\n";

			break;
		}

		if (style != 1 && style != 3 && style != 5) {
			if (CUS.specialLibraryMap.containsKey(library))
				srl.add(getStyleRange(this.locText, library + "\n", CUS.specialLibraryMap.get(library), display));

			if (CUS.specialLocationMap.containsKey(location))
				srl.add(getStyleRange(this.locText, location + "\n", CUS.specialLocationMap.get(location), display));
		}

		if (style != 3 && CUS.specialItemtypeMap.containsKey(itemType)) {
			String s = itemType.startsWith("HOT-") ? "熱門館藏!" : itemType;
			srl.add(getStyleRange(this.locText, s, CUS.specialItemtypeMap.get(itemType), display));
		}

		if (style == 6) {
			String s = library + "-" + itemType + "\n";
			srl.add(getStyleRange(this.locText, s, SWT.COLOR_RED, display));
		}

		if (!srl.isEmpty())
			this.locText.setStyleRanges(srl.toArray(new StyleRange[0]));
		this.resText.setText(respStr);
	}

	private StyleRange getStyleRange(StyledText styledText, String appendText, int color, Display display) {
		int start = styledText.getText().length();
		styledText.append(appendText);
		return new StyleRange(start, appendText.length(), display.getSystemColor(color), styledText.getBackground());
	}

	public Text getFocusControl() {
		return this.text;
	}

}
