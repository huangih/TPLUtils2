package net;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import swt.client.QueryHoldsTab;
import swt.client.comp.NoNumHoldsDialog;
import swt.client.comp.OverHoldsDialog;

public class NetHoldMan extends Thread {
	private BlockingQueue<String[]> taskQueue = new LinkedBlockingQueue<String[]>();
	private OverHoldsDialog overHoldsDialog;
	private NoNumHoldsDialog noNumHoldsDialog;
	private NetManager netMan;

	public NetHoldMan(NetManager netMan) {
		this.setName("NetHoldMan");
		this.netMan = netMan;
	}

	@Override
	public void run() {
		for (;;) {
			try {
				String[] strs = taskQueue.take();
				Integer taskId = new Integer(strs[0]);
				if (taskId == 0)
					break;
				switch (taskId) {
				case -1:
					synOEHoldsStatusTask(strs[1]);
					break;
				case -3:
					listOverHoldsTask(strs[1]);
					break;
				case -4:
					listNONumHoldsTask(strs[1]);
					break;
				case -5:
					listNoticeResultsTask(strs[1]);
					break;
				default:
					workTask(strs);
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				netMan.shutdown();
			}
		}
	}

	private void synOEHoldsStatusTask(String oeHolds) {
		List<String> l = Arrays.asList(oeHolds.split("\\|"));
		this.netMan.synOEHoldsStatus(l);
	}

	private void listNoticeResultsTask(String hId) {
		this.netMan.getResultsWithHoldId(hId);
	}

	private void listNONumHoldsTask(String brnIdList) {
		List<String[]> noNumHolds = this.netMan.listNONumHolds(brnIdList);
		this.noNumHoldsDialog.setNoNumHolds(noNumHolds);
	}

	private void listOverHoldsTask(String brnIdList) {
		ArrayList<String[]> overHolds = this.netMan.listOverHolds(brnIdList);
		this.overHoldsDialog.setOverHolds(overHolds);
	}

	private void workTask(String[] datas) {
	}

	public void addTask(String[] datas) {
		try {
			taskQueue.put(datas);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setQueryHoldsTab(QueryHoldsTab queryHoldsTab) {
	}

	public void setOverHoldsDialog(OverHoldsDialog overHoldsDialog) {
		this.overHoldsDialog = overHoldsDialog;
	}

	public void setNoNumHoldsDialog(NoNumHoldsDialog noNumHoldsDialog) {
		this.noNumHoldsDialog = noNumHoldsDialog;
	}
}
