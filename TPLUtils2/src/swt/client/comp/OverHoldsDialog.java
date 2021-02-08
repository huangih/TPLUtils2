package swt.client.comp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.CUS;
import net.NetHoldMan;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
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

public class OverHoldsDialog extends Dialog {

	private StyledText listText;
	private Button clsButton;
	private List<StyleRange> styleRangeList = new LinkedList<StyleRange>();
	private Table listTable;
	private List<String[]> itemList = new ArrayList<String[]>();
	private ArrayList<Integer> seqNumList = new ArrayList<Integer>();
	private String seqNums = "";
	private NetHoldMan netHoldMan;

	public OverHoldsDialog(Shell parent, NetHoldMan netHoldMan) {
		this(parent, SWT.RESIZE | SWT.BORDER | SWT.APPLICATION_MODAL);
		this.netHoldMan = netHoldMan;
	}

	public OverHoldsDialog(Shell parent, int style) {
		super(parent, style);
		setText("逾期未取須取消預約書");
	}

	public void open() {
		Shell dialog = new Shell(getParent(), getStyle());
		Point size = dialog.computeSize(360, 300, true);
		dialog.setSize(size.x, size.y);
		dialog.setText(getText());
		createContents(dialog);
		netHoldMan.setOverHoldsDialog(this);
		netHoldMan.addTask(new String[] { "-3", CUS.BranchID });
		dialog.pack();
		dialog.open();
	}

	private void createContents(final Shell dialog) {
		// Composite dialogPage = new Composite(dialog, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		dialog.setLayout(gridLayout);

		Button prnButton = new Button(dialog, SWT.PUSH);
		prnButton.setText("列印流水編號");
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		// data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		prnButton.setLayoutData(data);
		clsButton = new Button(dialog, SWT.PUSH);
		clsButton.setText("關閉");
		clsButton.setEnabled(false);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		// data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		clsButton.setLayoutData(data);
		clsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				dialog.close();
			}
		});

		this.listText = new StyledText(dialog, SWT.BORDER | SWT.MULTI
				| SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL);
		listText.setText("");
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		data.heightHint = 64;
		listText.setLayoutData(data);

		Font font = new Font(listText.getDisplay(), "Arial", 16, SWT.NONE);
		listText.setFont(font);
		listText.setEditable(false);

		listText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String text = listText.getText();
				if (styleRangeList.size() > 0 && text.length() > 1)
					listText.setStyleRanges(styleRangeList
							.toArray(new StyleRange[0]));
			}
		});
		listText.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				listText.getFont().dispose();
			}

		});

		this.listTable = new Table(dialog, SWT.BORDER | SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.VIRTUAL);
		this.listTable.setHeaderVisible(true);
		this.listTable.setLinesVisible(true);
		data = new GridData();
		data.widthHint = 300;
		data.heightHint = 240;
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		this.listTable.setLayoutData(data);

		this.listTable.addListener(SWT.SetData, new Listener() {
			public void handleEvent(Event e) {
				TableItem item = (TableItem) e.item;
				int index = listTable.indexOf(item);
				// NoticeProp nProp = noticeProps[index];
				// item.setData(nProp);
				item.setText(itemList.get(index));
			};
		});
		this.listTable.setItemCount(itemList.size());

		Listener sortListener = new Listener() {
			public void handleEvent(Event e) {
				TableColumn sortColumn = listTable.getSortColumn();
				TableColumn currentColumn = (TableColumn) e.widget;
				int direction = listTable.getSortDirection();
				if (sortColumn == currentColumn)
					direction = direction == SWT.UP ? SWT.DOWN : SWT.UP;
				else {
					listTable.setSortColumn(currentColumn);
					direction = SWT.UP;
				}
				int index = listTable.indexOf(currentColumn);
				Collections.sort(itemList, new ColumnSorter(index, direction));
				listTable.setSortDirection(direction);
				listTable.clearAll();
			}
		};

		TableColumn[] columns = new TableColumn[CUS.oeHoldsTableHead.length];
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new TableColumn(listTable, SWT.LINE_SOLID);
			columns[i].setText(CUS.oeHoldsTableHead[i]);
			columns[i].addListener(SWT.Selection, sortListener);
		}
		for (TableColumn column : columns) {
			column.pack();
		}
	}

	public void setOverHolds(List<String[]> overHolds) {
		this.itemList.clear();
		this.seqNumList.clear();
		for (String[] strs : overHolds) {
			int num = strs[0].indexOf('#');
			num = num > 0 ? Integer.parseInt(strs[0].substring(num + 1))
					* 10000 + Integer.parseInt(strs[0].substring(0, num))
					: Integer.parseInt(strs[0]);
			this.seqNumList.add(num);
			this.itemList.add(strs);
		}
		Collections.sort(this.seqNumList);
		if (this.styleRangeList.size() > 0)
			this.styleRangeList.clear();

		this.listTable.getDisplay().asyncExec(new Runnable() {
			public void run() {
				seqNums = "";
				int s1 = 0, s2 = 0, s3, s4 = 0;
				for (Integer seqNum : seqNumList) {
					s3 = seqNum / 10000;
					if (s3 != s1) {
						s1 = s3;
						s2 = styleRangeList.size();
						s4 = seqNums.length();
						Color color = listText.getDisplay().getSystemColor(
								SWT.COLOR_WHITE + s3 * 2);
						StyleRange styleRange = new StyleRange(s4, 4, color,
								null);
						styleRangeList.add(styleRange);
						if (s2 > 0)
							styleRangeList.get(s2).length = s4
									- styleRangeList.get(s2).start;
					}
					seqNums += String.format("%03d", seqNum % 10000) + ", ";
				}
				s2 = styleRangeList.size();
				s4 = seqNums.length();
				if (s2 > 0)
					styleRangeList.get(s2 - 1).length = s4
							- styleRangeList.get(s2 - 1).start;
				listTable.setItemCount(itemList.size());
				listTable.clearAll();
				listText.setText(seqNums);
				clsButton.setEnabled(true);
			}
		});
	}
}
