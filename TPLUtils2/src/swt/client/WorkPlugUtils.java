package swt.client;

import java.util.ArrayList;
import java.util.List;

import net.CUS;
import net.NetHoldMan;
import net.NetManager;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import swt.client.comp.NoNumHoldsDialog;
import swt.client.comp.OverHoldsDialog;
import swt.client.comp.SetMiscDialog;

public class WorkPlugUtils extends ApplicationWindow implements SWTClient {
	private NetManager netMan;
	private NetHoldMan netHoldMan;
	private TabFolder tabFolder;
	private List<TabItem> tItems = new ArrayList<TabItem>();

	public WorkPlugUtils() {
		super(null);
		this.netMan = new NetManager(this);
		this.netHoldMan = this.netMan.getNetHoldMan();
	}

	@Override
	public void initTask() {
		final Shell shell = getShell();
		CUS.initResourceRegistry(Display.getCurrent());
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		MenuItem item = new MenuItem(menuBar, SWT.CASCADE);
		item.setText("印表機(&P)");
		Menu printerMenu = new Menu(shell, SWT.DROP_DOWN);
		item.setMenu(printerMenu);
		item = new MenuItem(printerMenu, SWT.PUSH);
		item.setText("選擇設定印表機(&s)");
		item.setAccelerator(SWT.CTRL + 's');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PrintDialog diag = new PrintDialog(shell, SWT.PRIMARY_MODAL
						| SWT.OK | SWT.CANCEL);
				PrinterData printerData = diag.open();
				if (printerData != null)
					netMan.setPrinter(printerData);
			}
		});
		item = new MenuItem(menuBar, SWT.CASCADE);
		item.setText("查詢(&S)");
		Menu searchMenu = new Menu(shell, SWT.DROP_DOWN);
		item.setMenu(searchMenu);
		item = new MenuItem(searchMenu, SWT.PUSH);
		item.setText("須處理之逾期未取預約資料(&o)");
		item.setAccelerator(SWT.CTRL + 'o');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				OverHoldsDialog ovHoldsDialog = new OverHoldsDialog(shell,
						netHoldMan);
				ovHoldsDialog.open();
			}
		});
		item = new MenuItem(searchMenu, SWT.PUSH);
		item.setText("尚須給予流水編號的預約資料(&N)");
		item.setAccelerator(SWT.CTRL + 'N');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				NoNumHoldsDialog noNumHoldsDialog = new NoNumHoldsDialog(shell,
						netHoldMan);
				noNumHoldsDialog.open();
			}
		});
		item = new MenuItem(menuBar, SWT.CASCADE);
		item.setText("設定(&s)");
		Menu setMenu = new Menu(shell, SWT.DROP_DOWN);
		item.setMenu(setMenu);
		item = new MenuItem(setMenu, SWT.PUSH);
		item.setText("設定還書日期(&r)");
		item.setAccelerator(SWT.CTRL + 'r');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				SetMiscDialog setMiscDialog = new SetMiscDialog(shell);
				setMiscDialog.open();
			}
		});

		tItems.get(0).getControl().dispose();
		TaskTab taskTab = new TaskTab(netMan);
		tItems.get(0).setText(taskTab.getText());
		tItems.get(0).setControl(taskTab.createTabFloderPage(tabFolder));
		tItems.get(0).setData(taskTab.getFocusControl());
		tItems.add(new TabItem(tabFolder, SWT.NONE));
		QueryHoldsTab qhTab = new QueryHoldsTab(netHoldMan, netMan);
		tItems.get(1).setText(qhTab.getText());
		tItems.get(1).setControl(qhTab.createTabFloderPage(tabFolder));
		tItems.get(1).setData(qhTab.getFocusControl());
		netMan.start();
		this.tabFolder.setSelection(tItems.get(1));
		qhTab.getFocusControl().forceFocus();

		this.tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TabItem tabItem = (TabItem) e.item;
				((Control) tabItem.getData()).forceFocus();
			}
		});
		getShell().pack();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//if (CUS.checkAppVersion())
		//	return;
		new WorkPlugUtils().run();
	}

	private void run() {
		setBlockOnOpen(true);
		open();
		this.netMan.addTask("shutdown");
		Display.getCurrent().dispose();
	}

	@Override
	protected Control createContents(Composite parent) {
		getShell().setText(CUS.shellTitle);
		getShell().setSize(800, 600);
		// getShell().setBounds(0, 0, 1920, 1080);
		constrainShellSize();
		this.tabFolder = new TabFolder(parent, SWT.NONE);
		this.tItems.add(new TabItem(tabFolder, SWT.NONE));
		LogonTab logonTab = new LogonTab(netMan, this);
		tItems.get(0).setText(logonTab.getText());
		tItems.get(0).setControl(logonTab.createTabFloderPage(tabFolder));
		return super.createContents(parent);
	}

	public void setActiveTabItem(final int i) {
		tabFolder.getDisplay().asyncExec(new Runnable() {
			public void run() {
				tabFolder.setSelection(i);
				((Control) tItems.get(i).getData()).forceFocus();
			}
		});
	}

}
