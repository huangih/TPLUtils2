package swt.client.comp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.CUS;
import net.NetHoldMan;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class NoNumHoldsDialog extends Dialog {

	private NetHoldMan netHoldMan;
	private Table table;
	private List<String[]> itemList = new ArrayList<String[]>();
	private Button clsButton;

	public NoNumHoldsDialog(Shell parent, NetHoldMan netHoldMan) {
		this(parent, SWT.RESIZE | SWT.BORDER | SWT.APPLICATION_MODAL);
		this.netHoldMan = netHoldMan;
	}

	public NoNumHoldsDialog(Shell parent, int style) {
		super(parent, style);
		setText("尚待給予流水編號之預約書");
	}

	public void open() {
		Shell dialog = new Shell(getParent(), getStyle());
		Point size = dialog.computeSize(360, 300, true);
		dialog.setSize(size.x, size.y);
		dialog.setText(getText());
		createContents(dialog);
		netHoldMan.setNoNumHoldsDialog(this);
		netHoldMan.addTask(new String[] { "-4", CUS.BranchID });
		dialog.pack(true);
		dialog.open();
	}

	private void createContents(final Shell dialog) {
		// Composite dialogPage = new Composite(dialog, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		dialog.setLayout(gridLayout);

		this.clsButton = new Button(dialog, SWT.PUSH);
		clsButton.setText("關閉");
		clsButton.setEnabled(false);
		clsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				dialog.close();
			}
		});
		this.table = new Table(dialog, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.VIRTUAL);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		// data = new GridData();
		// data.widthHint = 300;
		// data.heightHint = 240;
		// data.horizontalAlignment = GridData.FILL;
		// data.horizontalSpan = 2;
		// data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		table.setLayoutData(data);

		table.addListener(SWT.SetData, new Listener() {
			public void handleEvent(Event e) {
				TableItem item = (TableItem) e.item;
				int index = table.indexOf(item);
				item.setText(itemList.get(index));
			};
		});
		table.setItemCount(itemList.size());

		Listener sortListener = new Listener() {
			public void handleEvent(Event e) {
				TableColumn sortColumn = table.getSortColumn();
				TableColumn currentColumn = (TableColumn) e.widget;
				int direction = table.getSortDirection();
				if (sortColumn == currentColumn)
					direction = direction == SWT.UP ? SWT.DOWN : SWT.UP;
				else {
					table.setSortColumn(currentColumn);
					direction = SWT.UP;
				}
				int index = table.indexOf(currentColumn);
				Collections.sort(itemList, new ColumnSorter(index, direction));
				table.setSortDirection(direction);
				table.clearAll();
			}
		};

		TableColumn[] columns = new TableColumn[CUS.nnHoldsTableHead.length];
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new TableColumn(table, SWT.LINE_SOLID);
			columns[i].setText(CUS.nnHoldsTableHead[i]);
			columns[i].addListener(SWT.Selection, sortListener);
		}
		for (TableColumn column : columns) {
			column.pack();
		}
	}

	public void setNoNumHolds(List<String[]> noNumHolds) {
		this.itemList.clear();
		for (String[] strs : noNumHolds)
			this.itemList.add(strs);
		this.table.getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (itemList.size() > 0) {
					// int size = itemList.size() > 40 ? 40 : itemList.size();
					table.setItemCount(itemList.size());
					table.setSortColumn(table.getColumn(4));
					table.setSortDirection(SWT.UP);
					table.getColumn(5).notifyListeners(SWT.Selection,
							new Event());
					table.clearAll();
					Shell shell = table.getShell();
					Point psize = shell.computeSize(SWT.DEFAULT, SWT.DEFAULT);
					Rectangle monitorArea = shell.getMonitor().getClientArea();
					shell.setSize(Math.min(psize.x, monitorArea.width), Math
							.min(psize.y, monitorArea.height));
				}
				clsButton.setEnabled(true);
			}
		});
	}
}
