window.monui_sTable_YukSTable = function() {
	var table;
	var tblId;
	
	this.onStateChange = function() {
		try{
			if (typeof table == "undefined") {
				var dv = this.getElement();
				var tbl = document.createElement('table');
				tblId = this.getState().domId + '1'
				tbl.id = tblId;
				dv.appendChild(tbl);
				var columns = JSON.parse(this.getState().header);
				var sColumns = '';
				sColumns += "<thead>";
				sColumns += "<tr>";
				for (i = 0; i < columns.length; i++) {
					sColumns += "<th>" + columns[i] + "</th>";
				}
				sColumns += "</tr>";
				sColumns += "</thead>";
				tbl.innerHTML = sColumns;
				tblId = '#' + tblId;
				table = $(tblId).DataTable({
					paging : true,
					scrollX: true,
					autoWidth: false,
					scrollCollapse: true
				});
		}
		}
		catch(e){
			console.log(e);
		}
    };
    
 
    this.draw = function(dt,count){
    	try{
			var data = JSON.parse(dt);
			for (i = 0; i < count; i++) {
				var frow = tblId + ' tbody tr';
				table.row($(frow)[0]).remove();
			}
			for (i = 0; i < data.length; i++) {
				table.row.add(data[i]);
			}
			table.columns.adjust().draw();
		}
    	catch(e){
    		console.log(e);
    	}
    }
    
    this.clear = function(){
    	try{
    		table.clear().draw();
    	}
    	catch(e){
    		console.log(e);
    	}
    }
};