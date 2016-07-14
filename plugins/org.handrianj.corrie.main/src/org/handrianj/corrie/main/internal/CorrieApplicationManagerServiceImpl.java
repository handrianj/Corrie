package org.handrianj.corrie.main.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.handrianj.corrie.applicationmanagerservice.IApplicationConfig;
import org.handrianj.corrie.applicationmanagerservice.IApplicationManagerService;

public class CorrieApplicationManagerServiceImpl implements IApplicationManagerService {

	private Map<String, IApplicationConfig> nameToConfig = new ConcurrentHashMap<>();

	public void init() {
		nameToConfig = ExtensionReader.readExtensionPoint();
	}

	@Override
	public IApplicationConfig getConfigForApplication(String application) {

		return nameToConfig.get(application);
	}

	@Override
	public List<String> getAllAvailableApplications() {
		return new ArrayList<>(nameToConfig.keySet());
	}
}
