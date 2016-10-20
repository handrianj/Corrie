package org.handrianj.corrie.ressourcegc;

/**
 * Interface for Ressource GC Services. This class will act like a garbage
 * collector. It will regularly check the ressource register to dispose unused
 * ressources after a certain amount of time in order to save memory. The
 * ressource is identifed via a key value. Default value for each check is 12
 * hours.
 *
 * @author Heri Andrianjafy
 *
 */
public interface IRessourceGC<N extends Comparable<N>> {

	/**
	 * Updates the timestamp for last usage of the current key
	 *
	 * @param keyToUpdate
	 */
	public void updateTimestampForRessource(N keyToUpdate);

	/**
	 * Adds a ressource to manage into the
	 *
	 * @param key
	 *            Key to add as a timestamp
	 */
	public void addRessource(N key);

	/**
	 * Returns the time interval between each ressource check
	 *
	 * @return
	 */
	public Long getCheckDelay();

	/**
	 * Updates the interval between each ressource check
	 *
	 * @param newCheckDelay
	 *            update interval in MS
	 */
	public void updateChechDelay(long newCheckDelay);

}
