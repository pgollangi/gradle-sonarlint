package name.pgollangi.gradle.sonarlint.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonarsource.sonarlint.core.serverapi.HttpClient;

public class ApacheHttpResponse implements HttpClient.Response {

	private static final Logger LOG = LoggerFactory.getLogger(ApacheHttpResponse.class);
	private static final String BODY_ERROR_MESSAGE = "Error reading body content";
	private String requestUrl;
	private ClassicHttpResponse response;

	public ApacheHttpResponse(String requestUrl, ClassicHttpResponse response) {
		this.requestUrl = requestUrl;
		this.response = response;
	}

	@Override
	public int code() {
		return response.getCode();
	}

	@Override
	public String bodyAsString() {
		try {
			return EntityUtils.toString(response.getEntity());
		} catch (IOException | ParseException e) {
			throw new IllegalStateException(BODY_ERROR_MESSAGE, e);
		}
	}

	@Override
	public InputStream bodyAsStream() {
		try {
			return response.getEntity().getContent();
		} catch (IOException e) {
			throw new IllegalStateException(BODY_ERROR_MESSAGE, e);
		}
	}

	@Override
	public void close() {
		try {
			response.close();
		} catch (IOException e) {
			LOG.error("Can't close response: ", e);
		}
	}

	@Override
	public String url() {
		return requestUrl;
	}
}
