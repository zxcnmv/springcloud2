package com.xangqun.springcloud.component.base.http;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import lombok.extern.slf4j.Slf4j;
import org.mozilla.universalchardet.UniversalDetector;

import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@Slf4j
public class OkHttpUtil {

	private static OkHttpClient httpClient = new OkHttpClient();


    protected static final int AVAILABLE_PROCESSOR = Runtime.getRuntime().availableProcessors();

	/**
	 * 自动编码
	 */
	public static final String AUTO_CHARSET = "AUTO_CHARSET";

	static {
		//initHttpClient();
		
		httpClient.setConnectTimeout( 5, TimeUnit.SECONDS );
		httpClient.setReadTimeout( 5, TimeUnit.SECONDS );
		httpClient.setWriteTimeout(5, TimeUnit.SECONDS );

		// 开启10个线程大小的连接池, 每个连接保持2分钟存活
		ConnectionPool connectionPool = new ConnectionPool( AVAILABLE_PROCESSOR*2, 2 * 60 * 1000 );
		httpClient.setConnectionPool( connectionPool );
	}
	
	private static void initHttpClient(){
		X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        
		httpClient
		.setSslSocketFactory(sslContext.getSocketFactory())
        .setHostnameVerifier(DO_NOT_VERIFY);
	}

	public static InputStream getInputStream( String url ) {
		try {
			long startTime = System.currentTimeMillis();
			Request request = new Builder().url( url ).get().addHeader( "Referrer", url )
					.addHeader( "User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko" ).build();
			Response response = httpClient.newCall( request ).execute();
			if( !response.isSuccessful() ) {
				log.error( "获取网络数据失败: " + response.message() + ", 请求:[" + request + "], 累计耗时:" + ( System.currentTimeMillis() - startTime ) + " ms" );
				throw new RuntimeException( "获取网络数据失败[" + url + "]" );
			}

			return response.body().byteStream();
		} catch( Exception e ) {
			throw new RuntimeException( "获取网络数据失败", e );
		}
	}

	public static String get( String url ) {
		return get( url, "UTF-8" );
	}

	public static String get( String url, String charset ) {
		return get( url, null, charset );
	}

	public static String get( String url, Map<String, Object> params, String charset ) {
		StringBuilder sb = new StringBuilder( url );

		if( params != null ) {
			sb.append( "?" );
			for( Entry<String, Object> entry : params.entrySet() ) {
				Object value = entry.getValue();
				sb.append( entry.getKey() ).append( "=" ).append( value == null ? "" : value.toString() ).append( "&" );
			}
		}

		Request request = new Builder().url( sb.toString() ).get().addHeader( "Referer", url )
				.addHeader( "User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko" ).build();

		return _call( request, charset );
	}

	public static String delete( String url, Map<String, Object> params, String charset ) {
		StringBuilder sb = new StringBuilder( url );
		if( params != null ) {
			sb.append( "?" );
			for( Entry<String, Object> entry : params.entrySet() ) {
				Object value = entry.getValue();
				sb.append( entry.getKey() ).append( "=" ).append( value == null ? "" : value.toString() ).append( "&" );
			}
		}

		Request request = new Builder().url( sb.toString() ).delete().addHeader( "Referer", url )
				.addHeader( "User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko" ).build();
		return _call( request, charset );
	}

	public static String post( String url ) {
		return post( url, "UTF-8" );
	}

	public static String post( String url, String charset ) {
		return post( url, null, charset );
	}

	public static String postJson(String url,String json,String charset){
		 MediaType JSONMediaType = MediaType.parse("application/json; charset=utf-8"); 
		RequestBody body = RequestBody.create(JSONMediaType, json);
		return post( url, body, charset, null );
	}
	
	public static String postXml(String url,String xml,String charset){
		 MediaType JSONMediaType = MediaType.parse("application/xml; charset=utf-8"); 
		RequestBody body = RequestBody.create(JSONMediaType, xml);
		return post( url, body, charset, null );
	}
	
	public static String postString(String url,String str,String charset){
		 MediaType StringMediaType = MediaType.parse("text/x-markdown; charset=utf-8"); 
		RequestBody body = RequestBody.create(StringMediaType, str);
		return post( url, body, charset, null );
	}
	
	public static String post( String url, Map<String, Object> params, String charset ) {
		// Map<String, Object> params,
		RequestBody body = newBody( params );
		return post( url, body, charset, null );
	}

	public static String post( String url, RequestBody body, String charset, Map<String, String> headers ) {
		if( headers == null ) {
			headers = new HashMap<String, String>();
		}
		headers.put( "Referer", url );
		headers.put( "User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko" );

		Builder requestBuilder = new Builder().url( url ).post( body );
		for( Entry<String, String> entry : headers.entrySet() ) {
			requestBuilder.addHeader( entry.getKey(), entry.getValue() );
		}

		return _call( requestBuilder.build(), charset );
	}

	public static RequestBody newBody( Map<String, Object> params ) {
		if( params == null ) {
			params = new HashMap<String, Object>();
			params.put( "time", System.currentTimeMillis() );
		}

		FormEncodingBuilder formBuilder = new FormEncodingBuilder();
		for( Entry<String, Object> entry : params.entrySet() ) {
			Object value = entry.getValue();
			formBuilder.add( entry.getKey(), value == null ? "" : value.toString() );
		}

		return formBuilder.build();
	}

	public static String put( String url, Map<String, Object> params, String charset ) {
		RequestBody body = newBody( params );
		Request request = new Builder().url( url ).put( body ).addHeader( "Referer", url )
				.addHeader( "User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko" ).build();

		return _call( request, charset );
	}

	static String _call( Request request, String charset ) {
		long startTime = System.currentTimeMillis();
		try {
			Response response = httpClient.newCall( request ).execute();

			if( !response.isSuccessful() ) {
				log.error( "获取网络数据失败: " + response.message() + ", 请求:[" + request + "], 累计耗时:" + ( System.currentTimeMillis() - startTime ) + " ms" );
				return "";
			}

			byte[] content = response.body().bytes();
			if( AUTO_CHARSET.equals( charset ) ) {
				if( content.length > 4096 ) {
					byte[] sources = new byte[ 4096 ];
					System.arraycopy( content, 0, sources, 0, 4096 );
					charset = getEncoding( sources );
				} else {
					charset = getEncoding( content );
				}
			}

			log.info( "请求:[" + request + "], 累计耗时:" + ( System.currentTimeMillis() - startTime ) + " ms" );
			return new String( content, charset );
		} catch( IOException e ) {
			log.error( "获取网络数据异常, 请求:[" + request + "], 累计耗时:" + ( System.currentTimeMillis() - startTime ) + " ms", e );
			return "";
		}
	}

	/**
	 * 自动识别输出流编码
	 */
	static String getEncoding( byte[] bytes ) {
		String encoding = "utf-8";
		try {
			UniversalDetector detector = new UniversalDetector( null );
			detector.handleData( bytes, 0, bytes.length );
			detector.dataEnd();

			encoding = detector.getDetectedCharset();
			if( encoding == null ) {
				encoding = "utf-8";
			}

			detector.reset();
		} catch( Exception e ) {
			log.warn( "识别流编码异常", e );
		}

		return encoding;
	}
}
