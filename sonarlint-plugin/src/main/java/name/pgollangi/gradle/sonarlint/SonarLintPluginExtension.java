package name.pgollangi.gradle.sonarlint;

import org.gradle.api.provider.Property;

import name.pgollangi.gradle.sonarlinter.LinterMode;

abstract public class SonarLintPluginExtension {

	abstract public Property<String> getMode();

	abstract public Property<String> getServerUrl();

	abstract public Property<String> getToken();

	abstract public Property<String> getProjectKey();

	public SonarLintPluginExtension() {
		getMode().convention(LinterMode.STANDALONE.name());
		getServerUrl().convention("");
		getToken().convention("");
		getProjectKey().convention("");
	}

}
