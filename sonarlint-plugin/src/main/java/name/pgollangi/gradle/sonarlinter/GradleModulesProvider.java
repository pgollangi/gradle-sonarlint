package name.pgollangi.gradle.sonarlinter;

import java.net.URI;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.sonarsource.sonarlint.core.client.api.common.ModuleInfo;
import org.sonarsource.sonarlint.core.client.api.common.ModulesProvider;

public class GradleModulesProvider implements ModulesProvider {

	private Path[] folders;

	public GradleModulesProvider(Path... folders) {
		this.folders = folders;
	}

	public static URI key(Path folder) {
		return folder.toUri();
	}

	@Override
	public List<ModuleInfo> getModules() {
		return Arrays.stream(folders).map(this::createModuleInfo).collect(Collectors.toList());
	}

	private ModuleInfo createModuleInfo(Path folder) {
		FolderFileSystem clientFileWalker = new FolderFileSystem(folder);
		return new ModuleInfo(key(folder), clientFileWalker);
	}

}
