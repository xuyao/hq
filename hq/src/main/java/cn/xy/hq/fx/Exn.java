package cn.xy.hq.fx;

public class Exn {
	
	private double current_price;
	private double current_price_cny;
	private String code;
	private String name;
	private String platform;
	private String platform_name;
	private String market;
	private double change_percent;
	private double volum_24h;
	private double amount ;
	
	public double getCurrent_price() {
		return current_price;
	}
	public void setCurrent_price(double current_price) {
		this.current_price = current_price;
	}
	public double getCurrent_price_cny() {
		return current_price_cny;
	}
	public void setCurrent_price_cny(double current_price_cny) {
		this.current_price_cny = current_price_cny;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getPlatform_name() {
		return platform_name;
	}
	public void setPlatform_name(String platform_name) {
		this.platform_name = platform_name;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public double getChange_percent() {
		return change_percent;
	}
	public void setChange_percent(double change_percent) {
		this.change_percent = change_percent;
	}
	public double getVolum_24h() {
		return volum_24h;
	}
	public void setVolum_24h(double volum_24h) {
		this.volum_24h = volum_24h;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
