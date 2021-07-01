package name.pgollangi.gradle.sonarlinter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;

public final class SonarSourceFile implements Comparable<SonarSourceFile>, ClientInputFile {

	@NotNull
	private Path file;

	private boolean isTest;

	@NotNull
	private Charset charset;

	@Nullable
	private String language;

	private Path relativePath;

	public SonarSourceFile(@NotNull Path file, Path relativePath, boolean isTest) {
		this.file = file;
		this.relativePath = relativePath;
		this.isTest = isTest;
	}

	public final boolean isTest() {
		return this.isTest;
	}

	@NotNull
	public final Charset getCharset() {
		return this.charset;
	}

	@Nullable
	public final String getLanguage() {
		return this.language;
	}

	public int compareTo(@NotNull SonarSourceFile other) {
		return this.file.compareTo(other.file);
	}

	@NotNull
	public String toString() {
		return "SonarSourceFile(file=" + this.file + ", isTest=" + this.isTest + ", charset=" + this.charset
				+ ", language=" + this.language + ")";
	}

	public int hashCode() {
		if (this.isTest)
			;
		return ((((this.file != null) ? this.file.hashCode() : 0) * 31
				+ ((this.charset != null) ? this.charset.hashCode() : 0)) * 31
				+ ((this.language != null) ? this.language.hashCode() : 0));
	}

	public boolean equals(@Nullable Object paramObject) {
		if (this != paramObject) {
			if (paramObject instanceof SonarSourceFile) {
			}
		} else {
			return true;
		}
		return false;
	}

	@Override
	public String getPath() {
		return this.file.toAbsolutePath().toString();
	}

	@Override
	public <G> G getClientObject() {
		return (G) this;
	}

	@Override
	public InputStream inputStream() throws IOException {
		return new FileInputStream(this.file.toFile());
	}

	@Override
	public String contents() throws IOException {
		return Files.readString(this.file);
	}

	@Override
	public String relativePath() {
		return this.relativePath.toString();
	}

	@Override
	public URI uri() {
		return file.toUri();
	}
}
