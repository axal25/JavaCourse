package uri.url;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UriUrlTest {
    private static final String scheme = "aScheme";
    private static final String user = "aUser";
    private static final String password = "aPassword";
    private static final String host = "aHost";
    private static final String port = "8080";
    private static final String path = "/a/path";
    private static final String query = "a=query";
    private static final String fragment = "aFragment";

    @Nested
    public class UrlTest {
        private static final Random random = new Random();
        private static final List<String> protocols = List.of(
                "file",
                "ftp",
                "http",
                "https"
                // special case
                // , "mailto"
                // Java invalid
                // , "telnet",
                // "gopher",
                // "news",
                // "nntp",
                // "wais",
                // "prospero"
        );
        private static final String protocol = protocols.get(
                random.nextInt(
                        protocols.size()));
        private static final String scheme = protocol;
        private static final String fullCosntructorString = scheme + "://"
                + user + ":" + password
                + "@" + host
                + ":" + port
                + path
                + "?" + query
                + "#" + fragment;

        @Test
        void singleStringConstructor_canThrowURISyntaxException() {
            AtomicReference<URL> url = new AtomicReference<>();
            assertDoesNotThrow(() -> url.set(new URL(fullCosntructorString)));

            assertThat(url.get().getProtocol()).isEqualTo(scheme);
            if ("mailto".equals(scheme)) {
                assertThat(url.get().getUserInfo()).isNull();
                assertThat(url.get().getHost()).isEmpty();
                assertThat(url.get().getPort()).isEqualTo(-1);
                assertThat(url.get().getAuthority()).isNull();
                assertThat(url.get().getPath()).isEqualTo("//" + user + ":" + password + "@" + host + ":" + port + path);
            } else {
                assertThat(url.get().getUserInfo()).isEqualTo(user + ":" + password);
                assertThat(url.get().getHost()).isEqualTo(host);
                assertThat(url.get().getPort()).isEqualTo(Integer.parseInt(port));
                assertThat(url.get().getAuthority()).isEqualTo(user + ":" + password + "@" + host + ":" + port);
                assertThat(url.get().getPath()).isEqualTo(path);
            }
            assertThat(url.get().getQuery()).isEqualTo(query);
            // URI uri;
            // assertThat(uri.getFragment()).isEqualTo(fragment);
        }

        @Test
        void URLtoURI_URItoURL() {
            assertThat(scheme).isNotEqualTo("mailto");
            AtomicReference<URL> url = new AtomicReference<>();
            assertDoesNotThrow(() -> url.set(new URL(fullCosntructorString)));
            AtomicReference<URI> uri = new AtomicReference<>();
            assertDoesNotThrow(() -> uri.set(new URI(fullCosntructorString)));
            AtomicReference<URI> urlToUri = new AtomicReference<>();
            assertDoesNotThrow(() -> urlToUri.set(url.get().toURI()));
            AtomicReference<URL> uriToUrl = new AtomicReference<>();
            assertDoesNotThrow(() -> uriToUrl.set(uri.get().toURL()));

            assertThat(url.get().toString()).isEqualTo(uri.get().toString());
            assertThat(urlToUri.get().toString()).isEqualTo(uriToUrl.get().toString());
            assertThat(url.get()).isEqualTo(uriToUrl.get());
            assertThat(uri.get()).isEqualTo(urlToUri.get());
        }

        @Test
        void openStream_jdkPre8() {
            AtomicReference<URL> url = new AtomicReference<>();
            assertDoesNotThrow(() -> url.set(new URL("https://www.google.com")));
            AtomicReference<InputStream> inputStream = new AtomicReference<>();

            assertDoesNotThrow(() -> inputStream.set(url.get().openStream()));

            // pre JDK 8
            StringBuilder sb = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream.get(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            AtomicInteger charCode = new AtomicInteger(0);
            assertDoesNotThrow(() ->
                    charCode.set(bufferedReader.read()));
            while (charCode.get() != -1) {
                sb.append((char) charCode.get());
                assertDoesNotThrow(() ->
                        charCode.set(bufferedReader.read()));
            }
            assertDoesNotThrow(bufferedReader::close);
            assertDoesNotThrow(inputStreamReader::close);
            String content = sb.toString();

            assertThat(content.toLowerCase(Locale.ROOT)).contains("<!doctype html>");
        }

        @Test
        void openStream_jdk8() {
            AtomicReference<URL> url = new AtomicReference<>();
            assertDoesNotThrow(() -> url.set(new URL("https://www.google.com")));
            AtomicReference<InputStream> inputStream = new AtomicReference<>();

            assertDoesNotThrow(() -> inputStream.set(url.get().openStream()));

            // JDK 8
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream.get(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String content = bufferedReader.lines().collect(Collectors.joining("\r\n"));
            assertDoesNotThrow(bufferedReader::close);
            assertDoesNotThrow(inputStreamReader::close);

            assertThat(content.toLowerCase(Locale.ROOT)).contains("<!doctype html>");
        }

        @Test
        void openStream_jdk9() {
            AtomicReference<URL> url = new AtomicReference<>();
            assertDoesNotThrow(() -> url.set(new URL("https://www.google.com")));
            AtomicReference<InputStream> inputStream = new AtomicReference<>();

            assertDoesNotThrow(() -> inputStream.set(url.get().openStream()));

            // JDK 9
            byte[] allBytes;
            try {
                allBytes = inputStream.get().readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String content = new String(allBytes, StandardCharsets.UTF_8);

            assertThat(content.toLowerCase(Locale.ROOT)).contains("<!doctype html>");
        }

        @Test
        void openStream_scanner() {
            AtomicReference<URL> url = new AtomicReference<>();
            assertDoesNotThrow(() -> url.set(new URL("https://www.google.com")));
            AtomicReference<InputStream> inputStream = new AtomicReference<>();

            assertDoesNotThrow(() -> inputStream.set(url.get().openStream()));

            // Scanner
            Scanner scanner = new Scanner(inputStream.get(), StandardCharsets.UTF_8);
            String delimiterBoundaryInputBeginning = "\\A";
            String content = scanner.useDelimiter(delimiterBoundaryInputBeginning).next();
            scanner.close();

            assertThat(content.toLowerCase(Locale.ROOT)).contains("<!doctype html>");
        }

        @Test
        void openStream_byteArrayOutputStream() {
            AtomicReference<URL> url = new AtomicReference<>();
            assertDoesNotThrow(() -> url.set(new URL("https://www.google.com")));
            AtomicReference<InputStream> inputStream = new AtomicReference<>();

            assertDoesNotThrow(() -> inputStream.set(url.get().openStream()));

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int byteOffset = 0;
            AtomicInteger byteReadAmount = new AtomicInteger(0);
            byte[] bytesRead = new byte[1024];
            assertDoesNotThrow(() ->
                    byteReadAmount.set(inputStream.get().read(bytesRead, byteOffset, bytesRead.length)));
            while (byteReadAmount.get() != -1) {
                byteArrayOutputStream.write(bytesRead, byteOffset, byteReadAmount.get());
                assertDoesNotThrow(() ->
                        byteReadAmount.set(inputStream.get().read(bytesRead, byteOffset, bytesRead.length)));
            }
            assertDoesNotThrow(byteArrayOutputStream::flush);
            // byte[] allBytesRead = byteArrayOutputStream.toByteArray();
            // String content = new String(allBytesRead, StandardCharsets.UTF_8);
            String content = byteArrayOutputStream.toString(StandardCharsets.UTF_8);

            assertThat(content.toLowerCase(Locale.ROOT)).contains("<!doctype html>");
        }
    }

    @Nested
    public class UriTest {
        private static final String fullCosntructorString = scheme + "://"
                + user + ":" + password
                + "@" + host
                + ":" + port
                + path
                + "?" + query
                + "#" + fragment;

        @Test
        public void singleStringConstructor_canThrowURISyntaxException() {
            AtomicReference<URI> uri = new AtomicReference<>();
            assertDoesNotThrow(() -> uri.set(new URI(fullCosntructorString)));

            assertThat(uri.get().getScheme()).isEqualTo(scheme);
            assertThat(uri.get().getUserInfo()).isEqualTo(user + ":" + password);
            assertThat(uri.get().getHost()).isEqualTo(host);
            assertThat(uri.get().getPort()).isEqualTo(Integer.parseInt(port));
            assertThat(uri.get().getAuthority()).isEqualTo(user + ":" + password + "@" + host + ":" + port);
            assertThat(uri.get().getPath()).isEqualTo(path);
            assertThat(uri.get().getQuery()).isEqualTo(query);
            assertThat(uri.get().getFragment()).isEqualTo(fragment);
        }

        @Test
        void create_singleStringConstructor_canNotThrowURISyntaxException_canThrowIllegalArgumentException() {
            URI uri = URI.create(fullCosntructorString);

            assertThat(uri.getScheme()).isEqualTo(scheme);
            assertThat(uri.getUserInfo()).isEqualTo(user + ":" + password);
            assertThat(uri.getHost()).isEqualTo(host);
            assertThat(uri.getPort()).isEqualTo(Integer.parseInt(port));
            assertThat(uri.getAuthority()).isEqualTo(user + ":" + password + "@" + host + ":" + port);
            assertThat(uri.getPath()).isEqualTo(path);
            assertThat(uri.getQuery()).isEqualTo(query);
            assertThat(uri.getFragment()).isEqualTo(fragment);
        }

        @Test
        void uri_nonUrl_toUrl_throwsMalformedURLException() {
            assertThrows(MalformedURLException.class, () -> URI.create(fullCosntructorString).toURL());
        }

        @Test
        void allArgsConstructor_canThrowURISyntaxException() {
            AtomicReference<URI> uri = new AtomicReference<>();
            assertDoesNotThrow(() -> uri.set(
                    new URI(scheme,
                            user + ":" + password,
                            host,
                            Integer.parseInt(port),
                            path,
                            query,
                            fragment)));

            assertThat(uri.get().getScheme()).isEqualTo(scheme);
            assertThat(uri.get().getUserInfo()).isEqualTo(user + ":" + password);
            assertThat(uri.get().getHost()).isEqualTo(host);
            assertThat(uri.get().getPort()).isEqualTo(Integer.parseInt(port));
            assertThat(uri.get().getAuthority()).isEqualTo(user + ":" + password + "@" + host + ":" + port);
            assertThat(uri.get().getPath()).isEqualTo(path);
            assertThat(uri.get().getQuery()).isEqualTo(query);
            assertThat(uri.get().getFragment()).isEqualTo(fragment);
        }
    }
}
