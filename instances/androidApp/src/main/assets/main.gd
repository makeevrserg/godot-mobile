extends Node3D

# Reference to the gltf model that's currently being shown.
var current_gltf_node: Node3D = null

func _ready() -> void:
	# Default asset to load when the app starts
	#_load_gltf("res://gltfs/food_kit/turkey.glb")
	_set_ball_scale(1,0)
	_set_background(0, 0, 0, 0)
	var appPlugin := Engine.get_singleton("IntentGodotPlugin")
	if appPlugin:
		print("App plugin is available")

		# Signal fired from the app logic to update the gltf model being shown
		appPlugin.connect("show_gltf", _load_gltf)
		appPlugin.connect("set_background", _set_background)
		appPlugin.connect("set_ball_scale", _set_ball_scale)
		appPlugin.connect("set_ball_color", _set_ball_color)
		appPlugin.connect("set_ball_skew", _set_ball_skew)
	else:
		print("App plugin is not available")


func _process(_delta: float) -> void:
	if current_gltf_node == null:
		return
		
	# Make the gltf model slowly rotate
	current_gltf_node.rotate_y(0.001)


func _unhandled_input(event: InputEvent) -> void:
	if current_gltf_node == null:
		return
	
	if event is InputEventMagnifyGesture:
		# Scale the gltf model based on pinch / zoom gestures
		current_gltf_node.scale = current_gltf_node.scale * event.factor
		
	if event is InputEventMouseMotion and event.button_mask == MOUSE_BUTTON_MASK_LEFT:
		# Rotate the gltf model based on swipe gestures
		var relative_drag: Vector2 = event.relative
		current_gltf_node.rotate_y(relative_drag.x / 100)
		current_gltf_node.rotate_x(relative_drag.y / 100)

func _set_background(r: int,g: int,b: int,a: int) -> void:
	var world_env := $WorldEnvironment
	if world_env and world_env.environment:
		world_env.environment.background_mode = Environment.BG_COLOR
		world_env.environment.background_color = Color.from_rgba8(r,g,b,a,)
		
func _set_ball_scale(i: int, scale: float) -> void:
	var ball: Node2D = get_node("Node2D/Ball%d" % i)
	if ball:
		ball.scale = Vector2(scale, scale)


func _set_ball_color(i: int, r: int,g: int,b: int,a: int) -> void:
	var ball: Node2D = get_node("Node2D/Ball%d" % i)
	if ball:
		ball.modulate = Color.from_rgba8(r,g,b,a)
		
func _set_ball_skew(i: int, skew: float) -> void:
	var ball: Node2D = get_node("Node2D/Ball%d" % i)
	if ball:
		ball.skew = skew


	



# Load the gltf model specified by the given path
func _load_gltf(gltf_path: String) -> void:
	if current_gltf_node != null:
		remove_child(current_gltf_node)
	
	current_gltf_node = load(gltf_path).instantiate()

	add_child(current_gltf_node)
