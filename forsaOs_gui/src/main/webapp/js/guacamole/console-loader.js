function onConsoleLoad(data, isAttached, mainObj) {

    const keyboardJSON = {

        "language": "en_US",
        "type": "qwerty",
        "width": 22,

        "keys": {
        	"Back": 65288,
            "Tab": 65289,
            "Esc": 65307,
            "F1": 65470,
            "F2": 65471,
            "F3": 65472,
            "F4": 65473,
            "F5": 65474,
            "F6": 65475,
            "F7": 65476,
            "F8": 65477,
            "F9": 65478,
            "F10": 65479,
            "F11": 65480,
            "F12": 65481,
            "Del": 65535,
            "LCtrl": [{
                "title": "Ctrl",
                "modifier": "control",
                "keysym": 65507
            }],
            "LAlt": [{
                "title": "Alt",
                "modifier": "alt",
                "keysym": 65513
            }]
        },

        "layout": [
            {
                "main": {
                    "alpha": [
                    	["LCtrl", "LAlt"],
                        ["Esc", "Tab", "Del", "Back"]
                        
                    ]
                }
            },
            ["F1", "F2", "F3", "F4"],
            ["F5", "F6", "F7", "F8"], 
            ["F9", "F10", "F11", "F12"]
        ],

        "keyWidths": {
            "LCtrl": 2.1,
            "LAlt": 2.1
        }
    }

    if (!isAttached) 
    {
	    mainObj.keyboard.onkeyup = null;
	    mainObj.keyboard.onkeydown = null;
		mainObj.osKeyboard.onkeyup = null;
		mainObj.osKeyboard.onkeydown = null;
        mainObj.guac.disconnect();
    }
    else 
    {
	    var mainObj = {
	    	"guac": null,
	    	"keyboard": null,
	    	"osKeyboard": null
	    }
    
        // Get display div from document
        var display = document.getElementById("console-panel-id");

        // Instantiate client, using an HTTP tunnel for communications.
        var guac = new Guacamole.Client(
            new Guacamole.HTTPTunnel("tunnel")
        );

        // Add client to display div
        display.appendChild(guac.getDisplay().getElement());

        // Error handler
        guac.onerror = function (error) {
            // alert("Guac"+error);
        };

        // Connect
        guac.connect(data);

        // Disconnect on close
        window.onunload = function () {
            guac.disconnect();
        }

        // Mouse
        var mouse = new Guacamole.Mouse(guac.getDisplay().getElement());

        mouse.onmousedown =
            mouse.onmouseup =
            mouse.onmousemove = function (mouseState) {
                guac.sendMouseState(mouseState);
            };

        // Keyboard
        var keyboard = new Guacamole.Keyboard(document);

        keyboard.onkeydown = function (keysym) {
            console.log("keydown" + keysym);
            guac.sendKeyEvent(1, keysym);
        };

        keyboard.onkeyup = function (keysym) {
            console.log("keyup" + keysym);
            guac.sendKeyEvent(0, keysym);
        };

        var keyboardContainer = document.getElementById("console-panel-id-2");

        keyboardContainer.style.display = "block";

        var osKeyboard = new Guacamole.OnScreenKeyboard(keyboardJSON);
        keyboardContainer.appendChild(osKeyboard.getElement());
        osKeyboard.resize(1000);

		osKeyboard.onkeydown = function (keysym) {
            console.log("osKeyboard keydown" + keysym);
            guac.sendKeyEvent(1, keysym);
        };

        osKeyboard.onkeyup = function (keysym) {
            console.log("osKeyboard keyup" + keysym);
            guac.sendKeyEvent(0, keysym);
        };

		/*
        osKeyboard.setKeyPressedHandler(
            function (keysym) {
                alert("OS KeyPress");
                guac.sendKeyEvent(1, keysym);
            }
        );

        osKeyboard.setKeyReleasedHandler(
            function (keysym) {
                alert("OS Release");
                guac.sendKeyEvent(0, keysym);
            }
        ); */
        mainObj.guac = guac;
        mainObj.keyboard = keyboard;
        mainObj.osKeyboard = osKeyboard;

        return mainObj;
    }
}