package name.pgollangi.gradle.sonarlint.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;

public final class SonarSourceFile implements Comparable<SonarSourceFile>, ClientInputFile {
	@NotNull
	private final String absolutePath;

	@NotNull
	private final File file;

	@NotNull
	private final String relativePath;

	private final boolean isTest;

	@NotNull
	private final Charset charset;

	@Nullable
	private final String language;

	public SonarSourceFile(@NotNull File file, @NotNull String relativePath, boolean isTest, @NotNull Charset charset,
			@Nullable String language) {
		this.file = file;
		this.relativePath = relativePath;
		this.isTest = isTest;
		this.charset = charset;
		this.language = language;
		this.absolutePath = this.file.getAbsolutePath();
	}

	@NotNull
	public final File getFile() {
		return this.file;
	}

	@NotNull
	public final String getRelativePath() {
		return this.relativePath;
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

	@NotNull
	public final String getAbsolutePath() {
		return this.absolutePath;
	}

	public int compareTo(@NotNull SonarSourceFile other) {
		return this.absolutePath.compareTo(other.absolutePath);
	}

	@NotNull
	public final File component1() {
		return this.file;
	}

	@NotNull
	public final String component2() {
		return this.relativePath;
	}

	public final boolean component3() {
		return this.isTest;
	}

	@NotNull
	public final Charset component4() {
		return this.charset;
	}

	@Nullable
	public final String component5() {
		return this.language;
	}

	@NotNull
	public final SonarSourceFile copy(@NotNull File file, @NotNull String relativePath, boolean isTest,
			@NotNull Charset charset, @Nullable String language) {
		return new SonarSourceFile(file, relativePath, isTest, charset, language);
	}

	@NotNull
	public String toString() {
		return "SonarSourceFile(file=" + this.file + ", relativePath=" + this.relativePath + ", isTest=" + this.isTest
				+ ", charset=" + this.charset + ", language=" + this.language + ")";
	}

	public int hashCode() {
		if (this.isTest)
			;
		return (((((this.file != null) ? this.file.hashCode() : 0) * 31
				+ ((this.relativePath != null) ? this.relativePath.hashCode() : 0)) * 31 + 1) * 31
				+ ((this.charset != null) ? this.charset.hashCode() : 0)) * 31
				+ ((this.language != null) ? this.language.hashCode() : 0);
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
		return this.file.getAbsolutePath();
	}

	@Override
	public <G> G getClientObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream inputStream() throws IOException {
		return new FileInputStream(this.file);
	}

	@Override
	public String contents() throws IOException {
		return Files.readString(this.file.toPath());
	}

	@Override
	public String relativePath() {
		return this.file.getAbsolutePath();
	}

	@Override
	public URI uri() {
		return file.toURI();
	}
}
