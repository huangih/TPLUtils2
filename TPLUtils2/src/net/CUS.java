package net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import tw.gov.tpl.holdnotice_service.HoldnoticeService;
import tw.gov.tpl.holdnotice_service.HoldnoticeServicePortType;

public class CUS {
	final static private String VERSION = "1.0.20210423";
	final static private String PROGNAME = "TPLUtils2";
	final static private String URLPATH = "http://webcat4.tpml.edu.tw/tpl.notice.hold.service/";
	private static Properties properties = new Properties();
	public static ColorRegistry CR;
	public static FontRegistry FR;
	public static int minSeqNum = 1;
	public static int curSeqNum = 1;
	public static int maxSeqNum;
	public static String shellTitle = "臺北市立圖書館預約外掛系統 (" + VERSION + ")";
	public static String NetHostName = null;
	public static int NetPort;
	public static Charset charset = Charset.forName("UTF-8");
	public static String BranchID;
	public static String TaskTabText;
	public static String QueryHoldsTabText;
	public static String[] tableHead;
	public static String[] ovHoldsTableHead;
	public static String[] oeHoldsTableHead;
	public static String[] nnHoldsTableHead;
	public static int tableListSize = 3000;
	public static Map<String, String> regColorMap = new HashMap<String, String>();
	public static Map<String, String> messageMap = new HashMap<String, String>();
	public static Map<String, String> noticeTypeMap = new HashMap<String, String>();
	public static String TransitStatusMA = null;
	public static String ReceiveStatusMA = null;
	public static String CheckinStatusMA = null;
	public static String ONHoldsStatusMA = null;
	public static String AvailHoldStatusMA;
	public static String RemoveHoldStatusMA;
	public static String UnAvailHoldStatusMA;
	public static Map<String, String> brnClynumMap = new HashMap<String, String>();
	public static Map<String, String> brnNameMap = new HashMap<String, String>();
	public static int ppNum;
	public static int SeqClass = 0;
	public static boolean Debug = false;
	public static int pnPerWidth;
	public static int pnPerHeight;
	public static Map<String, Integer> specialLocationMap = new HashMap<String, Integer>();
	public static Map<String, Integer> specialItemtypeMap = new HashMap<String, Integer>();
	public static Map<String, Integer> specialLibraryMap = new HashMap<String, Integer>();
	public static boolean EXPANDAVAILHOLDDATE = false;
	public static boolean MULTIRECEIVEITEM = false;
	public static Map<String, String> ChargedCheckMap = new HashMap<String, String>();
	public static String CheckinLocation = "NEWARRIVAL";
	public static List<String> noTransitItemTypes = new ArrayList<String>();

	static {
		Properties props = new Properties();
		try {
			URL url = new URL(URLPATH + "CUS.properties");
			props.loadFromXML(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		NetHostName = props.getProperty("NetHostName", "libsys.tpml.edu.tw");
		NetPort = Integer.parseInt(props.getProperty("NetPort", "17171"));
		TaskTabText = props.getProperty("TaskTabText");
		QueryHoldsTabText = props.getProperty("QueryHoldsTabText");
		TransitStatusMA = props.getProperty("TransitStatusMA");
		ReceiveStatusMA = props.getProperty("ReceiveStatusMA");
		CheckinStatusMA = props.getProperty("CheckinStatusMA");
		ONHoldsStatusMA = props.getProperty("ONHoldsStatusMA");
		AvailHoldStatusMA = props.getProperty("AvailHoldStatusMA");
		RemoveHoldStatusMA = props.getProperty("RemoveHoldStatusMA");
		UnAvailHoldStatusMA = props.getProperty("UnAvailHoldStatusMA");
		CheckinLocation = props.getProperty("CheckinLocation");

		if (props.containsKey("Debug"))
			Debug = props.getProperty("Debug").equals("YES") ? true : false;

		List<String[]> temp = new ArrayList<String[]>();
		List<String[]> temp1 = new ArrayList<String[]>();
		List<String[]> temp2 = new ArrayList<String[]>();
		List<String[]> temp3 = new ArrayList<String[]>();
		for (Object key : props.keySet()) {
			int index = ((String) key).indexOf("NoticeType_");
			if (index == 0)
				noticeTypeMap.put(((String) key).substring(11), (String) props.getProperty((String) key));

			index = ((String) key).indexOf("registerColor_");
			if (index == 0) {
				String value = (String) props.getProperty((String) key);
				regColorMap.put(((String) key).substring(14), value);
			}

			index = ((String) key).indexOf("MessageMap_");
			if (index == 0) {
				String value = (String) props.getProperty((String) key);
				messageMap.put("$(" + ((String) key).substring(11) + ")", value);
			}

			index = ((String) key).indexOf("ChargedCheckMap_");
			if (index == 0) {
				String value = (String) props.getProperty((String) key);
				ChargedCheckMap.put(value, ((String) key).substring(16));
			}

			index = ((String) key).indexOf("TableHead_");
			if (index == 0) {
				String value = (String) props.getProperty((String) key);
				temp.add(new String[] { ((String) key).substring(10), value });
			}

			index = ((String) key).indexOf("ovHoldsTableHead_");
			if (index == 0) {
				String value = (String) props.getProperty((String) key);
				temp1.add(new String[] { ((String) key).substring(17), value });
			}

			index = ((String) key).indexOf("oeHoldsTableHead_");
			if (index == 0) {
				String value = (String) props.getProperty((String) key);
				temp2.add(new String[] { ((String) key).substring(17), value });
			}

			index = ((String) key).indexOf("nnHoldsTableHead_");
			if (index == 0) {
				String value = (String) props.getProperty((String) key);
				temp3.add(new String[] { ((String) key).substring(17), value });
			}
		}
		tableHead = new String[temp.size()];
		for (String[] strs : temp) {
			int i = Integer.parseInt(strs[0]);
			tableHead[i] = strs[1];
		}
		ovHoldsTableHead = new String[temp1.size()];
		for (String[] strs : temp1) {
			int i = Integer.parseInt(strs[0]);
			ovHoldsTableHead[i] = strs[1];
		}
		oeHoldsTableHead = new String[temp2.size()];
		for (String[] strs : temp2) {
			int i = Integer.parseInt(strs[0]);
			oeHoldsTableHead[i] = strs[1];
		}
		nnHoldsTableHead = new String[temp3.size()];
		for (String[] strs : temp3) {
			int i = Integer.parseInt(strs[0]);
			nnHoldsTableHead[i] = strs[1];
		}

		try {
			properties.loadFromXML(new FileInputStream("CUS.txt"));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		shellTitle = properties.containsKey("自訂Title") ? properties.getProperty("自訂Title") : "臺北市立圖書館預約外掛系統";
		shellTitle += " (" + VERSION + ")";
		BranchID = properties.getProperty("館別代號");
		if (properties.containsKey("是否多館混收通還"))
			MULTIRECEIVEITEM = "Y".equals(properties.get("是否多館混收通還"));
		if (properties.containsKey("點收不調撥館藏類型"))
			noTransitItemTypes = Arrays.asList(properties.getProperty("點收不調撥館藏類型").split(","));
		maxSeqNum = Integer.parseInt((String) properties.getProperty("流水編號最大值"));
		curSeqNum = Integer.parseInt((String) properties.getProperty("目前之流水編號"));
		ppNum = Integer.parseInt((String) properties.getProperty("每頁列印份數"));
		pnPerWidth = Integer.parseInt((String) properties.getProperty("每頁左右列印份數"));
		pnPerHeight = Integer.parseInt((String) properties.getProperty("每頁上下列印份數"));
		if (properties.containsKey("流水編號種類"))
			SeqClass = Integer.parseInt((String) properties.getProperty("流水編號種類"));
		if (properties.containsKey("流水編號最小值"))
			minSeqNum = Integer.parseInt((String) properties.getProperty("流水編號最小值"));
		curSeqNum = Math.min(Math.max(curSeqNum, minSeqNum), maxSeqNum);

		for (Object key : properties.keySet()) {
			int index = ((String) key).indexOf("需提醒館藏地_");
			if (index == 0) {
				int value = Integer.parseInt((String) properties.getProperty((String) key));
				specialLocationMap.put(((String) key).substring(7), value);
			}
		}

		for (Object key : properties.keySet()) {
			int index = ((String) key).indexOf("需提醒館藏類型_");
			if (index == 0) {
				int value = Integer.parseInt((String) properties.getProperty((String) key));
				specialItemtypeMap.put(((String) key).substring(8), value);
			}
		}

		for (Object key : properties.keySet()) {
			int index = ((String) key).indexOf("需提醒館藏館_");
			if (index == 0) {
				int value = Integer.parseInt((String) properties.getProperty((String) key));
				specialLibraryMap.put(((String) key).substring(7), value);
			}
		}
	}

	public static void saveProp() {
		File cusFile = new File("CUS.txt");
		StringBuilder sb = new StringBuilder();
		BufferedReader rd;
		try {
			rd = new BufferedReader(new InputStreamReader(new FileInputStream(cusFile), charset));
			String s;
			while ((s = rd.readLine()) != null) {
				if (s.contains("目前之流水編號")) {
					int inx = s.indexOf('>');
					int inx1 = s.indexOf('<', inx);
					s.replace(s.subSequence(inx, inx1), ">" + curSeqNum + "<");
				}
				sb.append(s + System.lineSeparator());
			}
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedWriter wr;
		try {
			wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cusFile), charset));
			wr.append(sb);
			wr.flush();
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean checkAppVersion() {
		HoldnoticeServicePortType port = new HoldnoticeService().getHoldnoticeServicePort();
		List<String> l = port.checkNewVersion(VERSION, PROGNAME);
		if (l != null && l.size() > 0) {
			String[] ss = l.toArray(new String[l.size()]);
			for (int i = 0; i < ss.length; i++)
				downloadFile(ss[i]);
			try {
				Runtime.getRuntime().exec(new String[] { "javaw", "-jar", ss[0] });
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	private static void downloadFile(String name) {
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(name));
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("B" + name));
			byte[] data = new byte[2048];
			int count;
			while ((count = in.read(data, 0, 2048)) != -1) {
				out.write(data, 0, count);
			}
			in.close();
			out.flush();
			out.close();
			URL url = new URL(URLPATH + name);
			in = new BufferedInputStream(url.openStream());
			out = new BufferedOutputStream(new FileOutputStream(name));
			while ((count = in.read(data, 0, 2048)) != -1) {
				out.write(data, 0, count);
			}
			in.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initResourceRegistry(Display display) {
		CR = new ColorRegistry(display);
		for (String key : regColorMap.keySet()) {
			String[] ss = regColorMap.get(key).split(",");
			CR.put(key, new RGB(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]), Integer.parseInt(ss[2])));
		}
		FR = new FontRegistry(display);
	}
}
