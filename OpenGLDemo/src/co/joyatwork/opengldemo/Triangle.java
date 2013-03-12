package co.joyatwork.opengldemo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

//The GL10 interface contains the Java(TM) programming language bindings for OpenGL(R) ES 1.0 core functionality:
import javax.microedition.khronos.opengles.GL10;

public class Triangle {
	private FloatBuffer vertexBuffer;	// buffer holding the vertices
	 
	private float vertices[] = {
			-0.5f, -0.5f,  0.0f,		// V1 - first vertex (x,y,z)
			 0.5f, -0.5f,  0.0f,		// V2 - second vertex
			 0.0f,  0.5f,  0.0f			// V3 - third vertex
	};
 
	/**
	 * Constructs triangle out of 3 points.
	 * Stores vertexes of points in java.nio.FloatBuffer for direct use by OpenGL.
	 */
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

	/**
	 * Draws triangle.
	 * @param gl
	 */
	public void draw(GL10 gl) {
		// we need to enable OpenGL to read from FloatBuffer and understand that is a triangle there
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		// set the colour for the triangle
		// the values of the rgb are floats and are between 0.0 and 1.0
		gl.glColor4f(0.0f, 1.0f, 0.0f, 0.5f);

		// Point to our vertex buffer
		// glVertexPointer(size, type, stride, pointer) will tell OpenGL to use the vertexBuffer
		//
		// size specifies the number of coordinates per vertex and type the data type of the coordinates
		// stride is the offset between values in array
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

		// Draw the vertices as triangle strip
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}
