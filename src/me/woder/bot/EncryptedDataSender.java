package me.woder.bot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;

public class EncryptedDataSender {
	public DataOutputStream out;
	public Encrypt en;
	
	public EncryptedDataSender(DataOutputStream out, Encrypt en){
	     this.out = out;
	     this.en = en;
	}
	
	// Returns an output stream for a ByteBuffer.
	// The write() methods use the relative ByteBuffer put() methods.
	public static OutputStream newOutputStream(final ByteBuffer buf) {
	    return new OutputStream() {
	        public synchronized void write(int b) throws IOException {
	            buf.put((byte)b);
	        }

	        public synchronized void write(byte[] bytes, int off, int len) throws IOException {
	            buf.put(bytes, off, len);
	        }
	    };
	}

	// Returns an input stream for a ByteBuffer.
	// The read() methods use the relative ByteBuffer get() methods.
	public InputStream newInputStream(final ByteBuffer buf) {
	    return new InputStream() {
	        public synchronized int read() throws IOException {
	            if (!buf.hasRemaining()) {
	                return -1;
	            }
	            return buf.get();
	        }

	        public synchronized int read(byte[] bytes, int off, int len) throws IOException {
	            // Read only what's left
	            len = Math.min(len, buf.remaining());
	            buf.get(bytes, off, len);
	            return len;
	        }
	    };
	}

}


