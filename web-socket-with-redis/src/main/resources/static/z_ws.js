/**
 *
 * @returns {{sendMessage: _sendMessage}}
 * @constructor
 */
function Z_WS(wsEndpointUri) {

    var stompClient;

    function connectZws() {
        var socket = new SockJS(wsEndpointUri);
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log("stompClient connected.");
            stompClient.subscribe('/topic/messages', function (chatMessage) {
                var JSON_ = JSON.parse(chatMessage.body);
                renderPoint(JSON_["objectPointXY"]["x"], JSON_["objectPointXY"]["y"]);
                playAudio(JSON_["song"]);
                //console.log("song: " + JSON_["song"]);
            });
        }, function (message) {
            disconnect();
            console.log("stompClient unable to connect: " + message);
        });

        return stompClient;
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
            stompClient = null;
        }
        console.log("Disconnected");
    }

    function sendMessage(x, y) {
        var message = {
            x: x,
            y: y
        };
        if (stompClient !== null && stompClient.connected) {
            stompClient.send("/app/message", {}, JSON.stringify(message));
        } else {
            connectZws(function () {
                stompClient.send("/app/message", {}, message);
            });
        }
    }

    connectZws();
    return {
        sendMessage: sendMessage
    }

}

function playAudio(base64string) {
    var player = document.getElementById('audioPlayerId');
    var blob = base64toBlob(base64string, "wav");
    var blobUrl = URL.createObjectURL(blob);

    player.src = blobUrl;
    player.volume = 0.8;

    player.play();
}

function base64toBlob(base64Data, contentType) {
    contentType = contentType || '';
    var sliceSize = 1024;
    var byteCharacters = atob(base64Data);
    var bytesLength = byteCharacters.length;
    var slicesCount = Math.ceil(bytesLength / sliceSize);
    var byteArrays = new Array(slicesCount);

    for (var sliceIndex = 0; sliceIndex < slicesCount; ++sliceIndex) {
        var begin = sliceIndex * sliceSize;
        var end = Math.min(begin + sliceSize, bytesLength);

        var bytes = new Array(end - begin);
        for (var offset = begin, i = 0; offset < end; ++i, ++offset) {
            bytes[i] = byteCharacters[offset].charCodeAt(0);
        }
        byteArrays[sliceIndex] = new Uint8Array(bytes);
    }
    var returnBlob = new Blob(byteArrays, {type: contentType});
    return returnBlob;
}
