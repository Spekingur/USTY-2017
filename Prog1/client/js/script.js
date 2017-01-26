class Shape {
	constructor(x, y, color) {
		this.x = x;
		this.y = y;
		this.endX = x;
		this.endY = y;
		this.color = color;
	}
	
	setEnd(x, y) {
		this.endX = x;
		this.endY = y;
	}
}

class Rectangle extends Shape {
	constructor(x, y, color) {
		super(x, y, color);
	}
	
	draw(context) {
		context.fillStyle = this.color;
		context.fillRect(this.x, this.y, this.endX, this.endY);
	}
	
}

var settings = {
	canvasObj: document.getElementById("myCanvas"),
	nextObject: "Rectangle",
	nextColor: "Black",
	isDrawing: false,
	currentShape: undefined,
	shapes: []
}

function onchange_tool(val)
{
	nextObject = val;
}

$("#myCanvas").on("mousedown", function(e) {
	
	settings.isDrawing = true;
	
	var shape = undefined;
	var context = settings.canvasObj.getContext("2d");
	

	x = e.pageX - this.offsetLeft;
	y = e.pageY - this.offsetTop;

	
	if(nextObject === "Rectangle") {
		shape = new Rectangle(x, y, settings.nextColor)
	}
	else if(nextObject === "Circle") {
		
	}
	else if(nextObject === "Pencil") {

	}
	
	settings.currentShape = shape;
	if(shape !== undefined){
		settings.shapes.push(shape);
	}
	
	shape.draw(context);
	
});

$("#myCanvas").on("mousemove", function(e) {
	
	if(settings.currentShape !== undefined) {
		// TODO: update the end position of the current shape
		settings.currentShape.setEnd(e.x - x, e.y - y/* TODO: Fix the position */);
	}

});

$("#myCanvas").on("mouseup", function(e) {
	
	settings.currentShape = undefined;
	settings.isDrawing = false;
	
});