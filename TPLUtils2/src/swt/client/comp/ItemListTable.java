package swt.client.comp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.CUS;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ItemListTable extends Composite {

	private Table listTable;
	private List<String[]> itemList = new ArrayList<String[]>();

	public ItemListTable(Composite parent, int style) {
		super(parent, style);
		final Table table = new Table(this, SWT.BORDER | SWT.MULTI
				| SWT.FULL_SELECTION | SWT.VIRTUAL);
		this.listTable = table;

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addListener(SWT.SetData, new Listener() {
			public void handleEvent(Event e) {
				TableItem item = (TableItem) e.item;
				int index = table.indexOf(item);
//				NoticeProp nProp = noticeProps[index];
//				item.setData(nProp);
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
		TableColumn[] columns = new TableColumn[CUS.tableHead.length];
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new TableColumn(table, SWT.LINE_SOLID);
			columns[i].setText(CUS.tableHead[i]);
			columns[i].addListener(SWT.Selection, sortListener);
		}
		for (TableColumn column : columns) {
			column.pack();
		}
	}

	public void addListItem(String[] strs) {
		itemList.add(0,strs);
		listTable.getDisplay().asyncExec(new Runnable() {
			public void run() {
				listTable.setItemCount(itemList.size());
				listTable.clearAll();
			}
		});
		
	}

}
