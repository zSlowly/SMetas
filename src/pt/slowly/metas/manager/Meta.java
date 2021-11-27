package pt.slowly.metas.manager;

public class Meta {
	
	private int metaTotal;
	private int metaPaga;
	private String faction_tag;
	
	public Meta(int metaTotal, int metaPaga, String faction_tag) {
		this.metaTotal = metaTotal;
		this.metaPaga = metaPaga;
		this.faction_tag = faction_tag;
	}

	public int getMetaTotal() {
		return metaTotal;
	}

	public void setMetaTotal(int metaTotal) {
		this.metaTotal = metaTotal;
	}

	public int getMetaPaga() {
		return metaPaga;
	}

	public void setMetaPaga(int metaPaga) {
		this.metaPaga = metaPaga;
	}

	public String getFaction_tag() {
		return faction_tag;
	}

	public void setFaction_tag(String faction_tag) {
		this.faction_tag = faction_tag;
	}

}
