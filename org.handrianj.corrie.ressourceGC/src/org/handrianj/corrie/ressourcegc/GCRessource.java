package org.handrianj.corrie.ressourcegc;

/**
 * Interface used to represend a disposable ressource to be managed by the
 * ressource GC
 *
 * @author Heri Andrianjafy
 *
 */
public class GCRessource<O extends Object> {

	private O object;

	private long lastUsedTime;

	public GCRessource(O object) {

		this.object = object;

	}

	/**
	 * Returns the object to be used
	 *
	 * @return
	 */
	public O getKey() {

		return object;
	}

	/**
	 * Updates the last time that the ressource was used
	 */
	public void updateRessourceTime() {

		lastUsedTime = System.currentTimeMillis();
	}

	/**
	 * Returns the last time that the ressource has been used
	 */
	public long getLastUsedTime() {
		return lastUsedTime;
	}

}
