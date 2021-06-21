package name.pgollangi.gradle.sonarlint;

import java.io.File;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

public class SonarLintPlugin implements Plugin<Project> {

	@Override
	public void apply(Project proj) {
		Logger logger = proj.getLogger();
		logger.info("IN SonarLintPlugin");
		System.out.println("OUT IN SonarLintPlugin");
		File projectDir = proj.getProjectDir();
		String path = proj.getPath();
		SonarLintPluginExtension extension = proj.getExtensions().create("sonarlint", SonarLintPluginExtension.class);

		proj.getTasks().create("sonarlint", SonarLintTask.class, task -> {
			task.getMode().set(extension.getMode());
			task.getServerUrl().set(extension.getServerUrl());
		});
	}

}
