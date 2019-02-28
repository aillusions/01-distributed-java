/**
 *
 */

var zws = new Z_WS('/ws/drawing');

var SELECT_ON_MOVE = true;
var SELECTED_NUM = 0;
var FIELD_SIDE_SIZE_WIDTH = 1000;
var FIELD_SIDE_SIZE_HEIGHT = 500;
var FIELD_SQUARE = FIELD_SIDE_SIZE_WIDTH * FIELD_SIDE_SIZE_HEIGHT;
var FIELD_CONTAINER;
$(function () {
    FIELD_CONTAINER = $("#field_container");
    FIELD_CONTAINER.mousemove(function (evt) {

        // var x = evt.pageX;
        // var y = evt.pageY;

        //var x = evt.offsetX;
        //var y = evt.offsetY;

        var x = evt.pageX - FIELD_CONTAINER.offset().left;
        var y = evt.pageY - FIELD_CONTAINER.offset().top;

        console.log("x:" + Math.round(x) + " y:" + Math.round(y));

        if (!SELECT_ON_MOVE) {
            return;
        }

        if (y > FIELD_SIDE_SIZE_HEIGHT || x > FIELD_SIDE_SIZE_WIDTH) {
            return;
        }

        // FIELD_CONTAINER.append("<div class='selected' style='top:" + y + "px; left:" + x + "'></div>");
        zws.sendMessage(Math.round(x), Math.round(y));
        SELECTED_NUM++;
        //console.log("SELECTED_NUM: " + SELECTED_NUM + " of " + FIELD_SQUARE);
    });

    /*
    FIELD_CONTAINER.mouseup(function (event) {
        switch (event.which) {
            case 1:
                SELECT_ON_MOVE = !SELECT_ON_MOVE;
                console.log('Left Mouse button pressed, SELECT_ON_MOVE: ' + SELECT_ON_MOVE);
                break;
            case 2:
                console.log('Middle Mouse button pressed.');
                break;
            case 3:
                console.log('Right Mouse button pressed.');
                FIELD_CONTAINER.empty();
                SELECTED_NUM = 0;
                break;
            default:
                console.log('You have a strange Mouse!');
        }

        return false;
    });*/
});

function renderPoint(x, y) {
    FIELD_CONTAINER = $("#field_container");
    FIELD_CONTAINER.append("<div class='selected' style='top:" + y + "px; left:" + x + "'></div>");
}
