package fomjar.server;

import java.util.HashMap;
import java.util.Map;

public class FjHttpMsg extends FjMsg {
	
	private static final String ln = "\r\n";
	
	private String title;
	private Map<String, String> head;
	private StringBuffer body;
	
	public FjHttpMsg(String http) {
		String[] lines = http.split(ln);
		int phase = 0;
		for (String line : lines) {
			switch (phase) {
			case 0:
				phase++;
				title = line;
				break;
			case 1:
				if (0 == line.length()) {
					phase++;
					break;
				}
				if (null == head) head = new HashMap<String, String>();
				String[] kvs = line.split(";");
				for (String kv : kvs) {
					String k = kv.substring(0, kv.indexOf(":")).trim();
					String v = kv.substring(kv.indexOf(":") + 1).trim();
					head.put(k, v);
				}
				break;
			case 2:
				if (null == body) body = new StringBuffer();
				body.append(line + ln);
				break;
			default:
				break;
			}
		}
	}
	
	public String title() {
		return title;
	}
	
	public String head(String key) {
		if (null == head) return null;
		return head.get(key);
	}
	
	public Map<String, String> heads() {
		return head;
	}
	
	public String body() {
		return body.toString();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(title + ln);
		for (Map.Entry<String, String> e : head.entrySet()) {
			sb.append(e.getKey() + ": " + e.getValue() + ln);
		}
		sb.append(ln);
		sb.append(body);
		return sb.toString();
	}

}
