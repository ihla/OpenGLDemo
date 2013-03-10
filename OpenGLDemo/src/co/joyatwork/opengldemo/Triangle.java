package co.joyatwork.opengldemo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {
	private FloatBuffer vertexBuffer;	// buffer holding the vertices
	 
	private float vertices[] = {
			-0.5f, -0.5f,  0.0f,		// V1 - first vertex (x,y,z)
			 0.5f, -0.5f,  0.0f,		// V2 - second vertex
			 0.0f,  0.5f,  0.0f			// V3 - third vertex
	};
 
	public Triangle() {
		// a float has 4 bytes so we allocate for each coordinate 4 bytes
		ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		vertexByteBuffer.order(ByteOrder.nativeOrder());
 
		// allocates the memory from the byte buffer
		vertexBuffer = vertexByteBuffer.asFloatBuffer();
 
		// fill the vertexBuffer with the vertices
		vertexBuffer.put(vertices);
 
		// set the cursor position to the beginning of the buffer
		vertexBuffer.position(0);
	}
}
