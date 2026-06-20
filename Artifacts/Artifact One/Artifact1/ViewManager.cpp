///////////////////////////////////////////////////////////////////////////////
// viewmanager.h
// ============
// manage the viewing of 3D objects within the viewport
//
//  AUTHOR: Brian Battersby - SNHU Instructor / Computer Science
//	Created for CS-330-Computational Graphics and Visualization, Nov. 1st, 2023
// 
//	Modified by Kameron Hinman-Mitchell - SNHU Student, Dec. 12th, 2025
// 
///////////////////////////////////////////////////////////////////////////////

#include "ViewManager.h"

// GLM Math Header inclusions
#include <glm/glm.hpp>
#include <glm/gtx/transform.hpp>
#include <glm/gtc/type_ptr.hpp>    

// declaration of the global variables and defines
namespace
{
	// Variables for window width and height
	const int WINDOW_WIDTH = 1000;
	const int WINDOW_HEIGHT = 800;
	const char* g_ViewName = "view";
	const char* g_ProjectionName = "projection";

	// camera object used for viewing and interacting with
	// the 3D scene
	Camera* g_pCamera = nullptr;

	// these variables are used for mouse movement processing
	float gLastX = WINDOW_WIDTH / 2.0f;
	float gLastY = WINDOW_HEIGHT / 2.0f;
	bool gFirstMouse = true;

	// time between current frame and last frame
	float gDeltaTime = 0.0f; 
	float gLastFrame = 0.0f;

	// the following variable is false when orthographic projection
	// is off and true when it is on
	bool bOrthographicProjection = false;

	//camera movement speed multiplier
	float gCameraSpeedMultiplier = 1.0f;
	//left/right looking sensitivity
	float gMouseSensitivityX = 10.0f;
	//up/down looking sensitivity
	float gMouseSensitivityY = 10.0f;
}

/***********************************************************
 *  ViewManager()
 *
 *  The constructor for the class
 ***********************************************************/
ViewManager::ViewManager(
	ShaderManager *pShaderManager)
{
	// initialize the member variables
	m_pShaderManager = pShaderManager;
	m_pWindow = NULL;
	g_pCamera = new Camera();
	// default camera view parameters
	g_pCamera->Position = glm::vec3(0.0f, 5.0f, 12.0f);
	g_pCamera->Front = glm::vec3(0.0f, -0.5f, -2.0f);
	g_pCamera->Up = glm::vec3(0.0f, 1.0f, 0.0f);
	g_pCamera->Zoom = 80;
}

/***********************************************************
 *  ~ViewManager()
 *
 *  The destructor for the class
 ***********************************************************/
ViewManager::~ViewManager()
{
	// free up allocated memory
	m_pShaderManager = NULL;
	m_pWindow = NULL;
	if (NULL != g_pCamera)
	{
		delete g_pCamera;
		g_pCamera = NULL;
	}
}

/***********************************************************
 *  CreateDisplayWindow()
 *
 *  This method is used to create the main display window.
 ***********************************************************/
GLFWwindow* ViewManager::CreateDisplayWindow(const char* windowTitle)
{
	GLFWwindow* window = nullptr;

	// try to create the displayed OpenGL window
	window = glfwCreateWindow(
		WINDOW_WIDTH,
		WINDOW_HEIGHT,
		windowTitle,
		NULL, NULL);
	if (window == NULL)
	{
		std::cout << "Failed to create GLFW window" << std::endl;
		glfwTerminate();
		return NULL;
	}
	glfwMakeContextCurrent(window);

	// tell GLFW to capture all mouse events
	glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

	// this callback is used to receive mouse moving events
	glfwSetCursorPosCallback(window, &ViewManager::Mouse_Position_Callback);

	// enable blending for supporting tranparent rendering
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

	m_pWindow = window;

	return(window);
}

/***********************************************************
 *  Mouse_Position_Callback()
 *
 *  This method is automatically called from GLFW whenever
 *  the mouse is moved within the active GLFW display window.
 ***********************************************************/
void ViewManager::Mouse_Position_Callback(GLFWwindow* window, double xMousePos, double yMousePos)
{
// when the first mouse move event is received, this needs to be recorded so that
// all subsequent mouse moves can correctly calculate the X position offset and Y
// position offset for proper operation
	if (gFirstMouse)
	{
		gLastX = xMousePos;
		gLastY = yMousePos;
		gFirstMouse = false;
		return;
	}

	// calculate the X offset and Y offset values for moving the 3D camera accordingly
	float xOffset = xMousePos - gLastX;
	float yOffset = gLastY - yMousePos; // reversed since y-coordinates go from bottom to top

	xOffset *= gMouseSensitivityX;
	yOffset *= gMouseSensitivityY;

	// set the current positions into the last position variables
	gLastX = xMousePos;
	gLastY = yMousePos;

	// move the 3D camera according to the calculated offsets
	if (g_pCamera != nullptr)
	{
		g_pCamera->ProcessMouseMovement(xOffset, yOffset);
	}
}
void ViewManager::Mouse_Scroll_Wheel_Callback(GLFWwindow* window, double xOffset, double yOffset)
{
	// Process mouse scroll for movement speed adjustment
	// Based on LearnOpenGL camera implementation

	// Adjust the camera movement speed multiplier based on scroll input
	// Scroll up increases speed, scroll down decreases speed
	gCameraSpeedMultiplier += (float)yOffset * 0.1f;

	// Cap the speed multiplier to limits of 5.0x normal speed
	if (gCameraSpeedMultiplier < 0.1f)
		gCameraSpeedMultiplier = 0.1f;
	if (gCameraSpeedMultiplier > 5.0f)
		gCameraSpeedMultiplier = 5.0f;

	// Apply the speed multiplier to the camera's movement speed
	// This assumes the camera has a default speed that we're modifying
	if (g_pCamera != nullptr)
	{
		// Store the modified speed in the camera
		// If the camera class doesn't have this, we'll handle it differently
		g_pCamera->MovementSpeed = 2.5f * gCameraSpeedMultiplier;
	}
}

/***********************************************************
 *  ProcessKeyboardEvents()
 *
 *  This method is called to process any keyboard events
 *  that may be waiting in the event queue.
 ***********************************************************/
void ViewManager::ProcessKeyboardEvents()
{
	// close the window if the escape key has been pressed
	if (glfwGetKey(m_pWindow, GLFW_KEY_ESCAPE) == GLFW_PRESS)
	{
		glfwSetWindowShouldClose(m_pWindow, true);
	}

	// if the camera object is null, then exit this method
	if (NULL == g_pCamera)
	{
		return;
	}

	//Process camera zooming in and out (W/S keys)
	if (glfwGetKey(m_pWindow, GLFW_KEY_W) == GLFW_PRESS)
	{
		g_pCamera->ProcessKeyboard(FORWARD, gDeltaTime);
	}
	if (glfwGetKey(m_pWindow, GLFW_KEY_S) == GLFW_PRESS)
	{
		g_pCamera->ProcessKeyboard(BACKWARD, gDeltaTime);
	}

	//Process camera panning left and right (A/D keys)
	if (glfwGetKey(m_pWindow, GLFW_KEY_A) == GLFW_PRESS)
	{
		g_pCamera->ProcessKeyboard(LEFT, gDeltaTime);
	}
	if (glfwGetKey(m_pWindow, GLFW_KEY_D) == GLFW_PRESS)
	{
		g_pCamera->ProcessKeyboard(RIGHT, gDeltaTime);
	}

	//Add Q/E keys for upward and downward movement
	if (glfwGetKey(m_pWindow, GLFW_KEY_Q) == GLFW_PRESS)
	{
		// Move the camera upward
		g_pCamera->ProcessKeyboard(UP, gDeltaTime);
	}
	if (glfwGetKey(m_pWindow, GLFW_KEY_E) == GLFW_PRESS)
	{
		// Move the camera downward
		g_pCamera->ProcessKeyboard(DOWN, gDeltaTime);
	}

	// Switch between perspective and orthographic views
	// Press P for perspective view (3D), O for orthographic view (2D)
	// Uses static variables outside the if statements for proper key repeat handling
	static bool pKeyPressed = false;
	static bool oKeyPressed = false;

	if (glfwGetKey(m_pWindow, GLFW_KEY_P) == GLFW_PRESS && !pKeyPressed)
	{
		bOrthographicProjection = false; // Switch to perspective view
		// Adjust camera for perspective view
		g_pCamera->Position = glm::vec3(0.0f, 5.0f, 12.0f);
		g_pCamera->Front = glm::vec3(0.0f, -0.5f, -2.0f);
		g_pCamera->Up = glm::vec3(0.0f, 2.0f, 0.0f);
		pKeyPressed = true;
	}
	else if (glfwGetKey(m_pWindow, GLFW_KEY_P) == GLFW_RELEASE)
	{
		pKeyPressed = false;
	}

	if (glfwGetKey(m_pWindow, GLFW_KEY_O) == GLFW_PRESS && !oKeyPressed)
	{
		bOrthographicProjection = true; // Switch to orthographic view
		// Adjust camera to look directly at objects for orthographic view
		g_pCamera->Position = glm::vec3(0.0f, 18.0f, 0.1f);
		g_pCamera->Front = glm::vec3(0.0f, -2.0f, 0.0f);
		g_pCamera->Up = glm::vec3(0.0f, 0.0f, 3.0f);
		oKeyPressed = true;
	}
	else if (glfwGetKey(m_pWindow, GLFW_KEY_O) == GLFW_RELEASE)
	{
		oKeyPressed = false;
	}


}

/***********************************************************
 *  PrepareSceneView()
 *
 *  This method is used for preparing the 3D scene by loading
 *  the shapes, textures in memory to support the 3D scene 
 *  rendering
 ***********************************************************/
void ViewManager::PrepareSceneView()
{
	glm::mat4 view;
	glm::mat4 projection;

	// per-frame timing
	float currentFrame = glfwGetTime();
	gDeltaTime = currentFrame - gLastFrame;
	gLastFrame = currentFrame;

	// process any keyboard events that may be waiting in the 
	// event queue
	ProcessKeyboardEvents();

	// get the current view matrix from the camera
	view = g_pCamera->GetViewMatrix();

	// define the current projection matrix
	projection = glm::perspective(glm::radians(g_pCamera->Zoom), (GLfloat)WINDOW_WIDTH / (GLfloat)WINDOW_HEIGHT, 0.1f, 100.0f);

	// if the shader manager object is valid
	if (NULL != m_pShaderManager)
	{
		// set the view matrix into the shader for proper rendering
		m_pShaderManager->setMat4Value(g_ViewName, view);
		// set the view matrix into the shader for proper rendering
		m_pShaderManager->setMat4Value(g_ProjectionName, projection);
		// set the view position of the camera into the shader for proper rendering
		m_pShaderManager->setVec3Value("viewPosition", g_pCamera->Position);
	}
	// STUDENTS CAN MODIFY - Switch between perspective and orthographic projection
	if (bOrthographicProjection)
	{
		// Orthographic projection (2D view) - objects maintain size regardless of distance
		// Camera looks directly at scene, bottom plane should not be visible
		float orthoSize = 10.0f; // Adjust this to control view area size
		projection = glm::ortho(
			-orthoSize, orthoSize,           // left, right
			-orthoSize, orthoSize,           // bottom, top  
			-100.0f, 100.0f                  // near, far planes
		);
	}
	else
	{
		// Perspective projection (3D view) - normal 3D perspective with depth
		projection = glm::perspective(
			glm::radians(g_pCamera->Zoom),   // field of view
			(GLfloat)WINDOW_WIDTH / (GLfloat)WINDOW_HEIGHT, // aspect ratio
			0.1f, 100.0f                    // near, far planes
		);
	}
}