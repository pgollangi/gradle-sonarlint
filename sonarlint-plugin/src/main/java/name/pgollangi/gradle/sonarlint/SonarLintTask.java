package name.pgollangi.gradle.sonarlint;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.VerificationTask;

import name.pgollangi.gradle.sonarlint.api.LinterMode;

abstract public class SonarLintTask extends DefaultTask implements VerificationTask {

	@Input
	@Optional
	abstract public Property<LinterMode> getMode();

	@Input
	@Optional
	abstract public Property<LinterMode> getServerUrl();

	@Input
	@Optional
	abstract public Property<LinterMode> getToken();

	@Input
	@Optional
	abstract public Property<LinterMode> getProjectKey();

	@Override
	public String getDescription() {
		return "Execute SonarLint analysis locally with Standalone or SonarQube profiles";
	}

	@Override
	public String getGroup() {
		return "Verification";
	}

	@TaskAction
	public void performLint() {
		System.out.println("Retrieving artifact " + getMode().get() + " from " + getServerUrl().get());
		// issue HTTP call and parse response
	}
}
