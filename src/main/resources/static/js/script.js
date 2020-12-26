'use strict';
var url= 'http://localhost:8080';
var channelUuid;
var senderId =1;
var receiverId =2
var welcomeForm = document.querySelector('#welcomeForm');
var dialogueForm = document.querySelector('#dialogueForm');
welcomeForm.addEventListener('submit', connect, true)
dialogueForm.addEventListener('submit', sendMessage, true)

var stompClient = null;
var name = null;

function connect(event) {
	name = document.querySelector('#name').value.trim();

	if(name == 'user2'){
          senderId =2;
          receiverId =1;
      }
	var request = JSON.stringify({
                          senderId:senderId ,
                          receiverId:receiverId });

    $.ajax({
                type: 'post',
                url: url +'/api/private-chat/channel',
                data: request,
                async: false,
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                  channelUuid = data.channelUuid;
                },
                error : function(data){
                        console.log(data);
                }
            });

	if (channelUuid) {
		document.querySelector('#welcome-page').classList.add('hidden');
		document.querySelector('#dialogue-page').classList.remove('hidden');
        var channelName = '/queue/private.chat/'+channelUuid;
		var socket = new SockJS('/secured/room');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
        		console.log("connected: " + frame);
        		stompClient.subscribe(channelName, function(response) {
        			var message = JSON.parse(response.body);
        			onMessageReceived(message);
        		});
        		stompClient.send(channelName, {}, JSON.stringify({
                            		senderId:senderId ,
                                    receiverId:receiverId,
                                    senderName : name,
                                    receiverName : "user2",
                            		type : 'newUser'
                            	}))
        	});

	}
	event.preventDefault();
}

function sendMessage(event) {
	var messageContent = document.querySelector('#chatMessage').value.trim();
    var channelName ='/app/private.chat/'+channelUuid;
	if (messageContent && stompClient) {
		var chatMessage = {
			senderId:senderId ,
            receiverId:receiverId,
            senderName : name,
            receiverName : "user2",
			message : messageContent,
			type : 'CHAT'
		};
		stompClient.send(channelName, {}, JSON
				.stringify(chatMessage));
		document.querySelector('#chatMessage').value = '';
		//onMessageReceived(chatMessage);
	}
	event.preventDefault();
}

function onMessageReceived(message) {

	var messageElement = document.createElement('li');

	if (message.type === 'newUser') {
		messageElement.classList.add('event-data');
		message.message = message.senderName + ' has joined the chat';
	} else if (message.type === 'Leave') {
		messageElement.classList.add('event-data');
		message.message = message.senderName + 'has left the chat';
	} else {
		messageElement.classList.add('message-data');

		var element = document.createElement('i');
		var text = document.createTextNode(message.senderName[0]);
		element.appendChild(text);

		messageElement.appendChild(element);

		var usernameElement = document.createElement('span');
		var usernameText = document.createTextNode(message.senderName);
		usernameElement.appendChild(usernameText);
		messageElement.appendChild(usernameElement);
	}

	var textElement = document.createElement('p');
	var messageText = document.createTextNode(message.message);
	textElement.appendChild(messageText);

	messageElement.appendChild(textElement);

	document.querySelector('#messageList').appendChild(messageElement);
	document.querySelector('#messageList').scrollTop = document
			.querySelector('#messageList').scrollHeight;

}
