package name.pgollangi.gradle.sonarlinter;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonarsource.sonarlint.core.ConnectedSonarLintEngineImpl;
import org.sonarsource.sonarlint.core.client.api.common.Language;
import org.sonarsource.sonarlint.core.client.api.common.ModulesProvider;
import org.sonarsource.sonarlint.core.client.api.common.Version;
import org.sonarsource.sonarlint.core.client.api.connected.ConnectedAnalysisConfiguration;
import org.sonarsource.sonarlint.core.client.api.connected.ConnectedGlobalConfiguration;
import org.sonarsource.sonarlint.core.client.api.connected.ConnectedSonarLintEngine;
import org.sonarsource.sonarlint.core.client.api.connected.ConnectedSonarLintEngine.State;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneAnalysisConfiguration;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneGlobalConfiguration;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneSonarLintEngine;
import org.sonarsource.sonarlint.core.serverapi.EndpointParams;

import name.pgollangi.gradle.sonarlinter.http.ApacheHttpClient;

public class SonarLinter {

	private static final Logger LOGGER = LoggerFactory.getLogger(SonarLinter.class);
	private LinterConfiguration config;
	private GradleLogOutput logOutput;
	private GradleProgressMonitor progressMonitor;
	private GradleIssueListener issueListener;

	public SonarLinter(LinterConfiguration config) {
		this.config = config;
	}

	public void lint() throws MalformedURLException {

		this.logOutput = new GradleLogOutput(LOGGER);

		this.progressMonitor = new GradleProgressMonitor(LOGGER, null);
		this.issueListener = new GradleIssueListener(this.logOutput);

		ModulesProvider modulesProvider = new GradleModulesProvider(this.config.getProjectDir());

		EnginesFactory factory = new EnginesFactory(config, logOutput, modulesProvider);

		switch (config.getMode()) {
		case STANDALONE:
			this.analyzeStandalone(factory);
		case CONNECTED:
			this.analyzeConnected(factory);
			break;
		default:
			LOGGER.warn("Unsupported linter mode: " + this.config.getMode());
		}

		File projectDir = new File("C:\\dev\\ot2\\corecm\\ui");
		StandaloneGlobalConfiguration engineConfig = StandaloneGlobalConfiguration.builder()
				.addEnabledLanguages(Language.values())
//				.addPlugin(new File(
//						"C:\\Users\\pgollang\\.gradle\\caches\\modules-2\\files-2.1\\org.sonarsource.javascript\\sonar-javascript-plugin\\7.3.0.15071\\ab2916914cc004559d8dc8b55657c2fd50bcba27\\sonar-javascript-plugin-7.3.0.15071.jar")
//								.toURL())
//                .apply {
//                    pluginFiles.forEach(file ->
//                        val pluginUrl = file.toURI().toURL()
//                        logger.debug("Use Sonar plugin: {}", pluginUrl)
//                        addPlugin(pluginUrl)
//                    )
//                }
//                .setSonarLintUserHome(homeDir.toPath())
//                .setWorkDir(workDir.toPath())
				.setLogOutput(logOutput)
				.setNodeJs(Paths.get("C:\\Program Files\\nodejs\\node.exe"), Version.create("14.17.0")).build();

//		StandaloneSonarLintEngine engine = new StandaloneSonarLintEngineImpl(engineConfig);

		ConnectedGlobalConfiguration connectedGlobalConfiguration = ConnectedGlobalConfiguration.builder()
				.addEnabledLanguages(Language.JS, Language.JAVA).setConnectionId("https://sonarqube.otxlab.net")
				.setStorageRoot(Paths.get("C:\\dev\\poc\\gradle-sonarlint\\sstorage"))
				.setNodeJs(Paths.get("C:\\Program Files\\nodejs\\node.exe"), Version.create("14.17.0")).build();

//		ServerConfiguration sConfig = ServerConfiguration.builder()
//				.url("https://sonarqube.otxlab.net")
//				.token("a751bb74069e32f892cbc21384683f7fedd70c52")
//				.userAgent("sonarlint")
//				.build();

		EndpointParams endpointParams = new EndpointParams("https://sonarqube.otxlab.net", false, null);
		ApacheHttpClient httpClient = ApacheHttpClient.create().withToken("a751bb74069e32f892cbc21384683f7fedd70c52");
		ConnectedSonarLintEngineImpl engine = new ConnectedSonarLintEngineImpl(connectedGlobalConfiguration);
		if (engine.getState() == State.NEVER_UPDATED) {
			System.out.println("ENGINE STATE " + engine.getState());
			engine.update(endpointParams, httpClient, progressMonitor);
			System.out.println("ENGINE STATE " + engine.getState());
			engine.updateProject(endpointParams, httpClient, "OT2_CORECM_Dev:refs_heads_Dev", false, progressMonitor);
		}
		engine.start();

		SonarSourceFile file = new SonarSourceFile(new File(
				"C:\\dev\\ot2\\corecm\\src\\main\\java\\com\\opentext\\core\\cm\\api\\hateoas\\model\\PageModel.java"),
				"C:\\dev\\ot2\\corecm\\src\\main\\java\\com\\opentext\\core\\cm\\api\\hateoas\\model\\PageModel.java",
				false, Charset.defaultCharset(), "java");

//		StandaloneAnalysisConfiguration analysisConfig = StandaloneAnalysisConfiguration.builder()
//				.setBaseDir(projectDir.toPath()).putExtraProperty("moduleKey", "file:///c:/dev/ot2/corecm/ui")
//				.putExtraProperty("projectKey", "OT2_CORECM_Dev:refs_heads_Dev").addInputFiles(file).build();

		ConnectedAnalysisConfiguration analysisConfig = ConnectedAnalysisConfiguration.builder()
				.setProjectKey("OT2_CORECM_Dev:refs_heads_Dev").setBaseDir(projectDir.toPath()).addInputFiles(file)
				.build();

		engine.analyze(analysisConfig, issueListener, logOutput, progressMonitor);

	}

	private void analyzeStandalone(EnginesFactory factory) {
		StandaloneAnalysisConfiguration analysisConfig = StandaloneAnalysisConfiguration.builder()
				.setBaseDir(this.config.getProjectDir()).setModuleKey(this.config.getProjectDir().toUri())
//				.addInputFiles(file)
				.build();

		StandaloneSonarLintEngine engine = factory.createStandaloneEngine();
		engine.analyze(analysisConfig, issueListener, logOutput, progressMonitor);
	}

	private void analyzeConnected(EnginesFactory factory) {
		ConnectedAnalysisConfiguration analysisConfig = ConnectedAnalysisConfiguration.builder()
				.setProjectKey("OT2_CORECM_Dev:refs_heads_Dev").setBaseDir(this.config.getProjectDir())
//				.addInputFiles(file)
				.build();

		ConnectedSonarLintEngine engine = factory.createConnectedEngine("");

		engine.analyze(analysisConfig, issueListener, logOutput, progressMonitor);

	}

}
