var updateNewFileTree;
function isoBrowser(env, destroyFl, browser) {
	env = JSON.parse(env);
	function get(path) {
		var current = env;
		browser.walk(path, function (file) {
			if (file.match(/[A-Z]:/)) {
				file += '\\';
			}
			current = current[file];
		});
		return current;
	}
	function process(src, dest, remove) {
		console.log('process ' + src + ' => ' + dest);
		var file = env;
		var name;
		browser.walk(src, function (part, last) {
			var src = file[part];
			if (last) {
				if (remove) {
					delete file[part];
				}
			}
			file = src;
		});
		var current = env;
		browser.walk(dest, function (part, last) {
			if (!last) {
				current = current[part];
			} else {
				name = part;
			}
		});
		current[name] = file;
		var defer = $.Deferred();
		// one second delay promise that simulate ajax upload
		setTimeout(function () {
			defer.resolve();
		}, 1000);
		return defer.promise();
	}
	if (destroyFl) {
		browser.destroy();
		return browser;
	} else {
		browser = $('.iso-browser').browse({
			root: '',
			separator: '\\',
			name: 'filestystem',
			rename: function (src, dest) {
				return process(src, dest, true);
			},
			refresh_timer: 0,
			copy: process,
			contextmenu: false,
			dir: function (path) {
				resultIsoFb = undefined;
				if(path != "") {
					var dfd = $.Deferred();
					window.getNewFileTree(path); 
					updateNewFileTree = function (dirNewRaw) {
						dirNew = JSON.parse(dirNewRaw);
						if ($.isPlainObject(dirNew)) {
							resultIsoFb = { files: [], dirs: [] };
							Object.keys(dirNew).forEach(function (key) {
								if (typeof dirNew[key] == 'string') {
									resultIsoFb.files.push(key);
								} else if ($.isPlainObject(dirNew[key])) {
									resultIsoFb.dirs.push(key);
								}
							});
							$.when(resultIsoFb).then(function(){
								dfd.resolve(resultIsoFb);
							});
						}
					}
					// return $.when(resultIsoFb);
					return dfd.promise();
				} else {
					dir = get(path);
					if ($.isPlainObject(dir)) {
						resultIsoFb = { files: [], dirs: [] };
						Object.keys(dir).forEach(function (key) {
							if (typeof dir[key] == 'string') {
								resultIsoFb.files.push(key);
							} else if ($.isPlainObject(dir[key])) {
								resultIsoFb.dirs.push(key);
							}
						});
					}
					return $.when(resultIsoFb); // resolved promise
				}
			},
			open: function (filename) {
				// var file = get(filename);
				// if (typeof file == 'string') {
					window.setISOPath(filename);
				//}
			},
			on_change: function () {
				$('#path').val(this.path());
			},
			item_class: function (path, name) {
				return name.match(/[A-Z]:/) ? 'drive' : '';
			}
		});
		// var browser = $('.iso-browser').eq(0).browse();
		return browser;
	}
}