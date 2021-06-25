package name.pgollangi.gradle.sonarlint.api;

import java.util.Set;

import org.sonarsource.sonarlint.core.client.api.common.Language;

public class LinterConfiguration {
	private LinterMode mode;
	private Set<Language> enabledLanguages;
}
