package name.pgollangi.gradle.sonarlint;

import org.gradle.api.provider.Property;

import name.pgollangi.gradle.sonarlint.api.LinterMode;

abstract public class SonarLintPluginExtension {

	abstract public Property<LinterMode> getMode();

	abstract public Property<LinterMode> getServerUrl();

	abstract public Property<LinterMode> getToken();

	abstract public Property<LinterMode> getProjectKey();

}
