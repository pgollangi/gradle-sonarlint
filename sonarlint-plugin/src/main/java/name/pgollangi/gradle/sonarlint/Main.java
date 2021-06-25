package name.pgollangi.gradle.sonarlint;

import name.pgollangi.gradle.sonarlinter.SonarLinter;

public class Main {

	public static void main(String[] args) throws Exception {

		SonarLinter linter = new SonarLinter(null);
		linter.lint();
	}
}
