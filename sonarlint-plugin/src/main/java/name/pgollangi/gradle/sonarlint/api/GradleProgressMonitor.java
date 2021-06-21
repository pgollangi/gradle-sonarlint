package name.pgollangi.gradle.sonarlint.api;

import org.gradle.initialization.BuildCancellationToken;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.sonarsource.sonarlint.core.client.api.common.ProgressMonitor;

public final class GradleProgressMonitor extends ProgressMonitor {
	private final Logger logger;

	private final BuildCancellationToken buildCancellationToken;

	public GradleProgressMonitor(@NotNull Logger logger, @NotNull BuildCancellationToken buildCancellationToken) {
		this.logger = logger;
		this.buildCancellationToken = buildCancellationToken;
	}

	public boolean isCanceled() {
		return false; //this.buildCancellationToken.isCancellationRequested();
	}

	public void setMessage(@NotNull String msg) {
		this.logger.debug(msg);
	}
}
