package uk.co.optimisticpanda.pdfreport.util;

public enum CssColour {
	RED("C00"),
	BLUE("30C"),
	GREEN("060"),
	WHITE("FFF"),
	BLACK("000");
	
	private String code;

	private CssColour(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
