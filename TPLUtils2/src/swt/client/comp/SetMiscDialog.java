package swt.client.comp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.CUS;
import tpl.net.service.comp.NetClientCUS;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SetMiscDialog extends Dialog {

	public SetMiscDialog(Shell parent) {
		this(parent, SWT.RESIZE | SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	public SetMiscDialog(Shell parent, int style) {
		super(parent, style);
		setText("設定");
	}

	public void open() {
		Shell dialog = new Shell(getParent(), getStyle());
		createContents(dialog);
		dialog.pack();
		dialog.open();
	}

	private void createContents(final Shell dialog) {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		dialog.setLayout(gridLayout);

		Label label = new Label(dialog, SWT.NONE);
		label.setText("請輸入還書日期(格式: 2008/2/3)");

		final Text text = new Text(dialog, SWT.BORDER);
		text.setText(getReturnDate());
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		text.setLayoutData(data);

		Button okButton = new Button(dialog, SWT.PUSH);
		okButton.setText("確定");
		data = new GridData();
		data.horizontalAlignment = GridData.END;
		data.grabExcessHorizontalSpace = true;
		okButton.setLayoutData(data);
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String strDate = text.getText();
				try {
					Date date = new SimpleDateFormat("yyyy/M/d H:m")
							.parse(strDate + " 23:59");
					if (date.after(new Date())) {
						text.setText("還書日期不可設於今天之後");
						return;
					}
				} catch (ParseException e1) {
					text.setText("");
					return;
				}
				((Shell) dialog.getParent()).setText(CUS.shellTitle + "--還書日期 "
						+ strDate + " 設定中");
				NetClientCUS.DataValueSetMap.get("EV").put("CO",
						strDate + ",23:59");
				dialog.close();
			}
		});

		Button clrButton = new Button(dialog, SWT.PUSH);
		clrButton.setText("不使用還書箱");
		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		clrButton.setLayoutData(data);
		clrButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				NetClientCUS.DataValueSetMap.get("EV").remove("CO");
				((Shell) dialog.getParent()).setText(CUS.shellTitle);
				dialog.close();
			}
		});
	}

	private String getReturnDate() {
		if (NetClientCUS.DataValueSetMap.get("EV").containsKey("CO")) {
			String str = NetClientCUS.DataValueSetMap.get("EV").remove("CO");
			int index = str.indexOf(',');
			return str.substring(0, index);
		}
		return "";
	}

}
