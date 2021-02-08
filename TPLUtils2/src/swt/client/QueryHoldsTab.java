package swt.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import swt.client.comp.ColumnSorter;
import tw.gov.tpl.holdnotice_service.NoticeResult;

import net.CUS;
import net.NetHoldMan;
import net.NetManager;

public class QueryHoldsTab {

	private NetHoldMan netHoldMan;
	private Text text;
	private List<String[]> itemList = new ArrayList<String[]>();
	private List<String[]> cancelItems = new ArrayList<String[]>();
	private List<Integer> seqNumList = new ArrayList<Integer>();
	private Table listTable;
	private StyledText resText;
	private String userStrs = "";
	// private String seqNums = "";
	private List<StyleRange> styleRangeList = new LinkedList<StyleRange>();
	private NetManager netMan;
	private Table cancelTable;
	private boolean isExpanded = false;

	public QueryHoldsTab(NetHoldMan netHoldMan, NetManager netMan) {
		this.netMan = netMan;
		this.netMan.setQueryHoldsTab(this);
		this.netHoldMan = netHoldMan;
		this.netHoldMan.setQueryHoldsTab(this);
	}

	public String getText() {
		return CUS.QueryHoldsTabText;
	}

	public Text getFocusControl() {
		return this.text;
	}

	public Composite createTabFloderPage(Composite parent) {
		Composite tabFolderPage = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.makeColumnsEqualWidth = true;
		tabFolderPage.setLayout(gridLayout);

		Label label = new Label(tabFolderPage, SWT.NONE);
		label.setText("借閱證號 : ");
		GridData data = new GridData();
		data.horizontalAlignment = GridData.END;
		label.setLayoutData(data);

		this.text = new Text(tabFolderPage, SWT.BORDER);
		text.setText("");
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		// data.horizontalSpan = 2;
		text.setLayoutData(data);

		Font font = new Font(text.getDisplay(), "Arial", 16, SWT.NONE);
		text.setFont(font);
		text.setEditable(true);
		text.forceFocus();
		// font.dispose();

		text.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				e.text = e.text.toUpperCase();
			}
		});
		text.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				cancelItems.clear();
				String str = text.getText();
				text.setText("");
				if (str.length() > 1)
					netMan.addTask(str);
			}
		});

		text.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				text.getFont().dispose();
			}

		});
		Button clrButton = new Button(tabFolderPage, SWT.PUSH);
		clrButton.setText("清除");
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		clrButton.setLayoutData(data);
		clrButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				itemList.clear();
				listTable.setItemCount(itemList.size());
				listTable.clearAll();
				cancelItems.clear();
				cancelTable.setItemCount(cancelItems.size());
				cancelTable.clearAll();
				userStrs = "";
				seqNumList.clear();
				if (styleRangeList.size() > 0)
					styleRangeList.clear();
				resText.setText("");
				text.setFocus();
				super.widgetSelected(e);
			}
		});

		this.resText = new StyledText(tabFolderPage, SWT.BORDER | SWT.MULTI);
		resText.setText("");
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		data.heightHint = 64;
		resText.setLayoutData(data);

		resText.setFont(font);
		resText.setEditable(false);

		resText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String text = resText.getText();
				if (styleRangeList.size() > 0 && text.length() > 1) {
					int inx = text.indexOf('\n') + 1;
					if (inx > 1) {
						for (StyleRange styleRange : styleRangeList)
							styleRange.start += inx;
						resText.setStyleRanges(styleRangeList
								.toArray(new StyleRange[0]));
						for (StyleRange styleRange : styleRangeList)
							styleRange.start -= inx;
					}
				}
			}
		});

		final Table table = new Table(tabFolderPage, SWT.BORDER | SWT.MULTI
				| SWT.FULL_SELECTION | SWT.VIRTUAL);
		this.listTable = table;
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		data = new GridData();
		data.widthHint = 300;
		data.heightHint = 160;
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		table.setLayoutData(data);

		table.addListener(SWT.SetData, new Listener() {
			public void handleEvent(Event e) {
				TableItem item = (TableItem) e.item;
				int index = table.indexOf(item);
				// NoticeProp nProp = noticeProps[index];
				item.setData(itemList.get(index)[8]);
				item.setText(itemList.get(index));
				if (itemList.get(index)[5].equals("熱門！"))
				{
					item.setForeground(new Color(listTable.getDisplay(), 255,
							63, 31));
				}
			};
		});
		table.setItemCount(itemList.size());

		this.listTable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem item = (TableItem) e.item;
				String hId = (String) item.getData();
				netHoldMan.addTask(new String[] { "-5", hId });
				super.widgetSelected(e);
			}
		});

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

		//TableColumn[] columns = new TableColumn[CUS.tableHead.length];		
		TableColumn[] columns = new TableColumn[CUS.tableHead.length + 3];	//陳躍升 2013.5.10 新增讀者姓名欄位
		
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new TableColumn(table, SWT.LINE_SOLID);
			//陳躍升 2013.8.23 新增館藏類型欄位
			if (i == columns.length - 3)
				columns[i].setText("熱門館藏");
			else if (i == columns.length - 2)						//陳躍升 2013.5.10 新增讀者姓名欄位
				columns[i].setText("讀者證號");						//陳躍升 2013.5.10 新增讀者姓名欄位
			else if (i == columns.length - 1)						//陳躍升 2013.5.10 新增讀者姓名欄位
				columns[i].setText("讀者姓名");						//陳躍升 2013.5.10 新增讀者姓名欄位
			else													//陳躍升 2013.5.10 新增讀者姓名欄位
				columns[i].setText(CUS.tableHead[i]);
			columns[i].addListener(SWT.Selection, sortListener);
		}
		for (TableColumn column : columns) {
			column.pack();
		}

		label = new Label(tabFolderPage, SWT.NONE);
		label.setFont(font);
		label.setForeground(new Color(label.getDisplay(), 255, 0, 0));
		label.setText("讀者未取預約資料如下:");

		this.cancelTable = new Table(tabFolderPage, SWT.BORDER | SWT.MULTI
				| SWT.FULL_SELECTION | SWT.VIRTUAL);
		this.cancelTable.setHeaderVisible(true);
		this.cancelTable.setLinesVisible(true);
		data = new GridData();
		data.widthHint = 300;
		data.heightHint = 160;
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		this.cancelTable.setLayoutData(data);

		this.cancelTable.addListener(SWT.SetData, new Listener() {
			public void handleEvent(Event e) {
				TableItem item = (TableItem) e.item;
				int index = cancelTable.indexOf(item);
				item.setData(cancelItems.get(index)[8]);
				item.setText(cancelItems.get(index));
				if (Integer.parseInt(cancelItems.get(index)[7]) == 1)
					item.setForeground(new Color(cancelTable.getDisplay(), 255,
							63, 31));
			};
		});
		cancelTable.setItemCount(this.cancelItems.size());

		this.cancelTable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem item = (TableItem) e.item;
				String hId = (String) item.getData();
				netHoldMan.addTask(new String[] { "-5", hId });
				super.widgetSelected(e);
			}
		});

		Listener sortListener1 = new Listener() {
			public void handleEvent(Event e) {
				TableColumn sortColumn = cancelTable.getSortColumn();
				TableColumn currentColumn = (TableColumn) e.widget;
				int direction = cancelTable.getSortDirection();
				if (sortColumn == currentColumn)
					direction = direction == SWT.UP ? SWT.DOWN : SWT.UP;
				else {
					cancelTable.setSortColumn(currentColumn);
					direction = SWT.DOWN;
				}
				cancelTable.setSortDirection(direction);
				int index = cancelTable.indexOf(currentColumn);
				Collections.sort(cancelItems,
						new ColumnSorter(index, direction));
				cancelTable.clearAll();
			}
		};

		columns = new TableColumn[CUS.ovHoldsTableHead.length];
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new TableColumn(cancelTable, SWT.LINE_SOLID);
			columns[i].setText(CUS.ovHoldsTableHead[i]);
			columns[i].addListener(SWT.Selection, sortListener1);
		}
		this.cancelTable.setSortColumn(columns[columns.length - 2]);
		this.cancelTable.setSortDirection(SWT.DOWN);
		// this.cancelTable.setForeground(new
		// Color(this.cancelTable.getDisplay(),
		// 255, 63, 31));
		for (TableColumn column : columns) {
			column.pack();
		}
		return tabFolderPage;
	}

	public void addUserSeqNums(final String userstr,
			List<Map<String, String>> aholds) {
		for (Map<String, String> m : aholds) {
			String s = m.get("HN");
			int inx = s.indexOf("#");
			if (inx > 0)
				inx = Integer.parseInt(s.substring(0, inx))
						+ Integer.parseInt(s.substring(inx + 1)) * 10000;
			else
				inx = Integer.parseInt(s);
			this.seqNumList.add(inx);
		}
		Collections.sort(this.seqNumList);
		if (this.styleRangeList.size() > 0)
			this.styleRangeList.clear();

		this.resText.getDisplay().asyncExec(new Runnable() {
			public void run() {
				String seqNums = "";
				int s1 = 0, s2 = 0, s3, s4 = 0;
				for (Integer num : seqNumList) {
					s3 = num / 10000;
					if (s3 != s1) {
						s1 = s3;
						s2 = styleRangeList.size();
						s4 = seqNums.length();
						Color color = resText.getDisplay().getSystemColor(
								SWT.COLOR_WHITE + s3 * 2);
						StyleRange styleRange = new StyleRange(s4, 4, color,
								null);
						styleRangeList.add(styleRange);
						if (s2 > 0)
							styleRangeList.get(s2).length = s4
									- styleRangeList.get(s2).start;
					}
					seqNums += String.format("%03d", num.intValue() % 10000)
							+ ", ";
				}
				s2 = styleRangeList.size();
				s4 = seqNums.length();
				if (s2 > 0)
					styleRangeList.get(s2 - 1).length = s4
							- styleRangeList.get(s2 - 1).start;
				userStrs += ", " + userstr;
				resText.setText(userStrs.substring(2) + "\n" + seqNums);
			}
		});
	}

	public void addCancelItems(List<String> strList) {
		for (String str : strList)
			this.cancelItems.add(str.split("\\^"));

		Collections.sort(cancelItems, new ColumnSorter(6, SWT.DOWN));

		this.cancelTable.getDisplay().asyncExec(new Runnable() {
			public void run() {
				cancelTable.setItemCount(cancelItems.size());
				cancelTable.clearAll();
			}
		});
	}

	public void setNoticeResults(final List<NoticeResult> results) {
		this.cancelTable.getDisplay().asyncExec(new Runnable() {

			public void run() {
				Shell dialog = new Shell(cancelTable.getShell(), SWT.RESIZE
						| SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				dialog.setText("逾期未取預約通知結果");
				dialog.setLayout(new FillLayout());
				Tree resultTree = new Tree(dialog, SWT.NONE);
				for (NoticeResult noticeResult : results) {
					if (noticeResult.getResult() == null)
						continue;
					TreeItem resultItem = new TreeItem(resultTree, SWT.NONE);
					String str = CUS.noticeTypeMap.get(noticeResult.getPkid()
							.getType())
							+ "-"
							+ noticeResult.getPkid().getNum()
							+ "次結果 : "
							+ noticeResult.getResult();
					resultItem.setText(str);
					new TreeItem(resultTree, SWT.NONE).setText(noticeResult
							.getLastStatus());
					new TreeItem(resultTree, SWT.NONE).setText(noticeResult
							.getStatusTime().toString());
					new TreeItem(resultItem, SWT.MULTI).setText(noticeResult
							.getDescString());
					new TreeItem(resultItem, SWT.NONE).setText(noticeResult
							.getStartTime().toString());
					resultTree.addListener(SWT.Expand, new Listener() {
						public void handleEvent(Event event) {
							isExpanded = true;
							// System.out.println("expanded event.");
						}
					});
					resultTree.addListener(SWT.Collapse, new Listener() {
						public void handleEvent(Event event) {
							isExpanded = false;
							// System.out.println("Collapse event.");
						}
					});
					resultItem.setExpanded(isExpanded);
				}

				dialog.pack();
				dialog.open();
			}

		});
	}

	public void displayNoUser(final String uId) {
		resText.getDisplay().asyncExec(new Runnable() {
			public void run() {
				resText.setText("無此借閱證號: " + uId);
			}
		});
	}

	public void addListItems(List<String[]> strsList) {
		for (String[] strs : strsList)
			this.itemList.add(strs);
		listTable.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				listTable.setItemCount(itemList.size());
				listTable.clearAll();
			}
		});
	}
}
