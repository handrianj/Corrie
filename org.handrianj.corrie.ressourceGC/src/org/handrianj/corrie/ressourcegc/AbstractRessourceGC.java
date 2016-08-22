package org.handrianj.corrie.ressourcegc;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class for Ressource GC Services. This class will act like a garbage
 * collector. It will regularly check the ressource register to dispose unused
 * ressources after a certain amount of time in order to save memory.
 *
 * @author Heri Andrianjafy
 *
 */
public abstract class AbstractRessourceGC<N extends Object> {

	private static Logger logger = LoggerFactory.getLogger(AbstractRessourceGC.class);

	/**
	 * Default check delay : 12 hours
	 */
	public static final Long DEFAULT_CHECK_DELAY = 43200000l;

	private CopyOnWriteArrayList<GCRessource<N>> ressourceList = new CopyOnWriteArrayList<>();

	private Job job;

	public AbstractRessourceGC(String jobName, long checkDelay) {

		job = new Job(jobName) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {

				ArrayList<GCRessource<N>> itemsToRemove = new ArrayList<>();

				logger.info("Running GC Job : " + getName());

				long currentTimeMillis = System.currentTimeMillis();

				for (GCRessource<N> r : ressourceList) {

					long elapsedTime = currentTimeMillis - r.getLastUsedTime();

					int compare = Long.compare(elapsedTime, checkDelay);

					if (compare > 0) {

						logger.info("Deleting ressource " + r);
						clearRessource(r);
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

	/**
	 * Adds a ressource to the list
	 *
	 * @param ressource
	 */
	protected void addRessource(GCRessource<N> ressource) {

		ressourceList.add(ressource);

	}

	/**
	 * Clears the ressource in the Java memory and performs post cleaning
	 * actions
	 *
	 * @param ressource
	 */
	protected abstract void clearRessource(GCRessource<N> ressource);

}
