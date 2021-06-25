package name.pgollangi.gradle.sonarlinter;

import org.sonarsource.sonarlint.core.client.api.common.analysis.Issue;
import org.sonarsource.sonarlint.core.client.api.common.analysis.IssueListener;

public class GradleIssueListener implements IssueListener {

	public GradleIssueListener(GradleLogOutput logOutput) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(Issue issue) {
		if (issue.getType() == "BUG") {
			System.out.println(issue.getType());
			System.out.println(issue);
			System.out.println(issue.getMessage());
		}

	}

}
