package net;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.printing.Printer;

public class PrintManager extends Thread {
	private BlockingQueue<String[]> taskQueue = new LinkedBlockingQueue<String[]>();
	private List<String[]> plist = new LinkedList<String[]>();
	private GC gc;
	private Printer printer;
	private Rectangle rect;
	private int hMargin;
	private int vMargin;

	public PrintManager() {
		this.setName("PrintManager");
		this.printer = new Printer();
		setParam();
	}

	@Override
	public void run() {
		for (;;) {
			String[] strs;
			try {
				strs = taskQueue.take();
				Integer taskId = new Integer(strs[0]);
				if (taskId == 0) {
					if (plist.size() > 0)
						printerTask();
					this.printer.dispose();
					break;
				}
				switch (taskId) {
				case -1:
					String[] pstrs = new String[strs.length - 1];
					System.arraycopy(strs, 1, pstrs, 0, pstrs.length);
					plist.add(pstrs);
					if (plist.size() >= CUS.ppNum)
						printerTask();
					break;
				case -2:
					if (plist.size() > 0)
						printerTask();
					break;

				default:
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void printerTask() {
		int i = 0;
		String[] strs;
		this.gc = new GC(printer);
		printer.startJob("Transit Item page");
		printer.startPage();
		while (i < CUS.ppNum && !plist.isEmpty()) {
			strs = plist.remove(0);
			Point po = new Point(0, 0);
			po.x += this.rect.width * (i % CUS.pnPerWidth);
			po.y += this.rect.height * (i++ / CUS.pnPerWidth);
			printItem(po, strs, printer);
		}
		printer.endPage();
		printer.endJob();
		gc.dispose();
	}

	private void printItem(Point po, String[] strs, Printer printer) {
		Font font = new Font(printer, "Arial", 54, SWT.BOLD);
		this.gc.setFont(font);
		int height = gc.getFontMetrics().getHeight();
		po.x += this.hMargin;
		po.y += this.vMargin;
		gc.drawString(CUS.brnClynumMap.get(strs[1]) + strs[2], po.x, po.y);
		po.y += height;
		gc.drawString(CUS.brnNameMap.get(strs[1]), po.x, po.y);
		po.y += height;
		font.dispose();
		font = new Font(printer, "Arial", 16, SWT.NONE);
		this.gc.setFont(font);
		height = gc.getFontMetrics().getHeight();
		gc.drawString("From: " + CUS.brnNameMap.get(strs[0]), po.x, po.y);
		for (int i = 3; i < strs.length; i++) {
			if (strs[i] != null) {
				po.y += height;
				if (i != strs.length - 1) {
					gc.drawString(strs[i], po.x, po.y);
				} else {
					gc.drawString("典藏館:" + CUS.brnNameMap.get(strs[i]), po.x, po.y);
				}
			}
		}
		font.dispose();
	}

	public void setPrinter(Printer printer) {
		if (this.printer != null)
			this.printer.dispose();
		this.printer = printer;
		setParam();
	}

	private void setParam() {
		this.rect = printer.getBounds();
		Rectangle clientArea = printer.getClientArea();
		Point po = this.printer.getDPI();
		this.hMargin = Math.max(po.x / 2 - (rect.width - clientArea.width) / 2, (rect.width - clientArea.width) / 2);
		this.vMargin = Math.max(po.y / 2 - (rect.height - clientArea.height) / 2,
				(rect.height - clientArea.height) / 2);
		this.rect.width /= CUS.pnPerWidth;
		this.rect.height /= CUS.pnPerHeight;
	}

	public void addTask(String[] datas) {
		try {
			taskQueue.put(datas);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
