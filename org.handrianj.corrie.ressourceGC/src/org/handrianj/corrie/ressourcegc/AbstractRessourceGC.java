package org.handrianj.corrie.ressourcegc;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRessourceGC<N extends Comparable<N>> implements IRessourceGC<N> {

	private static Logger logger = LoggerFactory.getLogger(AbstractRessourceGC.class);

	/**
	 * Default check delay : 12 hours
	 */
	public static final Long DEFAULT_CHECK_DELAY = 43200000l;

	private CopyOnWriteArrayList<GCRessource<N>> ressourceList = new CopyOnWriteArrayList<>();

	private Job job;

	private Long checkDelay = DEFAULT_CHECK_DELAY;

	public AbstractRessourceGC(String jobName) {
		this(jobName, DEFAULT_CHECK_DELAY);
	}

	public AbstractRessourceGC(String jobName, long checkDelay) {
		this.checkDelay = checkDelay;

		job = new Job(jobName) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {

				ArrayList<GCRessource<N>> itemsToRemove = new ArrayList<>();

				logger.info("Running GC Job : " + getName());

				long currentTimeMillis = System.currentTimeMillis();

				for (GCRessource<N> r : ressourceList) {

					long elapsedTime = currentTimeMillis - r.getLastUsedTime();

					int compare = Long.compare(elapsedTime, getCheckDelay());

					if (compare > 0) {

						logger.info("Deleting ressource " + r);
						clearRessource(r.getKey());
						itemsToRemove.add(r);
					}
				}

				ressourceList.removeAll(itemsToRemove);

				schedule(checkDelay);
				return Status.OK_STATUS;
			}
		};

		job.setPriority(Job.LONG);
		job.schedule(checkDelay);

	}

	@Override
	public Long getCheckDelay() {
		return checkDelay;
	}

	/**
	 * Adds a ressource to the list
	 *
	 * @param ressource
	 */
	@Override
	public void addRessource(N key) {

		ressourceList.add(new GCRessource<N>(key));

	}

	protected CopyOnWriteArrayList<GCRessource<N>> getRessourceList() {
		return ressourceList;
	}

	/**
	 * Updates the timestamp usage of a ressource
	 *
	 * @param key
	 */
	@Override
	public void updateTimestampForRessource(N key) {
		for (GCRessource<N> ressource : getRessourceList()) {
			if (ressource.getKey().compareTo(key) == 0) {
				ressource.updateRessourceTime();
				break;
			}
		}

	}

	@Override
	public void updateChechDelay(long newCheckDelay) {
		checkDelay = newCheckDelay;

	}

	/**
	 * Clears the ressource in the Java memory and performs post cleaning
	 * actions
	 *
	 * @param key
	 *            Key of the ressource to be deleted
	 */
	protected abstract void clearRessource(N key);

}
