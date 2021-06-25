package name.pgollangi.gradle.sonarlinter;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.reflections.Reflections;
import org.sonar.api.Plugin;
import org.sonarsource.sonarlint.core.ConnectedSonarLintEngineImpl;
import org.sonarsource.sonarlint.core.NodeJsHelper;
import org.sonarsource.sonarlint.core.StandaloneSonarLintEngineImpl;
import org.sonarsource.sonarlint.core.client.api.common.Language;
import org.sonarsource.sonarlint.core.client.api.common.LogOutput;
import org.sonarsource.sonarlint.core.client.api.common.ModulesProvider;
import org.sonarsource.sonarlint.core.client.api.common.Version;
import org.sonarsource.sonarlint.core.client.api.connected.ConnectedGlobalConfiguration;
import org.sonarsource.sonarlint.core.client.api.connected.ConnectedSonarLintEngine;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneGlobalConfiguration;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneSonarLintEngine;

public class EnginesFactory {

	private Path nodeJsPath = null;
	private Version nodeJsVersion = null;
	private ModulesProvider modulesProvider;

	private final LogOutput logOutput;
	private LinterConfiguration configuration;

	public EnginesFactory(LinterConfiguration configuration, LogOutput logOutput) {
		this.configuration = configuration;
		this.logOutput = logOutput;
		this.init();
	}

	private void init() {
		NodeJsHelper helper = new NodeJsHelper();
		helper.detect(Optional.ofNullable(configuration.getPathToNodeExecutable()).filter(StringUtils::isNotEmpty)
				.map(Paths::get).orElse(null));
		this.nodeJsPath = helper.getNodeJsPath();
		this.nodeJsVersion = helper.getNodeJsVersion();
	}

	public StandaloneSonarLintEngine createStandaloneEngine() {
		StandaloneGlobalConfiguration configuration = StandaloneGlobalConfiguration.builder()
				.setExtraProperties(prepareExtraProps()).addEnabledLanguages(getLanguages())
				.setNodeJs(nodeJsPath, nodeJsVersion).addPlugins(getStandaloneAnalyzers())
				.setModulesProvider(modulesProvider).setLogOutput(this.logOutput).build();

		StandaloneSonarLintEngine engine = new StandaloneSonarLintEngineImpl(configuration);

		return engine;
	}

	private URL[] getStandaloneAnalyzers() {
		Reflections reflections = new Reflections("org.sonar.plugins");

		Set<Class<? extends Plugin>> subTypes = reflections.getSubTypesOf(Plugin.class);

		URL[] analyzers = subTypes.stream().map(c -> {
			try {
				return c.getProtectionDomain().getCodeSource().getLocation().toURI();
			} catch (URISyntaxException e) {
				e.printStackTrace();
				return null;
			}
		}).filter(Objects::nonNull).toArray(URL[]::new);
		return analyzers;
	}

	public ConnectedSonarLintEngine createConnectedEngine(String connectionId) {

		ConnectedGlobalConfiguration configuration = ConnectedGlobalConfiguration.builder()
				.setConnectionId(connectionId).setExtraProperties(prepareExtraProps())
				.addEnabledLanguages(getLanguages()).setNodeJs(nodeJsPath, nodeJsVersion)
				.setModulesProvider(modulesProvider).setLogOutput(logOutput).build();

		ConnectedSonarLintEngine engine = new ConnectedSonarLintEngineImpl(configuration);
		return engine;
	}

	private Language[] getLanguages() {
		return null;
	}

	private Map<String, String> prepareExtraProps() {
		Map<String, String> extraProperties = new HashMap<>();
//		if (typeScriptPath != null) {
//			extraProperties.put(AnalysisManager.TYPESCRIPT_PATH_PROP, typeScriptPath.toString());
//		}
		return extraProperties;
	}

}
