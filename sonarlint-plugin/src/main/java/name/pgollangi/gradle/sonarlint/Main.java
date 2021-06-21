package name.pgollangi.gradle.sonarlint;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import org.reflections.Reflections;
import org.sonar.api.Plugin;

import name.pgollangi.gradle.sonarlint.api.SonarLinter;

public class Main {

	public static void main(String[] args) throws Exception {

		Reflections reflections = new Reflections("org.sonar.plugins");

		Set<Class<? extends Plugin>> subTypes = reflections.getSubTypesOf(Plugin.class);

		subTypes.forEach(c -> {
			URI uri;
			try {
				uri = c.getProtectionDomain().getCodeSource().getLocation().toURI();
				System.out.println(uri);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

//		SonarLinter linter = new SonarLinter();
//		linter.lint();
	}
}
