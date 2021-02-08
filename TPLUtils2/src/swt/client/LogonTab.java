package swt.client;

import net.NetManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class LogonTab {

	private NetManager netMan;
	private SWTClient swtClient;

	public LogonTab(NetManager netMan, SWTClient swtClient) {
		this.netMan = netMan;
		this.swtClient = swtClient;
	}

	public String getText() {
		return "登入";
	}

	public Composite createTabFloderPage(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		FormLayout formLayout = new FormLayout();
		composite.setLayout(formLayout);
		final Label label0 = new Label(composite, SWT.NONE);
		label0.setText("系統登入");
		FormData data = new FormData();
		data.top = new FormAttachment(4, 0);
		data.left = new FormAttachment(25, 0);
		label0.setLayoutData(data);

		Label label1 = new Label(composite, SWT.NONE);
		label1.setText("帳號名稱");
		data = new FormData();
		data.top = new FormAttachment(label0, 14, SWT.DEFAULT);
		data.left = new FormAttachment(10, 10);
		label1.setLayoutData(data);

		final Text text2 = new Text(composite, SWT.BORDER);
		text2.setSize(120, 28);
		text2.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				e.text = e.text.toUpperCase();
			}
		});
		// text2.setText ("text2");
		data = new FormData();
		data.left = new FormAttachment(label1, 8, SWT.RIGHT);
		data.top = new FormAttachment(label1, 0, SWT.CENTER);
		text2.setLayoutData(data);
		text2.forceFocus();

		Label label3 = new Label(composite, SWT.SHADOW_IN);
		label3.setText("密碼");
		data = new FormData();
		data.top = new FormAttachment(label1, 14, SWT.DEFAULT);
		data.right = new FormAttachment(label1, 0, SWT.RIGHT);
		label3.setLayoutData(data);

		final Text text4 = new Text(composite, SWT.BORDER);
		text4.setSize(120, 28);
		text4.setEchoChar('*');
		text4.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				e.text = e.text.toUpperCase();
			}
		});
		text4.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (text2.getText().length() == 0 || text4.getText().length() == 0)
					return;
				String errMessage = netMan.logon(text2.getText(), text4.getText());
				if (errMessage != null) {
					label0.setText(errMessage);
					return;
				}
				swtClient.initTask();				
			}
			
		});
		
		// text4.setText ("text4");
		data = new FormData();
		data.left = new FormAttachment(label3, 8, SWT.RIGHT);
		data.top = new FormAttachment(label3, 0, SWT.CENTER);
		text4.setLayoutData(data);

		Button button5 = new Button(composite, SWT.PUSH);
		button5.setText("登入");
		data = new FormData();
		data.top = new FormAttachment(label3, 16, SWT.DEFAULT);
		data.right = new FormAttachment(label1, 0, SWT.RIGHT);
		button5.setLayoutData(data);
		button5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (text2.getText().length() == 0 || text4.getText().length() == 0)
					return;
				String errMessage = netMan.logon(text2.getText(), text4.getText());
				if (errMessage != null) {
					label0.setText(errMessage);
					return;
				}
				swtClient.initTask();
			}
		});

		Button button6 = new Button(composite, SWT.PUSH);
		button6.setText("取消");
		data = new FormData();
		data.left = new FormAttachment(button5, 6, SWT.RIGHT);
		data.top = new FormAttachment(button5, 0, SWT.CENTER);
		button6.setLayoutData(data);
		button6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text2.setText("");
				text4.setText("");
			}
		});
		return composite;
	}

}
