

import java.util.ArrayList;
import java.util.List;

import net.CUS;
import net.NetHoldMan;
import net.NetManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
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

import swt.client.LogonTab;
import swt.client.QueryHoldsTab;
import swt.client.SWTClient;
import swt.client.TaskTab;
import swt.client.comp.NoNumHoldsDialog;
import swt.client.comp.OverHoldsDialog;
import swt.client.comp.SetMiscDialog;

public class ChkRecItem implements SWTClient {
	private NetManager netMan;
	// private TabItem tItem;
	private List<TabItem> tItems = new ArrayList<TabItem>();
	private TabFolder tabFolder;
	private NetHoldMan netHoldMan;
	private Shell shell;

	public ChkRecItem(Composite parent) {
		this.tabFolder = new TabFolder(parent, SWT.NONE);
		this.netMan = new NetManager(this);
		this.netHoldMan = this.netMan.getNetHoldMan();
		this.tItems.add(new TabItem(tabFolder, SWT.NONE));
		LogonTab logonTab = new LogonTab(netMan, this);
		tItems.get(0).setText(logonTab.getText());
		tItems.get(0).setControl(logonTab.createTabFloderPage(tabFolder));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (CUS.checkAppVersion())
			return;
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setText(CUS.shellTitle);
		ChkRecItem instance = new ChkRecItem(shell);
		setShellSize(instance, shell);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		instance.netMan.addTask("shutdown");
		display.dispose();
	}

	public static void setShellSize(ChkRecItem instance, Shell shell) {
		Point size = shell.computeSize(640, 360);
		Rectangle monitorArea = shell.getMonitor().getClientArea();
		shell.setSize(Math.min(size.x, monitorArea.width),
				Math.min(size.y, monitorArea.height));
	}

	public void initTask() {
		this.shell = tabFolder.getShell();
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
	}

	public void setActiveTabItem(final int i) {
		this.shell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				tabFolder.setSelection(i);
				((Control) tItems.get(i).getData()).forceFocus();
			}
		});
	}
}
