package swt.client.comp;

import java.util.Comparator;

import org.eclipse.swt.SWT;

public class ColumnSorter implements Comparator<String[]> {
	private int index;
	private boolean isAsc;

	public int compare(String[] o1, String[] o2) {
		String s1 = o1[index];
		String s2 = o2[index];
		if (s1 == null || s1.trim().length() == 0)
			s1 = null;
		if (s2 == null || s2.trim().length() == 0)
			s2 =null;
		if (s1 == null && s2 == null)
			return 0;
		else if (s1 == null)
			return 1;
		else if (s2 == null)
			return -1;
		else if (isAsc)
			return s1.compareTo(s2);
		else
			return s2.compareTo(s1);
	}

	public ColumnSorter(int index, int direction) {
		super();
		this.index = index;
		this.isAsc = direction == SWT.UP ? true : false;
	}

}
