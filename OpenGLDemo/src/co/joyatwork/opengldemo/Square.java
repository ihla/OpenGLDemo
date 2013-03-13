package co.joyatwork.opengldemo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Square {


	private FloatBuffer vertexBuffer;	// buffer holding the vertices

	//vertices are ordered clockwise what is the standard order in which OpenGL draws triangles
	private float vertices[] = {
			-1.0f, -1.0f,  0.0f,		// V1 - bottom left
			-1.0f,  1.0f,  0.0f,		// V2 - top left
			 1.0f, -1.0f,  0.0f,		// V3 - bottom right
			 1.0f,  1.0f,  0.0f			// V4 - top right
	};
	private static final int VERTEX_SIZE = 3;
	private static final int VERTEX_STRIDE = 0;
	
	private FloatBuffer textureBuffer;	// buffer holding the texture coordinates
	
	// coordinate array for the texture
	// texture is like a foil put on the window with given coordinates
	private float texture[] = {
			// Mapping coordinates for the vertices
			0.0f, 1.0f,		// top left		(V2)
			0.0f, 0.0f,		// bottom left	(V1)
			1.0f, 1.0f,		// top right	(V4)
			1.0f, 0.0f		// bottom right	(V3)
	};
	
	/** The texture pointer */
	private int[] textures = new int[1];

	public Square() {
		// a float has 4 bytes so we allocate for each coordinate 4 bytes
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		// allocates the memory from the byte buffer
		vertexBuffer = byteBuffer.asFloatBuffer();
		// fill the vertexBuffer with the vertices
		vertexBuffer.put(vertices);
		// set the cursor position to the beginning of the buffer
		vertexBuffer.position(0);

		byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
	}

	/** The draw method for the square with the GL context */
	public void draw(GL10 gl) {
		// bind the previously generated texture
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		// Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		// Set the face rotation
		gl.glFrontFace(GL10.GL_CW);

		// Point to our vertex buffer
		gl.glVertexPointer(VERTEX_SIZE, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

		// Draw the vertices as triangle strip
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / VERTEX_SIZE);

		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	/**
	 * This will load up the image from the disk and bind it to a texture in the OpenGL repository.
	 * <p>
	 *  It will basically assign an internal ID for the processed image 
	 *  and will be used by the OpenGL API to identify it among other textures.
	 * @param gl
	 * @param context
	 */
	public void loadGLTexture(GL10 gl, Context context) {
		// loading texture
		/** A note about this bitmap. 
		 * It is encouraged to be square. It helps a lot with scaling. 
		 * So make sure your bitmaps for textures are squares (6×6, 12×12, 128×128, etc.). 
		 * If not square, make sure the width and height are powers of 2 (2, 4, 8, 16, 32, …). 
		 * You can have a bitmap 128×512 and it is perfectly usable and it is optimised.
		 */
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.android);

		// generate one texture pointer
		// generates the names for the textures
		gl.glGenTextures(1, textures, 0);
		// ...and bind it to our array
		// a bound texture is the active texture
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		// create nearest filtered texture
		// We have just told OpenGL what types of filters to use when it needs to shrink or expand the texture 
		// to cover the square. 
		// We have chosen some basic algorithms on how to scale the image.
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		// Use Android GLUtils to specify a two-dimensional texture image from our bitmap
		// It creates the image (texture) internally in its native format based on our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

		// Clean up
		bitmap.recycle();
	}
}
