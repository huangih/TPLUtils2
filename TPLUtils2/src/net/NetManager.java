package net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.XMLGregorianCalendar;

import net.comp.MsgException;

import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Composite;

import swt.client.QueryHoldsTab;
import swt.client.WorkPlugUtils;
import swt.client.comp.ItemListTable;
import swt.client.comp.ReceiveMan;
import tpl.libsys.netclient.DataServiceException;
import tpl.libsys.netclient.NetClientService;
import tpl.libsys.netclient.comp.RespData;
import tw.gov.tpl.holdnotice_service.BrnName;
import tw.gov.tpl.holdnotice_service.CancelExpiredHold;
import tw.gov.tpl.holdnotice_service.HoldnoticeService;
import tw.gov.tpl.holdnotice_service.HoldnoticeServicePortType;
import tw.gov.tpl.holdnotice_service.NoticeResult;
import tw.gov.tpl.holdnotice_service.UserHoldPriv;

public class NetManager extends Thread {
	// private final static DateFormat dateFormat = new SimpleDateFormat(
	// "yyyy/M/d hh:mm:ss");
	private final HoldnoticeServicePortType port = new HoldnoticeService().getHoldnoticeServicePort();
	private NetClientService ncs;
	private BlockingQueue<String> taskQueue = new LinkedBlockingQueue<String>();
	private Composite[] showMap = new Composite[6];
	private int subSeqNum = 1;
	private String[] ItemDatas;
	private QueryHoldsTab queryHoldsTab;
	private NetHoldMan netHoldMan;
	private PrintManager printMan;
	private WorkPlugUtils chkRecItem;
	private Vector<String> status = new Vector<String>();
	private String expandAvailHoldDate;
	private Map<String, String> errMap;
	private boolean needTransit;
	private String pkBrn;

	public NetManager(WorkPlugUtils workPlugUtils) {
		this.chkRecItem = workPlugUtils;
		this.setName("NetManager");

		this.expandAvailHoldDate = port.getExpandAvailHoldDate(CUS.BranchID);
		CUS.EXPANDAVAILHOLDDATE = expandAvailHoldDate.length() > 1;
		List<BrnName> brnList = port.getAllBrn();
		for (BrnName brnData : brnList) {
			String brnId = brnData.getId();
			CUS.brnClynumMap.put(brnId, brnData.getClynum());
			CUS.brnNameMap.put(brnId, brnData.getValue());
		}

		this.netHoldMan = new NetHoldMan(this);
		this.printMan = new PrintManager();
	}

	@Override
	public void run() {
		for (;;) {
			try {
				String taskId = taskQueue.take();
				// 2014.05.05 陳躍升調整NRRC條碼判定
				// if (taskId.matches("^[A-Z]RRC.*"))
				if (taskId.matches("^[A-Z]RRC.*") || taskId.matches("^ETC.*") || taskId.matches("[A-Z0-9]\\d{6,9}")) {
					this.chkRecItem.setActiveTabItem(0);
					workTask(taskId);
					continue;
				}
				if (taskId.equals("shutdown")) {
					shutdown();
					break;
				}
				if (taskId.equals("PRINT")) {
					printMan.addTask(new String[] { "-2" });
					continue;
				}
				this.chkRecItem.setActiveTabItem(1);
				queryTask(taskId);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (DataServiceException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	private void queryTask(final String taskId) {
		Map<String, String> m = new HashMap<String, String>();
		try {
			RespData resp = this.ncs.getRespDatas("gM",
					new String[] { "list_availHoldsWithUID_20130823.pl " + taskId + " " + CUS.BranchID }, true);
			m = resp.getDataMap();
			final String ukey = m.containsKey("U6") ? m.get("U6") : null;
			final String name = m.containsKey("UA") ? m.get("UA") : null;
			if (ukey == null) {
				queryHoldsTab.displayNoUser(taskId);
				return;
			}
			new Thread(new Runnable() {
				@Override
				public void run() {
					List<String> cancelHoldStrs = port.getUserAllCancelHolds(ukey);
					if (cancelHoldStrs.size() > 0)
						queryHoldsTab.addCancelItems(cancelHoldStrs);
				}
			}).start();
			final List<Map<String, String>> holds = resp.getLists("LD");
			if (holds.size() > 0)
				new Thread(new Runnable() {
					@Override
					public void run() {
						List<String[]> strsList = new ArrayList<String[]>();
						for (Map<String, String> map : holds) {
							String itemId = map.get("NQ");
							String itemType = map.get("IG");
							String hotStr = "";

							if (itemType.equals("HOT-BOOK") || itemType.equals("HOT-BA"))
								hotStr = "熱門！";

							try {
								RespData resps = ncs.getRespDatas("hD", new String[] { itemId }, false);
								Map<String, String> m1 = resps.getDataMap();
								String s = map.get("HN");
								int inx;
								if ((inx = s.indexOf('#')) > 0)
									s = s.substring(0, inx);
								strsList.add(new String[] { s, itemId, m1.get("IB"), m1.get("IA"), m1.get("IQ"), hotStr, // 陳躍升
																															// 2013.8.23新增館藏類型欄位
										taskId, // 陳躍升 2013.5.10 新增讀者姓名欄位
										name, // 陳躍升 2013.5.10 新增讀者姓名欄位
										map.get("HH") });
							} catch (DataServiceException e) {
								e.printStackTrace();
							}
						}
						queryHoldsTab.addListItems(strsList);
					}
				}).start();
			String userStrs = taskId + name + " ";
			UserHoldPriv uHoldPriv = port.getNoPrivDays(ukey);
			String s = "";
			if (uHoldPriv != null) {
				XMLGregorianCalendar date = uHoldPriv.getEndDate();
				s += " 停止預約至" + date.getMonth() + "/" + date.getDay();
			}
			userStrs += s;
			this.queryHoldsTab.addUserSeqNums(userStrs, holds);
		} catch (Exception e) {
			e.printStackTrace();
			this.errMap = m;
			m.put("err", e.getMessage());
			displayResult(-1, this.errMap);
		}
	}

	private void workTask(String taskId) throws DataServiceException {
		Map<String, String> m;
		String ma = null;

		String ig = null; // 20140505 陳躍升新增熱門館藏點收顯示

		while (true) {
			try {

				// 20150114陳躍升新增其他區域中心直接調給總館程式
				if (taskId.matches("^[A-M]RRC.*") || taskId.matches("^[O-Z]RRC.*")) {
					m = new LinkedHashMap<String, String>();
					int n = CUS.BranchID.indexOf(',');
					String brnID = n < 1 ? CUS.BranchID : CUS.BranchID.substring(0, n);
					m.put("nt", brnID);
					m.put("nu", "C01");
					m.put("warn", "送總館");
					displayResult(3, m);
					break;
				} else {
					m = chkItemStatus(taskId);
					// 20140505 陳躍升新增熱門館藏點收顯示
					ig = m.get("IG");
//					 20201112 黃裔宏修改可同時點收另館通還圖書(如C01及RRC),不產生調撥夾書單亦不提示
					if (this.status.remove("ONSHELF") && !this.needTransit) {
//					if (this.status.remove("ONSHELF"))
//						if (!this.needTransit || CUS.BranchID.contains(m.get("nu"))) {
						if (ma != null) {
							m.put("MA", ma);
							displayResult(2, m);
						} else
							displayResult(4, m);
						break;
					}
					if (this.status.remove("HOLD_CANCELLED")) {
						Map<String, String> m1 = unavailHold(m.get("HH"));
						if (this.status.size() == 0) {
							ma = m1.remove("MA");
							continue;
						}
					}

					if (this.status.remove("INTRANSIT")) {
						String rvBrn = m.get("nu");
						String reason = m.get("nx");
						boolean b = this.status.contains("ONHOLD");
						boolean b1 = "LIBRARY".equals(reason);
						boolean b2 = "HOLD".equals(reason);
						boolean b3 = rvBrn.equals(pkBrn);
						if ((b1 && b) || (b2 && !(b && b3))) {// 若為回館調撥且有人預約或預約調撥但已無預約或預約取書館已變更
							m = receiveItem(taskId, rvBrn);// 摸擬為調撥目的館點收，再進行下一check迴圏
							ma = m.remove("MA");
							continue;
						}
						if (!CUS.BranchID.contains(rvBrn)
								|| (b1 && !CUS.MULTIRECEIVEITEM && CUS.BranchID.indexOf(rvBrn) != 0)) {
							if (!CUS.BranchID.contains(m.get("nt")))
								m.put("warn", "送錯館");
							displayResult(3, m);
							break;
						}
						m = receiveItem(taskId, rvBrn);
						// 20140505 陳躍升新增熱門館藏點收顯示
						m.put("IG", ig);

						if (this.status.size() == 0) {
							displayResult(2, m);
							break;
						}
					}

					if (this.status.remove("CHARGED")) {
						m = checkinItem(taskId, m);

						// 20140505 陳躍升新增熱門館藏點收顯示
						m.put("IG", ig);

						if (CUS.CheckinLocation.contains(m.get("IL"))) {
							displayResult(2, m);
							break;
						} else {
							ma = m.remove("MA");
							continue;
						}
					}

					if (this.status.remove("ONHOLD") && !this.needTransit) {
						m = processOnHold(taskId);

						// 20140505 陳躍升新增熱門館藏點收顯示
						m.put("IG", ig);

						if (m.containsKey("sN"))
							displayResult(5, m);
//						else if (m.containsKey("sM")) // 20141223 陳躍升新同讀者給同流水號程式
//							displayResult(6, m);
						else
							displayResult(1, m);
						break;
					}

					if (needTransit) {
						if (CUS.noTransitItemTypes.contains(ig))
							displayResult(6, m); // 20210415 新增附件不自動調撥，使用提醒顯示
						else {
							m = transitItem(m);
							// 20140505 陳躍升新增熱門館藏點收顯示
							m.put("IG", ig);
							displayResult(3, m);
						}
						break;
					}
					m.put("MA", this.status.toString());
					throwError("workTask", m);
				}
			} catch (MsgException e) {
				e.printStackTrace();
				displayResult(-1, this.errMap);
				break;
			}
		}
	}

	private void throwError(String err, Map<String, String> m) throws MsgException {
		this.errMap = m;
		m.put("err", err);
		throw new MsgException(err + "_ERR: " + m.get("MA"));

	}

	private Map<String, String> unavailHold(final String holdKey) throws DataServiceException, MsgException {
		RespData resps = this.ncs.getRespDatas("hM", new String[] { holdKey }, true);
		Map<String, String> m = resps.getDataMap();
		if (!CUS.UnAvailHoldStatusMA.equals(m.get("MA")))
			throwError("UnavailHold", m);
		new Thread(new Runnable() {
			@Override
			public void run() {
				port.cancelHold(holdKey);
			}
		}).start();
		return m;
	}

	private Map<String, String> processOnHold(String itemId) throws DataServiceException, MsgException {
		// if (!CUS.BranchID.contains(map.get("HO"))
		// && map.get("IL").equals(CUS.ONHoldsStatusMA))
		// throwError("processOnHold", map);
		RespData resps = this.ncs.getRespDatas("fZ", new String[] { itemId }, true);
		Map<String, String> m = resps.getDataMap();
		resps = this.ncs.getRespDatas("fD", new String[] { itemId }, false);
		m.putAll(resps.getDataMap());
		if (!CUS.AvailHoldStatusMA.equals(m.get("MA")) || !CUS.BranchID.contains(m.get("HO")))
			throwError("processOnHold", m);
		String sn = getHoldSeqNum(m.get("HG"), m.get("HO"));
		int inx = sn.indexOf("000");

		if (inx == 0) {
			// 20141222 陳躍升新增同一讀者相同流水號功能
			// 因為從setHoldSeqNum回傳一直有問題,改直接重作一次
			// 要做預約改號碼要改這邊程式
			// MAP抓的是fZ和fD的值
			// ^@01fZHH17068218^HKTITLE^HESYSTEM^h7N^UOA12121212103^UkA121212121^UA測試帳號^NSL13^HOC01^PA111^HA2014/12/22^HBNEVER^GBNEVER^1hNEVER^HDNEVER^HG^4MN^DHSTANDARD^h4NEVER^h5NEVER^h6N^^O00184
			// ^@02fZMA$(9267)^MN9267^ILHOLDS^NQ10681619^IS1^IQ388.794 4734^I4388.794
			// 4734^NSL13^IA松岡達英 (Matsuoka, Tatsuhide),
			// 1944-^IB??????恐????????^HKTITLE^HESYSTEM^DHSTANDARD^HG^GB2014/12/27^HLA12121212103^HM測試帳號^HN26966966^DT1^^O00265
			// 要先用map裡的HL值用上面的queryTask的list_availHoldsWithUID_20130823.pl
			// 取得預約清單後抓流水號,HN值就是流水號
			// 修改list_availHoldsWithUID_20130823.pl,不要全部,只要一筆就好

			/*
			 * 2015/04/08 取消同一讀者併號 String seqNum = null; Map<String, String> m_HN = new
			 * HashMap<String, String>(); try { RespData resp = this.ncs.getRespDatas("gM",
			 * new String[] { "list_one_availHoldsWithUID_20141222.pl " + m.get("HL") + " "
			 * + CUS.BranchID }, true);
			 * 
			 * m_HN = resp.getDataMap(); int chkHN = Integer.parseInt(m_HN.get("HN")); if
			 * (chkHN > 0) { seqNum = m_HN.get("HN"); } }catch (Exception e) {
			 * e.printStackTrace(); }
			 */

			setHoldSeqNum(m, getHoldSeqNum(), itemId, CUS.SeqClass); // 這一行是原程式

			/*
			 * 2015/04/08 取消同一讀者併號 if (seqNum != null) { if (CUS.SeqClass > 0) //2015.03.17
			 * 在給同一索書號時補給流水編號種類 seqNum += "#" + CUS.SeqClass; m.put("sN", "same" + seqNum);
			 * } //2014.12.22 陳躍升程式結束
			 * 
			 */
		} else {
			inx = sn.indexOf('#');
			if (inx > 0)
				sn = sn.substring(0, inx);
			m.put("sN", sn);
		}
		return m;
	}

	// 外掛點收
	private Map<String, String> chkItemStatus(String taskId) throws DataServiceException, MsgException {
		RespData resps = this.ncs.getRespDatas("hD", new String[] { taskId }, false);
		Map<String, String> m = resps.getDataMap();
		if (m.containsKey("MA"))
			throwError("chkItemStatus", m);
		this.needTransit = "Y".equals(m.get("nr")) && (!CUS.MULTIRECEIVEITEM || !CUS.BranchID.contains(m.get("nu")));
		this.pkBrn = m.get("HO");
		List<Map<String, String>> l = resps.getLists("lt");
		this.status.clear();
		for (Map<String, String> map : l)
			this.status.add(map.get("nq"));
		this.ItemDatas = new String[] { "", m.get("NQ"), m.get("IB"), m.get("IA"), m.get("IQ"), m.get("NS") };
		// 20140120陳躍升修改新增抓取館藏類型
//		RespData resp_IG = this.ncs.getRespDatas("gM",
//				new String[] { "list_IG.pl " + taskId }, true);
		// 20201112黃裔宏修改抓取館藏類型方法
		resps = this.ncs.getRespDatas("GA", new String[] { taskId }, true);
//		Map<String, String> m_IG = resp_IG.getDataMap();
		m.put("IG", resps.getDataMap().get("IG"));
		return m;
	}

	private Map<String, String> transitItem(Map<String, String> m) throws DataServiceException, MsgException {
		String[] strs = new String[] { m.get("NQ"), m.get("nu"), m.get("nx"), "nr|Y" };
		RespData resps = this.ncs.getRespDatas("hE", strs, true);
		m.putAll(resps.getDataMap());
//		m = resps.getDataMap();
		if (CUS.TransitStatusMA.equals(m.get("MA")))
			return m;
		throwError("transitItem", m);
		return m;
	}

	private Map<String, String> receiveItem(String itemId, String rvBrn) throws DataServiceException, MsgException {
		RespData resps = this.ncs.getRespDatas("hF", new String[] { itemId, "FE|" + rvBrn }, true);
		Map<String, String> m = resps.getDataMap();
		if (!CUS.ReceiveStatusMA.equals(m.get("MA")))
			throwError("receiveItem", m);
		return m;
	}

	private Map<String, String> checkinItem(String itemId, Map<String, String> map)
			throws DataServiceException, MsgException {
		RespData resps = this.ncs.getRespDatas("EV", new String[] { itemId }, true);
		Map<String, String> m = resps.getDataMap();
		if (!CUS.CheckinStatusMA.equals(m.get("MA")))
			throwError("checkinItem", m);
		boolean b = true;
		for (String key : CUS.ChargedCheckMap.keySet())
			if (!key.contains(map.get(CUS.ChargedCheckMap.get(key)))) {
				b = false;
				break;
			}
		if (b) {
			port.sendReceiveItemID(itemId);
			resps = this.ncs.getRespDatas("IV", new String[] { itemId, "FE|C01", "IL|NEWARRIVAL" }, false);
			m.put("IL", resps.getDataMap().get("IL"));
		}
		return m;
	}

	public String getSeqNum(int i, String s) {
		if (i == 6)
			return String.format("%3d-%s", CUS.curSeqNum - 1, s);
		if (i > 1)
			return String.format("%03d-%d", CUS.curSeqNum - 1, subSeqNum++);
		return String.format("%03d", CUS.curSeqNum - i);
	}

	private String getHoldSeqNum() {
		this.subSeqNum = 1;
		if (CUS.curSeqNum > CUS.maxSeqNum)
			CUS.curSeqNum = CUS.minSeqNum;
		return String.format("%03d", CUS.curSeqNum++);
	}

	private void displayResult(final int style, final Map<String, String> m) {
		String seqNum = "0";
		switch (style) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			// case 6:
			seqNum = m.containsKey("warn") ? m.get("warn") : getSeqNum(style, style == 6 ? m.get("IG") : "");
			if (style == 5) {
				// 2014.12.23陳躍升新增同讀者給同流水號程式
				String sN = m.get("sN");
				if (sN.startsWith("same")) {
					sN = sN.substring(4);
					seqNum = sN;
					m.put("MA", "此圖書有相同讀者預約書到館，給相同流水號如上");
				}
				// 2014.12.23陳躍升程式結束
				else // else內的是原程式
				{
					seqNum = m.get("sN");
					m.put("MA", "此圖書已給流水編號如上");
				}
			}

			this.ItemDatas[0] = seqNum;
			((ItemListTable) showMap[style == 6 ? 2 : style]).addListItem(this.ItemDatas); // 原程式
			if (style == 3) {
				String[] printStrs = new String[9];
				System.arraycopy(this.ItemDatas, 1, printStrs, 4, 5);
				String reason = m.get("nx");
				// 增加運送原因符號列印，@表"通還回館"，#代表"預約到館"
				if ("LIBRARY".equals(reason))
					reason = "@";
				else if ("HOLD".equals(reason))
					reason = "#";
				else
					reason = ""; // 其他原因
				//
				System.arraycopy(new String[] { "-1", m.get("nt"), m.get("nu"), reason }, 0, printStrs, 0, 4);
				printMan.addTask(printStrs);
			}
			break;

		default:
			break;
		}

		m.put("NS", this.ItemDatas[5]);
		final String numStr = seqNum;
		final Composite comp = showMap[0];
		comp.getDisplay().asyncExec(new Runnable() {
			public void run() {
				((ReceiveMan) comp.getData()).setResultData(style, m, numStr);
			}
		});
	}

	public void addTask(String itemId) {
		try {
			taskQueue.put(itemId);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {
		netHoldMan.addTask(new String[] { "0" });
		printMan.addTask(new String[] { "0" });
		CUS.saveProp();
		this.ncs.shutdown();
	}

	public void setShowPane(int i, Composite composite) {
		this.showMap[i] = composite;
	}

	public void setPrinter(PrinterData printerData) {
		printMan.setPrinter(new Printer(printerData));
	}

	public NetHoldMan getNetHoldMan() {
		return netHoldMan;
	}

	public void setQueryHoldsTab(QueryHoldsTab queryHoldsTab) {
		this.queryHoldsTab = queryHoldsTab;
	}

	public QueryHoldsTab getQueryHoldsTab() {
		return queryHoldsTab;
	}

	public void getResultsWithHoldId(String hId) {
		List<NoticeResult> results = port.getResultsWithHoldId(Integer.parseInt(hId));
		this.queryHoldsTab.setNoticeResults(results);
	}

	private void setHoldSeqNum(Map<String, String> map, String seqNum, String itemId, int seqClass) {
		if (seqClass > 0)
			seqNum += "#" + seqClass;

		// 20141222 陳躍升新增同一讀者相同流水號功能
		// 要做預約改號碼要改這邊程式
		// MAP抓的是fZ和fD的值
		// ^@01fZHH17068218^HKTITLE^HESYSTEM^h7N^UOA12121212103^UkA121212121^UA測試帳號^NSL13^HOC01^PA111^HA2014/12/22^HBNEVER^GBNEVER^1hNEVER^HDNEVER^HG^4MN^DHSTANDARD^h4NEVER^h5NEVER^h6N^^O00184
		// ^@02fZMA$(9267)^MN9267^ILHOLDS^NQ10681619^IS1^IQ388.794 4734^I4388.794
		// 4734^NSL13^IA松岡達英 (Matsuoka, Tatsuhide),
		// 1944-^IB??????恐????????^HKTITLE^HESYSTEM^DHSTANDARD^HG^GB2014/12/27^HLA12121212103^HM測試帳號^HN26966966^DT1^^O00265
		// 要先用map裡的HL值用上面的queryTask的list_availHoldsWithUID_20130823.pl
		// 取得預約清單後抓流水號,HN值就是流水號
		// 修改list_availHoldsWithUID_20130823.pl,不要全部,只要一筆就好
		/*
		 * Map<String, String> m_HN = new HashMap<String, String>(); try { RespData resp
		 * = this.ncs.getRespDatas("gM", new String[] {
		 * "list_one_availHoldsWithUID_20141222.pl " + map.get("HL") + " " +
		 * CUS.BranchID }, true);
		 * 
		 * m_HN = resp.getDataMap(); int chkHN = Integer.parseInt(m_HN.get("HN")); if
		 * (chkHN > 0) { seqNum = m_HN.get("HN"); } }catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		// 2014.12.22 陳躍升程式結束

		String s = "[" + seqNum + "_" + map.get("HO") + "]";
		String s1 = null;
		if ((s1 = map.get("HG")) != null)
			s += s1.replaceFirst("^\\[\\d+#?\\d*_.+\\]", "");
		String[] params = new String[] { map.get("HH"), itemId, "HG|" + s,
				CUS.EXPANDAVAILHOLDDATE ? "GB|" + this.expandAvailHoldDate : null };
		addHoldComment("IZ", params);
		if (CUS.EXPANDAVAILHOLDDATE)
			map.put("GB", this.expandAvailHoldDate);
		port.chknewHoldNotice(map.get("HH"), itemId, map.get("HL"), map.get("HO"), map.get("GB"));
	}

	private String getHoldSeqNum(String hComment, String pickupLib) {
		String seqNum = "000";
		if (hComment != null) {
			Pattern pat = Pattern.compile("^\\[(\\d+#?\\d*)_(.+?)\\].*$");
			Matcher matcher = pat.matcher(hComment);
			if (matcher.matches() && matcher.group(2).equals(pickupLib))
				seqNum = matcher.group(1);
		}
		return seqNum;
	}

	private void addHoldComment(String cmdcode, String[] params) {
		String ma = "";
		int retry = 10;
		try {
			while (!"$(207)".equals(ma) && retry-- > 0) {
				if ("$(116)".equals(ma))
					try {
						sleep(1000);
					} catch (InterruptedException e) {
					}
				RespData resps = this.ncs.getRespDatas(cmdcode, params, false);
				ma = resps.getDataMap().get("MA");
			}
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
	}

	public List<String[]> listNONumHolds(String brnIdList) {
		ArrayList<String[]> noNumHolds = new ArrayList<String[]>();
		try {
			for (String brnId : brnIdList.split(",")) {
				RespData resps = this.ncs.getRespDatas("gM", new String[] { "list_noSeqnum_hold.pl " + brnId }, false);
				List<Map<String, String>> l = resps.getLists("LD");
				if (l.size() == 0)
					continue;
				for (Map<String, String> map : l) {
					String seqNum = getHoldSeqNum(map.get("HG"), brnId);
					if (seqNum.indexOf("000") == 0) {
						// Date date = new Date(
						// Long.parseLong(map.get("H5")) * 1000);
						// String s = dateFormat.format(date);
						resps = this.ncs.getRespDatas("hD", new String[] { map.get("NQ") }, false);
						map.putAll(resps.getDataMap());
						noNumHolds.add(new String[] { map.get("NQ"), map.get("IB"), map.get("IA"), map.get("IQ"),
								map.get("HL"), map.get("H5") });
					}
				}
			}
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return noNumHolds;
	}

	public ArrayList<String[]> listOverHolds(String brnIdList) {
		ArrayList<String[]> oeHolds = new ArrayList<String[]>();
		StringBuffer hIdSb = new StringBuffer();
		try {
			for (String brnId : brnIdList.split(",")) {
				List<CancelExpiredHold> ceHolds = port.getNotChkOEHoldIds(brnId);
				if (ceHolds.size() == 0)
					continue;
				Map<Integer, Integer> hidCountMap = new HashMap<Integer, Integer>();
				for (CancelExpiredHold ceHold : ceHolds)
					hidCountMap.put(ceHold.getHoldId(), ceHold.getEncount());
				StringBuffer sb = new StringBuffer();
				for (Integer hId : hidCountMap.keySet())
					sb.append("," + hId);
				List<Map<String, String>> hDatas = new ArrayList<Map<String, String>>();
				String s = sb.toString().substring(1);
				while (true) {
					int n = s.indexOf(',', 600);
					String s1 = n >= 600 ? s.substring(0, n) : s;
					RespData resps = this.ncs.getRespDatas("gM", new String[] { "list_OEHolds_status.pl " + s1 },
							false);
					hDatas.addAll(resps.getLists("LD"));
					if (n < 0)
						break;
					s = s.substring(n + 1);
				}
				if (hDatas == null || hDatas.size() == 0)
					continue;
				for (Map<String, String> map : hDatas) {
					String oHid = map.get("HH");
					RespData resps = this.ncs.getRespDatas("hD", new String[] { map.get("NQ") }, false);
					map.putAll(resps.getDataMap());
					if (oHid.equals(map.get("HH"))) {
						List<Map<String, String>> lm = resps.getLists("lt");
						List<String> l = new ArrayList<String>();
						for (Map<String, String> m : lm)
							l.add(m.get("nq"));
						boolean b = l.remove("HOLD_CANCELLED");
						// boolean nochk = l.size() == 0 && b;
						if (l.size() == 0 && b) {
							String seqNum = getHoldSeqNum(map.get("HG"), brnId);
							int hid = Integer.parseInt(map.get("HH"));
							String encount = hidCountMap.get(hid).toString();
							oeHolds.add(new String[] { seqNum, map.get("NQ"), map.get("IB"), map.get("IA"),
									map.get("IQ"), map.get("UO"), map.get("GB"), map.get("Hk"), encount });
						} else {
							hIdSb.append(map.get("HH") + "|");
							if (b)
								try {
									unavailHold(oHid);
								} catch (MsgException e) {
									e.printStackTrace();
								}
						}
					} else
						hIdSb.append(oHid + "|");
				}
			}
			if (hIdSb.length() > 0)
				this.netHoldMan.addTask(new String[] { "-1", hIdSb.toString() });
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return oeHolds;
	}

	public void synOEHoldsStatus(List<String> l) {
		port.chgOEHoldsStatus(l);
	}

	@Override
	public synchronized void start() {
		this.netHoldMan.start();
		this.printMan.start();
		super.start();
	}

	public String logon(String userID, String password) {
		String s = port.chkAccessNetClient(userID, password);
		if (s == null) {
			int n = CUS.BranchID.indexOf(',');
			String brnID = n < 1 ? CUS.BranchID : CUS.BranchID.substring(0, n);
			this.ncs = new NetClientService(50, 600, password, userID, brnID);
		}
		return s;
	}
}
