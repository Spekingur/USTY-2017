class Shape {
	constructor(x, y, color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
}

class Rectangle extends Shape {
	constructor(x, y, color) {
		super(x, y, color);
	}
	
	draw(context) {
		context.fillStyle = this.color;
		context.fillRect(this.x, this.y, 40, 40);
	}
	
}


var settings = {
	canvasObj: document.getElementById("myCanvas"),
	nextObject: "Rectangle",
	nextColor: "Black",
	isDrawing: false
}

function onchange_tool()
{
	/*nextObject = document.getElementById("dtool")[0];*/
	var e = document.getElementById("dtool")[0];
	nextObject = e;
	/*alert("the value of the option here is "+e.value);*/
}

$("#myCanvas").on("mousedown", function(e) {
	
	settings.isDrawing = true;
	
	var shape = undefined;
	var context = settings.canvasObj.getContext("2d");
	

	var x = e.pageX - this.offsetLeft;
	var y = e.pageY - this.offsetTop;
	
	if(nextObject === "Rectangle") {
		shape = new Rectangle(x - 30, y - 30, settings.nextColor)
	}
	else if( nextObject === "Circle") {
		
	}
	
	shape.draw(context);
	
});

$("#myCanvas").on("mousemove", function(e) {
	
});