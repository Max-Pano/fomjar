package fomjar.server.msg;

public class FjDSCPMessage extends FjJsonMessage {
	
	FjDSCPMessage(Object json) {super(json);}
	
	public String fs()  {return json().getString("fs");}
	public String ts()  {return json().getString("ts");}
	public String sid() {return json().getString("sid");}
	public int    ssn() {return json().getInt("ssn");}

	public boolean isValid() {
		if (!json().containsKey("fs"))  return false;
		if (!json().containsKey("ts"))  return false;
		if (!json().containsKey("sid")) return false;
		if (!json().containsKey("ssn")) return false;
		return true;
	}
	
}