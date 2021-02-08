package swt.client.comp;

import java.util.List;

import net.CUS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import tw.gov.tpl.holdnotice_service.NoticeResult;

public class NoticeResultsDialog extends Dialog {

	private List<NoticeResult> results;
	private static boolean isExpanded = false;

	public NoticeResultsDialog(Shell parent, List<NoticeResult> results) {
		this(parent, SWT.RESIZE | SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.results = results;
	}

	public NoticeResultsDialog(Shell parent, int style) {
		super(parent, style);
		setText("預約通知結果");
	}

	public void open() {
		Shell dialog = new Shell(getParent(), getStyle());
		dialog.setText(getText());
		createContents(dialog);
		dialog.pack(true);
		dialog.open();
	}

	private void createContents(Shell dialog) {
		Tree resultTree = new Tree(dialog, SWT.NONE);
		for (NoticeResult noticeResult : results) {
			if (noticeResult.getResult() == null)
				continue;
			TreeItem resultItem = new TreeItem(resultTree, SWT.NONE);
			String str = CUS.noticeTypeMap
					.get(noticeResult.getPkid().getType())
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
	}

}
