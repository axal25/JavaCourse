package input.read.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author dfa1.github.io
 * @link https://stackoverflow.com/questions/924990/how-to-cache-inputstream-for-multiple-use
 */
public class DecoratorInputStream extends InputStream {

    /**
     * Customized (modified) implementation
     */

    private final InputStream decorated;

    /**
     * Overtly stating this constructor should not be used
     */
    private DecoratorInputStream() {
        // super();
        decorated = null;
    }

    public DecoratorInputStream(InputStream decorated) {
        if (!decorated.markSupported()) {
            throw new IllegalArgumentException("marking not supported");
        }

        decorated.mark(1 << 24); // magic constant: BEWARE

        this.decorated = decorated;
    }

    @Override
    public void close() throws IOException {
        // decorated.reset();
    }

    public InputStream getDecorated() {
        return decorated;
    }

    /**
     * Default InputStream inputStream implementation
     */

    @Override
    public int read() throws IOException {
        return decorated.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return decorated.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return decorated.read(b, off, len);
    }

    @Override
    public byte[] readAllBytes() throws IOException {
        return decorated.readAllBytes();
    }

    @Override
    public byte[] readNBytes(int len) throws IOException {
        return decorated.readNBytes(len);
    }

    @Override
    public int readNBytes(byte[] b, int off, int len) throws IOException {
        return decorated.readNBytes(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return decorated.skip(n);
    }

    @Override
    public int available() throws IOException {
        return decorated.available();
    }

    @Override
    public synchronized void mark(int readlimit) {
        decorated.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        decorated.reset();
    }

    @Override
    public boolean markSupported() {
        return decorated.markSupported();
    }

    @Override
    public long transferTo(OutputStream out) throws IOException {
        return decorated.transferTo(out);
    }
}
