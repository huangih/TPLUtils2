package swt.client;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import swt.client.comp.ItemListTable;
import swt.client.comp.ReceiveMan;

import net.CUS;
import net.NetManager;

public class TaskTab {

	private NetManager netMan;
	private ReceiveMan receMan;

	public TaskTab(NetManager netMan) {
		this.netMan = netMan;
	}

	public String getText() {
		return CUS.TaskTabText;
	}

	public Control createTabFloderPage(Composite parent) {
		Composite tabFolderPage = new Composite(parent, SWT.NONE);
		tabFolderPage.setLayout(new FillLayout());
		SashForm hSash = new SashForm(tabFolderPage, SWT.HORIZONTAL
				| SWT.SMOOTH);
		Composite lComposite = new Composite(hSash, SWT.BORDER);
		lComposite.setLayout(new FillLayout());
		Composite rComposite = new Composite(hSash, SWT.BORDER);
		rComposite.setLayout(new FillLayout());
		hSash.setWeights(new int[] { 50, 50 });

		SashForm lSash = new SashForm(lComposite, SWT.VERTICAL);
		lSash.setLayout(new FillLayout());
		this.receMan = new ReceiveMan(lSash, netMan);
		ItemListTable availHoldsComposite = new ItemListTable(lSash, SWT.BORDER);
		availHoldsComposite.setLayout(new FillLayout());
		netMan.setShowPane(1, availHoldsComposite);
		netMan.setShowPane(5, availHoldsComposite);
		lSash.setWeights(new int[] { 40, 60 });

		SashForm rSash = new SashForm(rComposite, SWT.VERTICAL);
		rSash.setLayout(new FillLayout());
		ItemListTable chkinComposite = new ItemListTable(rSash, SWT.BORDER);
		netMan.setShowPane(2, chkinComposite);
		netMan.setShowPane(4, chkinComposite);
		chkinComposite.setLayout(new FillLayout());
		ItemListTable transComposite = new ItemListTable(rSash, SWT.BORDER);
		netMan.setShowPane(3, transComposite);
		transComposite.setLayout(new FillLayout());
		rSash.setWeights(new int[] { 50, 50 });
		return tabFolderPage;
	}

	public NetManager getNetMan() {
		return netMan;
	}

	public ReceiveMan getReceMan() {
		return receMan;
	}
	public Text getFocusControl() {
		return this.receMan.getFocusControl();
	}
	
}
