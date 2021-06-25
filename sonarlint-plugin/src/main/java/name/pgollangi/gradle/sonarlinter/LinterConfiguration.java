package name.pgollangi.gradle.sonarlinter;

import java.util.Set;

import org.sonarsource.sonarlint.core.client.api.common.Language;

public class LinterConfiguration {
	private LinterMode mode;
	private Set<Language> enabledLanguages;
	private String pathToNodeExecutable;

	public String getPathToNodeExecutable() {
		return pathToNodeExecutable;
	}
}
