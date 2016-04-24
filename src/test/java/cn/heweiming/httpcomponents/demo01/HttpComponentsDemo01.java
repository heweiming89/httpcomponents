package cn.heweiming.httpcomponents.demo01;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpComponentsDemo01 {

	@Test
	public void testGet() {

		HttpGet get = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			URI uri = new URIBuilder()
					.setScheme("http")
					.setHost("ws.webxml.com.cn")
					.setPath("/WebServices/MobileCodeWS.asmx/getMobileCodeInfo")
					.setParameter("mobileCode", "15800979640")
					.setParameter("userID", "").build();
			get = new HttpGet(uri);
			System.out.println(get.getURI().toString());
			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				long len = entity.getContentLength();
				if (len != -1 && len < 2048) {
					String string = EntityUtils.toString(entity);
					System.out.println(string);
				} else {
					byte[] data = new byte[2048];
					int read = -1;
					InputStream inStream = entity.getContent();
					StringBuilder sb = new StringBuilder();
					while ((read = inStream.read(data)) != -1) {
						sb.append(new String(data, 0, read));
					}
					System.out.println(sb.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (get != null) {
				get.releaseConnection();
			}
		}
	}

	@Test
	public void testPost() {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			URI uri = new URIBuilder()
					.setScheme("http")
					.setHost("ws.webxml.com.cn")
					.setPath("/WebServices/MobileCodeWS.asmx/getMobileCodeInfo")
					.build();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("mobileCode", "15800979640"));
			params.add(new BasicNameValuePair("userID", ""));
			HttpPost post = new HttpPost(uri);
			post.setEntity(new UrlEncodedFormEntity(params));
			System.out.println(post.getURI().toString());
			CloseableHttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String string = EntityUtils.toString(entity);
				System.out.println(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
