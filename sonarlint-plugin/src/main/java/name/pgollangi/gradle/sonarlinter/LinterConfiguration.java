package name.pgollangi.gradle.sonarlinter;

import java.nio.file.Path;
import java.util.Set;

import org.sonarsource.sonarlint.core.client.api.common.Language;

public class LinterConfiguration {
	private LinterMode mode;
	private Set<Language> enabledLanguages;
	private String pathToNodeExecutable;
	private Path projectDir;

	public String getPathToNodeExecutable() {
		return pathToNodeExecutable;
	}

	public LinterMode getMode() {
		return mode;
	}

	public void setMode(LinterMode mode) {
		this.mode = mode;
	}

	public Set<Language> getEnabledLanguages() {
		return enabledLanguages;
	}

	public void setEnabledLanguages(Set<Language> enabledLanguages) {
		this.enabledLanguages = enabledLanguages;
	}

	public void setPathToNodeExecutable(String pathToNodeExecutable) {
		this.pathToNodeExecutable = pathToNodeExecutable;
	}

	public Path getProjectDir() {
		return this.projectDir;
	}
}
