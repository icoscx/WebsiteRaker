/*
*/
//any browser variable can be omitted to be enumerated
//Works for IE, Chrome, firefox, htmlunit (except for navigator)
var obj = navigator;
for (var key in obj) {
	let value = obj[key];
	document.writeln("<p>" + obj + "." + key + " = " + value);
	if(typeof value === 'object'){
		for (var key2 in obj[key]) {
			let value2 = obj[key][key2];
			document.writeln("<p> + >" + obj[key] + "." + key2 + " = " + value2);
			if(typeof value2 === 'object'){
				for (var key3 in obj[key][key2]) {
					let value3 = obj[key][key2][key3];
					document.writeln("<p> ++ >" + obj[key][key2] + "." + key3 + " = " + value3);
					if(typeof value3 === 'object'){
						for (var key4 in obj[key][key2][key3]) {
						let value4 = obj[key][key2][key3][key4];
						document.writeln("<p> +++ >" + obj[key][key2][key3] + "." + key4 + " = " + value4);
						}
					}
				}
			}
		}
	}
}

document.write('<hr>htmlunit properties<hr>');
//for HTMLUnit navigator (its navigator does not follow the plugin
//and mimetype object convention, properties etc)
var obj = navigator;
for (var key in obj) {
	let value = obj[key];
	document.writeln("<p>" + obj + "." + key + " = " + value);
	if(typeof value === 'object'){
		for (var key2 in obj[key]) {
			let value2 = obj[key][key2];
			document.writeln("<p> + >" + obj[key] + "." + key2 + " = " + value2);
		}
	}
}
document.write('<hr>Plugins and mimetypes<hr>');
//Print plugins
document.writeln("<p>Plugins found: " + navigator.plugins.length +  "<p>");
for(var i=0; i<navigator.plugins.length; i++){
	document.writeln("<p>Name: " + navigator.plugins[i].name + '|' +
	"filename: " + navigator.plugins[i].filename + '|' +
	"description: " + navigator.plugins[i].description + '|' +
	"version: " + navigator.plugins[i].version);
	//print any supported mimetype for plugin
	for(var j=0; j<navigator.plugins[i].length; j++){
		document.writeln("<p>Mimetype for plugin - type: " + navigator.plugins[i][j].type + '|' +
		"suffixes: " + navigator.plugins[i][j].suffixes + '|' +
		"description: " + navigator.plugins[i][j].description + '|' +
		"enabledPlugin: " + navigator.plugins[i][j].enabledPlugin);
	}

}
