package comp3170.week2;
import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_TRIANGLES;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import comp3170.GLBuffers;
import comp3170.GLException;
import comp3170.Shader;

public class Week2 extends JFrame implements GLEventListener {

	private GLCanvas canvas;
	private Shader shader;
	
	final private File DIRECTORY = new File("src/comp3170/week2"); 
	final private String VERTEX_SHADER = "vertex.glsl";
	final private String FRAGMENT_SHADER = "fragment.glsl";
	
	private int width = 800;
	private int height = 800;
	private float[] vertices;
	private int vertexBuffer;
	private int[] indices;
	private int indexBuffer;
	
	public Week2() {
		super("Week 2 example");

		// set up a GL canvas
		GLProfile profile = GLProfile.get(GLProfile.GL4);		 
		GLCapabilities capabilities = new GLCapabilities(profile);
		this.canvas = new GLCanvas(capabilities);
		this.canvas.addGLEventListener(this);
		this.add(canvas);
		
		// set up the JFrame
		
		this.setSize(width,height);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});			
	}
	
	@Override
	public void init(GLAutoDrawable arg0) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		// set the background colour to white
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);		
		
		// Compile the shader
		try {
			File vertexShader = new File(DIRECTORY, VERTEX_SHADER);
			File fragementShader = new File(DIRECTORY, FRAGMENT_SHADER);
			this.shader = new Shader(vertexShader, fragementShader);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (GLException e) {
			e.printStackTrace();
			System.exit(1);
		}

		this.vertices = new float[] {
			-0.6f, -0.6f,	// P0
			-0.3f, -0.6f,	// P1
			-0.6f,  0.6f,	// P2
			-0.3f,  0.15f,	// P3
			 0.0f,  0.3f,	// P4
			 0.0f,  0.0f,	// P5
			 0.3f,  0.15f,	// P6
			 0.6f,  0.6f,	// P7
			 0.3f, -0.6f,	// P8
			 0.6f, -0.6f,	// P9		
		};
		
	    this.vertexBuffer = GLBuffers.createBuffer(vertices, GL4.GL_FLOAT_VEC2);

	    this.indices = new int[] {
	    	0, 1, 3,
	    	0, 3, 2,
	    	2, 3, 4,
	    	3, 5, 4,
	    	4, 5, 6,
	    	4, 6, 7,
	    	6, 8, 7,
	    	7, 8, 9,
		};
		    
	    this.indexBuffer = GLBuffers.createIndexBuffer(indices);

	}

	@Override
	public void display(GLAutoDrawable arg0) {
		GL4 gl = (GL4) GLContext.getCurrentGL();

        // clear the colour buffer
		gl.glClear(GL_COLOR_BUFFER_BIT);	
		
		// activate the shader
		this.shader.enable();
		
        // connect the vertex buffer to the a_position attribute		   
	    this.shader.setAttribute("a_position", vertexBuffer);

	    // write the colour value into the u_colour uniform 	    	    
	    this.shader.setUniform("u_colour1", new float[] {0.5f, 0.0f, 0.5f});	    
	    this.shader.setUniform("u_colour2", new float[] {1.0f, 0.0f, 0.0f});	    
	    this.shader.setUniform("u_screenSize", new float[] {width, height});	    
	    
	    gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, this.indexBuffer);
	    gl.glDrawElements(GL_TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
	    
	}

	@Override
	public void reshape(GLAutoDrawable d, int x, int y, int width, int height) {
		this.width = width;
		this.height = height;
		
	}
	
	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		new Week2();
	}

	
	
}
