window.monui_chartjs_ChartJs = function() {
    var chart;
    var shift;
	
	this.onStateChange = function() {
    	try {
    		var state = this.getState();
    		shift = state.shift;
    		if(typeof chart == "undefined"){
        		var ty = state.type;
        		var config = JSON.parse(state.config);
        		var dt = JSON.parse(state.data);
    			var dv = this.getElement();
    			var canvas = document.createElement('canvas');
    			dv.appendChild(canvas);
    			chart = new Chart(canvas, {type : ty,data : dt,options: config});
    		}    		
		} catch (e) {
			console.log("make");
			console.log(chart);
			console.log(e);
		}	
    };
    
    this.update = function() {
    	try {
			chart.data.datasets.forEach(function(dataset) {
				if (dataset.data.length > shift){
					dataset.data.shift();
				}
			});
			chart.update();
		} catch (e) {
			console.log("update");
			console.log(chart);
			console.log(e);
		}
	};
	
	this.clear = function(){
		try {
			chart.data.labels = [];
			chart.data.datasets = [];
			chart.update();
		} catch (e) {
			console.log("clear");
			console.log(chart);
			console.log(e);
		}
	}
		
	this.redraw = function(){
		try {
			chart.render();
		} catch (e) {
			console.log("redraw");
			console.log(chart);
			console.log(e);
		}
	}
	
	this.addPoint = function(index,row){
		try {
			var data = JSON.parse(row);
			chart.data.datasets[index].data.push(data);
		} catch (e) {
			console.log("add");
			console.log(chart);
			console.log(chart.data.datasets[index]);
			console.log(chart.data.datasets[index].data);
			console.log(e);
		}		
	}
	
	this.makeDataSet = function(index, row){
		try {
			var data = JSON.parse(row);
			chart.data.datasets[index] = data;
		} catch (e) {
			console.log("dataSet");
			console.log(chart);
			console.log(e);
		}		
	}	
	
	this.makeLabels = function(row){
		try {
			chart.data.labels.push(row);
		} catch (e) {
			console.log("dataSet");
			console.log(chart);
			console.log(e);
		}
	}
	
	this.setShift = function(value){
		try {
			shift = value;
		} catch (e) {
			console.log("shift");
			console.log(chart);
			console.log(e);
		}		
	}
};