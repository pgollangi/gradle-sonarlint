package name.pgollangi.gradle.sonarlinter;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonarsource.sonarlint.core.client.api.common.ClientFileSystem;
import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;

public class FolderFileSystem implements ClientFileSystem {

	private Path folder;

	public FolderFileSystem(Path folder) {
		this.folder = folder;
	}

	@Override
	public Stream<ClientInputFile> files(String suffix, Type type) {
		try {
			return Files.walk(folder).filter(Files::isRegularFile)
					.filter(filePath -> filePath.toString().endsWith("." + suffix))
					.filter(filePath -> typeMatches(filePath.toUri(), type))
					.map(filePath -> toClientInputFile(filePath, type));
		} catch (IOException e) {
			throw new IllegalStateException("Cannot browse the files", e);
		}
	}

	private boolean typeMatches(URI uri, Type type) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Stream<ClientInputFile> files() {
		try {
			return Files.walk(folder).filter(Files::isRegularFile).map(filePath -> toClientInputFile(filePath,
					isTestFile(filePath.toUri()) ? InputFile.Type.TEST : InputFile.Type.MAIN));
		} catch (IOException e) {
			throw new IllegalStateException("Cannot browse the files", e);
		}
	}

	private boolean isTestFile(URI uri) {
		// TODO Auto-generated method stub
		return false;
	}

	private ClientInputFile toClientInputFile(Path filePath, InputFile.Type type) {
		return new SonarSourceFile(filePath, folder.relativize(filePath).toString(), isTestType(type));
	}

	private boolean isTestType(Type type) {
		// TODO Auto-generated method stub
		return false;
	}

}
