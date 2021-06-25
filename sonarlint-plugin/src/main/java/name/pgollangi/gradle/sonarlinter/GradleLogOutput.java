package name.pgollangi.gradle.sonarlinter;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.sonarsource.sonarlint.core.client.api.common.LogOutput;

public final class GradleLogOutput implements LogOutput {
	private final Logger logger;

	public GradleLogOutput(@NotNull Logger logger) {
		this.logger = logger;
	}

	public void log(@NotNull String formattedMessage, @NotNull LogOutput.Level level) {
//		switch (level) {
//		case TRACE:
//			this.logger.trace(formattedMessage);
//			break;
//		case DEBUG:
//			this.logger.debug(formattedMessage);
//			break;
//		case WARN:
//			this.logger.warn(formattedMessage);
//			break;
//		case ERROR:
//			this.logger.error(formattedMessage);
//			break;
//		case INFO:
//			this.logger.info(formattedMessage);
//			break;
//		}
		System.out.println(formattedMessage);
	}
}
